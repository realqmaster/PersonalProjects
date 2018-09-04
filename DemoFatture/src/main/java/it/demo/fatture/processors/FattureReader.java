package it.demo.fatture.processors;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.demo.fatture.models.Fattura;

/**
 * The Class FattureReader.
 */
public class FattureReader {

	/** Simple formatting logger */
	private static Logger log = LogManager.getFormatterLogger("console");

	/**
	 * Process input file.
	 *
	 * @param path
	 *            the path
	 * @return the tree set
	 */
	public Set<Fattura> process(String path) {

		Set<Fattura> tree = new TreeSet<Fattura>();
		FileReader reader = null;
		try {
			reader = new FileReader(path);
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
			log.error("File di ingresso non trovato");
			log.error(e);
		} catch (IOException e) {
			log.error("Errore durante la lettura del file di ingresso");
			log.error(e);
		} catch (ParseException e) {
			log.error("Formato data non valido");
		} catch (IllegalArgumentException e) {
			log.error(e);
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("Errore durante la lettura del file di ingresso");
					log.error(e);
				}
			}
		}

		return tree;
	}
}
