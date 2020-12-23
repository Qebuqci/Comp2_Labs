import java.util.Scanner;
public class Principal {

    private static Agencia agenciaJava = new Agencia();
  
    public static void main(String[] args) {

	boolean chaveWhile = true;
	System.out.println("# Bem vindo ao banco Java #\nEscolha uma opção baseado na letra entre parênteses");
	while (chaveWhile)
	 {
	    System.out.println("\n: Menu de opções :");
	    System.out.println("(D)epositar\n(S)acar\n(T)ransferir\n(C)onsultar saldo\nCadastrar (P)essoa como correntista\nCriar (N)ova conta\n(X) para sair");
	    System.out.printf("Opção desejada : ");
	    Scanner scanner = new Scanner(System.in);
	    char opcao = scanner.nextLine().charAt(0);
	    switch(opcao)
		{
		case 'D':
		    depositar();
		    break;
		case 'S':
		    sacar();
		    break;
		case 'T':
		    transferir();
		    break;
		case 'C':
		    consultarSaldo();
		    break;
		case 'P':
		    cadastrarPessoaCorrentista();
		    break;
		case 'N':
		    criarNovaConta();
		    break;
		case 'X':
		    chaveWhile = false;
		    break;
		default:
		    System.out.println(">> ERRO de entrada : Coloque um caracter válido no menu \n");
		}
	 }	
    }


    // Verifica a existência de uma conta na estrutrura de dados (HashMap - nContaByConta) criado em ContaCorrente
    public static boolean verificaExistenciaConta(long numeroDaConta)
    {
	if (ContaCorrente.nContaByConta.containsKey(numeroDaConta))
	    {
		return true;
	    }
	return false;
    }

    // Caso exista uma conta, retorna essa conta
    public static ContaCorrente retornaContaCasoExista(long numeroDaConta)
    {
	if (verificaExistenciaConta(numeroDaConta))
	{
	    System.out.println("Tudo certo. Conta existente");
	    return ContaCorrente.nContaByConta.get(numeroDaConta);
	}
	else
	{
	    System.out.println("Conta ainda não cadastrada \n");
	    return null;
	}
    }

    public static void depositar()
    {
	System.out.printf("Por favor, informe o número da conta para depósito : ");
	Scanner scanner = new Scanner(System.in);
	long numeroDaConta = scanner.nextLong();
	ContaCorrente conta = retornaContaCasoExista(numeroDaConta);
	if(conta != null)
	{
	    System.out.printf("Insira o valor do depósito : ");
	    float valor = scanner.nextFloat();
	    conta.depositar(valor);
	    System.out.println("\nHistórico de operações da conta : " + conta.getNumeroDaConta()+ " - correntista (CPF) : " + conta.getCorrentista().getCpf() + "\n" +
			       conta.getHistoricoDeOperacoes());
	}
	
    }

    public static void consultarSaldo()
    {
	System.out.printf("Informe o número da conta para a consulta de saldo : ");
	Scanner scanner = new Scanner(System.in);
	long numeroDaConta = scanner.nextLong();
	ContaCorrente conta = retornaContaCasoExista(numeroDaConta);
	if(conta != null)
	{
	    System.out.printf("Saldo da conta " + conta.getNumeroDaConta() + " em reais : R$ " );
	    System.out.println(conta.getSaldoEmReais());
	}
	
    }

    // Overload
    public static void criarNovaConta()
    {
	cadastrarPessoaCorrentista();
    }
    
    // Overload
    public static void criarNovaConta(Pessoa p1)
    {
	ContaCorrente novaConta = new ContaCorrente(p1, agenciaJava);
	System.out.println("Correntista : " + p1.getCpf() + " / Número da nova conta : " + novaConta.getNumeroDaConta());
    }

    public static void cadastrarPessoaCorrentista()
    {
	System.out.println("Por favor, informe os dados de cadastro para um novo correntista : \n");
	Scanner scanner = new Scanner(System.in);
	System.out.printf("Nome : ");
	String nome = scanner.nextLine();
	System.out.printf("\nCPF : ");
	Long cpf = scanner.nextLong();
	Pessoa correntista = new Pessoa(nome, cpf);
	System.out.println("\nTudo certo. Abrimos uma conta para você \n");
	criarNovaConta(correntista);

    }

    public static void transferir()
    {
	System.out.printf("Por favor, informe a conta de origem : ");
	Scanner scanner = new Scanner(System.in);
	Long numeroDaContaOrigem = scanner.nextLong();
	ContaCorrente contaOrigem = retornaContaCasoExista(numeroDaContaOrigem);
	if(contaOrigem != null)
	{
	    System.out.printf("Por favor, informe a conta de destino : ");
	    Long numeroDaContaDestino = scanner.nextLong();
	    ContaCorrente contaDestino = retornaContaCasoExista(numeroDaContaDestino);
	    if(contaDestino != null)
	    {
		System.out.printf("Por favor, informe o valor : ");
		float valor = scanner.nextFloat();
		contaOrigem.transferir(valor, contaDestino);
		System.out.println("\nHistórico de operações da conta Origem: " + contaOrigem.getNumeroDaConta()+ " - correntista (CPF) : " + contaOrigem.getCorrentista().getCpf() + "\n" +
			       contaOrigem.getHistoricoDeOperacoes());
		System.out.println("\nHistórico de operações da conta Destino: " + contaDestino.getNumeroDaConta()+ " - correntista (CPF) : " + contaDestino.getCorrentista().getCpf() + "\n" +
			       contaDestino.getHistoricoDeOperacoes());
	    }
	}

    }

    public static void sacar()
    {
	System.out.printf("Por favor, informe a conta para saque : ");
	Scanner scanner = new Scanner(System.in);
	Long numeroDaConta = scanner.nextLong();
	ContaCorrente conta = retornaContaCasoExista(numeroDaConta);
	if(conta!=null)
	{
	    System.out.printf("Informe o valor para saque : ");
	    float valor = scanner.nextFloat();
	    conta.sacar(valor);
	    System.out.println("\nHistórico de operações da conta : " + conta.getNumeroDaConta()+ " - correntista (CPF) : " + conta.getCorrentista().getCpf() + "\n" +
			       conta.getHistoricoDeOperacoes());
	    
        }
	    
    }

}
