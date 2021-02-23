import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LojaTest {

    Loja loja;
    Livro livro1;
    Livro livro2;
    Livro livro_didatico;
    CD cd1;
    CD cd2;
    Bicicleta bicicleta1;
    Usuario comprador;
    Transportadora gatoPreto;
    ImpressoraJatoDeTinta impressoraJatoDeTinta1;
    Grafica grafica1;

    @Before
    public void setUp() {
        gatoPreto = new Transportadora();
        impressoraJatoDeTinta1 = new ImpressoraJatoDeTinta("HP", 2018);
        grafica1 = new Grafica();

        loja = new Loja(
                gatoPreto,                // informamos à loja qual a transportadora que ela vai usar (agregação)
                impressoraJatoDeTinta1);  // ...e o serviço de impressão que ela vai usar (agregação tb)

				livro1 = new Livro(12345, "Da Terra à Lua", "Julio Verne", null, 1865, CategoriaLivro.FICCAO);
        livro1.setPrecoEmReais(25);

        livro2 = new Livro(12446, "Dom Quixote", "Miguel de Cervantes", null, 1605, CategoriaLivro.FICCAO);
        livro2.setPrecoEmReais(42.30f);
        
        livro_didatico = new Livro(1312, "Cálculo multivariável vol 2", "James Stewart", null, 2010, CategoriaLivro.LIVRO_DIDATICO);
        livro_didatico.setPrecoEmReais(150f);

        cd1 = new CD(121223, "Ride The Lightning", "Metallica", 1985);
        cd1.setPrecoEmReais(18.50f);
        
        cd2 = new CD(2131, "Meteor", "Linkin Park", 2005); // criado para testar o controle de estoque
        cd2.setPrecoEmReais(15f);

        bicicleta1 = new Bicicleta(9999, 700, "Pinarello");
        bicicleta1.setPrecoEmReais(580);

        loja.incluirItem(livro1);
        // inclui 5 quantidades de livro2 no estoque
        loja.incluirItem(livro2);
        loja.incluirItem(livro2);
        loja.incluirItem(livro2);
        loja.incluirItem(livro2);
        loja.incluirItem(livro2);
        loja.incluirItemNaQuantidade(3,livro_didatico);
        loja.incluirItem(cd1);
        loja.incluirItemNaQuantidade(5, cd2);
        loja.incluirItem(bicicleta1);
        loja.incluirItem(bicicleta1);
        loja.incluirItem(bicicleta1);

        comprador = new Usuario(111111, "Maria");
        comprador.setEndereco("Rua Tal, Numero Tal");
    }

    @Test
    public void testarVendaParaProdutoExistente() throws
            ItemNaoExisteNoCatalogoException, EstoqueInsuficienteException,
            EnderecoInvalidoException, ErroNoPagamentoException {

        String recibo = null;      
        recibo = loja.receberPedido(livro2, 5, comprador);

        assertNotNull(recibo);
        System.out.println(recibo);
        
        assertEquals(0, loja.consultaQuantidadeNoEstoque(livro2)); // verifica se abateu pós venda

        recibo = loja.receberPedido(cd1, 1, comprador);
        assertNotNull(recibo);
        System.out.println(recibo);

        recibo = loja.receberPedido(bicicleta1, 3, comprador);
        assertNotNull(recibo);
        System.out.println(recibo);
        
        assertEquals(0, loja.consultaQuantidadeNoEstoque(bicicleta1)); // verifica se abateu
        
        // Verificar o preço (sem e com desconto) printando recibo em cada caso
        recibo = loja.receberPedido(livro1, 1, comprador);
        System.out.println(recibo);
        
        recibo = loja.receberPedido(livro_didatico, 3, comprador);
        System.out.println(recibo);
    }

    @Test
    public void testarVendaParaProdutoNaoExistente() throws
            EnderecoInvalidoException, ItemNaoExisteNoCatalogoException,
            EstoqueInsuficienteException, ErroNoPagamentoException {

				Livro livroNaoExistente = new Livro(1010101, "Blah", "Qualquer coisa", null, 2000, null);
				String recibo;
								
				try
				{
		      recibo = loja.receberPedido(livroNaoExistente, 5, comprador);
		    }
		    catch (ItemNaoExisteNoCatalogoException e) 
		    {
		    	recibo = null;
		    }
		    	    
        assertNull(recibo);
    }
    
    @Test 
    public void testarFuncionamentoBásicoDoEstoque() throws
            EnderecoInvalidoException, ItemNaoExisteNoCatalogoException,
            EstoqueInsuficienteException, ErroNoPagamentoException 
    {
    	assertEquals(1, loja.consultaQuantidadeNoEstoque(livro1));
    	assertEquals(5, loja.consultaQuantidadeNoEstoque(livro2));
    	assertEquals(5, loja.consultaQuantidadeNoEstoque(cd2));
    	
    	String recibo = null;
    	recibo = loja.receberPedido(cd2, 3, comprador);
    	assertNotNull(recibo);
    	assertEquals(2, loja.consultaQuantidadeNoEstoque(cd2));
    	
    	// Quantidade não existente, não deixa fazer a venda
    	try
    	{
    		recibo = loja.receberPedido(cd2, 5, comprador);
    	}
    	catch (EstoqueInsuficienteException e)
    	{
    		recibo = null;
    	}
    	assertNull(recibo);
    	assertEquals(2, loja.consultaQuantidadeNoEstoque(cd2));
    
    }
    
    @Test
    public void testarMapaDeControleDeEstoque() throws
            EnderecoInvalidoException, ItemNaoExisteNoCatalogoException,
            EstoqueInsuficienteException, ErroNoPagamentoException 
    {
			// objetos com instancias iguais, mas referencias diferentes
    	Bicicleta bike1 = new Bicicleta(123, 26, "Caloi");
    	Bicicleta bike2 = new Bicicleta(123, 26, "Caloi");
    	
    	loja.incluirItemNaQuantidade(5, bike1);
    	assertEquals(loja.consultaQuantidadeNoEstoque(bike1), loja.consultaQuantidadeNoEstoque(bike2));
    	
    	// Mesma coisa para livros e cds
    	Livro livro3 = new Livro(1567, "The art of Computer Programming", "Donald Knuth", null, 1968, CategoriaLivro.LIVRO_DIDATICO);
    	Livro livro4 = new Livro(1567, "The art of Computer Programming", "Donald Knuth", null, 1968, CategoriaLivro.LIVRO_DIDATICO);
    	
    	loja.incluirItemNaQuantidade(5, livro3);
    	assertEquals(loja.consultaQuantidadeNoEstoque(livro3), loja.consultaQuantidadeNoEstoque(livro4));
    	
    	CD cd3 = new CD(321, "Back in Black", "AC/DC", 1990);
    	CD cd4 = new CD(321, "Back in Black", "AC/DC", 1990);
    	
    	loja.incluirItemNaQuantidade(5, cd3);
  		assertEquals(loja.consultaQuantidadeNoEstoque(cd3), loja.consultaQuantidadeNoEstoque(cd4));
    	   	
    	    
    }
       
}