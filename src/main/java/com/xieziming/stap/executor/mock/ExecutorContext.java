package com.xieziming.stap.executor.mock;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Suny on 8/17/16.
 */
public class ExecutorContext {
    private static String executor;
    private static String hostName;
    private static String ip;
    private static String osName;

    public static String getExecutor() {
        if (executor == null) {
            executor = getHostName() + "_" + UUID.randomUUID().toString();
        }
        return executor;
    }

    public static String getHostName() {
        if (hostName == null) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                hostName = addr.getHostName().toString();
            } catch (UnknownHostException e) {
                hostName = "Unknown";
            }

        }
        return hostName;
    }

    public static String getIp() {
        if (ip == null) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress().toString();
            } catch (UnknownHostException e) {
                ip = "Unknown";
            }

        }
        return ip;
    }

    public static String getOsName() {
        if (osName == null) {
            Properties props=System.getProperties();
            osName = props.getProperty("os.name");
        }
        return osName;
    }
}
