package br.com.munif.macinfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

public class HardwareInformation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MAC VALUE---->" + Encrypter.getMacValue());
        Encrypter en = new Encrypter();// new Encrypter("F23C9124F1A1");
        String encrypt = en.encrypt("Munif Gebara Junior");
        System.out.println(encrypt);
        String decrypt = en.decrypt(encrypt);
        System.out.println("---->" + decrypt);
    }

}
