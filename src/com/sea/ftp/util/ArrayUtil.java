package com.sea.ftp.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import com.sea.ftp.exception.illegal.IllegalArgumentException;

/**
 * 提供一些操作数组的方法<BR>
 * 是对Jakarta Commons-Lang包及java.util.Arrays的补充。<BR>
 *
 * @se java.util#Arrays
 * @se org.apache.commons.lang#ArrayUtils
 *
 * @author wangwei
 */

public final class ArrayUtil {

	/**
	 * 应用NullObject模式以确认不会返回 null的字符串数组，减少NPE的可能
	 */
	public static String[] NULL_STRINGS = new String[0];

	/**
	 * 应用NullObject模式以确认不会返回 null的对象数组，减少NPE的可能
	 */
	public static Object[] NULL_OBJECTS = new Object[0];

	/**
	 * 应用NullObject模式以确认不会返回 null的类数组，减少NPE的可能
	 */
	public static Class<?>[] NULL_CLASSES = new Class[0];

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 *
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	private ArrayUtil() {
		super();
	}

	/**
	 * 将指定的的对象数组全部复制到新的对象数组中。<BR>
	 * 请注意，两个数组的长度必须相等。<BR>
	 *
	 * Copy the source objects to the target objects.<BR>
	 * The length of the two arrays must be equal.<BR>
	 *
	 * @param sourceObjects
	 *            the source object array.<BR>
	 * @param targetObjects
	 *            the target object array.<BR>
	 *
	 * @throws IllegalArgumentException
	 *             If the length of the two arrays is not equal ,the exception
	 *             will be thrown.<BR>
	 */
	public static void copyArray(final Object[] sourceObjects,
			final Object[] targetObjects) throws IllegalArgumentException {
		if (ArrayUtils.isEmpty(sourceObjects)
				|| ArrayUtils.isEmpty(targetObjects)) {
			return;
		}

		if (sourceObjects.length != targetObjects.length) {
			throw new IllegalArgumentException(
					"The length of the two arrays must be equal");
		}

		System.arraycopy(sourceObjects, 0, targetObjects, 0,
				targetObjects.length);
	}

	/**
	 * 将预先设置好的值以字符串数组的形式返回<BR>
	 * 这个参数必须是
	 * 
	 * @see java.util#Collection 或者是Object[]，当然String[]也是可以的。<BR>
	 *      如果都不是，就会返回 String[0]，而非 <code>null</code>。 <BR>
	 *      返回结果数组中的元素有可能为 <code>null</code>。
	 *
	 *      Return the predefined values by string array format.<BR>
	 *      The paramter must be
	 * @see java.util#Collection or Object[],of course,String[] is suitable.<BR>
	 *      If the paramter is not right,return String[0] instead of
	 *      <code>null</code>.<BR>
	 *      The element of the returned array could be <code>null</code>.<BR>
	 *
	 * @param value
	 *            this value should be java.util.Collection or Object[].<BR>
	 *
	 * @return the return value will never be <code>null</code>.
	 *
	 */
	public static String[] getStringArrayValues(final Object value) {
		if (value instanceof Collection) {
			final String[] stringArray = new String[((Collection<?>) value)
					.size()];
			Iterator<?> iterator = ((Collection<?>) value).iterator();
			int index = 0;
			while (iterator.hasNext()) {
				Object t_Object = iterator.next();
				stringArray[index] = ObjectUtils.toString(t_Object, null);
				index++;
			}

			return stringArray;
		}

		if (value instanceof Object[]) {
			Object[] objects = (Object[]) value;
			return getStringArrayValues(objects);
		}

		return NULL_STRINGS;
	}

	/**
	 * 将一个Object[]转成长度相等的String[]。<BR>
	 * 如果参数为 <code>null</code>，就返回 String[0]。<BR>
	 * 返回结果数组中的元素有可能为 <code>null</code>。<BR>
	 *
	 * Convert Object[] to String[] with the same length.<BR>
	 * If the parameter is <code>null</code>,return String[0].<BR>
	 * The element of the returned array could be <code>null</code>.<BR>
	 *
	 * @param value
	 * @return
	 */
	public static String[] getStringArrayValues(final Object[] value) {
		if (ArrayUtils.isEmpty(value)) {
			return NULL_STRINGS;
		}

		final String[] stringArray = new String[value.length];

		for (int i = 0; i < value.length; i++) {
			Object object = value[i];
			stringArray[i] = ObjectUtils.toString(object, null);
		}

		return stringArray;
	}

	/**
	 * /** 将一个对象转换成数组，该对象必须是Object[]或者是Collectoin。<BR>
	 * 返回结果数组中的元素有可能为 <code>null</code>。
	 *
	 * Convert a object to a array.The parameter must be Object[] or collection.<BR>
	 * The element of the returned array could be <code>null</code>.<BR>
	 *
	 * @see java.util#Collection
	 *
	 * @param value
	 *            the source object for being converted.It should be Object[] or
	 *            java.util.Collection.<BR>
	 *
	 */
	public static Object[] getArrayValues(final Object value) {
		if (value instanceof Object[]) {
			return (Object[]) value;
		}

		if (value instanceof Collection) {
			return ((Collection<?>) value).toArray();
		}

		return null;
	}

	/**
	 * 返回在指定的数组中是否存在指定的类型。<BR>
	 *
	 * Return whether there is a specified type in the arrays.<BR>
	 *
	 * @param objects
	 * @param clazz
	 * @return
	 */
	public static boolean hasType(Object[] objects, Class<?> clazz) {
		return hasType(objects, clazz, false);
	}

	/**
	 * 返回在指定的数组中是否存在指定的类型。<BR>
	 *
	 * Return whether there is a specified type in the arrays.<BR>
	 *
	 * @param objects
	 * @param clazz
	 * @param allowSuperType
	 *            if the parameter is true.That means allow to compare the super
	 *            class.<BR>
	 *            For example,class "A" extends class "B".<BR>
	 *            Now hasType(new Object[]{new A()},B,true) will return
	 *            <code>true</code>,because the super class of A is B.<BR>
	 *
	 * @return
	 */
	public static boolean hasType(Object[] objects, Class<?> clazz,
			boolean allowSuperType) {
		if (null == clazz) {
			return false;
		}

		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		for (int i = 0; i < objects.length; i++) {
			Object object = objects[i];

			if (null == object) {
				continue;
			}

			if (allowSuperType) {
				if (clazz.isAssignableFrom(object.getClass())) {
					return true;
				}
			} else {
				if (clazz == object.getClass()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 对数组排序，其实是调用了Arrays.sort方法，但是先对 <code>null</code>进行了判断。<BR>
	 * 避免不必要的NPE。<BR>
	 *
	 * Turn given source String array into sorted array.<BR>
	 *
	 * @param array
	 *            If the parameter is <code>null</code> or the length is 0,the
	 *            return value will the same.<BR>
	 *            Otherwise the return value will be a new array.<BR>
	 *
	 * @return the sorted array (never <code>null</code>)
	 *
	 * @see java.util.Arrays#sort(Object[])
	 */
	public static Object[] sort(Object[] array) {
		if (ArrayUtils.isEmpty(array)) {
			return array;
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * 判断一个对象是否是数组。<BR>
	 *
	 * Return whether a object is array or not.<BR>
	 *
	 * @param r_Object
	 * @return
	 */
	public static boolean isArray(Object r_Object) {
		if (null == r_Object) {
			return false;
		}

		Class<?> t_Class = r_Object.getClass();
		return t_Class.isArray();
	}

	/**
	 * <p>
	 * Checks if an array of Objects is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *            the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 * @since 2.1
	 */
	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}
}