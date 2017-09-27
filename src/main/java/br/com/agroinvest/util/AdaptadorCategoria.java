package br.com.agroinvest.util;


import java.time.LocalDateTime;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import br.com.agroinvest.model.Categoria;
import br.com.agroinvest.model.UnidadeMedida;

public class AdaptadorCategoria extends XmlAdapter<String, Categoria> {

	private static String IP = "http://127.0.0.1:8080/ws-agroinvest/categoria/";
	
	@Override
	public String marshal(Categoria categoria) throws Exception {
		return categoria.getDescricao();
	}

	@Override
	public Categoria unmarshal(String categoria) throws Exception {
		Categoria categoriaObject;
		Client client  = ClientBuilder.newClient();
		WebTarget target;
		
		target = client.target(IP+categoria);
		Response response = target.request().get();
		if(response.getStatus()==200) {
			categoriaObject = response.readEntity(Categoria.class);
			response.close();
			return categoriaObject;
		} else {
			Client cliente = ClientBuilder.newClient();
			WebTarget targete;
			targete = cliente.target(IP);
			categoriaObject = new Categoria();
			categoriaObject.setDescricao(categoria);
			categoriaObject.setData(LocalDateTime.now());
			Response resposta = targete.request().post(Entity.json(categoriaObject));
			categoriaObject = resposta.readEntity(Categoria.class);
			resposta.close();
			return categoriaObject;
			
		}
		
	}

}
