package com.example.bootstrap.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPv4Tool {
    public static String getWLANIPv4Address(){
        // 获得本机的所有网络接口
        Enumeration<NetworkInterface> nifs = null;
        try {
            nifs = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();

            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();

                if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
                    //网卡接口名称
                    String nifName = nif.getName();
                    //网卡接口地址
                    String addrHostAddress = addr.getHostAddress();
                    if(nifName.startsWith("wlan")||nifName.startsWith("WLAN")){
                        return addrHostAddress;
                    }
                }
            }
        }
        return "";
    }

}
