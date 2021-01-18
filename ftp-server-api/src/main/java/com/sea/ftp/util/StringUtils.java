package com.sea.ftp.util;

import static java.lang.Character.isSpaceChar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author 华成伍
 * 
 */
public class StringUtils {

	String EMPTY = "";

	// public static boolean isNotEmpty(String value) {
	// return !isEmpty(value);
	// }
	//
	// public static boolean isEmpty(String value) {
	// return value == null || value.isEmpty();
	// }

	public static String trim(String value) {
		return isEmpty(value) ? value : isSpaceChar(value.charAt(0))
				|| isSpaceChar(value.length() - 1) ? value.trim() : value;
	}

	/**
	 * 将List拼装成以，号分割的字符串
	 */
	public static <T> String assembly(List<T> list, String limit) {
		return list == null || list.isEmpty() ? null : assembly(
				list.iterator(), limit);
	}

	/**
	 * 将Set拼装成以，号分割的字符串
	 */
	public static <T> String assembly(Set<T> set, String limit) {
		return set == null || set.isEmpty() ? null : assembly(set.iterator(),
				limit);
	}

	public static <T> String assembly(Iterator<T> it, String limit) {
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.next());
			sb = it.hasNext() ? sb.append(limit) : sb;
		}
		return sb.toString();
	}

	private static final String EmailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";

	private static final transient Pattern emailPattern;

	private static final String DEFAULT_PREFIX = "${";

	private static final String DEFAULT_SUFFIX = "}";

	final static String[] SBC = { "，", "。", "；", "“", "”", "？", "！", "（", "）",
			"：", "——", "、" };

	final static String[] DBC = { ",", ".", ";", "\"", "\"", "?", "!", "(",
			")", ":", "_", "," };

	static {
		emailPattern = Pattern.compile(EmailPattern);
	}

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 * 
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	private StringUtils() {
		super();
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal
	 * ignoring the case.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered equal. Comparison is case insensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, "abc")  = false
	 * StringUtil.equalsIgnoreCase("abc", null)  = false
	 * StringUtil.equalsIgnoreCase("abc", "abc") = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 * 
	 * @see java.lang.String#equalsIgnoreCase(String)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	// IndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *)         = -1
	 * StringUtil.indexOf("", *)           = -1
	 * StringUtil.indexOf("aabaabaa", 'a') = 0
	 * StringUtil.indexOf("aabaabaa", 'b') = 2
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * 判断字符串是否是包含a-z, A-Z, 0-9, _(下划线)
	 */
	public static boolean isWord(String str) {
		if (str == null) {
			return false;
		}

		char[] ch = str.toCharArray();
		int i;
		for (i = 0; i < str.length(); i++) {
			if ((!Character.isLetterOrDigit(ch[i])) && (ch[i] != '_')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为实数
	 */
	public static boolean isNumEx(String str) {
		if ((str == null) || (str.length() <= 0)) {
			return false;
		}
		// TODO

		char[] ch = str.toCharArray();

		for (int i = 0, comcount = 0; i < str.length(); i++) {
			if (!Character.isDigit(ch[i])) {
				if (ch[i] != '.') {
					return false;
				} else if ((i == 0) || (i == str.length() - 1)) {
					return false; // .12122 or 423423. is not a real number
				} else if (++comcount > 1) {
					return false; // 12.322.23 is not a real number
				}
			}
		}
		return true;
	}

	/**
	 * 得到字符串中的第index个数字字符串 example: int i =
	 * Integer.parseInt((String)StringUtil.getStringNumber
	 * ("asjfdkla3.asfa4",1)); return 1
	 */
	public static Object getStringNumber(String str, int index) {
		if (str == null) {
			return null;
		}

		char[] ch = str.toCharArray();
		int i;
		String tempStr = "";
		Vector<String> returnNumber = new Vector<String>();

		for (i = 0; i < str.length(); i++) {
			if (Character.isDigit(ch[i])) {
				tempStr += ch[i];
			} else {
				if (!tempStr.equals("")) {
					returnNumber.addElement(tempStr);
				}
				tempStr = "";
			}
		}

		if (!tempStr.equals("")) {
			returnNumber.addElement(tempStr);
		}

		if (returnNumber.isEmpty() || (index > returnNumber.size())) {
			return null;
		} else {
			if (index <= 0) {
				return returnNumber;
			} else {
				return returnNumber.elementAt(index - 1);
			}
		}
	}

	/**
	 * 排序字符串数组
	 */
	public static String[] sortByLength(String[] saSource, boolean bAsc) {
		if ((saSource == null) || (saSource.length <= 0)) {
			return null;
		}

		int iLength = saSource.length;
		String[] saDest = new String[iLength];

		for (int i = 0; i < iLength; i++) {
			saDest[i] = saSource[i];
		}

		String sTemp = "";
		int j = 0, k = 0;

		for (j = 0; j < iLength; j++) {
			for (k = 0; k < iLength - j - 1; k++) {
				if ((saDest[k].length() > saDest[k + 1].length()) && bAsc) {
					sTemp = saDest[k];
					saDest[k] = saDest[k + 1];
					saDest[k + 1] = sTemp;
				} else if ((saDest[k].length() < saDest[k + 1].length())
						&& !bAsc) {
					sTemp = saDest[k];
					saDest[k] = saDest[k + 1];
					saDest[k + 1] = sTemp;
				}
			}
		}
		return saDest;
	}

	/**
	 * 转换sbctodbc
	 */
	public static String symbolSBCToDBC(String sSource) {
		if ((sSource == null) || (sSource.length() <= 0)) {
			return sSource;
		}

		int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
		for (int i = 0; i < iLen; i++) {
			sSource = replace(sSource, SBC[i], DBC[i]);
		}
		return sSource;
	}

	/**
	 * 转换dbctosbc
	 */
	public static String symbolDBCToSBC(String sSource) {
		if ((sSource == null) || (sSource.length() <= 0)) {
			return sSource;
		}

		int iLen = SBC.length < DBC.length ? SBC.length : DBC.length;
		for (int i = 0; i < iLen; i++) {
			sSource = replace(sSource, DBC[i], SBC[i]);
		}
		return sSource;
	}

	/**
	 * 判断是否email地址
	 */
	public static boolean isEmailAddress(String str) {
		if (isEmpty(str)) {
			return false;
		}

		return emailPattern.matcher(str).matches();
	}

	/**
	 * 如果s为空或Null, 则返回"Null", 否则给s两边加上单引号返回。用在写数据库的时候。
	 */
	public static String quoteNullString(String s) {
		if (s == null) {
			return "Null";
		}
		if (s.trim().length() == 0) {
			return "Null";
		}
		return "'" + s.trim() + "'";
	}

	/**
	 * 
	 * 
	 * @param s
	 * @param pos
	 * @return
	 */
	public static char getCharAtPosDefaultZero(String s, int pos) {
		if (s == null) {
			return '0';
		}

		if (s.length() <= pos) {
			return '0';
		}
		return s.charAt(pos);

		// FIXME
	}

	/**
	 * 设置字符串的制定位置（0表示第一个字符）字符。 如果字符串长度小于 位置+1 ，则首先给字符串 补充0 允许传入参数为null
	 * 
	 * @param extend2
	 * @param pos_is_allow_sendback
	 * @param flag
	 * @return
	 */
	public static String setCharAtPosAppendZero(String s, int pos, char c) {
		if (s == null) {
			s = "";
		}

		while (pos > s.length() - 1) {
			s = s + '0';
		}

		String preString, afterString;

		if (pos == 0) {
			preString = "";
		} else {
			preString = s.substring(0, pos);
		}

		if (pos == s.length() - 1) {
			afterString = "";
		} else {
			afterString = s.substring(pos + 1);
		}

		return preString + c + afterString;

	}

	/**
	 * 在指定字符串的左面或者右面，添加指定数量的空格。<BR>
	 * 
	 * @param s
	 * @param n
	 * @param isLeft
	 * @return
	 */
	public static String fillBlank(String s, int n, boolean isLeft) {

		if (n < 0) {
			return s;
		}

		if (isEmpty(s)) {
			return rightPad("", n, " ");
		}

		if (s.length() >= n) {
			return s;
		}
		if (isLeft) {
			return leftPad(s, n, " ");
		} else {
			return rightPad(s, n, " ");
		}
	}

	/**
	 * 比较两个版本的大小。<BR>
	 * 
	 * @param version1
	 * @param version2
	 * @return 1 0 -1
	 */
	public static int compareVersion(String version1, String version2) {
		StringTokenizer st1 = new StringTokenizer(version1, ".");
		StringTokenizer st2 = new StringTokenizer(version2, ".");

		ArrayList<String> al1 = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();

		while (st1.hasMoreTokens()) {
			al1.add(st1.nextToken());
		}
		while (st2.hasMoreTokens()) {
			al2.add(st2.nextToken());
		}

		int size1 = al1.size();
		int size2 = al2.size();

		for (int i = 0; (i < size1) && (i < size2); i++) {
			int v1 = Integer.parseInt((String) al1.get(i));
			int v2 = Integer.parseInt((String) al2.get(i));

			if (v1 > v2) {
				return 1;
			}
			if (v1 < v2) {
				return -1;
			}
		}

		if (size1 > size2) {
			return 1;
		}
		if (size1 < size2) {
			return -1;
		}
		return 0;
	}

	/**
	 * Delete any character in a given string.
	 * 
	 * @param charsToDelete
	 *            a set of characters to delete. E.g. "az\n" will delete 'a's,
	 *            'z's and new lines.
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if ((inString == null) || (charsToDelete == null)) {
			return inString;
		}

		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	// ---------------------------------------------------------------------

	/**
	 * Quote the given String with single quotes.
	 * 
	 * @param str
	 *            the input String (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or
	 *         <code>null<code> if the input was <code>null</code>
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * Turn the given Object into a String with single quotes if it is a String;
	 * keeping the Object as-is else.
	 * 
	 * @param obj
	 *            the input Object (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or the input object as-is
	 *         if not a String
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * Unqualify a string qualified by a '.' dot character. For example,
	 * "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example,
	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param separator
	 *            the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Parse the given locale string into a <code>java.util.Locale</code>. This
	 * is the inverse operation of Locale's <code>toString</code>.
	 * 
	 * @param localeString
	 *            the locale string, following <code>java.util.Locale</code>'s
	 *            toString format ("en", "en_UK", etc). Also accepts spaces as
	 *            separators, as alternative to underscores.
	 * @return a corresponding Locale instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = (parts.length > 2 ? parts[2] : "");
		return (language.length() > 0 ? new Locale(language, country, variant)
				: null);
	}

	/**
	 * Remove duplicate Strings from the given array. Also sorts the array, as
	 * it uses a TreeSet.
	 * 
	 * @param array
	 *            the String array
	 * @return an array without duplicates, in natural sort order
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ArrayUtil.isEmpty(array)) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return ArrayUtil.getStringArrayValues(set);
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @param charsToDelete
	 *            one or more characters to remove from each element prior to
	 *            attempting the split operation (typically the quotation mark
	 *            symbol), or <code>null</code> if no removal should occur
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
			String delimiter, String charsToDelete) {

		if ((array == null) || (array.length == 0)) {
			return null;
		}

		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(),
					splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter)
	 * @param trimTokens
	 *            trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array (only applies to
	 *            tokens that are empty after trimming; StringTokenizer will not
	 *            consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters,
			boolean trimTokens, boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || (token.length() > 0)) {
				tokens.add(token);
			}
		}
		return ArrayUtil.getStringArrayValues(tokens);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * 
	 * @param str
	 *            CSV list
	 * @return an array of Strings, or the empty array if s is null
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return split(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set. Note that this
	 * will suppress duplicates.
	 * 
	 * @param str
	 *            CSV String
	 * @return a Set of String entries in the list
	 */
	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (arr == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 * @param prefix
	 *            string to start each element with
	 * @param suffix
	 *            string to end each element with
	 */
	public static String collectionToDelimitedString(Collection<?> coll,
			String delim, String prefix, String suffix) {
		if (coll == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		Iterator<?> it = coll.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(prefix).append(it.next()).append(suffix);
			i++;
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String collectionToDelimitedString(Collection<?> coll,
			String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 */
	public static String collectionToCommaDelimitedString(Collection<?> coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * replace tokens in a string that starts with "${" and ends with "}"; add
	 * by chugh 2006/12/26
	 * 
	 * @param src
	 *            source string that contains tokens;
	 * @param props
	 *            properties;
	 * @return String with tokens replaces;
	 */
	public static String replace(String src, Map<?, ?> props) {
		return replace(src, DEFAULT_PREFIX, DEFAULT_SUFFIX, props);
	}

	/**
	 * replace symbols in a string; add by chugh 2006/12/26
	 * 
	 * 
	 * @param src
	 * @param prefix
	 * @param suffix
	 * @param props
	 * @return a String with symbol replaced;
	 */
	public static String replace(String src, String prefix, String suffix,
			Map<?, ?> props) {
		int index1;
		int index2;
		int len1 = prefix.length();
		int len2 = suffix.length();

		StringBuffer sb = new StringBuffer();

		index1 = src.indexOf(prefix);
		while (index1 >= 0) {
			sb.append(src.substring(0, index1));
			src = src.substring(index1 + len1);
			if (src.startsWith(prefix)) {
				sb.append(prefix);
				break;
			} else {
				index2 = src.indexOf(suffix);
				if (index2 >= 0) {
					String t = src.substring(0, index2);
					Object o = props.get(t);
					String sp = (o == null ? "" : o.toString());
					sb.append(sp);
					src = src.substring((index2 + len2));
					index1 = src.indexOf(prefix);
				} else {
					sb.append(prefix);
					break;
				}
			}
		}
		sb.append(src);
		return new String(sb);
	}

	/**
	 * 判断是否为空指针或者空字符
	 * 
	 * Add by liuxj
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNullAndBlank(String str) {
		if (isNullOrBlank(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否为空指针或者空字符
	 * 
	 * Add by liuxj
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrBlank(String str) {
		if (isNull(str) || str.equals("") || str.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空指针
	 * 
	 * Add by liuxj
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空指针
	 * 
	 * Add by liuxj
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return false;
		} else if (str.trim().equalsIgnoreCase("null")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将空指针转成空字符
	 * 
	 * Add by liuxj
	 * 
	 * @param str
	 * @return
	 */
	public static String ifNullToBlank(String str) {
		if (isNotNull(str) && !(str.trim().equals("null"))) {
			return str.trim();
		} else {
			return "";
		}
	}

	/**
	 * 将空指针转成空字符
	 * 
	 * Add by liuxj
	 * 
	 * @param obj
	 * @return
	 */
	public static String ifNullToBlank(Object obj) {
		String ret = "";
		String s = String.valueOf(obj);
		if ((s == null) || "".equals(s) || "null".equals(s) || "NULL".equals(s)) {
			ret = "";
		} else {
			ret = s;
		}
		return ret;
	}

	/**
	 * Tests if a string contains stars or question marks
	 * 
	 * @param input
	 *            a String which one wants to test for containing wildcard
	 * @return true if the string contains at least a star or a question mark
	 */
	public static boolean hasWildcards(String input) {
		return (contains(input, "*") || contains(input, "?"));
	}

	/**
	 * 用来判断指定的词是否与指定的字符串数组中的一个匹配。支持'*'和'?'。<BR>
	 * 
	 * This method is used to judge whether one of the string array match the
	 * specified word.<BR>
	 * 
	 * @param r_Keyword
	 * @param r_WildcardMatcher
	 * @param r_CaseSensitive
	 * @return
	 */
	public static boolean isWildcardMatchOne(String r_Keyword,
			String[] r_WildcardMatcher, boolean r_CaseSensitive) {
		if (null == r_WildcardMatcher) {
			return true;
		}

		for (int i = 0; i < r_WildcardMatcher.length; i++) {
			String t_WildCardMatcher = r_WildcardMatcher[i];

			if (isWildcardMatch(r_Keyword, t_WildCardMatcher, r_CaseSensitive)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 用来判断指定的词是否与指定的字符串数组中的所有匹配。支持'*'和'?'。<BR>
	 * 
	 * This method is used to judge whether all the string arrays match the
	 * specified word.<BR>
	 * 
	 * @param r_Keyword
	 * @param r_WildcardMatcher
	 * @param r_CaseSensitive
	 * @return
	 */
	public static boolean isWildcardMatchAll(String r_Keyword,
			String[] r_WildcardMatcher, boolean r_CaseSensitive) {
		if (null == r_WildcardMatcher) {
			return true;
		}

		for (int i = 0; i < r_WildcardMatcher.length; i++) {
			String t_WildCardMatcher = r_WildcardMatcher[i];

			if (!isWildcardMatch(r_Keyword, t_WildCardMatcher, r_CaseSensitive)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 用来判断是否匹配。<BR>
	 * 可以使用'*'和'?'来进行匹配。<BR>
	 * 
	 * This method is used match 2 strings wildly.<BR>
	 * The char of '*' and '?' can be used.<BR>
	 * 
	 * For Example: isWildcardMatch("username", "u*er*",true) --> true
	 * isWildcardMatch("username", "u?er*",true) --> true
	 * isWildcardMatch("username", "u*ter*") --> false
	 * isWildcardMatch("username", "U*er*",true) --> false
	 * 
	 * @param r_Keyword
	 * @param r_WildcardMatcher
	 * @return
	 */
	public static boolean isWildcardMatch(String r_Keyword,
			String r_WildcardMatcher) {
		return isWildcardMatch(r_Keyword, r_WildcardMatcher, true);
	}

	/**
	 * 用来判断是否匹配。<BR>
	 * 可以使用'*'和'?'来进行匹配。<BR>
	 * 
	 * This method is used match 2 strings wildly.<BR>
	 * The char of '*' and '?' can be used.<BR>
	 * 
	 * For Example: isWildcardMatch("username", "u*er*",true) --> true
	 * isWildcardMatch("username", "u?er*",true) --> true
	 * isWildcardMatch("username", "u*ter*",true) --> false
	 * isWildcardMatch("username", "U*er*",true) --> false
	 * isWildcardMatch("username", "U*er*",false) --> true
	 * 
	 * @param r_Keyword
	 * @param r_WildcardMatcher
	 * @param r_CaseSensitive
	 * @return
	 */
	public static boolean isWildcardMatch(String r_Keyword,
			String r_WildcardMatcher, boolean r_CaseSensitive) {
		if ((r_Keyword == null) && (r_WildcardMatcher == null)) {
			return true;
		}
		if ((r_Keyword == null) || (r_WildcardMatcher == null)) {
			return false;
		}
		if (!r_CaseSensitive) {
			r_Keyword = r_Keyword.toLowerCase();
			r_WildcardMatcher = r_WildcardMatcher.toLowerCase();
		}
		String[] t_SplitValues = splitOnTokens(r_WildcardMatcher);
		boolean t_Chars = false;
		int t_Index = 0;
		int t_WildIndex = 0;
		Stack<int[]> t_BackStack = new Stack<int[]>();

		// loop around a backtrack stack, to handle complex * matching
		do {
			if (t_BackStack.size() > 0) {
				int[] array = (int[]) t_BackStack.pop();
				t_WildIndex = array[0];
				t_Index = array[1];
				t_Chars = true;
			}

			// loop whilst tokens and text left to process
			while (t_WildIndex < t_SplitValues.length) {

				if (t_SplitValues[t_WildIndex].equals("?")) {
					// ? so move to next text char
					t_Index++;
					t_Chars = false;

				} else if (t_SplitValues[t_WildIndex].equals("*")) {
					// set any chars status
					t_Chars = true;
					if (t_WildIndex == t_SplitValues.length - 1) {
						t_Index = r_Keyword.length();
					}

				} else {
					// matching text token
					if (t_Chars) {
						// any chars then try to locate text token
						t_Index = r_Keyword.indexOf(t_SplitValues[t_WildIndex],
								t_Index);
						if (t_Index == -1) {
							// token not found
							break;
						}
						int repeat = r_Keyword.indexOf(
								t_SplitValues[t_WildIndex], t_Index + 1);
						if (repeat >= 0) {
							t_BackStack.push(new int[] { t_WildIndex, repeat });
						}
					} else {
						// matching from current position
						if (!r_Keyword.startsWith(t_SplitValues[t_WildIndex],
								t_Index)) {
							// couldnt match token
							break;
						}
					}

					// matched text token, move text index to end of matched
					// token
					t_Index += t_SplitValues[t_WildIndex].length();
					t_Chars = false;
				}

				t_WildIndex++;
			}

			// full match
			if ((t_WildIndex == t_SplitValues.length)
					&& (t_Index == r_Keyword.length())) {
				return true;
			}

		} while (t_BackStack.size() > 0);

		return false;
	}

	/**
	 * 将字符串按照'?'和'*'进行分解。<BR>
	 * 
	 * Split a string into multi tokens by *'' and '?'.
	 * 
	 * @param r_Text
	 * @return
	 */
	private static String[] splitOnTokens(String r_Text) {
		// used by wildcardMatch
		// package level so a unit test may run on this

		if ((r_Text.indexOf("?") == -1) && (r_Text.indexOf("*") == -1)) {
			return new String[] { r_Text };
		}

		char[] t_Array = r_Text.toCharArray();
		ArrayList<String> t_List = new ArrayList<String>();
		StringBuffer t_Buffer = new StringBuffer();
		for (int i = 0; i < t_Array.length; i++) {
			if ((t_Array[i] == '?') || (t_Array[i] == '*')) {
				if (t_Buffer.length() != 0) {
					t_List.add(t_Buffer.toString());
					t_Buffer.setLength(0);
				}
				if (t_Array[i] == '?') {
					t_List.add("?");
				} else if ((t_List.size() == 0)
						|| ((i > 0) && (t_List.get(t_List.size() - 1).equals(
								"*") == false))) {
					t_List.add("*");
				}
			} else {
				t_Buffer.append(t_Array[i]);
			}
		}
		if (t_Buffer.length() != 0) {
			t_List.add(t_Buffer.toString());
		}

		return (String[]) t_List.toArray(new String[0]);
	}

	/**
	 * 用来判断一个字符串是否在指定的字符串数组中。<BR>
	 * 
	 * Used to judge whether the string array contains a specified string.<BR>
	 * 
	 * @param r_Source
	 * @param r_Target
	 * @param r_CaseSensitive
	 * @return the the parameter of "r_Source" is in the string arry,return
	 *         <code>true</code>.
	 */
	public static boolean isIn(String r_Source, String[] r_Target,
			boolean r_CaseSensitive) {
		for (int i = 0; i < r_Target.length; i++) {
			String t_Value = r_Target[i];
			if (r_CaseSensitive) {
				if (equals(r_Source, t_Value)) {
					return true;
				}
			} else {
				if (equalsIgnoreCase(r_Source, t_Value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 用来判断一个字符串是否在指定的字符串数组中。<BR>
	 * 
	 * Used to judge whether the string array contains a specified string.<BR>
	 * 
	 * @param r_Source
	 * @param r_Target
	 * @return the the parameter of "r_Source" is in the string arry,return
	 *         <code>true</code>.
	 */
	public static boolean isIn(String r_Source, Collection<?> r_Target) {
		for (Iterator<?> t_Iterator = r_Target.iterator(); t_Iterator.hasNext();) {
			String t_Value = (String) t_Iterator.next();
			if (equalsIgnoreCase(r_Source, t_Value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 以xml结点结尾形式返回 如:'<\/'+ targetName + '>'
	 * 
	 * @param name
	 * @return
	 */
	public static String targetEndStyle(String name) {
		return "</" + name + ">";
	}

	/**
	 * 返回被赋值样式 如:="value"
	 * 
	 * @param value
	 * @return
	 */
	public static String valueToSetStyle(String value) {
		if (value == null) {
			value = "";
		}
		return "=\"" + value + "\"";
	}

	/**
	 * 判断是否相等，null和""都认为相等
	 * 
	 * @param s1
	 *            字符串1
	 * @param s2
	 *            字符串2
	 * @return true:相等
	 */
	public static boolean equal(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}

		if (s1 == null) {
			s1 = "";
		}

		if (s2 == null) {
			s2 = "";
		}

		s1 = s1.trim();
		s2 = s2.trim();

		if (s1.equals(s2)) {
			return true;
		}
		return false;
	}

	/**
	 * 连接对象的字符串
	 * 
	 * @param args
	 *            被连接的对象
	 * @return 字符串
	 */
	public static String concat(Object... args) {
		StringBuffer buf = new StringBuffer();
		for (Object arg : args) {
			buf.append(arg);
		}
		return buf.toString();
	}

	/**
	 * 格式化字符串
	 * 
	 * @param s
	 *            被格式化的字符串
	 * @param params
	 *            格式化参数
	 * @return 格式化后的字符串
	 */
	public static String format(String s, Object[] params) {
		String message = s;
		if (message == null) {
			return "";
		}
		if (params != null && params.length > 0) {
			message = new MessageFormat(message).format(params);
		}
		return message;

	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	// Review: 去除对apache common-lang的依赖
	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isNotEmpty(null)      = false
	 * StringUtil.isNotEmpty("")        = false
	 * StringUtil.isNotEmpty(" ")       = true
	 * StringUtil.isNotEmpty("bob")     = true
	 * StringUtil.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * <p>
	 * Gets the substring after the first occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the empty string if the input string is not
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.substringAfter(null, *)      = null
	 * StringUtil.substringAfter("", *)        = ""
	 * StringUtil.substringAfter(*, null)      = ""
	 * StringUtil.substringAfter("abc", "a")   = "bc"
	 * StringUtil.substringAfter("abcba", "b") = "cba"
	 * StringUtil.substringAfter("abc", "c")   = ""
	 * StringUtil.substringAfter("abc", "d")   = ""
	 * StringUtil.substringAfter("abc", "")    = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the first occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the substring before the first occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the input string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.substringBefore(null, *)      = null
	 * StringUtil.substringBefore("", *)        = ""
	 * StringUtil.substringBefore("abc", "a")   = ""
	 * StringUtil.substringBefore("abcba", "b") = "a"
	 * StringUtil.substringBefore("abc", "c")   = "ab"
	 * StringUtil.substringBefore("abc", "d")   = "abc"
	 * StringUtil.substringBefore("abc", "")    = ""
	 * StringUtil.substringBefore("abc", null)  = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the first occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank("")        = true
	 * StringUtil.isBlank(" ")       = true
	 * StringUtil.isBlank("bob")     = false
	 * StringUtil.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator. For more control over the split
	 * use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.split(null, *)         = null
	 * StringUtil.split("", *)           = []
	 * StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]
	 * StringUtil.split("a..b.c", '.')   = ["a", "b", "c"]
	 * StringUtil.split("a:b:c", '.')    = ["a:b:c"]
	 * StringUtil.split("a\tb\nc", null) = ["a", "b", "c"]
	 * StringUtil.split("a b c", ' ')    = ["a", "b", "c"]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChar
	 *            the character used as the delimiter, <code>null</code> splits
	 *            on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.0
	 */
	public static String[] split(String str, char separatorChar) {
		return splitWorker(str, separatorChar, false);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator. For more control over the split
	 * use the StrTokenizer class.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.split(null, *)         = null
	 * StringUtil.split("", *)           = []
	 * StringUtil.split("abc def", null) = ["abc", "def"]
	 * StringUtil.split("abc def", " ")  = ["abc", "def"]
	 * StringUtil.split("abc  def", " ") = ["abc", "def"]
	 * StringUtil.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	/**
	 * <p>
	 * Splits the provided text into an array with a maximum length, separators
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <p>
	 * If more than <code>max</code> delimited substrings are found, the last
	 * returned string includes all characters after the first
	 * <code>max - 1</code> returned strings (including separator characters).
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.split(null, *, *)            = null
	 * StringUtil.split("", *, *)              = []
	 * StringUtil.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]
	 * StringUtil.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
	 * StringUtil.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
	 * StringUtil.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, false);
	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)     = false
	 * StringUtil.contains(*, null)     = false
	 * StringUtil.contains("", "")      = true
	 * StringUtil.contains("abc", "")   = true
	 * StringUtil.contains("abc", "a")  = true
	 * StringUtil.contains("abc", "z")  = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Deletes all whitespaces from a String as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.deleteWhitespace(null)         = null
	 * StringUtil.deleteWhitespace("")           = ""
	 * StringUtil.deleteWhitespace("abc")        = "abc"
	 * StringUtil.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to delete whitespace from, may be null
	 * @return the String without whitespaces, <code>null</code> if null String
	 *         input
	 */
	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

	/**
	 * Performs the logic for the <code>split</code> and
	 * <code>splitPreserveAllTokens</code> methods that do not return a maximum
	 * array length.
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChar
	 *            the separate character
	 * @param preserveAllTokens
	 *            if <code>true</code>, adjacent separators are treated as empty
	 *            token separators; if <code>false</code>, adjacent separators
	 *            are treated as one separator.
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	private static String[] splitWorker(String str, char separatorChar,
			boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<String>();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			} else {
				lastMatch = false;
			}
			match = true;
			i++;
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * <p>
	 * Replaces all occurrences of a String within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *)        = null
	 * StringUtil.replace("", *, *)          = ""
	 * StringUtil.replace("any", null, *)    = "any"
	 * StringUtil.replace("any", *, null)    = "any"
	 * StringUtil.replace("any", "", *)      = "any"
	 * StringUtil.replace("aba", "a", null)  = "aba"
	 * StringUtil.replace("aba", "a", "")    = "b"
	 * StringUtil.replace("aba", "a", "z")   = "zbz"
	 * </pre>
	 * 
	 * @see #replace(String text, String repl, String with, int max)
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, for the
	 * first <code>max</code> values of the search String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *, *)         = null
	 * StringUtil.replace("", *, *, *)           = ""
	 * StringUtil.replace("any", null, *, *)     = "any"
	 * StringUtil.replace("any", *, null, *)     = "any"
	 * StringUtil.replace("any", "", *, *)       = "any"
	 * StringUtil.replace("any", *, *, 0)        = "any"
	 * StringUtil.replace("abaa", "a", null, -1) = "abaa"
	 * StringUtil.replace("abaa", "a", "", -1)   = "b"
	 * StringUtil.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtil.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtil.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtil.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if no
	 *            maximum
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || isEmpty(repl) || with == null || max == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * <p>
	 * Left pad a String with spaces (' ').
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size<code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *)   = null
	 * StringUtil.leftPad("", 3)     = "   "
	 * StringUtil.leftPad("bat", 3)  = "bat"
	 * StringUtil.leftPad("bat", 5)  = "  bat"
	 * StringUtil.leftPad("bat", 1)  = "bat"
	 * StringUtil.leftPad("bat", -1) = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * <p>
	 * Left pad a String with a specified character.
	 * </p>
	 * 
	 * <p>
	 * Pad to a size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *, *)     = null
	 * StringUtil.leftPad("", 3, 'z')     = "zzz"
	 * StringUtil.leftPad("bat", 3, 'z')  = "bat"
	 * StringUtil.leftPad("bat", 5, 'z')  = "zzbat"
	 * StringUtil.leftPad("bat", 1, 'z')  = "bat"
	 * StringUtil.leftPad("bat", -1, 'z') = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	/**
	 * <p>
	 * Returns padding using the specified delimiter repeated to a given length.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.padding(0, 'e')  = ""
	 * StringUtil.padding(3, 'e')  = "eee"
	 * StringUtil.padding(-2, 'e') = IndexOutOfBoundsException
	 * </pre>
	 * 
	 * @param repeat
	 *            number of times to repeat delim
	 * @param padChar
	 *            character to repeat
	 * @return String with repeated character
	 * @throws IndexOutOfBoundsException
	 *             if <code>repeat &lt; 0</code>
	 */
	private static String padding(int repeat, char padChar) {
		// be careful of synchronization in this method
		// we are assuming that get and set from an array index is atomic
		String pad = PADDING[padChar];
		if (pad == null) {
			pad = String.valueOf(padChar);
		}
		while (pad.length() < repeat) {
			pad = pad.concat(pad);
		}
		PADDING[padChar] = pad;
		return pad.substring(0, repeat);
	}

	/**
	 * <p>
	 * Left pad a String with a specified String.
	 * </p>
	 * 
	 * <p>
	 * Pad to a size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.leftPad(null, *, *)      = null
	 * StringUtil.leftPad("", 3, "z")      = "zzz"
	 * StringUtil.leftPad("bat", 3, "yz")  = "bat"
	 * StringUtil.leftPad("bat", 5, "yz")  = "yzbat"
	 * StringUtil.leftPad("bat", 8, "yz")  = "yzyzybat"
	 * StringUtil.leftPad("bat", 1, "yz")  = "bat"
	 * StringUtil.leftPad("bat", -1, "yz") = "bat"
	 * StringUtil.leftPad("bat", 5, null)  = "  bat"
	 * StringUtil.leftPad("bat", 5, "")    = "  bat"
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 *            the String to pad with, null or empty treated as single space
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * <p>
	 * Right pad a String with spaces (' ').
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.rightPad(null, *)   = null
	 * StringUtil.rightPad("", 3)     = "   "
	 * StringUtil.rightPad("bat", 3)  = "bat"
	 * StringUtil.rightPad("bat", 5)  = "bat  "
	 * StringUtil.rightPad("bat", 1)  = "bat"
	 * StringUtil.rightPad("bat", -1) = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * <p>
	 * Right pad a String with a specified character.
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.rightPad(null, *, *)     = null
	 * StringUtil.rightPad("", 3, 'z')     = "zzz"
	 * StringUtil.rightPad("bat", 3, 'z')  = "bat"
	 * StringUtil.rightPad("bat", 5, 'z')  = "batzz"
	 * StringUtil.rightPad("bat", 1, 'z')  = "bat"
	 * StringUtil.rightPad("bat", -1, 'z') = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	/**
	 * <p>
	 * Right pad a String with a specified String.
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.rightPad(null, *, *)      = null
	 * StringUtil.rightPad("", 3, "z")      = "zzz"
	 * StringUtil.rightPad("bat", 3, "yz")  = "bat"
	 * StringUtil.rightPad("bat", 5, "yz")  = "batyz"
	 * StringUtil.rightPad("bat", 8, "yz")  = "batyzyzy"
	 * StringUtil.rightPad("bat", 1, "yz")  = "bat"
	 * StringUtil.rightPad("bat", -1, "yz") = "bat"
	 * StringUtil.rightPad("bat", 5, null)  = "bat  "
	 * StringUtil.rightPad("bat", 5, "")    = "bat  "
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 *            the String to pad with, null or empty treated as single space
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, "abc")  = false
	 * StringUtil.equals("abc", null)  = false
	 * StringUtil.equals("abc", "abc") = true
	 * StringUtil.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * Performs the logic for the <code>split</code> and
	 * <code>splitPreserveAllTokens</code> methods that return a maximum array
	 * length.
	 * 
	 * @param str
	 *            the String to parse, may be <code>null</code>
	 * @param separatorChars
	 *            the separate character
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit.
	 * @param preserveAllTokens
	 *            if <code>true</code>, adjacent separators are treated as empty
	 *            token separators; if <code>false</code>, adjacent separators
	 *            are treated as one separator.
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	private static String[] splitWorker(String str, String separatorChars,
			int max, boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * <p>
	 * Removes a substring only if it is at the begining of a source string,
	 * otherwise returns the source string.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> source string will return <code>null</code>. An empty
	 * ("") source string will return the empty string. A <code>null</code>
	 * search string will return the source string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.removeStart(null, *)      = null
	 * StringUtil.removeStart("", *)        = ""
	 * StringUtil.removeStart(*, null)      = *
	 * StringUtil.removeStart("www.domain.com", "www.")   = "domain.com"
	 * StringUtil.removeStart("domain.com", "www.")       = "domain.com"
	 * StringUtil.removeStart("www.domain.com", "domain") = "www.domain.com"
	 * StringUtil.removeStart("abc", "")    = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the source String to search, may be null
	 * @param remove
	 *            the String to search for and remove, may be null
	 * @return the substring with the string removed if found, <code>null</code>
	 *         if null String input
	 * @since 2.1
	 */
	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * Removes a substring only if it is at the end of a source string,
	 * otherwise returns the source string.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> source string will return <code>null</code>. An empty
	 * ("") source string will return the empty string. A <code>null</code>
	 * search string will return the source string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.removeEnd(null, *)      = null
	 * StringUtil.removeEnd("", *)        = ""
	 * StringUtil.removeEnd(*, null)      = *
	 * StringUtil.removeEnd("www.domain.com", ".com.")  = "www,domain"
	 * StringUtil.removeEnd("www.domain.com", ".com")   = "www.domain"
	 * StringUtil.removeEnd("www.domain.com", "domain") = "www.domain.com"
	 * StringUtil.removeEnd("abc", "")    = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the source String to search, may be null
	 * @param remove
	 *            the String to search for and remove, may be null
	 * @return the substring with the string removed if found, <code>null</code>
	 *         if null String input
	 * @since 2.1
	 */
	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	/**
	 * <p>
	 * Removes all occurances of a substring from within the source string.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> source string will return <code>null</code>. An empty
	 * ("") source string will return the empty string. A <code>null</code>
	 * remove string will return the source string. An empty ("") remove string
	 * will return the source string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.remove(null, *)        = null
	 * StringUtil.remove("", *)          = ""
	 * StringUtil.remove(*, null)        = *
	 * StringUtil.remove(*, "")          = *
	 * StringUtil.remove("queued", "ue") = "qd"
	 * StringUtil.remove("queued", "zz") = "queued"
	 * </pre>
	 * 
	 * @param str
	 *            the source String to search, may be null
	 * @param remove
	 *            the String to search for and remove, may be null
	 * @return the substring with the string removed if found, <code>null</code>
	 *         if null String input
	 * @since 2.1
	 */
	public static String remove(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		return replace(str, remove, "", -1);
	}

	/**
	 * <p>
	 * Removes all occurances of a character from within the source string.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> source string will return <code>null</code>. An empty
	 * ("") source string will return the empty string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.remove(null, *)       = null
	 * StringUtil.remove("", *)         = ""
	 * StringUtil.remove("queued", 'u') = "qeed"
	 * StringUtil.remove("queued", 'z') = "queued"
	 * </pre>
	 * 
	 * @param str
	 *            the source String to search, may be null
	 * @param remove
	 *            the char to search for and remove, may be null
	 * @return the substring with the char removed if found, <code>null</code>
	 *         if null String input
	 * @since 2.1
	 */
	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	private static final int PAD_LIMIT = 8192;

	/**
	 * <p>
	 * An array of <code>String</code>s used for padding.
	 * </p>
	 * 
	 * <p>
	 * Used for efficient space padding. The length of each String expands as
	 * needed.
	 * </p>
	 */
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Get hex string from byte array
	 */
	public final static String toHexString(byte[] res) {
		StringBuilder sb = new StringBuilder(res.length << 1);
		for (int i = 0; i < res.length; i++) {
			String digit = Integer.toHexString(0xFF & res[i]);
			if (digit.length() == 1) {
				sb.append('0');
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase();
	}
}
