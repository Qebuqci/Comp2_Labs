public class Figurinha {

    private int posicao; // no Album
    private String urlImagem;
    
    public Figurinha(int posicao) {
	this.posicao = posicao;
    }

    /**
     * @return A posição que esta figurinha deve ocupar no álbum
     */
    public int getPosicao() {
       return this.posicao;  
    }

    public boolean equals(Figurinha outraFig)
    {
	return this.getPosicao() == outraFig.getPosicao();
	
    }
    
}
