package br.com.agroinvest.jwt;

import java.security.Key;

import io.jsonwebtoken.impl.crypto.MacProvider;

/*
 * A chave é uma variavel final e static onde só será instanciada uma vez enquanto o servidor estiver online
 *  
 */
public class Chave {
	
private static final Key KEY = MacProvider.generateKey();

	
	public static Key getKey() {
		return KEY;
	}
	
}
