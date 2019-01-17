package net.gdface.facelog;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.io.LineReader;

/**
 * 文本LOGO输出
 * @author guyadong
 *
 */
public class Logo {

	/**
	 *  显示文本LOGO
	 */
	public static final void textLogo(){
		try(InputStreamReader reader = new InputStreamReader(Logo.class.getResourceAsStream("/logotext.txt"))) {
			LineReader lineReader = new LineReader(reader);
			String line ;
			while(null != (line = lineReader.readLine())){
				System.out.println(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
