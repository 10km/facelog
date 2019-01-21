package net.gdface.facelog.service.plugin;

import org.apache.maven.plugin.AbstractMojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.gdface.service.facelog.FacelogServiceMain;

/**
 * maven 插件<br>
 * 执行facelog服务启动,等同于执行{@link Main#main(String[])}
 * 
 * @author guyadong
 *
 */
@Mojo(name = "run", requiresProject = false)
public class RunMojo extends AbstractMojo {
	/**
	 * Set this to true to allow Maven to continue to execute after invoking the
	 * run goal.
	 *
	 */
	@Parameter(property = "maven.facelog.fork", defaultValue = "false")
	private boolean fork;

	public RunMojo() {
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		FacelogServiceMain.main(null);
		if (!fork) {
			waitIndefinitely();
		}
	}

	/**
	 * Causes the current thread to wait indefinitely. This method does not
	 * return.
	 */
	private void waitIndefinitely() {
		Object lock = new Object();

		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				getLog().warn("RunMojo.interrupted", e);
			}
		}
	}
}
