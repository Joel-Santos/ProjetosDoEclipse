package algoritmogenetico;

import java.util.Arrays;
import java.util.Random;

public class Individuo implements Comparable<Individuo>{
	private final Random random = new Random();
	private Double aptidao;
	
	//atributos do problema especifico
	private double qtdMilho;
	private double qtdSoja;

	//cria um individuo aleatorio da primeira geracao
	public Individuo() {
		do {
			this.setQtdMilho();
			this.setQtdSoja();
		} while (!validar());
		avaliar();
	}

	// cria um individuo a partir de genes definidos
	public Individuo(double[] genes) {
		qtdMilho = genes[0];
		qtdSoja = genes[1];
		//testa se deve fazer mutacao
		if (random.nextDouble() <= Genetico.TAXADEMUTACAO) {
			int posAleatoria = random.nextInt(genes.length); //define gene que sera mutado
			mutacao(posAleatoria);
		}
		avaliar();
	}

	private boolean validar() {
		double proteina = 0.0851 * qtdMilho + 0.456 * qtdSoja;
		double energia = 3146 * qtdMilho + 2283 * qtdSoja;
		return proteina >= 0.1716 && energia >= 3000;
	}

	private void mutacao(int posicao) {
		do {
			if (posicao == 0)
				this.setQtdMilho();
			else if (posicao == 1)
				this.setQtdSoja();
		} while (!validar());

	}

	private void setQtdMilho() {
		this.qtdMilho = random.nextDouble();
	}

	private void setQtdSoja() {
		this.qtdSoja = random.nextDouble();
	}

	public double getAptidao() {
		return aptidao;
	}

	public double[] getGenes() {
		return new double[] { qtdMilho, qtdSoja };
	}

	private void avaliar() {
		aptidao = 0.8 * qtdMilho + 3.8 * qtdSoja;
	}

	@Override
	public String toString() {
		return "Cromossomo " + Arrays.toString(getGenes()) + " Aptidao: " + aptidao + "\n";
	}

	@Override
	public int compareTo(Individuo i) {
		return this.aptidao.compareTo(i.aptidao);
	}
}
