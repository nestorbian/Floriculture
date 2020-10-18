package com.nestor.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * ip工具类
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/18
 */
@Slf4j
public class IpUtil {
    private static final String UNKNOWN = "unknown";
    private static final List<String> LOCALHOST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");
    private static final String SEPARATOR = ",";

    /**
     * 获取远程请求ip
     *
     * @param request
     * @return java.lang.String
     * @date : 2020/10/18 15:42
     * @author : Nestor.Bian
     * @since : 1.0
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        System.out.println(request);
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.contains(ipAddress)) {
                    ipAddress = getLocalIp();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 获取本地ip
     *
     * @param
     * @return java.lang.String
     * @date : 2020/10/18 15:42
     * @author : Nestor.Bian
     * @since : 1.0
     */
    public static String getLocalIp() throws SocketException {
        String localIp = "";
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip;
        try {
            while (allNetInterfaces.hasMoreElements())
            {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address)
                    {
                        localIp = ip.getHostAddress();
                    }
                }
            }
        } catch (Throwable e) {
            log.error("获取本地ip发生错误", e);
        }

        return localIp;
    }
}
