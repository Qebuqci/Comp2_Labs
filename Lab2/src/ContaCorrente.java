import java.util.ArrayList;
import java.util.Date;

public class ContaCorrente {

    private final long numeroDaConta;

    private final Agencia agencia;

    private float saldoEmReais;

    private Date dataDeCriacao;

    private Pessoa correntista;

    private Pessoa gerenteDaConta;

    private ArrayList<String> historicoDeOperacoes;

    public ContaCorrente(long numeroDaConta, Pessoa correntista, Agencia agencia) {
        this.historicoDeOperacoes = new ArrayList<>();
        this.dataDeCriacao = new Date();  // data corrente
        this.saldoEmReais = 10;  // o banco dá 10 reais de estímulo para a abertura de conta
        this.numeroDaConta = numeroDaConta;
        this.correntista = correntista;
        this.agencia = agencia;
    }

    public float getSaldoEmReais() {  // getter (métodos de acesso para leitura)
        return saldoEmReais;
    }

    public void depositar(float valor) {
        // valida o parâmetro
        if (valor <= 0) {
            return;  // ToDo lançar uma exceção!!!!
        }

        // altera o saldo
        saldoEmReais += valor;

        historicoDeOperacoes.add("Deposito em dinheiro: " + valor +
                "na data " + new Date());
    }

    /**
     * Transfere um valor desta conta para a conta destino informada, se houver saldo suficiente
     * nesta conta.
     *
     * @param valor o valor desejado
     * @param contaDestino a conta de destino
     */
    public void transferir(float valor, ContaCorrente contaDestino) {
	if(this.getSaldoEmReais() > valor && valor > 0)
	 {
	     contaDestino.depositar(valor);
	     this.saldoEmReais -= valor;

	 }
	else if(valor < 0)
	{
		// Lançar uma exceção alertando a tentativa de transferência com valor negativo
		System.out.println("Tentativa negativa de transferência");
	}
	else
	{
	    // Lançar uma exceção alertando que a conta não tem saldo
	    System.out.println("A conta não tem saldo suficiente para transferir");
	}
    }

    /**
     *
     * Saca um valor em reais, se houver saldo. Se não há, imprime na tela que não há
     * 
     * @param valor de saque
     * 
     **/
    public void sacar(float valor)
    {
	String historico = "Saque em dinheiro de R$ " + valor + "na data "+ new Date(); 
	if(this.getSaldoEmReais() > valor && valor > 0)
	{
	    this.saldoEmReais -= valor;
	    this.historicoDeOperacoes.add(historico);
	}
	else if(valor < 0)
	{
	    // Lançar uma exceção alertando da tentativa negativa de saque
	    System.out.println("Tentativa negativa de saque");
	    
	}
	else
	{
	    // Lançar uma exceção alertando que a conta não tem saldo
	    System.out.println("A conta não tem saldo suficiente para sacar");
	}
    }

    
}
