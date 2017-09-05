package com.striverfeng.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;


/**
 * @author StriverFeng
 *api仓库
 * 说明:
 */
public class ApiStore {

    private ApplicationContext applicationContext;
    private HashMap<String, ApiRunnable> apiMap = new HashMap<String, ApiRunnable>();

    public ApiStore(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext);
        this.applicationContext = applicationContext;
    }

    public void loadApiFromSpringBeans() {
        // ioc all bean
        String[] names = applicationContext.getBeanDefinitionNames();
        Class<?> type;
        //反射
        for (String name : names) {
            type = applicationContext.getType(name);
            for (Method m : type.getDeclaredMethods()) {
                APIMapping apiMapping = m.getAnnotation(APIMapping.class);
                if (apiMapping != null) {
                    addApiItem(apiMapping, name, m);
                }
            }

        }


    }

    public ApiRunnable findApiRunnable(String apiName) {

        return apiMap.get(apiName);
    }

    public ApiRunnable findApiRunnable(String apiName, String version) {

        return apiMap.get(apiName + "_" + version);
    }


    /**
     * @return void    返回类型
     * @throws
     * @Title: 添加api
     * @Description: TODO api配置
     * @author StriverFeng
     * @time 2017年8月13日下午3:31:43
     */
    private void addApiItem(APIMapping apiMapping, String beanName, Method method) {
        //执行接口规范的自动检测
        for (Field field:method.getReturnType().getDeclaredFields()){
            if (field.getType().equals(Object.class)){
                throw new RuntimeException("接口模型不符合规范,请改正:"+method.getName());
            }
        }
        ApiRunnable apiRun = new ApiRunnable();
        apiRun.apiName = apiMapping.value();
        apiRun.targetMethod = method;
        apiRun.targetName = beanName;
        apiMap.put(apiMapping.value(), apiRun);
    }

    public List<ApiRunnable> findApiRunnables(String apiName) {
        if (apiName == null) {
            throw new IllegalArgumentException("api name must not null");
        }
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        for (ApiRunnable api : apiMap.values()) {
            if (api.apiName.equals(apiName)) {
                list.add(api);
            }
        }
        return list;
    }

    public List<ApiRunnable> getAll() {
        List<ApiRunnable> list = new ArrayList<ApiRunnable>(20);
        list.addAll(apiMap.values());
        Collections.sort(list, new Comparator<ApiRunnable>() {

            @Override
            public int compare(ApiRunnable o1, ApiRunnable o2) {
                return 0;
            }
        });
        return list;
    }

    public  ApplicationContext getApplicationContext(){
        return applicationContext;
    }
     public class ApiRunnable {
        //加注解的api全称
        public String apiName;
        //bean名称
        public String targetName;
        //目标方法
        public Method targetMethod;
        //serviceImpl实例
        public Object target;

        /**
         * @return Object    返回类型
         * @throws
         * @Title: run
         * @Description: TODO
         * @author StriverFeng
         * @time 2017年8月13日下午7:57:52
         */
        public Object run(Object[] args) throws InvocationTargetException, Exception {
            if (null == target) {
                target = applicationContext.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public Class<?>[] getParamTypes() {
            return targetMethod.getParameterTypes();
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

        public void setTargetMethod(Method targetMethod) {
            this.targetMethod = targetMethod;
        }

        public Object getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }
    }
}
