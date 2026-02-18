package com.cajario.api;
import com.cajario.bridge.CobolBridge;

public class CajeroAutomatico {
    public static void main(String[] args) {
        CobolBridge banco = new CobolBridge();
        System.out.println("=== BIENVENIDO A CAJARIO ===");
        
        procesar(banco, 450012345678L, 1234, 500.0);  
        procesar(banco, 450012345678L, 9999, 100.0);  
        procesar(banco, 450012345678L, 1234, 20000.0); 
    }

    private static void procesar(CobolBridge b, long t, int p, double m) {
        System.out.println("\nIntentando retiro de: " + m + " EUR...");
        String rawStatus = b.callCajaRio(t, p, m);
        
        // Limpiamos espacios en blanco invisibles
        String status = rawStatus.trim();
        
        if (status.equals("00")) {
            System.out.println(">> OK: Operacion Exitosa. Retire su efectivo.");
        } else if (status.equals("01")) {
            System.out.println(">> ERROR: PIN INCORRECTO.");
        } else if (status.equals("02")) {
            System.out.println(">> ERROR: SALDO INSUFICIENTE.");
        } else {
            System.out.println(">> ERROR: Fallo en comunicacion (Status recibido: [" + status + "])");
        }
    }
}