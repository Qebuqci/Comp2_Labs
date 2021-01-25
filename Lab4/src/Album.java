import java.util.HashMap;
public class Album {

    protected static final float PERCENTUAL_MINIMO_PARA_AUTO_COMPLETAR = 10f;
        
    private int quantFigurinhasPorPacotinho;
    private int tamanhoAlbum;

    private HashMap<Integer, Figurinha> figurinhasDoAlbum;
    private int[] figurinhasRepetidas;  // o indice representa a posicao da figurinha e o valor em tal indice, a quantidade de figs repetidas

    public Album(int tamanhoDoAlbum, int quantFigurinhasPorPacotinho) {
        this.tamanhoAlbum = tamanhoDoAlbum;
	this.quantFigurinhasPorPacotinho = quantFigurinhasPorPacotinho;
	figurinhasDoAlbum = new HashMap<>();
	figurinhasRepetidas = new int[tamanhoAlbum];
    }
    
    public void receberNovoPacotinho(Pacotinho pacotinho)
    {
        for (Figurinha fig : pacotinho)
	{
	    if(possuiFigurinhaColada(fig.getPosicao()))
	    {
		figurinhasRepetidas[fig.getPosicao()]+=1;
	    }
	    else // cola figurinhas no album assim que recebe um pacotinho
	    {
		figurinhasDoAlbum.put(fig.getPosicao(), fig);
	    }
            
        }
    }
   
    public void autoCompletar() {
        // verifica se o álbum já está suficientemente cheio
	if(!(this.getQuantFigurinhasFaltantes() == this.tamanhoAlbum))
	{
	    for(int posFig=0; posFig < tamanhoAlbum;posFig++)
	    {
		if(!possuiFigurinhaColada(posFig))
		    {
			Figurinha fig = new Figurinha(posFig);
			this.figurinhasDoAlbum.put(posFig, fig);
		    }
	    }
	}
       
    }

    public int getTamanho() {
        return this.tamanhoAlbum;
    }

    public int getQuantFigurinhasPorPacotinho() {
        return this.quantFigurinhasPorPacotinho;
    }

    public int getQuantFigurinhasColadas()
    {
        return figurinhasDoAlbum.size();
    }

    public int getQuantFigurinhasRepetidas() {
	int quantidadeFigRepetidas = 0;
        for(int valores: figurinhasRepetidas)
	    {
		quantidadeFigRepetidas += valores;
	    }
        return quantidadeFigRepetidas;
    }

    public boolean possuiFigurinhaColada(int posicao) {
        return figurinhasDoAlbum.containsKey(posicao);
    }

    public boolean possuiFigurinhaRepetida(int posicao) {
	if(this.figurinhasRepetidas[posicao] == 0)
	    {
		return false;
	    }
	return true;
    }

    public int getQuantFigurinhasFaltantes()
    {
	return (this.tamanhoAlbum - this.getQuantFigurinhasColadas());
    }
    
}
