package br.com.pubfuture.enums;

public enum TipoReceita {

	SALARIO("SALARIO"), PRESENTE("PRESENTE"), PREMIO("PREMIO"), OUTROS("OUTROS");
	
	private final String tipoReceita;
	
	TipoReceita(String tipoReceita){
		this.tipoReceita = tipoReceita;
	}
	
	public String getTipoReceita() {
		return this.tipoReceita;
	}

}
