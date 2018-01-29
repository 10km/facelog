package net.gdface.facelog.service;

import net.gdface.utils.FaceUtilits;

/**
 * {@link CryptographGenerator} 接口实现
 * @author guyadong
 *
 */
public class CryptographBySalt implements ServiceConstant, CryptographGenerator {
	public static final CryptographBySalt INSTANCE = new CryptographBySalt();
	/** 加密盐值 */
	private final String salt;
	protected CryptographBySalt() {
		this.salt = CONFIG.getString(TOKEN_SALT);
	}

	@Override
	public String cryptograph(String input,boolean isMd5){
		return cryptograph(input,isMd5,salt);
	}
	/**
	 * 根据盐值生成{@code input}的密文
	 * @param input
	 * @param isMd5 {@code input}是否为MD5,
	 * 						为{@code false}代表{@code input}为明文,{@code true}指定{@code input}为MD5密文
	 * @param salt 盐值
	 * @return MD5密文
	 */
	private static final String cryptograph(String input,boolean isMd5,String salt){
		// 避免 password为null
		input = String.valueOf(input);
		// 避免 salt 为null
		salt = String.valueOf(salt);
		String inputMd5 = isMd5 && FaceUtilits.validMd5(input) 
				? input
				: FaceUtilits.getMD5String(input.getBytes());
		StringBuffer buffer = new StringBuffer(inputMd5.length() + salt.length());
		// 将盐值和input md5交替掺在一起形成一个新的字符串
		for(int i = 0,endIndex = Math.max(inputMd5.length(), salt.length());i<endIndex;++i){
			try{
				buffer.append(salt.charAt(i));
			}catch(IndexOutOfBoundsException e){}
			try{
				buffer.append(inputMd5.charAt(i));
			}catch(IndexOutOfBoundsException e){}
		}
		return FaceUtilits.getMD5String(buffer.toString().getBytes());
	}
}
