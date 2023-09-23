package atividade_bimestral_henrique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import contas.ContaConjunta;
import contas.ContaCorrente;
import contas.ContaPoupanca;
import contas.ContaSalario;
import enums.TipoRelacionamento;
import exceptions.AtingiuLimiteFinanciamentoException;
import exceptions.SaldoInsuficienteException;
import exceptions.TipoConjuntaUsuariosDiferentesException;
import exceptions.ValorOperacaoNegativoException;
import exceptions.ValorOperacaoZeroException;
import relacionamento.Relacionamento;
import usuarios.Fisica;
import usuarios.Juridica;

public class ContaTeste {
	
	//classe de equivalência válida
	@Test
	public void deveDepositarDinheiro() throws Exception {
		Juridica usuario1 = new Juridica("222222-22");
		Juridica usuario2 = new Juridica("999999-99");
		usuario1.criarConta(new ContaPoupanca());
		usuario2.criarConta(new ContaCorrente());
		
		usuario1.getConta().depositarDinheiro(20, usuario2.getConta());
		assertEquals(30, usuario1.getConta().getSaldo());
		assertEquals(20, usuario2.getConta().getSaldo());
	}
	
	@Test
	public void deveApresentarExtrato() throws Exception {
		Fisica usuario1 = new Fisica("222-22");
		Fisica usuario2 = new Fisica("333-33");
		usuario1.criarConta(new ContaPoupanca());
		usuario2.criarConta(new ContaCorrente());
		
		double valor = 15;
		usuario1.getConta().depositarDinheiro(valor, usuario2.getConta());

		assertEquals("Sua conta id "+usuario1.getConta().getId()
				+" efetuou um depósito no valor de "+valor+": conta id "
				+usuario2.getConta().getId(),usuario1.getConta().imprimirExtrato());
		
		assertEquals("Sua conta id "+usuario2.getConta().getId()
				+" recebeu um pagamento no valor de "+valor+": conta id "
				+usuario1.getConta().getId(),usuario2.getConta().imprimirExtrato());
	}
	
	@Test
	public void deveIncrementarSaldo() {
		Juridica usuario1 = new Juridica("222222-22");
		usuario1.criarConta(new ContaCorrente());
		
		usuario1.getConta().incrementarSaldo(100,usuario1.getConta());
		assertEquals(100, usuario1.getConta().getSaldo());
	}
	
	@Test
	public void deveDecrementarSaldo() {
		Juridica usuario1 = new Juridica("222222-22");
		usuario1.criarConta(new ContaCorrente());
		
		usuario1.getConta().incrementarSaldo(100,usuario1.getConta());
		usuario1.getConta().decrementarSaldo(60,usuario1.getConta());
		assertEquals(40, usuario1.getConta().getSaldo());
	}
	
	@Test
	public void deveIniciarComSaldosCorretos() throws TipoConjuntaUsuariosDiferentesException {
		Fisica usuario1 = new Fisica("222-222");
		Fisica usuario2 = new Fisica("444-444");
		Fisica usuario3 = new Fisica("111-111");
		usuario1.criarConta(new ContaCorrente());
		usuario2.criarConta(new ContaPoupanca());
		usuario3.criarConta(new ContaSalario());
		
		ContaConjunta contaConjunta = new ContaConjunta();
		Fisica usuario4 = new Fisica("444-444");
		Fisica usuario5 = new Fisica("111-111");
		usuario4.criarConta(contaConjunta);
		usuario5.criarConta(contaConjunta);
		Relacionamento usuarios = new Relacionamento<Fisica, Fisica>(usuario5, usuario4);
		
		contaConjunta.setRelacionamento(usuarios);
		
		assertEquals(0.00, usuario1.getConta().getSaldo());
		assertEquals(50.00, usuario2.getConta().getSaldo());
		assertEquals(0.00, usuario3.getConta().getSaldo());
		assertEquals(0.00, usuario4.getConta().getSaldo());
		assertEquals(0.00, usuario5.getConta().getSaldo());
	}
	@Test
	public void deveRetornarMesmosUsuarios() throws TipoConjuntaUsuariosDiferentesException {
		ContaConjunta contaConjunta = new ContaConjunta();
		Fisica usuario1 = new Fisica("222.222.222-22");
	    Fisica usuario2 = new Fisica("333.333.333-33");
	    usuario1.criarConta(contaConjunta);
	    usuario2.criarConta(contaConjunta);

	    Relacionamento<Fisica,Fisica> usuarios = 
	    		new Relacionamento<Fisica, Fisica>(usuario1, usuario2);
	    
	    contaConjunta.setRelacionamento(usuarios);

	    assertEquals(contaConjunta.getRelacionamento().getUsuarios(), usuarios.getUsuarios());
	}
	
	@Test
	public void deveRetornarMesmaConta() throws TipoConjuntaUsuariosDiferentesException {	
		ContaConjunta contaConjunta = new ContaConjunta();
		Fisica usuario1 = new Fisica("222.222.222-22");
	    Fisica usuario2 = new Fisica("333.333.333-33");
	    usuario1.criarConta(contaConjunta);
	    usuario2.criarConta(contaConjunta);
	    
	    Relacionamento<Fisica,Fisica> usuarios = 
	    		new Relacionamento<Fisica, Fisica>(usuario1, usuario2);
	    
	    contaConjunta.setRelacionamento(usuarios);
	    
	    assertEquals(usuario1.getConta().getId(), usuario2.getConta().getId());
	}
	
	@Test
	public void deveRetornarRelacionamentoUsuarios() throws Exception {
		Fisica usuario1 = new Fisica("222.222.222-22");
	    Fisica usuario2 = new Fisica("333.333.333-33");
	    
	    Relacionamento<Fisica,Fisica> usuarios1 = 
	    		new Relacionamento<Fisica, Fisica>(usuario1, usuario2);
	    
	    Juridica usuario3 = new Juridica("222-22");
	    Juridica usuario4 = new Juridica("333-33");
	    
	    Relacionamento<Juridica,Juridica> usuarios2 = 
	    		new Relacionamento<Juridica, Juridica>(usuario3, usuario4);
	    
	    assertEquals(TipoRelacionamento.CASADOS, usuarios1.getTipoRelacionamento());
	    assertEquals(TipoRelacionamento.EMPRESARIAL, usuarios2.getTipoRelacionamento());
	}
	
	@Test
	public void deveTerIdCorreto() {
		ContaCorrente contaCorrente1 = new ContaCorrente();
		ContaCorrente contaCorrente2 = new ContaCorrente();
		ContaPoupanca contaPoupanca1 = new ContaPoupanca();
		
		assertEquals(contaCorrente1.getId(),contaCorrente2.getId()-1);
		assertEquals(contaCorrente2.getId(),contaPoupanca1.getId()-1);
	}
	
	@Test
	public void deveRetornarFinanciamentoComJuros() throws Exception {
		ContaCorrente contaCorrente = new ContaCorrente();
		Juridica usuario = new Juridica("222-22");
		
		usuario.setConta(contaCorrente);
		usuario.getConta().receberFinanciamento(100);
		
		assertEquals(120, usuario.getConta().getFinanciamento());
	}
	
	@Test
	public void deveRetornarLucroPelaAplicacao() throws Exception {
		ContaPoupanca contaPoupanca = new ContaPoupanca();
		
		contaPoupanca.inserirDinheiroAplicacao(50);
		assertEquals(60, contaPoupanca.getAplicacao());
	}
	
	//classe de equivalência inválida
	@Test
	public void deveCausarErroPorUsuariosDiferentes() {
		ContaCorrente contaCorrente = new ContaCorrente();
	    Fisica usuario1 = new Fisica("222.222.222-22");
	    Juridica usuario2 = new Juridica("222222-22");
	    usuario1.criarConta(contaCorrente);
		usuario2.criarConta(contaCorrente);

	    Exception resultado = assertThrows(
	        TipoConjuntaUsuariosDiferentesException.class,
	        () -> new Relacionamento<Fisica, Juridica>(usuario1, usuario2));

	    assertEquals("Tipos de usuários diferentes para a conta conjunta", 
	    		resultado.getMessage());   
	}
	
	@Test
	public void deveCausarErroPorValorNegativoNoDeposito() throws Exception {
		Juridica usuario1 = new Juridica("222222-22");
		Juridica usuario2 = new Juridica("999999-99");
		usuario1.criarConta(new ContaPoupanca());
		usuario2.criarConta(new ContaCorrente());
		
		Exception resultado = assertThrows(ValorOperacaoNegativoException.class, 
				()-> usuario1.getConta().depositarDinheiro(-70, usuario2.getConta()));
		assertEquals("O valor da operação é Negativo", resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorZeroNoDeposito() throws Exception {
		Juridica usuario1 = new Juridica("222222-22");
		Juridica usuario2 = new Juridica("999999-99");
		usuario1.criarConta(new ContaPoupanca());
		usuario2.criarConta(new ContaCorrente());
		
		Exception resultado = assertThrows(ValorOperacaoZeroException.class, 
				()-> usuario1.getConta().depositarDinheiro(0, usuario2.getConta()));
		assertEquals("O valor da operação é Zero", resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorSaldoInsuficienteDeposito() throws Exception {
		Juridica usuario1 = new Juridica("222222-22");
		Juridica usuario2 = new Juridica("999999-99");
		usuario1.criarConta(new ContaPoupanca());
		usuario2.criarConta(new ContaCorrente());
		
		Exception resultado = assertThrows(SaldoInsuficienteException.class, 
				()-> usuario1.getConta().depositarDinheiro(999, usuario2.getConta()));
		assertEquals("O saldo é insuficiente para realizar a operação", 
				resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorAtingeLimiteFinanciamento() {
		Fisica usuario1 = new Fisica("222-22");
		usuario1.criarConta(new ContaCorrente());
		
		Exception resultado = assertThrows(AtingiuLimiteFinanciamentoException.class, 
				()-> usuario1.getConta().receberFinanciamento(1000));
		assertEquals("Atingiu o valor limite do financiamento",resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorLimiteFinanciamentoAtingido() throws Exception {
		Fisica usuario1 = new Fisica("222-22");
		usuario1.criarConta(new ContaCorrente());
		
		usuario1.getConta().receberFinanciamento(999);
		
		Exception resultado = assertThrows(AtingiuLimiteFinanciamentoException.class, 
				()-> usuario1.getConta().receberFinanciamento(1));
		assertEquals("Atingiu o valor limite do financiamento",resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorNegativoNoFinanciamento() throws Exception {
		Fisica usuario1 = new Fisica("222-22");
		usuario1.criarConta(new ContaCorrente());
		
		usuario1.getConta().receberFinanciamento(999);
		
		Exception resultado = assertThrows(ValorOperacaoNegativoException.class, 
				()-> usuario1.getConta().receberFinanciamento(-80));
		assertEquals("O valor da operação é Negativo",resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorZeroNoFinanciamento() throws Exception {
		Fisica usuario1 = new Fisica("222-22");
		usuario1.criarConta(new ContaCorrente());
		
		usuario1.getConta().receberFinanciamento(999);
		
		Exception resultado = assertThrows(ValorOperacaoZeroException.class, 
				()-> usuario1.getConta().receberFinanciamento(0));
		assertEquals("O valor da operação é Zero",resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorSaldoInsuficienteAplicacao() throws Exception {
		ContaPoupanca contaPoupanca = new ContaPoupanca();
		
		Exception resultado = assertThrows(SaldoInsuficienteException.class, 
				()-> contaPoupanca.inserirDinheiroAplicacao(100000));
		assertEquals("O saldo é insuficiente para realizar a operação", 
				resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorNegativoNaAplicacao() throws Exception {
		ContaPoupanca contaPoupanca = new ContaPoupanca();
		
		Exception resultado = assertThrows(ValorOperacaoNegativoException.class, 
				()-> contaPoupanca.inserirDinheiroAplicacao(-1));
		assertEquals("O valor da operação é Negativo",resultado.getMessage());
	}
	
	@Test
	public void deveCausarErroPorValorZeroNaAplicacao() throws Exception {
		ContaPoupanca contaPoupanca = new ContaPoupanca();
		
		Exception resultado = assertThrows(ValorOperacaoZeroException.class, 
				()-> contaPoupanca.inserirDinheiroAplicacao(0));
		assertEquals("O valor da operação é Zero",resultado.getMessage());
	}
	
}
