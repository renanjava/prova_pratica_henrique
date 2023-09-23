package usuarios;

import contas.Conta;

public class Fisica<C extends Conta> extends Usuario{
	private String cpf;
	
	public Fisica(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public void criarConta(Conta tipoConta) {
		setConta(tipoConta);		
	}

	@Override
	public void criarConta(Object tipoConta) {
		
	}
	
}
