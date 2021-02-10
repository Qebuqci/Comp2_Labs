import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Loja virtual para a venda de livros (a princípio).
 */
public class Loja {

    private static final int NUMERO_DE_PEDIDOS_POR_RELATORIO = 1;

    private String nomeDaLoja;

    private ArrayList<Vendavel> catalogo;

    private Map<Vendavel, Integer> quantidadeEmEstoquePorItem;

    private Transportadora frete;

    private Impressora impressora;

    private long quantPedidosRecebidos;

    public Loja(Transportadora transportadora,
                Impressora impressora) {

        catalogo = new ArrayList<>();  // COMPOSIÇÃO
        setFrete(transportadora);      // AGREGAÇÃO
        this.impressora = impressora;  // AGREGAÇÃO
        this.quantPedidosRecebidos = 0;


	quantidadeEmEstoquePorItem = new HashMap<>(); // COMPOSIÇÃO
    }

    public void setFrete(Transportadora transportadora) {
        frete = transportadora;
    }

    public String receberPedido(Vendavel item, int quantidade, Usuario usuario)
            throws
            ItemNaoExisteNoCatalogoException,
            EstoqueException,
            EnderecoInvalidoException,
            ErroNoPagamentoException {

	try
	{
		// verifica se existe no catálogo da loja
		if (buscarItem(item.getId()) == null)
		{
		    throw new ItemNaoExisteNoCatalogoException();
		}

		if (!(contemItemEstoque(item)))
	        {
			throw new EstoqueException();
		}
	
		// verifica se existe aquela quantidade do produto desejado
		// no estoque da loja
		final Integer quantidadeEmEstoque = quantidadeEmEstoquePorItem.get(item);

		if (quantidadeEmEstoque < quantidade) {
		    throw new EstoqueException(quantidadeEmEstoque);
		}

		// verifica se o usuário tem um endereço de entrega válido
		if (usuario.getEndereco() == null) {
		    throw new EnderecoInvalidoException();
		}
	}
	
	catch(Exception e)
	 {
	     String recibo = null;
	     return recibo;
	 }
	
        float precoTotal = quantidade * item.getPrecoEmReais();

        if (!processarPagamento(precoTotal)) {
            throw new ErroNoPagamentoException();
        }

        if (item instanceof Transportavel) {  // é transportável?
            // cria um array com todos os itens que precisarão ser entregues
            // (possivelmente várias unidades do mesmo item)
            ArrayList<Transportavel> pedido = new ArrayList<>();
            for (int i = 0; i < quantidade; i++) {
                pedido.add((Transportavel) item);
            }
            frete.transportar(pedido, usuario.getEndereco());
        }

        if (++this.quantPedidosRecebidos % NUMERO_DE_PEDIDOS_POR_RELATORIO == 0) {
            imprimirRelatorioUltimasVendas();
        }

        String recibo = String.format("Recibo no valor de R$%.2f referente à " +
                "compra de %d unidades do item: %s",
                precoTotal, quantidade, item);

	abaterEstoque(item,quantidade);
      
        return recibo;
    }

    private void imprimirRelatorioUltimasVendas() {
        String relatorio = ".................To Do...............";
        this.impressora.imprimir(relatorio);
    }


    /** 
     * Inclui UM (1) item no catálogo e no estoque da Loja. Se o produto já existe no 
     * icrementa a quantidade dele no estoque de 1. Caso contrário, coloca o item no catálogo e 
     * no estoque.
     *
     * @param Um item vendavel
     * @return nada, é um método void
     **/
    public void incluirItem(Vendavel vendavel)
    {
	if (buscarItem(vendavel.getId()) != null) {
            // produto já existe no catálogo -- icrementa a quantidade dele no estoque de 1
            Integer quantidadeNoEstoque = quantidadeEmEstoquePorItem.get(vendavel);
	    quantidadeEmEstoquePorItem.put(vendavel, quantidadeNoEstoque++);
	    return;
        } // caso contrário, coloca o item no catálogo e no estoque
        catalogo.add(vendavel);
	quantidadeEmEstoquePorItem.put(vendavel, 1);
    }

    /** 
     * Inclui uma quantidade de itens no catálogo e no estoque da Loja (como uma 
     * compra no atacado). Se o produto já existe no catálogo, atualiza a quantidade
     * dele no estoque, icrementando a quantidade no estoque, pela quantidade incluída.
     * Caso contrário, insere o produto tanto no catálogo quanto no estoque, sendo
     * no estoque, com a quantidade informada.
     *
     *
     * @param Um item vendavel, a quantidade de compras (inclusões) desse
     * @return nada, é um método void
     *
     **/
    public void incluirItem(Vendavel vendavel, int quantidade)
    {
	if (buscarItem(vendavel.getId()) != null) {
            // produto já existe no catálogo -- atualiza a quantidade dele no estoque
            Integer quantidadeNoEstoque = quantidadeEmEstoquePorItem.get(vendavel);
	    quantidadeEmEstoquePorItem.put(vendavel, quantidadeNoEstoque+quantidade);
	    return;
        } // caso contrário, coloca o item no catálogo e no estoque
        catalogo.add(vendavel);
	quantidadeEmEstoquePorItem.put(vendavel,quantidade);
    }


    /** Abate uma quantidade de itens no estoque. Se a quantidade a ser abatida for maior ou igual
     * a quantidade no estoque, remove o item do estoque e do catálogo.
     * Caso contrário, apenas atualiza o estoque, para a diferença da quantidade atual
     * pela quantidade a ser abatida.
     *
     * @param um vendavel e uma quantidade inteira
     *
     **/ 
    public void abaterEstoque(Vendavel vendavel, int quantidadeAbatida) 
    {
	int quantidadeEstoque = quantidadeEmEstoquePorItem.get(vendavel);
	if (quantidadeAbatida >= quantidadeEstoque)
	    {
		quantidadeEmEstoquePorItem.remove(vendavel);
		catalogo.remove(vendavel);
	    }
	else
	    {
		quantidadeEmEstoquePorItem.put(vendavel, quantidadeEstoque-quantidadeAbatida);
	    }
    }

    /** Verifica se tal item está no estoque
     *
     * @param item vendavel
     * @return true caso sim, false caso não
     *
     **/
    public boolean contemItemEstoque(Vendavel vendavel)				       
    {
	if(quantidadeEmEstoquePorItem.containsKey(vendavel) == true )
	{
	    return true;
	}
	return false;
    }

    public Integer getQuantidadeEstoque(Vendavel item)
    {
	return quantidadeEmEstoquePorItem.get(item);
    }
        
    /**
     * Busca um ítem no catálogo da loja a partir de sua descrição.
     *
     * @param descricao a descrição do ítem desejado (ou parte dela)
     * @return o primeiro Vendavel que case com a descrição fornecida, caso encontre;
     *         ou null, caso contrário
     */
    public Vendavel buscarItem(String descricao) {
        for (Vendavel item : catalogo) {
            if (item.getDescricao().contains(descricao)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Busca um ítem no catálogo da loja a partir de seu código.
     *
     * @param id o código de identificação do ítem desejado
     * @return o Vendavel cujo código seja igual ao código fornecido, caso encontre;
     *         ou null, caso contrário
     */
    public Vendavel buscarItem(long id) {
        for (Vendavel item : catalogo) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private boolean processarPagamento(float valor) {
        // ToDo passar o cartão de crédito, ou emitir boleto, etc.
        System.out.println(String.format(
                "Processando pagamento no valor de R$%.2f...",
                valor));
        return true;  // ToDo retornar false caso o pagamento falhe
    }
}
