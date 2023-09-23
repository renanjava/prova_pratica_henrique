package interfaces;

import contas.Conta;
import enums.TipoOperacaoConta;

public interface OperacoesConta<C extends Conta> {
	
	public void iniciarSaldo();
	public String imprimirExtrato();
	public void cadastrarExtrato(double valor, C conta, TipoOperacaoConta operacao);
	public void incrementarSaldo(double valor, C conta);
	public void decrementarSaldo(double valor, C conta);
	public void depositarDinheiro(double valor, C conta) throws Exception;
	public void receberFinanciamento(double valor) throws Exception;
	public void pagarFinanciamento(double valor) throws Exception;
	
}
