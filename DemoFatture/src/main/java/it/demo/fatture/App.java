package it.demo.fatture;

import java.util.TreeSet;

import it.demo.fatture.models.Fattura;
import it.demo.fatture.processors.FattureReader;
import it.demo.fatture.processors.FattureWriter;

public class App {

	public static void main(String[] args) {

		FattureReader reader = new FattureReader();
		FattureWriter writer = new FattureWriter();
		TreeSet<Fattura> myTree = reader.process("c:\\STS\\input.csv");
		writer.process(myTree, "C:\\STS\\output.csv");
		
	}
}
