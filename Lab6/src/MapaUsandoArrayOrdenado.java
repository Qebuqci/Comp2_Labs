import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapaUsandoArrayOrdenado<C extends Comparable<C>, V> implements Map<C, V> {

    private ArrayList<ParChaveValor<C, V>> minhaListaOrdenadaDePares;

    public MapaUsandoArrayOrdenado() {
        this.minhaListaOrdenadaDePares = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.minhaListaOrdenadaDePares.size();
    }

    @Override
    public boolean isEmpty() {
        return this.minhaListaOrdenadaDePares.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
	for (ParChaveValor<C, V> par : this.minhaListaOrdenadaDePares)
	{
	    if (par.getChave().equals(key))
	    {
		    return true;
	    }
	}
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
	for (ParChaveValor<C, V> par : this.minhaListaOrdenadaDePares)
	{
	    if (par.getValor() == value)
	     {
		    return true;
	     }
	}
	return false;
    }

    @Override
    public V put(C chave, V valor) {

        int posicaoParaInsercao = -1;

        for (int i = 0; i < this.minhaListaOrdenadaDePares.size(); i++) {
            ParChaveValor<C, V> par = this.minhaListaOrdenadaDePares.get(i);

	    if (par.getChave().equals(chave)) {
                par.setValor(valor);
                return valor;  // chave já existia; nada mais a ser feito!
                
            }

            if (par.getChave().compareTo(chave) > 0) {
                // a chave da posição que estou olhando é maior do que a chave que quero put

                posicaoParaInsercao = i;
                break;  // saio do for, pois já encontrei a posição para inserir
            }
        }

        if (posicaoParaInsercao == -1) {
            // minha chave é maior do que todas que existiam na lista,
            // então quero adicioná-la de fato no final da lista ordenada
            this.minhaListaOrdenadaDePares.add(new ParChaveValor<>(chave, valor));

        }
	else
	{
            // preciso inserir "no meio" da lista ordenada, então
            // antes vou mover os pares pré-existentes uma casa para a direita
            final ParChaveValor<C, V> ultimoParDaLista = this.minhaListaOrdenadaDePares.get(this.size() - 1);

            this.minhaListaOrdenadaDePares.add(ultimoParDaLista);
            // isso abrirá uma nova posição no fim da lista,
            // repetindo a referência àquele mesmo último objeto ParChaveValor

            for (int i = this.size() - 3; // a penúltimo posição da lista ANTES do add
                 i >= posicaoParaInsercao;
                 i--)
	    {
                // shift right
                this.minhaListaOrdenadaDePares.set(i + 1, this.minhaListaOrdenadaDePares.get(i));
            }

            // agora sim posso setar o meu elemento na posição desejada
            this.minhaListaOrdenadaDePares.set(posicaoParaInsercao, new ParChaveValor<>(chave, valor));
        }

        return valor;
    }

    @Override
    public V remove(Object key)
    {
	if (this.containsKey(key))
	    {
		ParChaveValor<C, V> par = obterParChaveValor(key);
		this.minhaListaOrdenadaDePares.remove(par);
		return par.getValor();
	    }
	return null;
    }

    @Override
    public void putAll(Map<? extends C, ? extends V> m) {
        throw new RuntimeException("Operação não suportada!");
    }

    @Override
    public void clear() {
	this.minhaListaOrdenadaDePares.clear();
    }

    @Override
    public Set<C> keySet() {
        throw new RuntimeException("Operação não suportada!");
    }

    @Override
    public Collection<V> values() {
        throw new RuntimeException("Operação não suportada!");
    }

    @Override
    public Set<Entry<C, V>> entrySet() {
        throw new RuntimeException("Operação não suportada!");
    }

    @Override
    public V get(Object chaveDeBusca) {
        ParChaveValor<C, V> par = obterParChaveValor(chaveDeBusca);
        return par == null
                ? null
                : par.getValor();
    }

    private ParChaveValor<C, V> obterParChaveValor(Object chave) {
        for (ParChaveValor<C, V> par : this.minhaListaOrdenadaDePares) {
            if (par.getChave().equals(chave)) {
                return par;
            } else if (par.getChave().compareTo((C) chave) > 0) {
                break;
            }
        }
        return null;
    }


}
