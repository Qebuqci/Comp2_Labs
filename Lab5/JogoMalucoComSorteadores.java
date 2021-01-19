public class JogoMalucoComSorteadores extends JogoDeDoisJogadores
{
    
    Sorteador sorteador1;
    Sorteador sorteador2;
    
    public JogoMalucoComSorteadores(Sorteador sorteador1, Sorteador sorteador2, String nomeDoJogo, String participante1, String participante2, int numeroRodadas)
    {
	super(nomeDoJogo, participante1, participante2, numeroRodadas);
	this.sorteador1 = sorteador1;
	this.sorteador2 = sorteador2;
     }

    @Override
    protected int executarRodadaDoJogo()
    {
	int resultadoSorteioJogador1 = sorteador1.sortear();
	int resultadoSorteioJogador2 = sorteador2.sortear();
	if (resultadoSorteioJogador1 > resultadoSorteioJogador2)
	{
	    System.out.println("Vitória jogador [" + this.getNomeJogador1() +"]");
	    return 1;
	}
	else if (resultadoSorteioJogador1 < resultadoSorteioJogador2)
       {
	   System.out.println("Vitória jogador [" + this.getNomeJogador2() + "]");
	   return 2;
       }
	else
	{
	    System.out.println("Empate");
	    return 0;
	}

    }

    public static void main(String[] args)
    {
	DadosDeGamao dadoDeGamao = new DadosDeGamao();
	DadosTriplos dadoTriplo = new DadosTriplos();
		
	JogoMalucoComSorteadores jogoMaluco;
	for(int i=1;i<=100;i++)
	{
	    System.out.println("# QUE comecem os jogos #");
	    jogoMaluco = new JogoMalucoComSorteadores(dadoDeGamao, dadoTriplo, "Jogo Maluco", "A", "B", i);
       	    System.out.println(jogoMaluco.jogar());

	}


    }



    
}
