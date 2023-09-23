package exceptions;

public class ValorOperacaoNegativoException extends Exception{

	public ValorOperacaoNegativoException() {
		super("O valor da operação é Negativo");
	}
}
