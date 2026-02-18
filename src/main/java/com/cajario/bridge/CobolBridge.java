package com.cajario.bridge;

public class CobolBridge {
    static {
        System.loadLibrary("cajario_native");
    }

    public native String callCajaRio(long tarjeta, int pin, double monto);
}