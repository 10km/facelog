package net.gdface.facelog.message;

import java.lang.reflect.Type;

public interface IPublish  {
	public void publish(Channel channel,Object obj,Type type);
}
