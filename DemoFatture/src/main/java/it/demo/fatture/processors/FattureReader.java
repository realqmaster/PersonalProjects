package it.demo.fatture.processors;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import it.demo.fatture.models.Fattura;

public class FattureReader {

	public TreeSet<Fattura> process(String path) {

		TreeSet<Fattura> tree = new TreeSet<Fattura>();

		try {
			FileReader reader = new FileReader(path);
			CSVFormat format = CSVFormat.newFormat(';');
			Iterable<CSVRecord> records = format.parse(reader);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			for (CSVRecord record : records) {

				Integer numero = Integer.parseInt(record.get(0));
				GregorianCalendar data = new GregorianCalendar();
				data.setTime(sdf.parse(record.get(1)));
				String modalita = record.get(2);
				Fattura item = new Fattura(numero, data, modalita);
				tree.add(item);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File di ingresso non trovato");
		} catch (IOException e) {
			System.out.println("Errore durante la lettura del file di ingresso");
		} catch (ParseException e) {
			System.out.println("Formato data non valido");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		return tree;
	}
}
