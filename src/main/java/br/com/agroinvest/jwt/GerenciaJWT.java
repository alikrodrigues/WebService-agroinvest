package br.com.agroinvest.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/* Gera o token para cada usuario online 
 * usando a chave statica e comprimindo para obter um token menor possivel
 */

public class GerenciaJWT {
	Key key = Chave.getKey();
	
	public String createJWT(String role) {
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		data = data.plusHours(4);
		String compactJws = Jwts.builder()			
		  .setExpiration(Date.from(data.atZone(ZoneId.of("America/Sao_Paulo")).toInstant()))
		  .setSubject(role) //role
		  .compressWith(CompressionCodecs.DEFLATE)
		  .signWith(SignatureAlgorithm.HS512, key)
		  .compact(); 
		
		
		return compactJws;
	}
	
	
	
	
}
