package exceptions;

public class SaldoInsuficienteException extends Exception{

	public SaldoInsuficienteException() {
		super("O saldo é insuficiente para realizar a operação");
	}
}
