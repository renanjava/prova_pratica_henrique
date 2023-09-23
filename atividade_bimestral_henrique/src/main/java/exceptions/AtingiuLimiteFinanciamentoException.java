package exceptions;

public class AtingiuLimiteFinanciamentoException extends Exception{

	public AtingiuLimiteFinanciamentoException() {
		super("Atingiu o valor limite do financiamento");
	}
}
