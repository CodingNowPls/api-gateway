/**
 * 
 */
package com.striverfeng.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author StriverFeng
 *
 * 说明:
 */

public class ApiGatewayServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ApplicationContext context;
	private ApiGatewayHand apiHand;
	
	/**
	* @Title: init
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:42:48
	* @throws
	*/
	@Override
	public void init() throws ServletException {
		super.init();
		context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		apiHand=context.getBean(ApiGatewayHand.class);
	}
	
	/**
	* @Title: doPost
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:44:10
	* @throws
	*/
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		apiHand.handle(request, response);
	}
	/**
	* @Title: doGet
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:44:44
	* @throws
	*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		apiHand.handle(request, response);
	}
	
}
