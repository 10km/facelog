package net.gdface.service.facelog;

import java.nio.charset.Charset;

import com.facebook.swift.service.XHRHomepageProvider;
import com.google.common.io.Resources;

import net.gdface.facelog.Version;

public class FacelogXHRHomepageProvider implements XHRHomepageProvider {

	@Override
	public byte[] homepage() throws Exception {
		String homepage = Resources.toString(FacelogXHRHomepageProvider.class.getResource("/facelog_homepage.html"), 
				Charset.forName("utf-8"));
		return homepage.replaceAll("\\$\\{version\\}", Version.VERSION).getBytes();
	}

}
