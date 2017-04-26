package br.com.munif.macinfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class HardwareInformation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MAC VALUE---->" + Encrypter.getMacValue());
        Encrypter en = new Encrypter();// new Encrypter("F23C9124F1A1");
        System.out.print("---->");
        //String encrypt = "SSTjeFnydggsP14VYDNInAVAIb4IOZDA";
        //en.encrypt("Munif Gebara Junior");

        String encrypt = scanner.nextLine();
        System.out.println(encrypt);
        String decrypt = en.decrypt(encrypt);
        System.out.println("---->" + decrypt);
    }

    public static void main2(String[] args) {

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                System.out.print("----> " + network.getDisplayName() + " " + network.getName());
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    mac = new byte[]{0, 0, 0, 0, 0, 0};
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
