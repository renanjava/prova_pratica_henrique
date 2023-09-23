package contas;

import java.util.ArrayList;
import java.util.List;

import enums.TipoOperacaoConta;
import exceptions.AtingiuLimiteFinanciamentoException;
import exceptions.SaldoInsuficienteException;
import exceptions.ValorOperacaoNegativoException;
import exceptions.ValorOperacaoZeroException;
import exceptions.ValorMaiorQueFinanciamentoException;
import interfaces.OperacoesConta;
import usuarios.Usuario;

public abstract class Conta<C extends Conta> implements OperacoesConta{
	private static int idQuant;
	private int id;
	protected double saldo;
	private double financiamento;
	private List<String> extrato = new ArrayList<String>();
	
	public abstract void iniciarSaldo();
	
	public Conta() {
		id = Conta.idQuant++;
		iniciarSaldo();
	}
	
	public int getId() {
		return id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public double getFinanciamento() {
		return financiamento;
	}
	
	public void incrementarSaldo(double valor, Conta conta) {
		saldo += valor;
		this.cadastrarExtrato(valor, conta, TipoOperacaoConta.INCREMENTO);
		conta.cadastrarExtrato(valor, this, TipoOperacaoConta.DECREMENTO);
	}
	
	public void decrementarSaldo(double valor, Conta conta) {
		saldo -= valor;
		this.cadastrarExtrato(valor, conta, TipoOperacaoConta.DECREMENTO);
		conta.cadastrarExtrato(valor, this, TipoOperacaoConta.INCREMENTO);
	}
	
	public void cadastrarExtrato(double valor, Conta conta, TipoOperacaoConta operacao) {
		
		String registro = "Sua conta id "+this.getId()+" ";
		
		switch(operacao) {
			case INCREMENTO: registro += "recebeu um pagamento "; break;
			case DECREMENTO: registro += "efetuou um depósito "; break;	
		}
		registro += "no valor de "+valor+": conta id "+conta.getId();
		
		extrato.add(registro);
	}
	
	public String imprimirExtrato() {
		return extrato.get(0);
	}
	
	public void depositarDinheiro(double valor, Conta conta) throws Exception {
		if(valor <= 0)
			if(valor < 0)
				throw new ValorOperacaoNegativoException();
			else
				throw new ValorOperacaoZeroException();
		
		if(valor <= saldo) {
			this.decrementarSaldo(valor,conta);
			conta.incrementarSaldo(valor,conta);
		}else
			throw new SaldoInsuficienteException();
	}
	
	public void receberFinanciamento(double valor) throws Exception{
		if(valor <= 0)
			if(valor < 0)
				throw new ValorOperacaoNegativoException();
			else
				throw new ValorOperacaoZeroException();
		
		if(financiamento+valor > 999)
			throw new AtingiuLimiteFinanciamentoException();
		saldo += valor;
		financiamento += valor*1.2; //juros
	}
	
	public void pagarFinanciamento(double valor) throws Exception{
		if(valor <= 0)
			if(valor < 0)
				throw new ValorOperacaoNegativoException();
			else
				throw new ValorOperacaoZeroException();
		
		if(valor > financiamento)
			throw new ValorMaiorQueFinanciamentoException();
		saldo -= valor;
		financiamento -= valor;
	}
	
}
