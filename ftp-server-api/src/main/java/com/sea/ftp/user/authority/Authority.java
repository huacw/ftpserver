package com.sea.ftp.user.authority;

/**
 * 
 * 用户验证接口
 *
 * @author sea 
 */
public interface Authority {

	/**
	 * 验证请求是否可以进行权限验证
	 * 
	 * @param request
	 *            需要验证的请求
	 * @return 如果验证通过返回true，否则返回false
	 */
	public boolean canAuthorize(AuthorizationRequest request);

	/**
	 * 验证请求
	 * 
	 * @param request
	 *            需要验证的请求 {@link AuthorizationRequest}
	 * @return 返回一个验证请求对象 {@link #canAuthorize(AuthorizationRequest)}
	 *         返回true时返回一个验证请求，否则返回null
	 *         {@link #canAuthorize(AuthorizationRequest)} 必须在调用本方法前调用
	 */
	public AuthorizationRequest authorize(AuthorizationRequest request);
}
