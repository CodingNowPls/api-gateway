/**
 * 
 */
package com.cn.striverfeng.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * @author StriverFeng
 *
 * 说明:
 */
public class UtilJson {

    private static ObjectMapper objectMapper = new ObjectMapper();  
    private static JsonFactory jsonFactory = new JsonFactory();  
  
    static {  
        objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);  
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);  
    }  
  
	
	public static final String JSON_MAPPER = null;

	/**
	* @Title: toMap
	* @Description: TODO
	* @return Map<String,Object>    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:32:28
	* @throws
	*/
	public static Map<String, Object> toMap(String paramJson) {
		Map<String, Object> map=null;
		try {
			map = objectMapper.readValue(paramJson, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return map;


	}

	/**
	* @Title: writeValueAsString
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午4:41:01
	* @throws
	*/
	public static String writeValueAsString(Object result)  throws JsonMappingException, JsonGenerationException, IOException{
		return toJson(result, false);
	}

	/**
	 * @return 
	* @Title: covertValue
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return void    返回类型
	* @author StriverFeng
	* @time 2017年8月13日下午5:59:21
	* @throws
	*/
	public static <T> T covertValue(String json, Class<T> clazz) throws JsonMappingException, JsonParseException, IOException {
		return objectMapper.readValue(json, clazz);
 
	}
	//UtilJson.covertValue(map.get(paramNames.get(i)),paramTypes[i]);
	public static <T> T covertValue(Object object, Class<T> clazz) throws JsonMappingException, JsonParseException, IOException, IllegalAccessException, InstantiationException {
        T t=clazz.newInstance();
        t= (T) object;
	    return t;

	}



    public static ObjectMapper getObjectMapper() {  
        return objectMapper;  
    }  
    
    
    /** 
     * Object对象转json 
     * 2015年4月3日上午10:41:53 
     * auther:shijing 
     * @param pojo 
     * @return 
     * @throws JsonMappingException 
     * @throws JsonGenerationException 
     * @throws IOException 
     */  
    public static String objectToJsonString(Object pojo) throws JsonMappingException, JsonGenerationException, IOException {  
        return toJson(pojo, false);  
    }  
  
    public static String toJson(Object pojo, boolean prettyPrint) throws JsonMappingException, JsonGenerationException,  
            IOException {  
        StringWriter sw = new StringWriter();  
        JsonGenerator jg = jsonFactory.createJsonGenerator(sw);  
        if (prettyPrint) {  
            jg.useDefaultPrettyPrinter();  
        }  
        objectMapper.writeValue(jg, pojo);  
        return sw.toString();  
    }  
  
    public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint) throws JsonMappingException,  
            JsonGenerationException, IOException {  
        JsonGenerator jg = jsonFactory.createJsonGenerator(fw);  
        if (prettyPrint) {  
            jg.useDefaultPrettyPrinter();  
        }  
        objectMapper.writeValue(jg, pojo);  
    }  
    
    
}
