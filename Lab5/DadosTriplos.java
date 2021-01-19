public class DadosTriplos implements Sorteador
{
    private int resultadoSorteio;
    
    /** Sorteia três dados e o resultado do sorteio
     ** (final) é a soma dos três sorteios
     **
     **/
    public int sortear()
    {
	Dado dadoSimples = new Dado();
	for (int i=1 ; i<=3 ;i++)
	{
	    this.resultadoSorteio += dadoSimples.sortear();
	}
	return this.resultadoSorteio;
    }
    

}
