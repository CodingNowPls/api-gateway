/**
 * 
 */
package com.cn.striverfeng.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.cn.striverfeng.common.ApiRunnable;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;


/**
 * @author StriverFeng
 *api仓库
 * 说明:
 */
public class ApiStore {
	
	private ApplicationContext applicationContext;
	private HashMap<String,ApiRunnable> apiMap=new HashMap<String,ApiRunnable>();
	public ApiStore(ApplicationContext applicationContext) {
		Assert.notNull(applicationContext);
		this.applicationContext = applicationContext;
	}
	
	public void loadApiFromSpringBeans() {
		// ioc all bean
		String[] names=applicationContext.getBeanDefinitionNames();
		Class<?> type;
		//反射
		for (String name : names) {
			type=applicationContext.getType(name);
			for (Method m : type.getDeclaredMethods()) {
				APIMapping apiMapping=m.getAnnotation(APIMapping.class);
				if (apiMapping != null) {
					addApiItem(apiMapping,name,m);
				}
			}
			
		}
		
		
	}

	public ApiRunnable findApiRunnable(String apiName){
		return apiMap.get(apiName);
	}
	
	public ApiRunnable findApiRunnable(String apiName,String version){
		return (ApiRunnable)apiMap.get(apiName+"_"+version);
	}
	
	
	/**
	* @Title: 添加api
	* @Description: TODO api配置
	* @return void    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午3:31:43
	* @throws
	*/
	private void addApiItem(APIMapping apiMapping, String beanName, Method method) {
		ApiRunnable apiRun=new ApiRunnable();
		apiRun.apiName=apiMapping.value();
		apiRun.targetMethod=method;
		apiRun.targetName=beanName;
		apiMap.put(apiMapping.value(), apiRun);
	}
	
	public List<ApiRunnable> findApiRunnables(String apiName){
		if (apiName == null) {
			throw new IllegalArgumentException("api name must not null");
		}
		List<ApiRunnable> list=new  ArrayList<ApiRunnable>(20);
		for (ApiRunnable api : apiMap.values()) {
			if (api.apiName.equals(apiName)) {
				list.add(api);
			}
		}
		return list;
	}	
	public List<ApiRunnable> getAll(){
		List<ApiRunnable> list=new ArrayList<ApiRunnable>(20);
		list.addAll(apiMap.values());
		Collections.sort(list,new Comparator<ApiRunnable>() {

			@Override
			public int compare(ApiRunnable o1, ApiRunnable o2) {
				
				return 0;  
			}
		});
		return list;
	}
}
