package net.gdface.facelog;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;


public class TestSerialVersionUID {
	/**
	 * 生成MD5校验码
	 * 
	 * @param source
	 * @return
	 */
	static public byte[] getMD5(byte[] source) {
		if (null==source)
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(source);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将16位byte[] 转换为32位的HEX格式的字符串String
	 * 
	 * @param buffer
	 * @return
	 */
	static public String toHex(byte buffer[]) {
		if (null==buffer)
			return null;
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}
		return sb.toString();
	}

	/**
	 * 将字节数组转为long<br>
	 * 如果input为null,或offset指定的剩余数组长度不足8字节则抛出异常
	 * @param input 
	 * @param offset 起始偏移量
	 * @param littleEndian 输入数组是否小端模式
	 * @return
	 */
	public static long longFrom8Bytes(byte[] input, int offset, boolean littleEndian){
		if(offset <0 || offset+8>input.length)
			throw new IllegalArgumentException(String.format("less than 8 bytes from index %d  is insufficient for long",offset));
		long value=0;
		for(int  count=0;count<8;++count){
			int shift=(littleEndian?count:(7-count))<<3;
			value |=((long)0xff<< shift) & ((long)input[offset+count] << shift);
		}
		return value;
	}
	public long getSerialVersionUID(String input){
		byte[] md5 = getMD5(input.getBytes());
		return longFrom8Bytes(md5,0, false)  ^ longFrom8Bytes(md5,8, false);
	}
	@Test
	public void test() throws IOException {
		String input="net.gdface.facelog.dborm.person.FlPersonBeanBase";
		byte[] md5 = getMD5(input.getBytes());
		System.out.println(toHex(md5));
		ByteArrayInputStream byteInput = new ByteArrayInputStream(md5);
		DataInputStream dataInput = new DataInputStream(byteInput);	
		long l1 = dataInput.readLong();
		long l2 = dataInput.readLong();
		System.out.printf("l1=0x%x l2=0x%x\n", l1,l2);
		long l3 = l1 ^ l2;
		
		System.out.println(l3);
		long ll1 = longFrom8Bytes(md5,0, false);
		long ll2 = longFrom8Bytes(md5,8, false);	
		System.out.printf("ll1=0x%x ll2=0x%x\n", ll1,ll2);
		System.out.println(ll1  ^ ll2);
		System.out.println(getSerialVersionUID(input));
	}

}
