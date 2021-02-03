import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapaUsandoArrayUnico<C, V> implements Map<C, V> {

    private ArrayList<ParChaveValor<C, V>> minhaListaDePares;

    public MapaUsandoArrayUnico() {
        this.minhaListaDePares = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.minhaListaDePares.size();
    }

    @Override
    public boolean isEmpty() {
        return this.minhaListaDePares.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
	for (ParChaveValor<C, V> par : this.minhaListaDePares)
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
	for (ParChaveValor<C, V> par : this.minhaListaDePares)
	{
	    if (par.getValor() == value)
	     {
		    return true;
	     }
	}
	return false;
    }

    @Override
    public V put(C chave, V valor)
    {
        ParChaveValor<C, V> parPreExistente = obterParChaveValor(chave);

	ParChaveValor<C, V> novoElemento = new ParChaveValor<>(chave, valor);
	
        if (parPreExistente == null)
	{  // chave inédita, cria um novo elemento!!
            this.minhaListaDePares.add(novoElemento);
	    return novoElemento.getValor();
        }
	else
	{  // chave pré-existente, apenas seta o existente
            parPreExistente.setValor(valor);
	    return parPreExistente.getValor();
        }
   }

    @Override
    public V remove(Object key)
    {
	if (this.containsKey(key))
	    {
		ParChaveValor<C, V> par = obterParChaveValor(key);
		this.minhaListaDePares.remove(par);
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
	this.minhaListaDePares.clear();
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
        for (ParChaveValor<C, V> par : this.minhaListaDePares) {
            if (par.getChave().equals(chave)) {
                return par;
            }
        }
        return null;
    }
}
