package br.com.agroinvest.rest.endpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.agroinvest.model.Periodo;
import br.com.agroinvest.util.ConversorXml;

@Stateless
@Path("/import")
public class ImportaEndpoint {
	@PersistenceContext(unitName = "ws-agroinvest-persistence-unit")
	private EntityManager em;
	private ConversorXml conversor;
	
	public static final String UPLOAD_FILE_SERVER = "/opt/AGROINVEST/upload/";
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response importacao(MultipartFormDataInput multipartFormDataInput) {
		// local variables
		MultivaluedMap<String, String> multivaluedMap = null;
		String fileName = null;
		InputStream inputStream = null;
		String uploadFilePath = null;

		try {
			Map<String, List<InputPart>> map = multipartFormDataInput.getFormDataMap();
			List<InputPart> lstInputPart = map.get("uploadedFile");

			for (InputPart inputPart : lstInputPart) {

				// get filename to be uploaded
				multivaluedMap = inputPart.getHeaders();
				 fileName = getFileName(multivaluedMap);

				if (null != fileName && !"".equalsIgnoreCase(fileName)) {

					// write & upload file to UPLOAD_FILE_SERVER
					inputStream = inputPart.getBody(InputStream.class, null);
					uploadFilePath = writeToFileServer(inputStream, fileName);

					// close the stream
					inputStream.close();
				}
			}
		conversor = new ConversorXml(new File(UPLOAD_FILE_SERVER+fileName),new File(UPLOAD_FILE_SERVER+"convertido.csv"));
		String retorno = conversor.ConvertXls();
		
		if(retorno==null)return Response.ok("File uploaded unsuccessfully at ").build();
		else return Response.ok(retorno).build();
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// release resources, if any
		}
		return Response.ok("File uploaded successfully at " + uploadFilePath).build();
	}

	private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {

		OutputStream outputStream = null;
		String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;

		try {
			outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// release resource, if any
			outputStream.close();
		}
		return qualifiedUploadFilePath;
	}
	
	private String getFileName(MultivaluedMap<String, String> multivaluedMap) {
		 
        String[] contentDisposition = multivaluedMap.getFirst("Content-Disposition").split(";");
 
        for (String filename : contentDisposition) {
 
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        return "UnknownFile";
    }

	// public Response create(String entity) {
	// String[] arquivo = entity.split(";");
	// String data;
	// for (String auxiliar : arquivo) {
	// if (auxiliar.contains("Data")) {
	// data = auxiliar.replace("Data: ", "");
	// if (verificaPeriodo(data)) {
	// return Response.status(Response.Status.CONFLICT)
	// .entity("ERRO 101 - Já duplicação de dados, verique se já não foi
	// importado").build();
	// } else {
	// Periodo periodo = new Periodo();
	// periodo.setData(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
	// periodo.setDescricao("Preços Mensal "+data);
	// periodo.setMesAno(data);
	// em.persist(periodo);
	// }
	// }
	// }
	// return Response.status(Response.Status.OK).build();
	// }

	private boolean verificaPeriodo(String data) {

		TypedQuery<Periodo> findByIdQuery = em
				.createQuery("SELECT DISTINCT p FROM Periodo p  WHERE p.id = :data ORDER BY p.id", Periodo.class);
		findByIdQuery.setParameter("data", data);
		Periodo entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return false;
		}
		return true;
	}

	
}
