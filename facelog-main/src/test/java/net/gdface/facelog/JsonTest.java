package net.gdface.facelog;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;

import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.service.Ack;

public class JsonTest {

	@SuppressWarnings("serial")
	@Test
	public void test() {
		try{
			{
				Type type = new TypeToken<Ack<Void>>(){}.getType();

				Ack<Void> ack = new Ack<Void>().setCmdSn(100).setDeviceId(1).setValue(null);
				System.out.println(JSON.toJSONString(ack));
				
				Ack<Void> deAck = JSON.parseObject(JSON.toJSONString(ack), type);
				System.out.println(deAck.getClass());
			}
			{
				Type type = new TypeToken<FaceBean>(){}.getType();
				System.out.println("type:" + type.getClass());
				System.out.println(JSON.toJSONString(type));
				Type deType = JSON.parseObject(JSON.toJSONString(type), type.getClass());
				System.out.println(deType);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
