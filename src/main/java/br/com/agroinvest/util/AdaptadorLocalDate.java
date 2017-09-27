package br.com.agroinvest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdaptadorLocalDate extends XmlAdapter<String, LocalDateTime> {
	 @Override
	    public LocalDateTime unmarshal(String dateString) throws Exception {
	        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	    }

	    @Override
	    public String marshal(LocalDateTime localDate) throws Exception {
	        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(localDate);
	    }
}