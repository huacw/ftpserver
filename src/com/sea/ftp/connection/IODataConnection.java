package com.sea.ftp.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.log4j.Logger;

import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.ftplet.DataType;
import com.sea.ftp.server.connection.DataConnection;
import com.sea.ftp.server.context.session.DefaultFTPServerSession;
import com.sea.ftp.util.IoUtils;

/**
 * 数据连接
 * 
 * @author sea
 *
 */
public class IODataConnection implements DataConnection {

	private final Logger logger = Logger.getLogger(getClass());

	private static final byte[] EOL = System.getProperty("line.separator").getBytes();

	// private final FtpIoSession session;

	private final Socket socket;

	private final ServerDataConnectionFactory factory;

	public IODataConnection(final Socket socket, // final FtpIoSession session,
			final ServerDataConnectionFactory factory) {
		// this.session = session;
		this.socket = socket;
		this.factory = factory;
	}

	/**
	 * Get data input stream. The return value will never be null.
	 */
	private InputStream getDataInputStream() throws FTPIOException {
		try {

			// get data socket
			Socket dataSoc = socket;
			if (dataSoc == null) {
				throw new IOException("Cannot open data connection.");
			}

			// create input stream
			InputStream is = dataSoc.getInputStream();
			if (factory.isZipMode()) {
				is = new InflaterInputStream(is);
			}
			return is;
		} catch (IOException ex) {
			factory.closeDataConnection();
			throw new FTPIOException(ex);
		}
	}

	/**
	 * Get data output stream. The return value will never be null.
	 */
	private OutputStream getDataOutputStream() throws FTPIOException {
		try {

			// get data socket
			Socket dataSoc = socket;
			if (dataSoc == null) {
				throw new FTPIOException("Cannot open data connection.");
			}

			// create output stream
			OutputStream os = dataSoc.getOutputStream();
			if (factory.isZipMode()) {
				os = new DeflaterOutputStream(os);
			}
			return os;
		} catch (IOException ex) {
			factory.closeDataConnection();
			throw new FTPIOException(ex);
		}
	}

	/**
	 * 上传文件
	 */
	@Override
	public final long transferFromClient(FTPServerSession session, final OutputStream out) throws FTPIOException {
		// TransferRateRequest transferRateRequest = new TransferRateRequest();
		// transferRateRequest = (TransferRateRequest)
		// session.getCurrentUser().authorize(transferRateRequest);
		int maxRate = 0;
		// if (transferRateRequest != null) {
		// maxRate = transferRateRequest.getMaxUploadRate();
		// }

		InputStream is = getDataInputStream();
		try {
			return transfer(session, false, is, out, maxRate);
		} finally {
			IoUtils.close(is);
		}
	}

	/**
	 * 下载文件
	 */
	@Override
	public final long transferToClient(FTPServerSession session, final InputStream in) throws FTPIOException {
		// TransferRateRequest transferRateRequest = new TransferRateRequest();
		// transferRateRequest = (TransferRateRequest)
		// session.getCurrentUser().authorize(transferRateRequest);
		int maxRate = 0;
		// if (transferRateRequest != null) {
		// maxRate = transferRateRequest.getMaxDownloadRate();
		// }

		OutputStream out = getDataOutputStream();
		try {
			return transfer(session, true, in, out, maxRate);
		} finally {
			IoUtils.close(out);
		}
	}

	@Override
	public final void transferToClient(FTPServerSession session, final String str) throws FTPIOException {
		OutputStream out = getDataOutputStream();
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(out, "UTF-8");
			writer.write(str);

			// update session
			if (session instanceof DefaultFTPServerSession) {
				// ((DefaultFTPServerSession)
				// session).increaseWrittenDataBytes(str.getBytes("UTF-8").length);
			}
		} catch (IOException e) {
			throw new FTPIOException(e);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
				} catch (IOException e) {
					logger.warn("", e);
				}
			}
			IoUtils.close(writer);
		}

	}

	private final long transfer(FTPServerSession session, boolean isWrite, final InputStream in, final OutputStream out,
			final int maxRate) throws FTPIOException {
		long transferredSize = 0L;

		boolean isAscii = session.getDataType() == DataType.ASCII;
		long startTime = System.currentTimeMillis();
		byte[] buff = new byte[4096];

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = IoUtils.getBufferedInputStream(in);

			bos = IoUtils.getBufferedOutputStream(out);

			DefaultFTPServerSession defaultFtpSession = null;
			if (session instanceof DefaultFTPServerSession) {
				defaultFtpSession = (DefaultFTPServerSession) session;
			}

			byte lastByte = 0;
			while (true) {

				// if current rate exceeds the max rate, sleep for 50ms
				// and again check the current transfer rate
				if (maxRate > 0) {

					// prevent "divide by zero" exception
					long interval = System.currentTimeMillis() - startTime;
					if (interval == 0) {
						interval = 1;
					}

					// check current rate
					long currRate = (transferredSize * 1000L) / interval;
					if (currRate > maxRate) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException ex) {
							break;
						}
						continue;
					}
				}

				// read data
				int count = bis.read(buff);

				if (count == -1) {
					break;
				}

				// update MINA session
				if (defaultFtpSession != null) {
					if (isWrite) {
						// defaultFtpSession.increaseWrittenDataBytes(count);
					} else {
						// defaultFtpSession.increaseReadDataBytes(count);
					}
				}

				// write data
				// if ascii, replace \n by \r\n
				if (isAscii) {
					for (int i = 0; i < count; ++i) {
						byte b = buff[i];
						if (isWrite) {
							if (b == '\n' && lastByte != '\r') {
								bos.write('\r');
							}

							bos.write(b);
						} else {
							if (b == '\n') {
								// for reads, we should always get \r\n
								// so what we do here is to ignore \n bytes
								// and on \r dump the system local line ending.
								// Some clients won't transform new lines into
								// \r\n so we make sure we don't delete new
								// lines
								if (lastByte != '\r') {
									bos.write(EOL);
								}
							} else if (b == '\r') {
								bos.write(EOL);
							} else {
								// not a line ending, just output
								bos.write(b);
							}
						}
						// store this byte so that we can compare it for line
						// endings
						lastByte = b;
					}
				} else {
					bos.write(buff, 0, count);
				}

				transferredSize += count;

				notifyObserver();
			}
		} catch (IOException | RuntimeException e) {
			logger.warn("Exception during data transfer, closing data connection socket", e);
			factory.closeDataConnection();
			throw new FTPIOException(e);
		} finally {
			if (bos != null) {
				try {
					bos.flush();
				} catch (IOException e) {
					logger.warn("", e);
				}
			}
		}

		return transferredSize;
	}

	/**
	 * Notify connection manager observer.
	 */
	protected void notifyObserver() {
		// session.updateLastAccessTime();

		// TODO this has been moved from AbstractConnection, do we need to keep
		// it?
		// serverContext.getConnectionManager().updateConnection(this);
	}
}
