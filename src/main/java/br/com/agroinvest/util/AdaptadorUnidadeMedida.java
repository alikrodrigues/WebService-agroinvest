package br.com.agroinvest.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import br.com.agroinvest.model.UnidadeMedida;

public class AdaptadorUnidadeMedida extends XmlAdapter<String, UnidadeMedida> {

	private static String IP = "http://127.0.0.1:8080/ws-agroinvest/unidademedidas/";
	
	
	@Override
	public String marshal(UnidadeMedida unidade) throws Exception {
		return unidade.getDescricao();
	}

	@Override
	public UnidadeMedida unmarshal(String unidade) throws Exception {
		UnidadeMedida unidadeObject;
		Client client  = ClientBuilder.newClient();
		WebTarget target;
		
		target = client.target(IP+unidade);
		Response response = target.request().get();
		if(response.getStatus()==200) {
			unidadeObject = response.readEntity(UnidadeMedida.class);
			response.close();
			return unidadeObject;
		} else {
			return null;
		}
	}

}
