package it.demo.fatture.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Class Fattura.
 */
public class Fattura implements Comparable<Fattura> {

	/** The numero. */
	private Integer numero;

	/** The data. */
	private GregorianCalendar data;

	/** The modalita pagamento. */
	private String modalitaPagamento;

	/**
	 * Instantiates a new fattura.
	 *
	 * @param numero
	 *            the numero
	 * @param data
	 *            the data
	 * @param modalitaPagamento
	 *            the modalita pagamento
	 */
	public Fattura(Integer numero, GregorianCalendar data, String modalitaPagamento) {
		super();
		this.numero = numero;
		this.data = data;
		if ("DF".equals(modalitaPagamento) || "DFFM".equals(modalitaPagamento) || "DF60".equals(modalitaPagamento)) {
			this.modalitaPagamento = modalitaPagamento;
		} else
			throw new IllegalArgumentException("La modalità di pagamento deve essere di tipo DF, DFFM o DF60");
	}

	/**
	 * Gets the numero.
	 *
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * Sets the numero.
	 *
	 * @param numero
	 *            the new numero
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public GregorianCalendar getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(GregorianCalendar data) {
		this.data = data;
	}

	/**
	 * Gets the modalita pagamento.
	 *
	 * @return the modalita pagamento
	 */
	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	/**
	 * Sets the modalita pagamento.
	 *
	 * @param modalitaPagamento
	 *            the new modalita pagamento
	 */
	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	/**
	 * Gets the scadenza.
	 *
	 * @return the scadenza
	 */
	public GregorianCalendar getScadenza() {
		GregorianCalendar result = new GregorianCalendar();
		result.set(this.getData().get(Calendar.YEAR), this.getData().get(Calendar.MONTH),
				this.getData().get(Calendar.DATE));

		if ("DFFM".equals(modalitaPagamento)) {
			result.add(Calendar.MONTH, 1);
			result.set(Calendar.DATE, 1);
			result.add(Calendar.DATE, -1);
		} else if ("DF60".equals(modalitaPagamento)) {
			result.add(Calendar.MONTH, 2);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Fattura compared) {
		return getScadenza().compareTo(compared.getScadenza());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj.getClass() != this.getClass()) {
				return false;
			} else
				return (((Fattura) obj).getNumero() == this.getNumero()
						&& ((Fattura) obj).getData().equals(this.getData())
						&& ((Fattura) obj).getModalitaPagamento().equals(this.getModalitaPagamento()));
		} else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
