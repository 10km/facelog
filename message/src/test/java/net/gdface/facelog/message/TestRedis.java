package net.gdface.facelog.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Transaction;
import redis.clients.util.JedisURIHelper;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRedis {
	private JedisPool pool;
	/**
	 * 初始化连接池
	 */
	@Before
	public void init() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxTotal(10);
	    pool = new JedisPool(jedisPoolConfig, "localhost");
        System.out.println("连接池初始化成功");
	}
	
    @Test
    public void test1Ping(){
    	// Jedis 实现了Closeable接口,所以这里可以用java 1.7 try-with-resources语法自动完成close
    	try(Jedis jedis = pool.getResource()){
            //查看服务是否运行 PING
            System.out.println("服务正在运行: "+jedis.ping());
    	}
    }
    
    @Test
    public void test2String(){
    	try(Jedis jedis = pool.getResource()){
            //设置 redis 字符串数据 SET 10km blog.csdn.net/10km
            jedis.set("10km", "blog.csdn.net/10km");
            // 获取存储的数据并输出
            System.out.println("redis 存储的字符串为: "+ jedis.get("10km"));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    @Test
    public void test3Key(){
    	try(Jedis jedis = pool.getResource()){
            //设置 redis 字符串数据 SET 10km blog.csdn.net/10km
            Set<String> keys = jedis.keys("*");
            // 获取存储的数据并输出
            int count=0;
            for(String key:keys)
            	System.out.println(++count + ":"+ key);
    	}
    }
    
    @Test
	public void test4List() {
		try (Jedis jedis = pool.getResource()) {
			// 选择数据库:  SELECT 2
			jedis.select(2);
			// 存储数据到列表中
			// LPUSH 
			jedis.lpush("phone_list", "Apple");
			jedis.lpush("phone_list", "Huawei");
			jedis.lpush("phone_list", "XiaoMi");

			// 获取存储的数据并输出: LRANGE phone_list 0 2
			List<String> list = jedis.lrange("phone_list", 0, 2);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("phone_list 列表项为: " + list.get(i));
			}
		}
	}
    
    @Test
    public void test5Transaction(){
    	System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    	try(Jedis jedis = pool.getResource()){
    		Transaction ctx = jedis.multi();
            System.out.println("resp:"+ctx.set("10km", "blog.csdn.net/10km"));
            System.out.println("resp:"+ctx.set("hello", "world"));
            List<Object> list = ctx.exec();
            if(list.isEmpty()){
            	System.out.println("Transaction fail...");
            }else{
            	for(Object e:list){
            		System.out.println("exec: " + e);
            	}
            }
    	}
    }
    
    @Test
    public void test6MSet(){
    	System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    	try(Jedis jedis = pool.getResource()){
    		String res = jedis.mset("k1", "v1","k2", "v2","k3", "v3");
    		for(String element:jedis.keys("k*")){
    			System.out.println(element);
    		}
    	}
    }    
    @Test
    public void test7JedisURI() throws URISyntaxException{
    	System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    	URI jedisUri = new URI("jedis",":hello",Protocol.DEFAULT_HOST,Protocol.DEFAULT_PORT,"/1",null,null);
    	System.out.println(jedisUri);
    	System.out.printf("password [%s] dbindex %d \n", JedisURIHelper.getPassword(jedisUri),JedisURIHelper.getDBIndex(jedisUri));
    }    
    
    /**
     * 程序关闭时，需要调用关闭方法
     */
    @After
    public void end(){    	
    	if(null != pool){
    		pool.destroy();
    		System.out.println("连接池关闭");
    	}
    		
    }
}
