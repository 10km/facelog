package net.gdface.facelog.client.dtalk;

import gu.dtalk.TaskAdapter;
import net.gdface.facelog.mq.Ack;
import net.gdface.facelog.mq.Ack.Status;

/**
 * 重写{@link #makeAck(Object, Exception, String, Long)}方法,在响应消息中添加设备id
 * @author guyadong
 *
 */
public class FacelogTaskAdapter extends TaskAdapter {
	/**
	 * 静态变量<br>
	 * 当前设备的id，建议设备初始化时设置
	 */
	private static int deviceId = -1;
	public FacelogTaskAdapter(String queue) {
		super(queue);
	}

	/**
	 * @param deviceId 要设置的 deviceId
	 */
	public static void setDeviceId(int deviceId) {
		FacelogTaskAdapter.deviceId = deviceId;
	}
	/**
	 * 响应对象中设置设备id<br>
	 * 目前facelog中的{@link Ack}对象与dtalk框架中的Ack对象字段完全一样但没有关联
	 * @see gu.dtalk.TaskAdapter#makeAck(java.lang.Object, java.lang.Exception, java.lang.String, Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T,ACK> ACK makeAck(T res, Exception err, String ackChannel, Number taskid) {
		final Ack<T> ack = new Ack<T>()
				.setDeviceId(deviceId)
				.setStatus(Status.OK)
				.setValue(res)
				.setCmdSn(taskid.longValue());
		if(err != null){
			ack.setStatus(Status.ERROR).setStatusMessage(err.getMessage());
		}
		return (ACK) ack;
	}

}
