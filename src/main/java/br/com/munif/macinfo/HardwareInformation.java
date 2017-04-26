package br.com.munif.macinfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class HardwareInformation {

    public static void main(String[] args) {

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                System.out.print("----> "+network.getDisplayName()+" "+network.getName());
                byte[] mac = network.getHardwareAddress();
                if (mac == null){
                    mac=new byte[]{0,0,0,0,0,0};
                }
                System.out.print("Current MAC address : ");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println(sb.toString());
            }

        } catch (SocketException e) {

            e.printStackTrace();

        }

    }

}
