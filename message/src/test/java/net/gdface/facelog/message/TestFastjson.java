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
		@SuppressWarnings("unchecked")
		Map<String, String> jsonObj = (Map<String, String>)  JSON.toJSON(group);
		for( Entry<String, String> entry:jsonObj.entrySet()){
			System.out.println(entry.getKey() +"  "+ JSON.toJSONString(entry.getValue()));
		}
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
		GenBean<Date> gb = new GenBean<Date>();
		gb.b=new Date();
		Object json = JSON.toJSON(gb);
		System.out.println(JSON.toJSON(gb));
		Type type = new TypeUtils<GenBean<Date>>(){}.getType();
		boolean b = new TypeUtils<GenBean<Date>>(){}.isJavaBean();
		System.out.println(b);
		System.out.println(TypeUtils.isJavaBean(gb.getClass()));
//		Assert.assertTrue(TypeUtils.<Group>isJavaBean());
//		Assert.assertTrue(TypeUtils.<GenBean<Date>>isJavaBean());

	}
}
