import java.util.ArrayList;
import java.util.Random;

public class Pacotinho extends ArrayList<Figurinha> {

    Album album;

    // ToDo atributo que seja uma estrutura para guardar as figurinhas deste pacotinho

    public Pacotinho(Album album) {
        this.album = album;
        adicionarFigurinhasAleatorias();

    }

    // sobrecarga no costrutor, passando aqui as posições desejadas
    public Pacotinho(Album album, int[] posicoes) {
        this.album = album;

        // verificar se o tamanho do array está correto;
        // caso não esteja, throw new RuntimeException("Pacotinho no tamanho errado!");
	if (!(posicoes.length == album.getQuantFigurinhasPorPacotinho() ))
	    {
		throw new RuntimeException("Pacotinho no tamanho errado!");
	    }

	for (int posFig : posicoes)
	    {
		Figurinha fig = new Figurinha(posFig);
		this.add(fig);
	    }
	
    }

    private void adicionarFigurinhasAleatorias() {
        int maxPosicao = album.getTamanho();
        int quantFigurinhasPorPacotinho = album.getQuantFigurinhasPorPacotinho();

        for (int i = 1; i <= quantFigurinhasPorPacotinho; i++) {
	    Random random = new Random();
            int posicao = random.nextInt(maxPosicao+1);
            Figurinha figurinha = new Figurinha(posicao);
            add(figurinha);
        }
    }
}
