import java.util.Random;
public class Dado implements Sorteador
{

    private final int NUMERO_DE_LADOS = 6;

    /** Sorteia um número de 0 a 6
     **
     ** @param
     ** @return numero aleatorio de 0 a 6
    **/
    public int sortear()
    {
	Random geradorNumeros = new Random();
	return geradorNumeros.nextInt(this.NUMERO_DE_LADOS+1);
    }


}
