import java.util.Objects;
public class Livro extends ArtigoCultural {

    private final int codigoISBN;

    private String titulo;
    
    private CategoriaLivro categoria; 

    private String autor;

    private String editora;

    private int anoPublicacao;

    private int numeroDePaginas;

    public Livro(int codigoISBN,
                 String titulo, String autor, String editora, int anoPublicacao, CategoriaLivro categoriaDoLivro) {

        super(codigoISBN,
                String.format("Livro: %s (%s, %d)",
                titulo, autor, anoPublicacao));

        this.codigoISBN = codigoISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.categoria = categoriaDoLivro;
    }

		public CategoriaLivro getCategoria()
		{
			return this.categoria;
		}

    public int getCodigoISBN() {
        return codigoISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public void setNumeroDePaginas(int numeroDePaginas) {
        this.numeroDePaginas = numeroDePaginas;
    }
    
    
    @Override
    public String toString()
    {
    	return "\nLivro: " + this.titulo + '\n' + "Autor: " + this.autor + '\n' + 
    	"Editora: " + this.editora + '\n' + "Ano de publicação: " + this.anoPublicacao + 
    	'\n' + "Categoria: " + this.categoria.getAbreviacao();
    }
    
    @Override
    public boolean equals(Object o)
    {
    	if (this == o)
    	{
    		return true;
    	}
    	
    	if (o == null || o.getClass() != this.getClass())
    	{
    		return false;
    	}
    	
    	Livro outroLivro = (Livro) o;
    	return Objects.equals(this.codigoISBN, outroLivro.codigoISBN) && 
    	Objects.equals(this.categoria, outroLivro.categoria);
    }
       
    @Override
    public int hashCode()
    {
    	return Objects.hash(this.codigoISBN,this.categoria);
    }
    
}
