package br.com.agroinvest.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Jwts;

/*A decodificação usa a chave principal para tratar o token e obter o Claim Subject.. 
 * que ira conter a role do usuario
 * 
 */
public class DecodeJWT {

	Key key = Chave.getKey(); 

	public String decodeJWT(String compactJws) {

		String decoder = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject();
		return decoder;
	}

	@Deprecated
	public String decodeExpirationJWT(String compactJws) {

		String decoder = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getSubject();
		Date expireTime = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody().getExpiration();
		
		LocalDateTime dateTime = LocalDateTime.ofInstant(expireTime.toInstant(), ZoneId.of("America/Sao_Paulo"));
		
		if(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).isBefore(dateTime)&&
				LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).isAfter(dateTime.minusMinutes(4))){
			return new GerenciaJWT().createJWT(decoder);
		}
			
			return "not";
	}
	
	
	
}
