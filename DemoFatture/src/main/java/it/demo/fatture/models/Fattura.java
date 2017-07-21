package it.demo.fatture.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fattura implements Comparable<Fattura> {

	private Integer numero;
	private GregorianCalendar data;
	private String modalitaPagamento;

	public Fattura(Integer numero, GregorianCalendar data, String modalitaPagamento) {
		super();
		this.numero = numero;
		this.data = data;
		if (modalitaPagamento.equals("DF") || modalitaPagamento.equals("DFFM") || modalitaPagamento.equals("DF60")) {
			this.modalitaPagamento = modalitaPagamento;
		}
		else
			throw new IllegalArgumentException("La modalità di pagamento deve essere di tipo DF, DFFM o DF60");
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public GregorianCalendar getData() {
		return data;
	}

	public void setData(GregorianCalendar data) {
		this.data = data;
	}

	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	public GregorianCalendar getScadenza() {
		GregorianCalendar result = new GregorianCalendar();
		result.set(this.getData().get(Calendar.YEAR), this.getData().get(Calendar.MONTH), this.getData().get(Calendar.DATE));

		if (modalitaPagamento.equalsIgnoreCase("DFFM")) {
			result.add(Calendar.MONTH, 1);
			result.set(Calendar.DATE, 1);
			result.add(Calendar.DATE, -1);
		} else if (modalitaPagamento.equals("DF60")) {
			result.add(Calendar.MONTH, 2);
		}
		return result;
	}

	public int compareTo(Fattura compared) {
		return getScadenza().compareTo(compared.getScadenza());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj.getClass() != this.getClass()) {
			return false;
		} else
			return (((Fattura) obj).getNumero() == this.getNumero() && ((Fattura) obj).getData().equals(this.getData()) && ((Fattura) obj).getModalitaPagamento().equals(this.getModalitaPagamento()));

	}
}
