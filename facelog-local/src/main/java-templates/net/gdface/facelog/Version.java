package net.gdface.facelog;

import com.google.common.collect.ImmutableMap;

/**
 * version information for project ${project.groupId}:${project.artifactId}
 * @author guyadong
 */
public final class Version {
	/** project version */
    public static final String VERSION = "${project.version}";
    /** SCM(git) revision */
    public static final String SCM_REVISION= "${buildNumber}";
    /** SCM branch */
    public static final String SCM_BRANCH = "${scmBranch}";
    /** build timestamp */
    public static final String TIMESTAMP ="${buildtimestamp}";
    /** map of version fields */
    public static final ImmutableMap<String,String> INFO = ImmutableMap.of(
    		"VERSION", VERSION, 
    		"SCM_REVISION", SCM_REVISION, 
    		"SCM_BRANCH",SCM_BRANCH,
    		"TIMESTAMP", TIMESTAMP);
}