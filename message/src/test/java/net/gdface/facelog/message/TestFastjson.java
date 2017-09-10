package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.junit.Assert;

public class TestFastjson {
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
	    private List<User> users = new ArrayList<User>();
	    public Date date=new Date();
	    public java.sql.Date sqldate=new java.sql.Date(date.getTime());
	    public byte[] array=new byte[]{22,33,3,2,3,1,5,-1};
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
		String jsonstr = JSON.toJSONString(group);
		System.out.println(jsonstr);
		Map<String, Object> jsonObj = JSON.parseObject(jsonstr);
		for( Entry<String, Object> entry:jsonObj.entrySet()){
			System.out.println(entry.getKey() +"  "+ entry.getValue());
			//entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		//System.out.println(JSON.toJSONString(jsonObj));
		
	}
	class GenBean<T>{
		public T b;
	}
	@Test
	public void testTypeReference(){
    	//System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    	Type type = new TypeReference<GenBean<String>>() {}.getType();
    	if(type instanceof ParameterizedType)
    		System.out.println(((ParameterizedType)type).getActualTypeArguments()[0]);
    	else
    		System.out.println(type);
	}
	@Test
	public void testisJavaBean(){
		byte[] bytes= new byte[]{32,44,125,2};
		System.out.println(JSON.toJSON(bytes).toString());
		System.out.println(JSON.toJSONString(bytes));
	}
	@Test 
	public void testSimpleObject(){
		Date date=new Date();
		Object jsonDate = JSON.toJSON(date);
		System.out.println(jsonDate.getClass().getName());
		int _int= 32;
		Object jsonInt = JSON.toJSON(_int);
		System.out.println(jsonInt.getClass().getName());
	}
}
