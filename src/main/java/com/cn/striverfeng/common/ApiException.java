/**
 * 
 */
package com.cn.striverfeng.common;

/**
 * @author StriverFeng
 *
 * 说明:
 */
public class ApiException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int code;
    public String message;

	/**
	 * @param string
	 */
	public ApiException(String string){
		super(string);
	}

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }

	public ApiException(Exception e) {
		super(e.getMessage());
	}
    public ApiException(String message,int code) {

    }
    public ApiException(String message,String code) {

    }
}
