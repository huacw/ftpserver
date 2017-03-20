package com.sea.ftp.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号码验证器
 * 
 * @author huachengwu
 *
 */
public class IDCardValidator {
	private static String[] factors = new String[] { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
	private static int[] weights = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	public static void main(String[] args) {
		System.out.println(validate("411303198512185213"));
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean validate(String idCard) {
		// 基本验证
		String reg = "^(11|12|13|14|15|21|22|23|31|32|32|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82)(\\d{2})(\\d{2})(\\d{8})\\d{3}([0-9Xx])$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(idCard);
		if (matcher.find()) {
			try {
				// 校验日期格式
				LocalDate.parse(matcher.group(4), DateTimeFormatter.ofPattern("yyyyMMdd"));
				// 校验位检测
				int length = idCard.length();
				int sum = 0;
				for (int i = 0; i < length - 1; i++) {
					sum += Integer.parseInt(idCard.substring(i, i + 1)) * weights[i];
				}
				return factors[sum % 11].equalsIgnoreCase(matcher.group(5));
			} catch (DateTimeException e) {
				return false;
			}
		}
		return false;
	}
}