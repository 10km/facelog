package net.gdface.facelog.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestKVTable {
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
	public void testSetGet() {
		Group group = new Group();
		group.setId(100L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.addUser(guestUser);
		group.addUser(rootUser);
		RedisTable<Group> table = new RedisTable<Group>(Group.class);
		table.setKeyHelper(new IKeyHelper<Group>(){
			@Override
			public String returnKey(Group v) {
				return v.getId().toString();
			}});
		table.set(group, false);
		System.out.println(JSON.toJSONString(group,SerializerFeature.WriteMapNullValue));
		Group deserialized = table.get(group.getId().toString());
		System.out.println(JSON.toJSONString(deserialized,SerializerFeature.WriteMapNullValue));
	}
	//@Test
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
}
