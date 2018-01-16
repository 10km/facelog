package net.gdface.facelog.service.plugin;

import org.apache.maven.plugin.AbstractMojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * maven 插件<br>
 * 用于docker镜像初始化
 * @author guyadong
 *
 */
@Mojo(name = "docker-init")
public class DockerInitMojo extends AbstractMojo {

	public DockerInitMojo() {
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// do nothing
		// 只为了创建docker镜像时自动下载所有依赖库
	}

}
