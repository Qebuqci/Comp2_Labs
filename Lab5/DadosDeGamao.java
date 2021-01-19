public class DadosDeGamao extends Dado implements Sorteador
{
    private int resultadoSorteio;


    /**
     ** Lança (sorteia) dois dados
     ** Se o resultado do segundo lançamento for igual ao primeiro,
     ** o resultado final é de duas vezes a soma de ambos os lançamentos
     ** ou seja, o quádruplo do lançamento.
     ** 
     ** Caso contrário, o resultado final é somente a soma de ambos os 
     ** lançamentos
     **/  
    public int sortear()
    {
	Dado dadoSimples = new Dado();
	this.resultadoSorteio = dadoSimples.sortear(); // primeiro lançamento
	int segundoSorteio = dadoSimples.sortear();
	if(segundoSorteio == this.resultadoSorteio)
	{
	    this.resultadoSorteio = 4*this.resultadoSorteio;
	    return this.resultadoSorteio;
	}
	this.resultadoSorteio += segundoSorteio;
	return this.resultadoSorteio;
	    
    }
    

}
