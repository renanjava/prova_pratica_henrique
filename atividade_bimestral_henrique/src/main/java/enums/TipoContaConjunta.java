package enums;

import interfaces.MetodosVinculo;

public enum TipoContaConjunta implements MetodosVinculo{
	CONTA_CONJUNTA_EMPRESARIAL("Conta Conjunta Empresarial"),
	CONTA_CONJUNTA_FISCAL("Conta Conjunta Fiscal");
	
	private String descricaoConta;
	
	TipoContaConjunta(String descricaoConta) {
		this.descricaoConta = descricaoConta;
	}
	
	public String getTipoContaConjunta() {
		return descricaoConta;
	}
}
