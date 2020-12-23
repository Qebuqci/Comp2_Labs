import java.util.Date;

public class Pessoa {

    private String nome;

    private final long cpf;  // final indica que o campo JAMAIS poderá ser atualizado

    private Date dataDeNascimento;

    private String endereco;

    public Pessoa(String nomeDaPessoa, long cpfDaPessoa) {
        nome = nomeDaPessoa;
        cpf = cpfDaPessoa;
        endereco = "Endereço desconhecido";
    }

    public void setEndereco(String endereco) {
        if (endereco.length() > 40) {  // tamanho máximo permitido para endereços
            return;  // ToDo lançar exceção!
        }
        this.endereco = endereco;
    }

    public long getCpf() {
        return cpf;
    }

    // Duas pessoas são iguais se seus cpfs são iguais
    public boolean equals(Pessoa outraPessoa)
    {
	return this.getCpf() == outraPessoa.getCpf();
    }
}
