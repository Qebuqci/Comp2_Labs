import java.util.Scanner;
public class Principal
{
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args)
    {
	System.out.println(" # Bem vindo ao jogo Com Dados Sorteadores # ");
	System.out.println("Para jogar, aperte 1. Caso contrário, aperte qualquer número para fechar");
	if(scanner.nextInt() == 1)
	{
	    jogo();
	}
    }

    public static void jogo()
    {
	System.out.println("Entre com o nome do jogador 1 : ");
	String jogador1 = scanner.next();
	System.out.println("Entre com o nome do jogador 2 : ");
	String jogador2 = scanner.next();
	System.out.println("Escolha o tipo de sorteador para o jogador 1");
	System.out.println("1 - Dado Simples\n2 - Dados de Gamão\n3 - Dados Triplos");
	Sorteador sorteador1 = escolhaSorteador();
	System.out.println("Escolha o tipo de sorteador para o jogador 2");
	Sorteador sorteador2 = escolhaSorteador();
	System.out.println("Entre com o número de rodadas : ");
	int numeroRodadas = scanner.nextInt();
	JogoMalucoComSorteadores jogoMaluco = new JogoMalucoComSorteadores(sorteador1, sorteador2, "Jogo com Dados Sorteadores", jogador1, jogador2, numeroRodadas );
	System.out.println(jogoMaluco.jogar());
	
    }

    public static Sorteador escolhaSorteador()
    {
	int escolha = scanner.nextInt(); // Se a escolha não estiver no menu - lançar uma exceção
	switch(escolha)
	    {
	    case 1:
		Dado dadoSimples = new Dado();
		return dadoSimples;
	   case 2:
		DadosDeGamao dadoDeGamao = new DadosDeGamao();
		return dadoDeGamao;
      
	    case 3:
		DadosTriplos dadoTriplo = new DadosTriplos();
		return dadoTriplo;
       
	    }
	return null;
    }
}
