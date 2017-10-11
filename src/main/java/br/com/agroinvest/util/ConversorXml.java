package br.com.agroinvest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.agroinvest.model.Insumo;
import br.com.agroinvest.model.Periodo;

public class ConversorXml {
	private File inputFile;
	private File outputFile;
	private int indexAquicultura;
	private int indexFrutas;
	private int indexHortalicas;
	private int indexGraos;
	private int indexPecuaria;
	private int indexVegetais;
	
	public ConversorXml(File inputFile, File outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public String ConvertXls() {
		// For storing data into CSV files
		StringBuffer data = new StringBuffer();
		try {
			FileOutputStream fos = new FileOutputStream(outputFile);

			// Get the workbook object for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell;
			Row row;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						data.append(cell.getBooleanCellValue() + ",");
						break;

					case Cell.CELL_TYPE_NUMERIC:
						data.append(cell.getNumericCellValue() + ",");
						break;

					case Cell.CELL_TYPE_STRING:
						data.append(cell.getStringCellValue() + ",");
						break;

					case Cell.CELL_TYPE_BLANK:
						data.append("" + ",");
						break;

					default:
						data.append(cell + ",");
					}

					data.append('\n');
				}
			}

			fos.write(data.toString().getBytes());
			fos.close();
			return Objeto(data.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String identarRetorno(String data) {
		String date;
		String newData = "";
		int cont = 0;
		String dataVetor[] = data.split(",");
		for (String string : dataVetor) {

			if (string.contains("Data:")) {
				date = string.replace("Data: ", "").replace(",", "");
			}
			if (string.contains("codigon") || string.contains("Produto") || string.contains("unidade")
					|| string.contains("Canoinhas") || string.contains("Chapecó") || string.contains("Jaraguá do sul")
					|| string.contains("Joaçaba") || string.contains("Lages") || string.contains("Rio do Sul")
					|| string.contains("Sul Catarinense") || string.contains("São Miguel do Oeste")) {
				newData = newData + string.replace(",", "").replace(" ", "") + "      ";
			}
			if (string.contains("Aquicultura")) {
				newData = newData + "\n" + string.replace(",", "").replace(" ", "") + "\n";
				for (int i = cont; i < dataVetor.length; i++) {
					int contador = 1;
					if (i == cont + 9) {
						newData = newData + "\n";
					}
					if (i > cont + 9) {
						if (contador == 11)
							newData = newData + "\n";
						newData = newData + dataVetor[i].replace(",", "") + "      ";
						contador++;
					}
				}
				return newData;
			}
			cont++;
		}
		return newData;
	}

	public String Objeto(String data) {
		String date;
		String dataVetor[] = data.split(",");
		List<String> auxiliar = new ArrayList<String>();
		Periodo periodo = new Periodo();
		for (int i = 0; i < dataVetor.length; i++) {
			dataVetor[i] = dataVetor[i].replace("  ", "");
			dataVetor[i] = dataVetor[i].replace(",", "");
			if (dataVetor[i].contains("Data:")) {
				date = dataVetor[i].replace("Data: ", "").replace(",", "");
				periodo.setMesAno(date);
				periodo.setData(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
			}
			if (dataVetor[i].length() > 1) {
				auxiliar.add(dataVetor[i]);
			}
		}
		for (int i = 0; i <= 14; i++) {
			auxiliar.remove(0);
		}
		localizador(auxiliar);
		
		
		int diferenca = indexFrutas - indexAquicultura;
		
		 System.out.println("Aquicultura tem : "+diferenca/11+" insumos");
		
		 diferenca =  indexGraos - indexFrutas;
		
		 
		System.out.println("Frutas tem : "+diferenca/11+" insumos");
		
		diferenca = indexHortalicas - indexGraos ;
		 
		 System.out.println("Grãos tem : "+diferenca/11+" insumos");
		
		 diferenca = indexPecuaria-indexHortalicas;
		 
		 System.out.println("Hortalicas tem : "+diferenca/11+" insumos");
		 
		 
		
		 diferenca = indexVegetais - indexPecuaria;
		 
		 System.out.println("Pecuaria tem : "+diferenca/11+" insumos");
		 diferenca = (auxiliar.size()-1) - indexVegetais ;
		 
		 System.out.println("Vegetais tem : "+diferenca/11+" insumos");
		 
		return null;
	}

	private void localizador(List<String> auxiliar) {
		for (int i = 0; i < auxiliar.size(); i++) {
			String aux = auxiliar.get(i).trim();
			if(aux.equals("Aquicultura"))	{
				this.indexAquicultura = i;
			}
			if(aux.equals("Frutas"))	{
				this.indexFrutas = i;
			}
			if(aux.equals("Hortaliças"))	{
				this.indexHortalicas = i;
			}
			if(aux.equals("Grãos"))	{
				this.indexGraos = i;
			}
			if(aux.equals("Pecuária e derivados da produção animal"))	{
				this.indexPecuaria = i;
			}
			if(aux.equals("Produtos vegetais e derivados"))	{
				this.indexVegetais = i;
			}
			
			
//			switch (aux) {
//			case "Aquicultura":
//				this.indexAquicultura = i;
//				break;
//
//			case "Frutas":
//				this.indexFrutas = i;
//				break;
//
//			case "Hortaliças":
//				this.indexHortalicas = i;
//				break;
//			case "Grãos":
//				this.indexGraos = i;
//				break;
//			case "Pecuária e derivados da produção animal":
//				this.indexPecuaria = i;
//				break;
//			case	"Produtos vegetais e derivados":
//				this.indexVegetais = i;
//				break;
//			
//			default:
//				break;
//			}
		}
	}

}
