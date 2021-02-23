public enum CategoriaLivro
{
	ENCICLOPEDIA("ENC"),
	LIVRO_DIDATICO("LD"),
	FICCAO("FIC"),
	BIOGRAFIA("BIO"),
	ARTE("AR"),
	DICIONARIO("DIC"),
	NAO_FICCAO("NF");

	private String categoriaAbreviada;
	
	CategoriaLivro(String abreviacao)
	{
		this.categoriaAbreviada = abreviacao;
	}
	
	public String getAbreviacao()
	{
		return this.categoriaAbreviada;
	}

}