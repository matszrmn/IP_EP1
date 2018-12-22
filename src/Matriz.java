import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Locale;

public class Matriz {
	
	protected double M[][];
	
	/** Este método lê a matriz armazenada no arquivo (espera-se que
	  * ela seja única). Esta tem que
	  * começar com dois inteiros que informam o número de linhas e
	  * o número de colunas da matriz. Os números que se seguem são
	  * são os elementos da matriz, do tipo double, cujo separador de
	  * casa decimal é o ponto.
	  * @param nomeArquivo nome do arquivo que contém a matriz (única).
	  */
	
	void le(String nomeArquivo) {
		
		int[] dim = new int[2];
		File arquivo = new File(nomeArquivo);
		
		try {
			Scanner sc = new Scanner(arquivo);
			sc.useLocale (new Locale ("US"));
			int i = 0;
			
			while (sc.hasNextInt() && (i < 2)) {
				dim[i++] = sc.nextInt();
			}
			
			int contador = 0;
			M = new double[dim[0]][dim[1]];
			
			while (sc.hasNextDouble() && (contador < (dim[0]*dim[1]))) {
				M[contador/dim[1]][contador%dim[1]] = sc.nextDouble();
				contador++;
			}
			sc.close();
		}
 		catch (FileNotFoundException e) { e.printStackTrace(); }
	}
	/** Sua solucao deve ser escrita dentro deste método. */
	
	public double[] resolve () {
		
		double [] x;	// Inicializa, para este dummy rodar
		x = new double[M.length];
		
		for (int k = 0; k < M.length; k++) {			// "k" percorre todas as linhas da matriz
			for (int i = k+1; i < M.length; i++) {	/* "i" percorre todas as linhas da matriz exceto a primeira linha,com a finalidade
													 de comparar os elementos da linha "i"(que vale k+1)com os elementos da linha "k".*/
				double pi = M[i][k]/M[k][k];		/* "pi"será um valor calculado a cada passagem de "i" e "k" para zerar
													 elementos quando k=j,posteriormente através da equação em while */
				int j = 0;							// "j" percorre COlunas da matriz (da esquerda para a direita)
				while (j < M[0].length) {
					M[i][j] = M[i][j]-(pi*M[k][j]);	/* Equação que zera os termos quando k=j e modifica os outros
													 termos de cada linha */
					j++;
				}
			}
		}
		/* Os três "fors" seguintes escalonam a parte de cima da matriz,para zerar todos os
		 valores exceto os valores da diagonal e os valores da ultima coluna (coluna b) */
		for (int k2 = M.length-1; k2 >= 0; k2--) {	// Percorre todas as linhas de baixo para cima
			for (int i2 = k2-1; i2 >= 0; i2--) {		/* Percorre todas as linhas de baixo para cima exceto a ultima linha e compara
													 elementos da linha "i2"(que vale k2-1) com os elementos da linha "k2"*/
				double pi2 = M[i2][k2]/M[k2][k2];	// Possui a mesma função anterior de "pi"(zerar e acrescentar valores aos elementos)
				
				for (int j2 = 0; j2 < M[0].length; j2++) {	// Percorre as colunas da matriz (da esquerda para a direita)
					M[i2][j2] = M[i2][j2] - (pi2 * M[k2][j2]);
				}
			}
		}
		/* As operaçoes seguintes vao encontrar a resolucao de x para cada linha atraves da
		 divisao do elemento da ultima coluna(b) pelo elemento da diagonal da matriz (da mesma linha).*/
		for (int f = 0; f < M.length; f++) {			// Referencia para linhas da matriz
			for (int g = 0; g < M[0].length; g++) {	// Referencia para colunas da matriz
				if (f == g) {	// Quando f é g,o elemento pertence à diagonal da matriz
					x[f] = M[f][M[0].length-1]/M[f][g];		/* Definimos o x de uma linha como sendo a divisao entre o elemento da ultima coluna(b)
															 pelo elemento da diagonal(ambos da mesma linha)*/
				}
			}
		}
		return x;
	}
	
	/** Este método imprime a matriz armazenada em M no mesmo formato como é lida do arquivo. */
	public void imprime () {
		if ((M!=null) && (M.length > 0) && (M[0].length > 0)) {
			System.out.println (M.length + " " + M[0].length);
			for (int i = 0; i < M.length; i++) {
				for (int j = 0; j < M[i].length; j++) {
					System.out.print (M[i][j] + " ");
				}
				System.out.println ();
			}
		}
		else {
			System.out.println ("Matriz inexistente ou de tamanho zero.");
		}
	}
	public static void main (String[] args) {
		Matriz M = new Matriz ();
		double[] r;
		
		M.le ("teste7.m");
		M.imprime ();
		System.out.println ("Solucao para teste7.m");
		r = M.resolve ();
		
		if (r != null) {
			for (int i = 0; i < r.length; i++) {
				System.out.println (r[i]);
			}
		}
	}
}
