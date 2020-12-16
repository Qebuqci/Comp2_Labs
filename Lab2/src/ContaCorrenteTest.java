import org.junit.Test;

import static org.junit.Assert.*;

public class ContaCorrenteTest {

    private final float ERRO_ACEITAVEL_NOS_FLOATS = 0.000001f;

    @Test
    public void testarDeposito() {
        Pessoa maria = new Pessoa("Maria", 12345678);
        Agencia minhaAgencia = new Agencia();
        ContaCorrente conta = new ContaCorrente(1, maria, minhaAgencia);

        // sanity check: a conta já começa com saldo 10 (regra de negócio)
        assertEquals(10f, conta.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

        conta.depositar(1000);
        assertEquals(1010f, conta.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

        conta.depositar(500);
        assertEquals(1510f, conta.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

        conta.depositar(-100);
        assertEquals(1510f, conta.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);  // nada mudou,porque o depósito não foi feito
   }

    @Test
    public void testarTransferecia() {
        Pessoa maria = new Pessoa("Maria", 12345678);
        Pessoa joao = new Pessoa("Joao", 23222);
        Agencia minhaAgencia = new Agencia();

        ContaCorrente contaMaria = new ContaCorrente(1, maria, minhaAgencia);
        ContaCorrente contaJoao = new ContaCorrente(2, joao, minhaAgencia);

        // sanity check: as contas já começam com saldo 10 (regra de negócio)
        assertEquals(10f, contaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
        assertEquals(10f, contaJoao.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

        contaMaria.transferir(7, contaJoao);

        assertEquals(3f, contaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
        assertEquals(17f, contaJoao.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

	// tentativa negativa de transferência
	contaJoao.transferir(-3, contaMaria);
	assertEquals(17f, contaJoao.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
	assertEquals(3f, contaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
    }

    @Test
    public void testarTransfereciaSemFundosNaContaDeOrigem() {
        Pessoa maria = new Pessoa("Maria", 12345678);
        Pessoa joao = new Pessoa("Joao", 23222);
        Agencia minhaAgencia = new Agencia();

        ContaCorrente contaMaria = new ContaCorrente(1, maria, minhaAgencia);
        ContaCorrente contaJoao = new ContaCorrente(2, joao, minhaAgencia);

        // sanity check: as contas já começam com saldo 10 (regra de negócio)
        assertEquals(10f, contaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
        assertEquals(10f, contaJoao.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

        contaMaria.transferir(200, contaJoao);

        assertEquals(10f, contaMaria.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
        assertEquals(10f, contaJoao.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
        // a transferência NÃO DEVE SER REALIZADA, porque não fundos na conta de origem (Maria).
    }

    @Test
    public void testarSaque()
    {
	Pessoa alguem = new Pessoa("Alguem", 1234);
	Agencia agenciaAleatoriaBanco = new Agencia();
	ContaCorrente contaAlguem = new ContaCorrente(123, alguem, agenciaAleatoriaBanco);

	contaAlguem.sacar(100); // vai tentar sacar e não vai conseguir, pois não há saldo suficiente
	assertEquals(10f, contaAlguem.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
	
	contaAlguem.depositar(100); // vai depositar para sacar
	contaAlguem.sacar(90);
        assertEquals(20f, contaAlguem.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);

	contaAlguem.sacar(-30); // vai tentar sacar negativo
	assertEquals(20f, contaAlguem.getSaldoEmReais(), ERRO_ACEITAVEL_NOS_FLOATS);
    }
}
