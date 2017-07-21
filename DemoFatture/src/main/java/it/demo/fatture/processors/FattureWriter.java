package it.demo.fatture.processors;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TreeSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import it.demo.fatture.models.Fattura;

public class FattureWriter {

	public void process(TreeSet<Fattura> input, String path) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			FileWriter writer = new FileWriter(path);
			CSVFormat format = CSVFormat.newFormat(';').withRecordSeparator("\n");
			CSVPrinter printer = new CSVPrinter(writer, format);
			for (Fattura fattura : input) {
				printer.printRecord(fattura.getNumero(), sdf.format(fattura.getData().getTime()), sdf.format(fattura.getScadenza().getTime()));
			}
			printer.close();

		} catch (IOException e) {
			System.out.println("Errore durante la scrittura del file in uscita");
		}

	}

}
