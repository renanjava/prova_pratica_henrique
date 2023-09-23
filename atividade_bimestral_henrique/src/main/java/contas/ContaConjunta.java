package contas;

import enums.TipoContaConjunta;
import relacionamento.Relacionamento;
import usuarios.Fisica;

public class ContaConjunta extends Conta{
	private Relacionamento usuarios;
	private String descricao;
	
	public void setRelacionamento(Relacionamento usuarios) {
		this.usuarios = usuarios;
		
		if(usuarios.getTipoRelacionamento() != null)
			setTipoContaConjunta(TipoContaConjunta.
					CONTA_CONJUNTA_FISCAL.getTipoContaConjunta());
		else
			setTipoContaConjunta(TipoContaConjunta.
					CONTA_CONJUNTA_EMPRESARIAL.getTipoContaConjunta());
	}
	
	private void setTipoContaConjunta(String descricao) {
		this.descricao = descricao;
	}
	
	public void iniciarSaldo() {
		setSaldo(0.00);
	}
	
	public Relacionamento getRelacionamento() {
		return usuarios;
	}
}
