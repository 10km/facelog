package net.gdface.facelog.simplemq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import net.gdface.facelog.simplemq.IKeyHelper;
import net.gdface.facelog.simplemq.redis.RedisTable;

public class TestKVTable {
	private static final Logger logger = LoggerFactory.getLogger(TestKVTable.class);

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
	@Test
	public void testGetSetField() {
		RedisTable<Group> table = new RedisTable<Group>(Group.class);
		table.setField("100", "nullStr", null, true);
		System.out.println(table.getField("100", "nullStr"));
	}
	
	@Test
	public void testGetFields(){
		RedisTable<Group> table = new RedisTable<Group>(Group.class);
		Map<String, Object> values = table.getFields("100", (String[])null);
		for(Entry<String, Object> entry:values.entrySet()){
			System.out.printf("%s %s\n",entry.getKey(),entry.getValue());
		}
	}
	
	@Test
	public void testGetFieldNames(){
		RedisTable<Group> table = new RedisTable<Group>(Group.class);
		for(String field:table.getFieldNames()){
			logger.info(field);
		}
	}
}
