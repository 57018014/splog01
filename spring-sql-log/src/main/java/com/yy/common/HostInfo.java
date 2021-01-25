package com.yy.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;

/**
 * @author yaoyuan
 * @createTime 2019/12/04 8:44 PM
 */
public class HostInfo {

    private static final String HOST_NAME;

    static {
        try {
            InetAddress host = getLocalHost();
            HOST_NAME = host != null ? host.getHostName() : null;
//            IP = getByName(HOST_NAME).getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHostName() {
        return HOST_NAME;
    }
}
