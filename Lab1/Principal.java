import java.util.Scanner;
public class Principal
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		long dreAluno;
		float notaAluno;	// Media do aluno em determinada disciplina

		float maiorNota = 0;
		long dreMaiorNota = 0;
		
		float sumNotasTurmas = 0;
		int qntNotasProcessadas = 0;
	
		System.out.println("Entre com um DRE e uma média, separados por espaço");
		String linha;
		boolean chave=true;
		while (chave)
		{
			linha = scanner.nextLine();
			if (linha.contains("-1")) 
			{
				chave = false;
				break;
			}
			String[] linhaArray = linha.split(" ");
			dreAluno = Long.valueOf(linhaArray[0]);		
			notaAluno = Float.valueOf(linhaArray[1]);
			
			sumNotasTurmas += notaAluno;
			
			if(maiorNota < notaAluno)
			{
				maiorNota = notaAluno;
				dreMaiorNota = dreAluno;
			}
			qntNotasProcessadas++;
		
		} 
		System.out.println(qntNotasProcessadas + " notas digitadas");
		System.out.println("Média da turma: " + sumNotasTurmas/qntNotasProcessadas );
		System.out.println("DRE do aluno com a maior média: " + dreMaiorNota);
		
		
	}




}
