package AG_Convolucional;

import java.util.Arrays;
import java.util.Random;

public class Individuo {
	private Random random = new Random();
	//TODO Colocar esses valores como parametros de entrada
	private double massaForragemInicial = 3560;
	private double taxaMediaDeAcumulo = 62;
	private double pesoMedioInicial = 25;
	private double ganhoPesoDiario = 0.0355;

	private int qtdDias;
	private int numeroAnimais;
	private double aptidao;

	//So gera individuos com saldo positivo de forragem
	public Individuo() {
		do {
			this.setNumeroAnimais();
			this.setQtdDias();
			avaliar();
		} while (aptidao < 0);

	}

	// cria um individuo com os genes definidos
	public Individuo(double[] genes) {
		this.setNumeroAnimais(genes[0]);
		this.setQtdDias((int) genes[1]);

		// se for mutar, cria um gene aleatorio
		if (random.nextDouble() <= Genetico.getTaxaDeMutacao()) {
			int posAleatoria = random.nextInt(genes.length);
			int cont = 0;
			do {
				if (posAleatoria == 0) {
					this.setNumeroAnimais();
				} else if (posAleatoria == 1) {
					this.setQtdDias();
				}
				avaliar();
				if (aptidao >= 0)
					break;
				//se realizar a mutacao 10 vezes e nao conseguir uma solucao valida, volta ao original
				else if (cont++ > 10) {
					if (posAleatoria == 0) {
						this.setNumeroAnimais(genes[0]);
					} else if (posAleatoria == 1) {
						this.setQtdDias((int) genes[1]);
					}
					break;
				}
			} while (true);
		}
		avaliar();
	}

	public void setNumeroAnimais(double numeroAnimais) {
		this.numeroAnimais = (int) numeroAnimais;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}
	
	//TODO colocar os limites como parametro de entrada

	// [1 100]
	public void setNumeroAnimais() {
		this.numeroAnimais = 1 + random.nextInt(200);
	}

	// [1 120]
	private void setQtdDias() {
		this.qtdDias = 1 + random.nextInt(300);

	}

	public double getAptidao() {
		return aptidao;
	}

	public double[] getGenes() {
		return new double[] { numeroAnimais, qtdDias };
	}

	/**
	 * Executa o planejamento forrageiro para a quantidade de dias definido
	 * Calcula o consumo diario para cada animal e para todos os animais Calcula
	 * o saldo da forragem ao final do dia Calcula o peso do animal ao final do
	 * dia
	 */
	public void avaliar() {
		double massaForragem = massaForragemInicial;
		double pesoMedio = pesoMedioInicial;
		double consumoVoluntario = 0;

		for (int i = 1; i <= qtdDias; i++) {
			consumoVoluntario = (0.311 + (0.0197 * pesoMedio + (0.682 * ganhoPesoDiario)));
			consumoVoluntario *= numeroAnimais;
			massaForragem += taxaMediaDeAcumulo - consumoVoluntario; // saldo
			pesoMedio += ganhoPesoDiario;
		}
		this.aptidao = massaForragem;
	}

	@Override
	public String toString() {
		return "Cromossomo " + Arrays.toString(getGenes()) + "\n";
	}

}
