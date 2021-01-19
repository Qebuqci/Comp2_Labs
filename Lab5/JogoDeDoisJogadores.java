import java.util.List;
import java.util.ArrayList;
public abstract class JogoDeDoisJogadores
{
    private String nomeJogo;
    private String nomeJogador1;
    private String nomeJogador2;
    private int numeroDeRodadas;
    ArrayList<Integer> historicoResultados;

    public JogoDeDoisJogadores(String nomeDoJogo, String participante1, String participante2, int numeroRodadas)
    {
	this.nomeJogo = nomeDoJogo;
	this.nomeJogador1 = participante1;
	this.nomeJogador2 = participante2;
	this.numeroDeRodadas = numeroRodadas;
    }

    // Getters
    public String getNomeJogo()
    {
	return this.nomeJogo;
    }

    public String getNomeJogador1()
    {
	return this.nomeJogador1;
    }

    public String getNomeJogador2()
    {
	return this.nomeJogador2;
    }

    public int getNumeroDeRodadas()
    {
	return this.numeroDeRodadas;
    }

    public String jogar()
    {
	int statusJogador1 = 0;
	int statusJogador2 = 0;
	String resultadoJogo; 
	for(int i=1;i<=this.numeroDeRodadas;i++)
	{
	    System.out.printf("# Rodada # " + i + " : ");
	    int statusVencedor = executarRodadaDoJogo(); 
	    switch(statusVencedor)
	    {
	    case 1:
		statusJogador1 += 1;
		break;
	    case 2:
		statusJogador2 += 1;
		break;
	    }
	}
	if(statusJogador1 > statusJogador2)
	{
	   resultadoJogo = "O jogador [" + this.nomeJogador1 + "] venceu o jogo por " + statusJogador1 + " a " + statusJogador2 ;
	}
	else if (statusJogador2 > statusJogador1)
	{
	    resultadoJogo = "O jogador [" + this.nomeJogador2 + "] venceu o jogo por " + statusJogador2 + " a " + statusJogador1;
	}
	else
	{
	     resultadoJogo = "O jogo terminou em empate após " + this.numeroDeRodadas + " rodadas";
	    
	}
return resultadoJogo;

    }

    protected abstract int executarRodadaDoJogo();
}
