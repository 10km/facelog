package net.gdface.facelog;

/**
 * 数据密文生成接口
 * @author guyadong
 *
 */
public interface CryptographGenerator {

	/**
	 * 根据盐值生成{@code input}的密文
	 * @param input
	 * @param isMd5 {@code input}是否为MD5,
	 * 						为{@code false}代表{@code input}为明文,{@code true}指定{@code input}为MD5密文
	 * @return MD5密文
	 */
	String cryptograph(String input, boolean isMd5);

}