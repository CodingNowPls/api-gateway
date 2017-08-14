/**
 * 
 */
package com.cn.striverfeng.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author StriverFeng
 *
 * 说明:
 */
public class ApiRunnable implements Runnable{

	public String apiName;
	public Method targetMethod;
	public Object targetName;

	/**
	* @Title: run
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:56:16
	* @throws
	*/
	@Override
	public void run() {

	}




	/**
	* @Title: getTargetMethod
	* @Description: TODO
	* @return Method    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:14:59
	* @throws
	*/
	public Method getTargetMethod() {
		return targetMethod;
 
	}




	/**
	* @Title: run
	* @Description: TODO 
	* @return Object    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午7:57:52
	* @throws
	*/
	public  Object run(Object[] args)throws InvocationTargetException,Exception {
		Class<?> targetClass = targetMethod.getDeclaringClass();
		Object targetObject = targetClass.newInstance();
		Object invoke = targetMethod.invoke(targetObject, args);
		return invoke;
	}

}
