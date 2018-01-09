package net.gdface.facelog.service;

import com.google.common.collect.ImmutableMap;

/**
 * version information for project
 * @author guyadong
 */
public final class Version {
	/** project version */
    public static final String VERSION = "${project.version}";
    /** SCM(git) revision */
    public static final String SCM_REVISION= "${buildNumber}";
    /** build timestamp */
    public static final String TIMESTAMP ="${timestamp}";
    /** map of version fields */
    public static final ImmutableMap<String,String> INFO = ImmutableMap.of(
    		"VERSION", VERSION, 
    		"SCM_REVISION", SCM_REVISION, 
    		"TIMESTAMP", TIMESTAMP);
}