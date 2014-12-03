/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.*;

/**
 *
 * @author bill
 */
public class Testing {

    private static void writeInt(ByteBuffer out, int val) {

        out.put((byte) (val >> 0));
        out.put((byte) (val >> 8));
        out.put((byte) (val >> 16));
        out.put((byte) (val >> 24));
        //out.write(val >> 8);
        //out.write(val >> 16);
        //out.write(val >> 24);
    }

    public static void main(String[] args) {
        long rate1 = 44100;
        long rate2 = 88200;
        int tester = 32;
        long tester2 = 88200;
        int rate3 = 88200;
        String poo = Integer.toHexString((int) rate1);
        System.out.println(poo);
        ByteBuffer bb = ByteBuffer.allocate(8);
        byte[] testbyte;
        bb.asIntBuffer();
        bb.putInt((int) (rate1 & 0xffffffffffffffL));
        bb.putInt((int) (rate2 & 0xffffffffffffffL));
        testbyte = bb.array();

        for (int i = 0; i < 8; i++) {
            System.out.println("Index:  " + testbyte[i]);
            System.out.println((byte) (testbyte[i]));
            System.out.println("TESTS " + ((byte) rate2 >>> 16));

        }

        ByteBuffer bitwiseTest = ByteBuffer.allocate(8);
        writeInt(bitwiseTest, 44100);
        writeInt(bitwiseTest, 88200);
        System.out.println(tester >> 2);
        System.out.println(tester >>> 2);
        System.out.println(tester2 >> 1 & 0xffffffffffffffffL);
        //System.out.println(bb.toString());
        //System.out.println(bb.limit());

        int bitwise = 44100;
        bitwise = bitwise >>> 1;
        System.out.println(Integer.toHexString(44100));
        System.out.println(Integer.toHexString(88200));
        System.out.println(Integer.toHexString(bitwise));
        System.out.println("Bitwise Int");
        for (int i = 0; i < 8; i++) {
            byte output = bitwiseTest.get(i);
            System.out.println(Byte.toString(output));
        }

        ByteBuffer nonBitwise = ByteBuffer.allocate(8);
        nonBitwise.putInt(44100);
        nonBitwise.putInt(88200);
        System.out.println("NonBitwise: ");
        for (int i = 0; i < 8; i++) {
            byte output = nonBitwise.get(i);

            System.out.println(Byte.toString(output));
        }

    }
}
