package org.itstep;

import java.util.Arrays;

public class XorEncoder {
    private final byte key;

    public XorEncoder(byte key) {
        this.key = key;
    }

    public byte[] encode(byte[] bytes) {
        byte[] encoded = Arrays.copyOf(bytes, bytes.length);
        for (int i = 0; i < encoded.length; i++) {
            encoded[i] ^= key;
        }
        return encoded;
    }

    public byte[] decode(byte[] bytes) {
        return encode(bytes);
    }

    public static void main(String[] args) {
        var xor = new XorEncoder((byte) 65);
        String message = "Hello World!";
        byte[] encoded = xor.encode(message.getBytes());
        System.out.println("message = " + message);
        System.out.println("new String(encoded) = " + new String(encoded));
        byte[] decoded = xor.decode(encoded);
        System.out.println("new String(decoded) = " + new String(decoded));
    }
}
