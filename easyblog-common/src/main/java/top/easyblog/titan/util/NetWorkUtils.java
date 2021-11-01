package top.easyblog.titan.util;


import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author frank.huang
 */
@Slf4j
public final class NetWorkUtils {


    /***内网IP*/
    public static final String INTRANET_IP = getIntranetIp();
    /***外网IP*/
    public static final String INTERNET_IP = getInternetIp();
    /***外网ip字节数组*/
    public static final byte[] INTERNET_IP_BYTES = getInternetIpBytes();
    /***内网ip字节数组*/
    public static final byte[] INTRANET_IP_BYTES = getIntranetIpBytes();
    /*** IP138 ip查询接口*/
    private static final String IP138 = "http://www.ip138.com/ips138.asp?ip=";
    /***太平洋网IP查询接口*/
    private static final String PCONLINE = "http://whois.pconline.com.cn/ipJson.jsp?ip=";


    /**
     * 获得内网IP
     *
     * @return 内网IP
     */
    public static String getIntranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得外网IP
     *
     * @return 外网IP
     */
    public static String getInternetIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(INTRANET_IP)) {
                        return ip.getHostAddress();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果没有外网IP，就返回内网IP
        return INTRANET_IP;
    }

    /**
     * 获得外网IP
     *
     * @return 外网IP
     */
    public static byte[] getInternetIpBytes() {
        return ipv4Address2BinaryArray(getInternetIp());
    }

    /**
     * 获取内网ip
     *
     * @return
     */
    public static byte[] getIntranetIpBytes() {
        return ipv4Address2BinaryArray(getIntranetIp());
    }

    /**
     * 将给定的用十进制分段格式表示的ipv4地址字符串转换成字节数组
     *
     * @param ipAdd
     * @return
     */
    public static byte[] ipv4Address2BinaryArray(String ipAdd) {
        byte[] binIP = new byte[4];
        String[] strs = ipAdd.split("\\.");
        for (int i = 0; i < strs.length; i++) {
            binIP[i] = (byte) Integer.parseInt(strs[i]);
        }
        return binIP;
    }


}

