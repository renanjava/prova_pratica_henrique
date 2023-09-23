package exceptions;

public class ValorMaiorQueFinanciamentoException extends Exception{
	
	public ValorMaiorQueFinanciamentoException() {
		super("Valor inserido é maior que o valor do financiamento");
	}
}
