package exceptions;

public class ValorOperacaoZeroException extends Exception{
	
	public ValorOperacaoZeroException() {
		super("O valor da operação é Zero");
	}
}
