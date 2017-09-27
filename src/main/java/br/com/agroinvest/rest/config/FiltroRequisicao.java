package br.com.agroinvest.rest.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.agroinvest.jwt.DecodeJWT;

/*
 * Filtra a requisição ao servidor para buscar a chave de segurança no cabeçalho do HTTP 
 * caso não esteja fazendo login
 */

@Provider
public class FiltroRequisicao implements ContainerRequestFilter {
	
	@Context //Injeto classe java que recebe o http request
	private HttpServletRequest servletRequest;
	
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
//		if(!servletRequest.getRequestURI().contains("login")){ //se for qualquer requisição diferente dde login
//			String codigo = request.getHeaderString("Content-Disposition");// ele busca na chake User-Agent do cabeçalho http
//			
//			try	{                                                 // e tenta decodifica-la 
//			new DecodeJWT().decodeJWT(codigo);	
//			}catch (Exception e) {
//				Response response = Response                     // caso erro na decodificação, o acesso é abortado
//		                 .status(Response.Status.BAD_REQUEST)   // e o cliente recebe o acesso restrito
//		                 .type(MediaType.TEXT_PLAIN_TYPE)
//		                 .entity("ACESSO RESTRITO")
//		                 .build();
//				request.abortWith(response);
//			}
//		}
			
	}

}
