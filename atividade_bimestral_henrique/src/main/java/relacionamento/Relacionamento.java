package relacionamento;

import java.util.ArrayList;
import java.util.List;

import enums.TipoRelacionamento;
import exceptions.TipoConjuntaUsuariosDiferentesException;
import usuarios.Fisica;
import usuarios.Usuario;

public class Relacionamento<A extends Usuario, B extends Usuario> {
    private List<Usuario> usuarios;
    private TipoRelacionamento tipoRelacionamento;

    public Relacionamento(A usuario1, B usuario2) 
    		throws TipoConjuntaUsuariosDiferentesException {
    	
        if (!usuario1.getClass().equals(usuario2.getClass()))
            throw new TipoConjuntaUsuariosDiferentesException();
        
        if(usuario1.getClass().equals(Fisica.class))
        	tipoRelacionamento = TipoRelacionamento.CASADOS;
        else
        	tipoRelacionamento = TipoRelacionamento.EMPRESARIAL;

        usuarios = new ArrayList<Usuario>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public TipoRelacionamento getTipoRelacionamento() {
    	return tipoRelacionamento;
    }
}
