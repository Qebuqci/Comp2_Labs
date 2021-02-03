import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class MapaTest {

    private Random random = new Random();

    private Map<Long, String> mapaUsandoDoisArraysParalelos;
    private Map<Long, String> mapaUsandoArrayUnico;
    private Map<Long, String> mapaUsandoArrayOrdenado;
    private Map<Long, String> hashMap;

    private final int TAMANHO = 50; // constante para mais inserções

    @Before
    public void setUp() {
        mapaUsandoDoisArraysParalelos = new MapaUsandoDoisArraysParalelos<>();
        mapaUsandoArrayUnico = new MapaUsandoArrayUnico<>();
        mapaUsandoArrayOrdenado = new MapaUsandoArrayOrdenado<>();
        hashMap = new HashMap<>();
    }

    @Test
    public void testeFuncionalidadeBasica()
    {
        rodarTesteDaFuncionalidadeBasica(mapaUsandoArrayUnico);
        rodarTesteDaFuncionalidadeBasica(mapaUsandoArrayOrdenado);
        rodarTesteDaFuncionalidadeBasica(mapaUsandoDoisArraysParalelos);
        rodarTesteDaFuncionalidadeBasica(hashMap);
    }

    private void rodarTesteDaFuncionalidadeBasica(Map<Long, String> mapa)
    {
	mapa.put(1234L, "Qualquer Coisa");
        mapa.put(2222L, "Outra Coisa Qualquer");

        assertEquals("Outra Coisa Qualquer", mapa.get(2222L));
        assertEquals("Qualquer Coisa", mapa.get(1234L));
        assertNull(mapa.get(8798798L));
     }


    @Test
    public void testarTamanho()
    {
	rodarTesteTamanho(mapaUsandoArrayUnico);
        rodarTesteTamanho(mapaUsandoArrayOrdenado);
        rodarTesteTamanho(mapaUsandoDoisArraysParalelos);
        rodarTesteTamanho(hashMap);
    }


    private void rodarTesteTamanho(Map<Long, String> mapa)
    {
	mapa.clear();
	assertEquals(0, mapa.size());
	mapa.put(1234L, "1");
	mapa.put(5678L, "2");
	assertEquals(2, mapa.size());

	mapa.clear();
	for (long i=0; i<this.TAMANHO; i++)
	{
	    mapa.put(i, "quadrado : " + Long.toString(i*i));
	}
	assertEquals(this.TAMANHO, mapa.size() );
	
    }
   
    @Test
    public void testeAtualizacaoParaChaveExistente()
    {
        rodarTesteAtualizacaoParaChaveExistente(mapaUsandoArrayUnico);
        rodarTesteAtualizacaoParaChaveExistente(mapaUsandoArrayOrdenado);
        rodarTesteAtualizacaoParaChaveExistente(mapaUsandoDoisArraysParalelos);
        rodarTesteAtualizacaoParaChaveExistente(hashMap);
    }

    private void rodarTesteAtualizacaoParaChaveExistente(Map<Long, String> mapa) {
        mapa.put(1234L, "Qualquer Coisa");
        mapa.put(1234L, "Qualquer Coisa Modificada");

        assertEquals("Qualquer Coisa Modificada", mapa.get(1234L));
    }

    /**
    @Test
    public void testarPerformance() {
        rodarTesteDePerformance(mapaUsandoArrayUnico);
        rodarTesteDePerformance(mapaUsandoArrayOrdenado);
        rodarTesteDePerformance(mapaUsandoDoisArraysParalelos);
        rodarTesteDePerformance(hashMap);
    }
    **/
    
    private void rodarTesteDePerformance(Map<Long, String> mapa) {
        System.out.println("\nRodando teste de performance para a classe " +
                mapa.getClass().getName() + "...");

        final int TAMANHO = 40_000;

        System.out.println("Vou fazer as inserções...");

        long inicio = System.currentTimeMillis();
        for (long i = 0; i < TAMANHO; i++) {
            long x = random.nextInt(1_000_000);
            mapa.put(x, String.format("%d^2 = %d", x, x*x));
        }

	long duracao = System.currentTimeMillis() - inicio;
        System.out.printf("tamanho = %d --- duracao = %.3f segundos\n",
                TAMANHO, duracao / 1000f);

        System.out.println("Vou fazer as buscas...");
        inicio = System.currentTimeMillis();
        for (long i = 0; i < TAMANHO; i++) {
            mapa.get(random.nextLong());
        }
        duracao = System.currentTimeMillis() - inicio;
        System.out.printf("tamanho = %d --- duracao = %.3f segundos\n",
                TAMANHO, duracao / 1000f);
    }

    @Test
    public void testarExistenciaDePares()
    {
        rodarTesteDeExistenciaDePares(mapaUsandoArrayUnico);
        rodarTesteDeExistenciaDePares(mapaUsandoArrayOrdenado);
        rodarTesteDeExistenciaDePares(mapaUsandoDoisArraysParalelos);
        rodarTesteDeExistenciaDePares(hashMap);
    }

    private void rodarTesteDeExistenciaDePares(Map<Long, String> mapa)
    {
	// Existencia de chaves

	mapa.put(553311L, "valor referente a 553311");
	assertTrue(mapa.containsKey(553311L));
	assertEquals("valor referente a 553311", mapa.get(553311L) );

	// Existencia de elementos
       	mapa.put(553311L, "novo Valor referente a 553311");
	assertTrue(mapa.containsValue("novo Valor referente a 553311"));

    }

      
    @Test
    public void testarRemocaoDeElementos()
    {
	rodarTesteDeRemocao(mapaUsandoArrayUnico);
        rodarTesteDeRemocao(mapaUsandoArrayOrdenado);
	rodarTesteDeRemocao(mapaUsandoDoisArraysParalelos);
        rodarTesteDeRemocao(hashMap);
    }

    private void rodarTesteDeRemocao(Map<Long, String> mapa)
    {
	mapa.put(1234L, "Algo");
	mapa.put(321L, "Outra Coisa");
	mapa.put(1L, "1");
	assertEquals("Algo", mapa.remove(1234L));
	assertEquals("Outra Coisa", mapa.remove(321L));
	assertEquals(1, mapa.size());
	assertEquals("1", mapa.remove(1L));
	assertTrue(mapa.isEmpty());
	
    }




    
}
