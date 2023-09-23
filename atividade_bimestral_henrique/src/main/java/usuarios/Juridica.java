package usuarios;

import contas.Conta;

public class Juridica<C extends Conta> extends Usuario{
	private String cnpj;
	
	public Juridica(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	@Override
	public void criarConta(Object tipoConta) {
		
	}
}
