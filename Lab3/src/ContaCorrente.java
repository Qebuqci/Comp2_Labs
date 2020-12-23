import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class ContaCorrente {

    // o banco já te dá algo como estímulo :-)
    public static final float SALDO_INICIAL_DE_NOVAS_CONTAS = 10.0f;

    private static long geradorConta; // Contador (gerador) automatico de contas, que é icrementado toda vez que há uma nova conta

    private final long numeroDaConta;

    private static HashMap<Long, ContaCorrente> CpfByConta = new HashMap<>(); // Estrutura que associa o Cpf com uma conta

    protected static HashMap<Long, ContaCorrente> nContaByConta = new HashMap<>(); // Estrutura que associa o número da Conta com uma conta efetiva
    
    private final Agencia agencia;

    private float saldoEmReais;

    private Date dataDeCriacao;

    private Pessoa correntista;
   
    private Pessoa gerenteDaConta;

    private ArrayList<String> historicoDeOperacoes;

    public ContaCorrente(Pessoa correntista, Agencia agencia) {
        this.historicoDeOperacoes = new ArrayList<>();
       
        this.dataDeCriacao = new Date();  // data corrente
        this.saldoEmReais = SALDO_INICIAL_DE_NOVAS_CONTAS;

        this.correntista = correntista;
        this.agencia = agencia;

	if(ContaCorrente.CpfByConta.containsKey(correntista.getCpf()))
	{
	    this.numeroDaConta = ContaCorrente.CpfByConta.get(correntista.getCpf()).getNumeroDaConta();
	}
	else
	{
	    ContaCorrente.geradorConta++;
	    this.numeroDaConta = geradorConta;
	    CpfByConta.put(correntista.getCpf(), this);
	} 

	nContaByConta.put(this.numeroDaConta, this);
    }

    public String getHistoricoDeOperacoes() // getter para o histórico
    {
	return this.historicoDeOperacoes.toString();
    }
    
    public Pessoa getCorrentista() // getter para o correntista
    {
	return this.correntista;
    }
    
    public long getNumeroDaConta() {
        return numeroDaConta;
    }

    public float getSaldoEmReais() {  // getter (métodos de acesso para leitura)
        return saldoEmReais;
    }

    public void depositar(float valor) {
        // valida o parâmetro
        if (valor <= 0) {
	    System.out.println("Não é possível depositar uma quantia negativa");
            return;  // ToDo lançar uma exceção!!!!
        }

	System.out.println("Depósito efetuado com sucesso"); 
	
        // altera o saldo
        saldoEmReais += valor;

        historicoDeOperacoes.add("Deposito em dinheiro: R$" + valor +
                " na data " + new Date());
    }

    public void sacar(float valor) {
        // valida o parâmetro
        if (valor <= 0) {
	    System.out.println("Não é possível sacar uma quantidade negativa em reais");
            return;  // ToDo lançar uma exceção!!!!
        }

        // verifica se há fundos na conta
        if (valor > saldoEmReais) {
	    System.out.println("A conta não possui saldo suficiente");
            return;  // ToDo lançar uma exceção!!!!
        }

	System.out.println("Saque efetuado com sucesso!");
	
        saldoEmReais -= valor;

        historicoDeOperacoes.add(String.format(
                "Saque em dinheiro: R$%.2f na data %s",
                valor, new Date()));
    }

    /**
     * Transfere um valor desta conta para a conta destino informada, se houver saldo suficiente
     * nesta conta.
     *
     * @param valor o valor desejado
     * @param contaDestino a conta de destino
     */
    public void transferir(float valor, ContaCorrente contaDestino) {
        // valida o parâmetro
        if (valor <= 0) {
	    System.out.println("Não é possível transferir uma quantidade negativa de reais");
            return;  // ToDo lançar uma exceção!!!!
        }

        // verifica se há fundos na conta
        if (valor > saldoEmReais) {
	    System.out.println("A conta de origem não possui saldo suficiente para transferência");
            return;  // ToDo lançar uma exceção!!!!
        }

	System.out.println("Transferência efetuada com sucesso");
	
        saldoEmReais -= valor;
        contaDestino.saldoEmReais += valor;

        historicoDeOperacoes.add(String.format(
                "Transferência efetuada para a conta %d: R$%.2f na data %s",
                contaDestino.numeroDaConta, valor, new Date()));

        contaDestino.historicoDeOperacoes.add(String.format(
                "Transferência recebida da conta %d: R$%.2f na data %s",
                this.numeroDaConta, valor, new Date()));
    }
}
