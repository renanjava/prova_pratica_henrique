package exceptions;

public class TipoConjuntaUsuariosDiferentesException extends Exception{
	
	public TipoConjuntaUsuariosDiferentesException() {
		super("Tipos de usuários diferentes para a conta conjunta");
	}
}
