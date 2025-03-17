package br.com.barbosa.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGeneratorConfig {
    public static String generateBase64SecretKey() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        String chaveBase64 = generateBase64SecretKey();
        System.out.println("Chave Secreta (Base64): " + chaveBase64);
    }
}
