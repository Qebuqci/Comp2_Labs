import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LojaTest {

    Loja loja;
    Livro livro1;
    Livro livro2;
    CD cd1;
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


        livro1 = new Livro(12345, "Da Terra à Lua", "Julio Verne", null, 1865);
        livro1.setPrecoEmReais(25);

        livro2 = new Livro(12446, "Dom Quixote", "Miguel de Cervantes", null, 1605);
        livro2.setPrecoEmReais(42.30f);

        cd1 = new CD(121223, "Ride The Lightning", "Metallica", 1985);
        cd1.setPrecoEmReais(18.50f);

        bicicleta1 = new Bicicleta(9999, 700, "Pinarello");
        bicicleta1.setPrecoEmReais(580);

        loja.incluirItem(livro1);
        loja.incluirItem(livro2);
	loja.incluirItem(livro2, 5);
        loja.incluirItem(cd1);
        loja.incluirItem(bicicleta1);
	loja.incluirItem(bicicleta1, 4);

        comprador = new Usuario(111111, "Maria");
        comprador.setEndereco("Rua Tal, Numero Tal");
    }

    @Test
    public void testarVendaParaProdutoExistente() throws
            ItemNaoExisteNoCatalogoException, EstoqueException,
            EnderecoInvalidoException, ErroNoPagamentoException {

        String recibo = null;
	
        recibo = loja.receberPedido(cd1, 1, comprador);
        assertNotNull(recibo);
	
    }

    @Test
    public void testarVendaParaProdutoNaoExistente() throws
            EnderecoInvalidoException, ItemNaoExisteNoCatalogoException,
            EstoqueException, ErroNoPagamentoException {

        Livro livroNaoExistente = new Livro(1010101, "Blah", "Qualquer coisa", null, 2000);
        String recibo = loja.receberPedido(livroNaoExistente, 5, comprador);
        assertNull(recibo);
    }

    @Test
    public void testarEstoque() throws EnderecoInvalidoException, ItemNaoExisteNoCatalogoException,
            EstoqueException, ErroNoPagamentoException
    {
	String recibo = null;

	// testa inclusão
	assertEquals(6, (int) loja.getQuantidadeEstoque(livro2)); 
	assertEquals(5, (int) loja.getQuantidadeEstoque(bicicleta1));
	
        recibo = loja.receberPedido(livro2, 5, comprador);
        assertNotNull(recibo);
	// testa abatimento
	assertEquals(1, (int) loja.getQuantidadeEstoque(livro2));

	recibo = loja.receberPedido(bicicleta1, 3, comprador);
        assertNotNull(recibo);
	// testa abatimento
	assertEquals(2, (int) loja.getQuantidadeEstoque(bicicleta1));

	// testa quantidade insuficiente
	recibo = loja.receberPedido(livro1, 200, comprador);
	assertNull(recibo);
		

	// Novos Objetos
	Bicicleta novaBike = new Bicicleta(123321, 26, "Caloi");
	Loja novaLoja = new Loja(gatoPreto, impressoraJatoDeTinta1);
	novaLoja.incluirItem(novaBike, 10);
	// verifica o estoque
	assertEquals(10, (int) novaLoja.getQuantidadeEstoque(novaBike));
	// loja vende 5 bikes dessa
	recibo = novaLoja.receberPedido(novaBike, 5, comprador);
        assertNotNull(recibo);
	// verifica o estoque novamente
	assertEquals(5, (int) novaLoja.getQuantidadeEstoque(novaBike));

	// novaLoja tenta vender mais do que tem
	recibo = novaLoja.receberPedido(novaBike, 6, comprador);
        assertNull(recibo);
	
	// e para não cometer injustiça, não vende nada, para ninguém
	assertEquals(5, (int) novaLoja.getQuantidadeEstoque(novaBike));
    }
    
    
}
