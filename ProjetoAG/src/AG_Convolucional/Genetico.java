package AG_Convolucional;

import java.util.Random;

public class Genetico {
	private Random r = new Random();
	private static final double taxaDeCrossover = 0.9;
	private static final double taxaDeMutacao = 0.05;
	private final boolean elitismo = true;
	private final int tamPopulacao = 100;
	private final static int numMaxGeracoes = 1000;
	private Populacao populacao = new Populacao(tamPopulacao);

	public Populacao gerarNovaGeracao(Populacao populacao, boolean elitismo) {

		Populacao novaPopulacao = new Populacao(populacao.getTamPopulacao());

		if (elitismo) {
			novaPopulacao.setIndividuo(populacao.getIndividuo(0));
		}

		// insere novos individuos na nova populacao, ate atingir o tamanho maximo
		while (novaPopulacao.getNumIndividuos() < novaPopulacao.getTamPopulacao()) {
			// seleciona os 2 pais por torneio
			Individuo[] pais = selecaoTorneioBinario(populacao);

			Individuo[] filhos = new Individuo[2];

			// verifica a taxa de crossover, se sim realiza o crossover, se
			// nao, mantem os pais selecionados para a proxima geracao
			if (r.nextDouble() <= taxaDeCrossover) {
				filhos = crossover(pais);
			} else {
				filhos[0] = new Individuo(pais[0].getGenes());
				filhos[1] = new Individuo(pais[1].getGenes());
			}

			// adiciona os filhos na nova geracao
			// Caso soh tenha mais uma vaga
			if (novaPopulacao.getTamPopulacao() - novaPopulacao.getNumIndividuos() >= 2) {
				novaPopulacao.setIndividuo(filhos[0]);
				novaPopulacao.setIndividuo(filhos[1]);
			} else if (filhos[0].getAptidao() > filhos[1].getAptidao()) {
				novaPopulacao.setIndividuo(filhos[0]);
			} else {
				novaPopulacao.setIndividuo(filhos[1]);
			}
		}
		novaPopulacao.ordenaPopulacao();
		return novaPopulacao;
	}

	public Individuo[] selecaoTorneioBinario(Populacao populacao) {
		Individuo[] pais = new Individuo[2];
		int a, b;
		for (int i = 0; i < 2; i++) {
			a = r.nextInt(populacao.getTamPopulacao());
			b = r.nextInt(populacao.getTamPopulacao());
			if (a < b)
				pais[i] = populacao.getIndividuo(a);
			else
				pais[i] = populacao.getIndividuo(b);
		}
		return pais;
	}

	public Individuo[] crossover(Individuo[] pais) {

		Individuo[] filhos = new Individuo[2];

		double[] geneFilho1 = pais[0].getGenes();
		double[] geneFilho2 = pais[1].getGenes();
		
		//recebendo os valores da somaConvolução
		int[] convolucao = somaConvolucao(geneFilho1, geneFilho2); // [x,x,x]
		double[] geneConvolucao = new double[2];
		geneConvolucao[0] = convolucao[0]; 
		geneConvolucao[1] = convolucao[1];
		

		// realiza o corte (troca)
		double aux = geneFilho1[0];
		geneFilho1[0] = geneFilho2[0];
		geneFilho2[0] = aux;

		//metade da população devera ser preenchida atraves da somaConvolução e a outra vai ser por roleta ou torneio	
		filhos[0] = new Individuo(geneConvolucao);
		filhos[1] = new Individuo(geneFilho2);
		
		//Se aptidao ficar negativa, volta ao original
		if(filhos[0].getAptidao() < 0 || filhos[1].getAptidao() < 0){
			System.out.println("aptidão ficou negativa " + filhos[0].toString() + " " + filhos[1].toString());
			
			
			filhos[0] = pais[0];
			filhos[1] = pais[1];
		}

		return filhos;
	}

	public static double getTaxaDeCrossover() {
		return taxaDeCrossover;
	}

	public static double getTaxaDeMutacao() {
		return taxaDeMutacao;
	}
	
	public static int[] somaConvolucao(double[] geneFilho1, double[] geneFilho2){
	        int[] soma = new int[geneFilho1.length + geneFilho2.length - 1];
	   
	        for(int i = 0; i < soma.length; i++){
	            for(int j = 0; j < geneFilho2.length; j++){
	                if((i-j) >= 0 && (i-j) < geneFilho1.length)
	                    soma[i] += geneFilho2[j] * geneFilho1[i-j]; 
	            }
	        }
	        return soma;
	  }
	 
	 

	public static void main(String[] args) {
		Genetico AG = new Genetico();
		AG.populacao.iniciarPopulacao();

		System.out.println("Populacao Inicial");
		for (int i = 0; i < AG.tamPopulacao; i++) {
			System.out.println(AG.populacao.getIndividuo(i) + " Aptidao " + AG.populacao.getIndividuo(i).getAptidao());
		}
		System.out.println("-------------------------------");
		
		int geracao = 0;
		int contEstagnar = 0;
		double anterior = -1;

		do {
			AG.populacao = AG.gerarNovaGeracao(AG.populacao, AG.elitismo);

			System.out.println("Geracao " + geracao + " |Aptidao: " + AG.populacao.getIndividuo(0).getAptidao()
					+ "| Melhor: " + AG.populacao.getIndividuo(0));

			//Verifica estagnacao
			if (geracao == 0 || AG.populacao.getIndividuo(0).getAptidao() != anterior) {
				anterior = AG.populacao.getIndividuo(0).getAptidao();
				contEstagnar = 1;
			} else {
				contEstagnar++;
			}

			if (contEstagnar > 100)
				break;

			if (geracao++ >= numMaxGeracoes)
				break;

		} while (AG.populacao.getIndividuo(0).getAptidao() > 0);
	}
}
