public class EstoqueException extends Exception
{
    private final String MSG_PRODUTO_INEXISTENTE = "Produto inexistente";
    private int quantidadeEmEstoque;

    
    public EstoqueException()
    {
        
    }

   
    public EstoqueException(int quantidadeEmEstoque)	 
    {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
    
    
    public int getQuantidadeEmEstoque()
    {
        return quantidadeEmEstoque;
    }


}
