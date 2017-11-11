// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: ConfigUtils.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
/**
 * 配置文件读取工具类
 * @author guyadong
 *
 */
public class ConfigUtils {
    /**
     * 顺序加载不同位置的properties文件,加载顺序为:<br>
     * 1.调用{@link ClassLoader#getResource(String)}方法在{@code clazz}所在位置查找,如果失败则抛出异常<br>
     * 2.如果class在jar包中,则尝试读取在jar所在位置../confFolder/propFile,tomcat下即为WEB-INF/confFolder/propFile<br>
     * 3.如果环境变量envVar定义，则从envVar指定的目录下读取propFile<br>
     * 4.user.dir下查找confFolder/propFile加载配置<br>
     * 后面的配置变量会覆盖前面的定义<br>
     * @param propFile 要加载的properties文件名,为{@code null}或空时抛出异常 {@link IllegalArgumentException}
     * @param confFolder popFile所在文件夹,{@code null}时使用默认值'conf'
     * @param envVar 环境变量名 用于定义propFile位置,可为{@code null}
     * @param clazz 用于获取 {@link ClassLoader}的类,为null时使用本类的class
     * @param showProp 加载后是否显示所有值
     * @return 返回加载后的{@link Properties}对象
     */
    public static Properties loadAllProperties(String propFile, String confFolder, String envVar, Class<?> clazz, boolean showProp) {
        if(null==propFile||propFile.isEmpty()){
            throw new IllegalArgumentException("the argument 'propFile' must not be null or empty");
        }
        if (null == confFolder){
            confFolder = "conf";
        }
        if (null == clazz){
            clazz = ConfigUtils.class;
        }
        final String fileSeparator = System.getProperty("file.separator");
        String prop_path = confFolder.concat(System.getProperty("file.separator")).concat(propFile);
        Properties props = new Properties();
        Set<File> loaded_files = new HashSet<File>();
        try {
            // 在jar包中查找默认配置文件
            URL url = clazz.getClassLoader().getResource(prop_path.replace(fileSeparator, "/"));
            if(null==url){
                throw new ExceptionInInitializerError(String.format("not found default properties %s", prop_path));
            }
            loadProperties(url, props);
        } catch (Exception e) {
            // 默认配置必须加载成功否则抛出异常
            throw new ExceptionInInitializerError(String.format("fail to load default properties(加载默认配置文件失败) %s cause by %s", prop_path,
                    e.getMessage()));
        }
        try {
            // 加载 jar包所在位置 ../conf/cassdk.properties
            URL class_location = clazz.getProtectionDomain().getCodeSource().getLocation();
            if (class_location.toString().endsWith(".jar")) {
                // jar包所在目录的父目录,tomcat下即为WEB-INF
                File jar_parent = new File(class_location.getPath()).getParentFile().getParentFile();
                if (null != jar_parent) {
                    File conf_file = new File(jar_parent, prop_path);
                    if (conf_file.isFile()) {
                        loadProperties(conf_file.toURI().toURL(), props);
                        loaded_files.add(conf_file);
                    }
                }
            }
        } catch (Exception e) {
        }
        try {
            // 通过环境变量查找properties文件
            if (envVar != null && !envVar.isEmpty()) {
                String cf = System.getProperty(envVar);
                if (null != cf&&!cf.isEmpty()) {
                    File env_file = new File(cf, propFile);
                    if (!loaded_files.contains(env_file)) {
                        loadProperties(env_file.toURI().toURL(), props);
                        loaded_files.add(env_file);
                    }
                } else {
                    log("not defined environment variable '%s'", envVar);
                }
            }
        } catch (Exception e) {
        }
        try {
            // 在当前路径下查找配置文件
            File propInUserDir = new File(System.getProperty("user.dir"), prop_path);
            if (propInUserDir.isFile() && !loaded_files.contains(propInUserDir)) {
                loadProperties(propInUserDir.toURI().toURL(), props);
                loaded_files.add(propInUserDir);
            }
        } catch (Exception e) {
        }

        // 输出所有参数值
        if(showProp){
            props.list(System.out);
        }
        return props;
    }

    /**
     * configure with the parameters given in the given url
     * 
     * @param url
     *            the resource filename to be used
     * @param props
     *            dest properties to add
     * @throws IOException
     */
    private static void loadProperties(URL url, Properties props) throws IOException {
        if (null != url) {
            InputStream is = null;
            try {
                props.load(is = url.openStream());
                log("Load properties from %s", url.toString());
            } finally {
                if (is != null){
                    is.close();
                }
            }            
        }
    }
    private static void log(String format, Object ... args){
        System.out.printf("[%s:%d]%s\n", 
                ConfigUtils.class.getSimpleName(),
                Thread.currentThread() .getStackTrace()[2].getLineNumber(),
                String.format(format, args));
    }
}
