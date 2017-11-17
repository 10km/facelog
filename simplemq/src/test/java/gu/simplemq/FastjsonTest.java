package gu.simplemq;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import gu.simplemq.json.ByteBufferCodec;

/**
 * @author guyadong
 *
 */
public class FastjsonTest {
	static{
		ParserConfig.global.putDeserializer(ByteBuffer.class, ByteBufferCodec.instance);
		SerializeConfig.globalInstance.put(ByteBuffer.wrap(new byte[]{}).getClass(), ByteBufferCodec.instance);
	}
	public interface Person<T>{
		
	}
	public static class User implements Person<String>{

	    private Long   id;
	    private String name;


	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	}
	public static  class Group {

	    private Long       id;
	    private String     name;
	    private boolean isNew=false;
	    private List<User> users = new ArrayList<User>(16);
	    public Date date=new Date();
	    private java.sql.Date sqldate=new java.sql.Date(date.getTime());
	    public byte[] array=new byte[]{22,33,3,2,3,1,5,-1};
	    public ByteBuffer byteBuffer =ByteBuffer.wrap(array);
	    public String nullStr=null;
	    private String privString="private string";
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public List<User> getUsers() {
	        return users;
	    }

	    public void setUsers(List<User> users) {
	    	this.users = users;
	    }

	    public void addUser(User user) {
	    	users.add(user);
	    }
	    
	    public boolean getModified(){
	    	return false;
	    }

		public boolean isNew() {
			return isNew;
		}

		public void setNew(boolean isNew) {
			this.isNew = isNew;
		}

		public java.sql.Date getSqldate() {
			return sqldate;
		}

		public void setSqldate(java.sql.Date sqldate) {
			this.sqldate = sqldate;
		}
		public void setSqldate(long sqldate) {
			this.sqldate =new java.sql.Date( sqldate);
		}
	}
	@Test
	public void test() {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.addUser(guestUser);
		group.addUser(rootUser);
		
		String serString = JSON.toJSONString(group.byteBuffer);
		System.out.println(serString);
		ByteBuffer deserialedByteBuffer = JSON.parseObject(serString,ByteBuffer.class);
		System.out.println(JSON.toJSONString(deserialedByteBuffer));
		String jsonString = JSON.toJSONString(group,SerializerFeature.UseISO8601DateFormat);

		System.out.println(jsonString);
		Group decodeGroup = JSON.parseObject(jsonString, Group.class);

		System.out.println(JSON.toJSONString(decodeGroup,SerializerFeature.UseISO8601DateFormat));
	}
	@Test
	public void testJSONObject() {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.addUser(guestUser);
		group.addUser(rootUser);


		{
			String jsonstr = JSON.toJSONString(group);
			System.out.println(jsonstr);
			Map<String, Object> jsonObj = JSON.parseObject(jsonstr);
			for (Entry<String, Object> entry : jsonObj.entrySet()) {
				//System.out.println(
				//		entry.getKey() + "  " + entry.getValue() + " " + entry.getValue().getClass().getName());
				entry.setValue(JSON.toJSONString(entry.getValue()));
			}

			Map<String, Object> deJsonObj = new HashMap<String, Object>(16);
			for (Entry<String, Object> entry : jsonObj.entrySet()) {
				String json = (String) entry.getValue();
				String key = entry.getKey();
				//System.out.printf("field %s json %s\n" ,key,json);
				Object field = JSON.parse( json);
				//System.out.printf("field %s parse type %s\n" ,key,field.getClass().getSimpleName());
				deJsonObj.put(entry.getKey(), field);
			}
			//System.out.println(JSON.toJSONString(deJsonObj));
			Group dgroup = JSON.parseObject(JSON.toJSONString(deJsonObj), Group.class);
			System.out.println(JSON.toJSONString(dgroup));
		}
		{
			JSONObject jsonObject = (JSONObject) JSON.toJSON(group);
			Map<String, String> jsonObj = new HashMap<String, String>(16);
			for (Entry<String, Object> entry : jsonObject.entrySet()) {
				Object value = entry.getValue();
//				System.out.println(
//						entry.getKey() + "  " +(null == value? "null":value) + " " + (null == value? "":value.getClass().getName()));
				if(null !=value){
					String jsonValue=JSON.toJSONString(value);
					//System.out.printf("json value %s\n", jsonValue);
					jsonObj.put(entry.getKey(), jsonValue);
				}
			}

			Map<String, Object> deJsonMap = new HashMap<String, Object>(16);
			for (Entry<String, String> entry : jsonObj.entrySet()) {
				String json = (String) entry.getValue();
				String key = entry.getKey();
				//System.out.printf("field %s json %s\n" ,key,json);
				Object field = JSON.parse( json);
//				System.out.printf("field %s parse type %s\n" ,key,field.getClass().getSimpleName());
				deJsonMap.put(entry.getKey(), field);
			}
			//System.out.println(JSON.toJSONString(deJsonMap));
			//TypeUtils.cast 不支持 ByteBuffer 
			//Group dgroup = com.alibaba.fastjson.util.TypeUtils.cast(deJsonMap, Group.class, ParserConfig.global);
			//System.out.println(JSON.toJSONString(dgroup));
		}
		{
			//String jsonString = JSON.toJSONString(group,SerializerFeature.WriteMapNullValue);
			@SuppressWarnings("unchecked")
			Map<String, Object> json = (Map<String,Object>)JSON.toJSON(group);
			for(Entry<String, Object> entry:json.entrySet()){
				System.out.printf("%s %s\n",entry.getKey(),entry.getValue());
			}
			
		}
	}
	class GenBean<T>{
		public T b;
	}

	public<T> void testTypeRef(){
    	Type type = new TypeReference<GenBean<T>>(Date.class) {}.getType();
    	if(type instanceof ParameterizedType){
    		System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
    	}else{
    		System.out.println(type);
    	}
	}
	@Test
	public void testTypeReference(){
    	//System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    	Type type = new TypeReference<GenBean<String>>() {}.getType();
    	if(type instanceof ParameterizedType){
    		System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
    	}else{
    		System.out.println(type);
    	}
    	this.<Date>testTypeRef();
	}
	@Test
	public void testisJavaBean(){
		byte[] bytes= new byte[]{32,44,125,2};
		Object obj = JSON.parse("null");
		System.out.println(JSON.toJSON(bytes).toString());
		System.out.println(JSON.toJSONString(bytes));
		System.out.println(JSON.toJSONString(null));
	}
	@Test 
	public void testSimpleObject(){
		System.out.println(JSON.toJSONString("hello word"));
		Date date=new Date();
		Object jsonDate = JSON.toJSON(date);
		System.out.println(jsonDate.getClass().getName());
		int intVar= 32;
		Object jsonInt = JSON.toJSON(intVar);
		System.out.println(jsonInt.getClass().getName());
	}
	@Test
	public void testComputeGetters(){
		Type type = new TypeReference<GenBean<Date>>(){}.getType();
		Class<?> clazz = getRawClass(type);
		System.out.println(clazz.getName());
		List<FieldInfo> fieldInfo = com.alibaba.fastjson.util.TypeUtils.computeGetters(clazz, null);
		for(FieldInfo field:fieldInfo){
			System.out.printf("name %s %s\n",field.name,field.fieldType.toString());
		}
	}
	public Class<?> getRawClass(Type type){		
		if(type instanceof Class<?>){
			return (Class<?>)type;
		}
		else if ( type instanceof ParameterizedType){
			return getRawClass(((ParameterizedType)type).getRawType());
		}
		return null;
	}
	@Test
	public void testType(){
		Type type = new TypeReference<Map<Date,List<String>>>(){}.getType();
		String typeString = JSON.toJSONString(type);
		System.out.println(typeString);
		 Object dObj = JSON.parse(typeString);
		 System.out.println(dObj.getClass().getName());
//		 Object dtype = com.alibaba.fastjson.util.TypeUtils.cast(dObj, com.alibaba.fastjson.util.ParameterizedTypeImpl.class, null);
//		Type dType = JSON.parseObject(typeString, com.alibaba.fastjson.util.ParameterizedTypeImpl.class);
	}
	
}
