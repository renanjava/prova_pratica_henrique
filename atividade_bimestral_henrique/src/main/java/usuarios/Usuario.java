package usuarios;

import contas.Conta;
import interfaces.MetodosUsuario;

public abstract class Usuario<C extends Conta> implements MetodosUsuario{
	private C conta;
	
	public void setConta(C tipoConta) {
		this.conta = tipoConta;
	}
	
	public C getConta() {
		return conta;
	}
	
	public void criarConta(C tipoConta) {
		setConta(tipoConta);
	}
	
}
