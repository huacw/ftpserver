package com.sea.ftp.ftplet;

/**
 * 数据传输类型
 * 
 * @author sea
 * 
 */
public enum DataType {

	/**
	 * Binary data type
	 */
	BINARY,

	/**
	 * ASCII data type
	 */
	ASCII;

	/**
	 * Parses the argument value from the TYPE command into the type safe class
	 * 
	 * @param argument
	 *            The argument value from the TYPE command. Not case sensitive
	 * @return The appropriate data type
	 * @throws IllegalArgumentException
	 *             If the data type is unknown
	 */
	public static DataType parseArgument(char argument) {
		switch (argument) {
			case 'A':
			case 'a':
				return ASCII;
			case 'I':
			case 'i':
				return BINARY;
			default:
				throw new IllegalArgumentException("Unknown data type: " + argument);
		}
	}
}
