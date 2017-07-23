package it.demo.fatture;

import java.util.Set;
import java.util.TreeSet;

import it.demo.fatture.models.Fattura;
import it.demo.fatture.processors.FattureReader;
import it.demo.fatture.processors.FattureWriter;

/**
 * The Class App.
 */
public class App {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		FattureReader reader = new FattureReader();
		FattureWriter writer = new FattureWriter();
		Set<Fattura> myTree = reader.process("c:\\STS\\input.csv");
		writer.process((TreeSet<Fattura>) myTree, "C:\\STS\\output.csv");
		
	}
}
