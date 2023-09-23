package contas;

import exceptions.SaldoInsuficienteException;
import exceptions.ValorOperacaoNegativoException;
import exceptions.ValorOperacaoZeroException;

public class ContaPoupanca extends Conta{
	
	private double aplicacao;

	public void iniciarSaldo() {
		setSaldo(50.00);
	}

	public double getAplicacao() {
		return aplicacao;
	}

	public void inserirDinheiroAplicacao(double valor) throws Exception {
		if(valor <= 0)
			if(valor < 0)
				throw new ValorOperacaoNegativoException();
			else
				throw new ValorOperacaoZeroException();
		
		if(valor <= saldo) {
			aplicacao += valor*1.2; //lucro
			saldo -= valor;
		}else
			throw new SaldoInsuficienteException();
	}
	
	
}
