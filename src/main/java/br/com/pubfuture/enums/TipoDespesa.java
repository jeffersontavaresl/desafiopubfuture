package br.com.pubfuture.enums;

public enum TipoDespesa {
	
	ALIMENTACAO("ALIMENTACAO"), EDUCACAO("EDUCACAO"), LAZER("LAZER"), MORADIA("MORADIA"),
	ROUPA("ROUPA"), SAUDE("SAUDE"), TRANSPORTE("TRANSPORTE"), OUTROS("OUTROS");
	
	private final String tipoDespesa;
	
	TipoDespesa(String tipoDespesa){
		this.tipoDespesa = tipoDespesa;
	}
	
	public String getTipoDespesa() {
		return this.tipoDespesa;
	}
}
