/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sea.ftp.util;

import com.sea.ftp.exception.illegal.IllegalArgumentException;
import com.sea.ftp.exception.illegal.IllegalPortException;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * 解码或编码ip与端口
 *
 * @author sea
 * @Date 2018/8/17 16:08
 * @Version 1.0
 */
public class SocketAddressEncoder {

    private static int convertAndValidateNumber(String s) {
        int i = Integer.parseInt(s);
        if (i < 0) {
            throw new IllegalArgumentException("Token can not be less than 0");
        } else if (i > 255) {
            throw new IllegalArgumentException("Token can not be larger than 255");
        }

        return i;
    }

    /**
     * 解码IP和PORT
     *
     * @param str
     * @return
     * @throws UnknownHostException
     */
    public static InetSocketAddress decode(String str)
            throws UnknownHostException {
        StringTokenizer st = new StringTokenizer(str, ",");
        if (st.countTokens() != 6) {
            throw new IllegalArgumentException("Illegal amount of tokens");
        }

        StringBuilder sb = new StringBuilder();
        try {
            sb.append(convertAndValidateNumber(st.nextToken()));
            sb.append('.');
            sb.append(convertAndValidateNumber(st.nextToken()));
            sb.append('.');
            sb.append(convertAndValidateNumber(st.nextToken()));
            sb.append('.');
            sb.append(convertAndValidateNumber(st.nextToken()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        InetAddress dataAddress = InetAddress.getByName(sb.toString());

        // get data server port
        int dataPort = 0;
        try {
            int hi = convertAndValidateNumber(st.nextToken());
            int lo = convertAndValidateNumber(st.nextToken());
            dataPort = (hi << 8) | lo;
        } catch (IllegalArgumentException ex) {
            throw new IllegalPortException("Invalid data port: " + str);
        }

        return new InetSocketAddress(dataAddress, dataPort);
    }

    /**
     * 编码IP和PORT，符合FTP规范
     *
     * @param address
     * @return
     */
    public static String encode(InetSocketAddress address) {
        InetAddress serverAddress = address.getAddress();
        int serverPort = address.getPort();
        return serverAddress.getHostAddress().replace('.', ',') + ','
                + (serverPort >> 8) + ',' + (serverPort & 0xFF);
    }

}
