import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContaCorrenteTest {

    private static final float ERRO_ACEITAVEL_NOS_FLOATS = 0.000001f;

    private Pessoa maria;
    private Pessoa joao;

    private Banco banco;

    private Agencia minhaAgencia;

    private ContaCorrente contaDaMaria;
    private ContaCorrente contaDoJoao;

    @Before
    public void setUp() {
        banco = new Banco();

        // cria algumas pessoas
        maria = new Pessoa("Maria", 12345678);
        joao = new Pessoa("Joao", 23222);

        // cria uma agencia
        minhaAgencia = new Agencia(banco, 1, "Agência Principal");

        ContaCorrente.numeroDeContasCriadas = 0;  // reseta o static da classe

        // cria algumas contas
        contaDaMaria = new ContaCorrente(maria, minhaAgencia);
        contaDoJoao = new ContaCorrente(joao, minhaAgencia);
    }

    @Test
    public void testarNumerosAutomaticosDeContas() {
        assertEquals(1, contaDaMaria.getNumeroDaConta());
        assertEquals(2, contaDoJoao.getNumeroDaConta());
//        ContaCorrente novaConta = new ContaCorrente(maria, minhaAgencia);
//        long numeroDaConta = novaConta.getNumeroDaConta();
//
//        assertEquals(numeroDaConta + 1,
//                (new ContaCorrente(joao, minhaAgencia).getNumeroDaConta()));
//        assertEquals(numeroDaConta + 2,
//                (new ContaCorrente(joao, minhaAgencia).getNumeroDaConta());
    }

    @Test
    public void testarDeposito() {
        checarSaldoInicial(contaDaMaria);

	try
	{
	    contaDaMaria.depositar(1000);
	    assertFloatEquals(1010f, contaDaMaria.getSaldoEmReais());

	    contaDaMaria.depositar(500);
	    assertFloatEquals(1510f, contaDaMaria.getSaldoEmReais());

	    contaDaMaria.depositar(-100);
	    assertFloatEquals(1510f, contaDaMaria.getSaldoEmReais());  // nada mudou,porque o depósito não foi feito
	 }
	
	catch (RequisicaoDeValorNaoPositivoException e)
	{
	     
	}


    }

    @Test
    public void testarSaque() {
        checarSaldoInicial(contaDaMaria);
	try
	{
	    contaDaMaria.sacar(7);
	    assertEquals(3f, contaDaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
	}
	catch (Exception e)
	{
	    
	}

    }

    @Test
    public void testarSaqueSemFundos() {
        checarSaldoInicial(contaDaMaria);

	try
	{
	    contaDaMaria.sacar(300);
	    assertFloatEquals(10f, contaDaMaria.getSaldoEmReais());
	}

	catch (Exception e)
	{
	   
	}
    }

    // banco deixa sacar (um valor inicial + valor do cheque especial)
    // e o saldo negativo fica para indicar quanto o cliente deve
    @Test
    public void testarSaqueNoChequeEspecial() 
    {
        checarSaldoInicial(contaDaMaria);
	try
	{
	    contaDaMaria.sacar(17);
	    assertFloatEquals(-7f, contaDaMaria.getSaldoEmReais());
	}

	catch (Exception e)
	{
	   
	}
    }
    
    @Test
    public void testarTransferecia() {
        // sanity check: as contas já começam com saldo 10 (regra de negócio)
        checarSaldoInicial(contaDaMaria);
        checarSaldoInicial(contaDoJoao);

	try
        {
	   contaDaMaria.transferir(7, contaDoJoao);
        }
	catch (Exception e)
	{
	    
	}
        assertFloatEquals(3f, contaDaMaria.getSaldoEmReais());
        assertFloatEquals(17f, contaDoJoao.getSaldoEmReais());
    }

    @Test
    public void testarTransfereciaSemFundosNaContaDeOrigem() {
        // sanity check: as contas já começam com saldo 10 (regra de negócio)
        assertFloatEquals(10f, contaDaMaria.getSaldoEmReais());
        assertFloatEquals(10f, contaDoJoao.getSaldoEmReais());
	try
	{
	    contaDaMaria.transferir(200, contaDoJoao);
	}
	catch (Exception e)
	{

	}
        assertFloatEquals(10f, contaDaMaria.getSaldoEmReais());
        assertFloatEquals(10f, contaDoJoao.getSaldoEmReais());
        // a transferência NÃO DEVE SER REALIZADA, porque não fundos na conta de origem (Maria).
    }

    private void checarSaldoInicial(ContaCorrente conta) {
        // sanity check: as contas já começam com saldo 10 (regra de negócio)
        assertFloatEquals(
                ContaCorrente.SALDO_INICIAL_DE_NOVAS_CONTAS,
                conta.getSaldoEmReais());
    }

    private static void assertFloatEquals(float expected, float actual) {
        assertEquals(expected, actual, ERRO_ACEITAVEL_NOS_FLOATS);
    }
}
