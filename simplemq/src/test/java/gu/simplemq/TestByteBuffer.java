package gu.simplemq;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class TestByteBuffer {
    public byte[] array=new byte[]{22,33,3,2,3,1,5,-1};
    public ByteBuffer byteBuffer =ByteBuffer.wrap(array);
	/**
	 * 返回buffer中所有字节(position~limit),不改变buffer状态
	 * @param buffer
	 * @return
	 */
	private static final byte[] getBytesInBuffer(ByteBuffer buffer){
		int pos = buffer.position();
		try{
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			return bytes;
		}finally{
			buffer.position(pos);
		}
	}
	@Test
	public void test() {
		byte[]dst = new byte[byteBuffer.remaining()];
		byteBuffer.get(dst);
		System.out.println(dst.length);
		byteBuffer.clear();
		byte[]dst2 = new byte[byteBuffer.remaining()];
		byteBuffer.get(dst2);
		System.out.println(dst2.length);
		ByteBuffer src3 = ByteBuffer.wrap(new byte[0]);
		System.out.println(src3.hasRemaining());
		byte[] dst3 = getBytesInBuffer(src3);
		System.out.println(dst3.length);
	}

}
