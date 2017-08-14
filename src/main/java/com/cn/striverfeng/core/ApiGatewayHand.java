package com.cn.striverfeng.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.striverfeng.common.ApiException;
import com.cn.striverfeng.common.ApiRunnable;
import com.cn.striverfeng.common.UtilJson;
import com.cn.striverfeng.pojo.Goods;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;




/**
 * @author StriverFeng
 *
 * 说明:
 */
public class ApiGatewayHand implements InitializingBean, ApplicationContextAware {
	
	private static final Logger logger=LoggerFactory.getLogger(ApiGatewayHand.class);
	private static String METHOD="method";
	private static String PARAMS="params";
	
	ApiStore apiStore;
	final ParameterNameDiscoverer parameterUtil;
	
	
	
	public ApiGatewayHand() {
		 parameterUtil=new LocalVariableTableParameterNameDiscoverer();
	}

	/**
	* @Title: setApplicationContext
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:34:28
	* @throws
	*/
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		apiStore=new ApiStore(applicationContext);

	}

	/**
	* @Title: afterPropertiesSet
	* @Description: TODO 
	* @return   返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:34:28
	* @throws
	*/
	@Override
	public void afterPropertiesSet() throws Exception {
		apiStore.loadApiFromSpringBeans();
	}
	public void handle(HttpServletRequest request,HttpServletResponse response){
		//系统参数验证
		String params=request.getParameter(PARAMS);
		String method=request.getParameter(METHOD);
		Object result;
		ApiRunnable apiRun=null;
		try {
			apiRun=sysParamsValdate(request);
			logger.info("请求接口={"+method+"} 参数="+params+"");
			Object[] args=bulidParams(apiRun,params,request,response);
			result=apiRun.run(args);
		} catch (ApiException e) {
			response.setStatus(500); //封装异常并返回
			logger.error("调用接口={"+method+"}异常 参数="+params+"",e);
			result=handleError(e);
		} catch (InvocationTargetException e) {
			response.setStatus(500); //封装异常并返回
			logger.error("调用接口={"+method+"}异常 参数="+params+"",e.getTargetException());
			result=handleError(e.getTargetException());
		} catch (Exception e) {
			response.setStatus(500); //封装异常并返回
			logger.error("其他异常",e);
			result=handleError(e);
		}
		//统一返回结果
		returnResult(result,response);
	}

	/**
	* @Title: returnResult
	* @Description: TODO 
	* @return void    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:13:12
	* @throws
	*/
	private void returnResult(Object result, HttpServletResponse response) {
		try {
//			UtilJson.JSON_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,true);
			String json= UtilJson.writeValueAsString(result);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/html/json;charset=utf-8");
//			response.setContentType("text/html/json;charset=utf-8");
			response.setHeader("Pragma", "no-cacahe");
			response.setDateHeader("Expires", 0);
			if (json !=null) {
				response.getWriter().write(json);
			}
		} catch (IOException e) {
			 logger.error("服务中心响应异常",e);
			 throw new RuntimeException(e);
		}

	}

	/**
	* @Title: handleError
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return Object    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:01:15
	* @throws
	*/
	private Object handleError(Throwable throwable) {
        String code="";

        String message="";
        if (throwable instanceof ApiException){
			return new ApiException(throwable.getMessage()).getMessage();
        }
		return throwable;


	}

//	/**
//	* @Title: handleError
//	* @Description: TODO(这里用一句话描述这个方法的作用)
//	* @param    参数
//	* @return Object    返回类型
//	* @author StriverFeng
//	* @time 2017年8月13日下午3:59:58
//	* @throws
//	*/
//	private Object handleError(ApiException e) {
//		return e;
// 
//	}

	/**
	 * @throws ApiException 
	* @Title: bulidParams
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return Object []    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:56:48
	* @throws
	*/
	private Object[] bulidParams(ApiRunnable run, String paramJson, HttpServletRequest request,
			HttpServletResponse response) throws ApiException{
		Map<String,Object> map=null;
		try {
			map=UtilJson.toMap(paramJson);
		} catch (IllegalArgumentException e) {
			 throw new ApiException("调用失败:json字符串格式异常,请检查params参数");
		}
		if (map == null) {
			map=new HashMap<String,Object>();
		}
		Method method=run.getTargetMethod();
		List<String> paramNames=Arrays.asList(parameterUtil.getParameterNames(method));
		 
		Class<?>[] paramTypes=method.getParameterTypes(); //反射
		for (Map.Entry<String, Object> m : map.entrySet()) {
			if (!paramNames.contains(m.getKey())) {
				throw new ApiException("调用失败:接口不存在'"+m.getKey()+"'");
			}
		}
		Object[] args=new  Object[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			if (paramTypes[i].isAssignableFrom(HttpServlet.class)) {
				args[i]=request;
			}else if (map.containsKey(paramNames.get(i))) {
				try {
					args[i]=UtilJson.covertValue(map.get(paramNames.get(i)),paramTypes[i]);
//					args[i]convertJsonToBean((String)map.get(paramNames.get(i)),paramTypes[i]);
				} catch (Exception e) {
					 throw new ApiException("调用失败:指定参数格式错误或值错误'"+paramNames.get(i)+"'"+e.getMessage());
				} 
			}
		}
		return args;
	}

	/**
	 * @param <T>
	* @Title: convertJsonToBean
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return Object    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:31:23
	* @throws
	*/
	private <T> Object convertJsonToBean(String jsonStr, Class<T> clazz) {
		
		
		
		return null;


	}

 
	
	/**
	* @Title: sysParamsValdate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return ApiRunnable    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:45:40
	* @throws
	*/
	private ApiRunnable sysParamsValdate(HttpServletRequest request) throws ApiException{
		String apiName=request.getParameter(METHOD);
		String json=request.getParameter(PARAMS);
		ApiRunnable api;
		if (apiName == null || apiName.trim().equals("")) {
			throw new ApiException("调用失败:参数'method'为空");
			
		}else if(json == null){
			throw new ApiException("调用失败:参数'params'为空");
		}else if ((api = apiStore.findApiRunnable(apiName)) == null) {
			throw new ApiException("调用失败:指定API不存在,API:"+apiName);
		}
		return api;
	}
	 public static void main(String[] args) throws JsonMappingException, JsonParseException, IOException {
		 String mapString="{\"goods\":{\"goodsName\":\"daa\",\"goodsId\":\"1111\"},\"id\":19}";
		 Map<String,Object> map=UtilJson.toMap(mapString);
		 System.err.println(map);
		 String goodsStr = (String) map.get("goods");
//		 Goods goodsPrase = UtilJson.covertValue(goodsStr, Goods.class);
//		 System.out.println("goodsPrase"+goodsStr);
		 String str="{\"goodsName\":\"daa\",\"goodsId\":\"1111\"}";
		 String str2="{\"userId\":\"123\"}";
		 Goods goods=UtilJson.covertValue(str,Goods.class);
		 System.out.println(goods);
		 
		 
		 
	}
}