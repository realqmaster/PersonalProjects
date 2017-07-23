package it.demo.fatture.processors;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.demo.fatture.models.Fattura;

/**
 * The Class FattureWriter.
 */
public class FattureWriter {

	/** Simple formatting logger */
	private static Logger log = LogManager.getFormatterLogger("console");

	/**
	 * Process.
	 *
	 * @param input
	 *            the input
	 * @param path
	 *            the path
	 */
	public void process(Set<Fattura> input, String path) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		FileWriter writer = null;
		CSVPrinter printer = null;

		try {
			writer = new FileWriter(path);
			CSVFormat format = CSVFormat.newFormat(';').withRecordSeparator("\n");
			printer = new CSVPrinter(writer, format);
			for (Fattura fattura : input) {
				printer.printRecord(fattura.getNumero(), sdf.format(fattura.getData().getTime()),
						sdf.format(fattura.getScadenza().getTime()));
			}

		} catch (IOException e) {
			log.error("Errore durante la scrittura del file in uscita");
			log.error(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					log.error("Errore durante la scrittura del file in uscita");
					log.error(e);
				}
				if (printer != null) {
					try {
						printer.close();
					} catch (IOException e) {
						log.error("Errore durante la scrittura del file in uscita");
						log.error(e);
					}
				}
			}
		}

	}

}
