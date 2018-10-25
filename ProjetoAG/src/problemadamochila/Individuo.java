package problemadamochila;

import java.util.Arrays;
import java.util.Random;

public class Individuo implements Comparable<Individuo>{
	private final Random random = new Random();
	private Double aptidao;
	
        //atributos do problema especifico
        private final static double[] PESO = {5, 4, 7, 8, 4, 4, 6, 8};
        private final static double[] VALOR = {3, 3, 2, 4, 2, 3, 5, 8};
        
        private int[] cromossomo;


	//cria um individuo aleatorio da primeira geracao
	public Individuo() {
                cromossomo  = new int[8];
		this.setCromossomo();
		avaliar();
	}

	// cria um individuo a partir de genes definidos (apos cruzamento)
	public Individuo(int[] genes) {
		cromossomo = genes;
		//testa se deve fazer mutacao
		if (random.nextDouble() <= Genetico.TAXADEMUTACAO) {
			int posAleatoria = random.nextInt(genes.length); //define gene que sera mutado
			mutacao(posAleatoria);
		}
		avaliar();
	}

	boolean validar() {
		return getPesoTotal() <= 25;
	}

        private double getPesoTotal() {
		double pesoTotal = 0;
                for(int i = 0; i < cromossomo.length; i++){
                    if(cromossomo[i] == 1)
                        pesoTotal += PESO[i];
                }
		return pesoTotal;
	}
        
	private void mutacao(int posicao) {
		if(cromossomo[posicao] == 1)
                            cromossomo[posicao] = 0;
                else
                            cromossomo[posicao] = 1;
	}
        
	private void setCromossomo() {
		for(int i = 0; i < cromossomo.length; i++){
                    if(random.nextBoolean())
                        cromossomo[i] = 1;
                    else
                        cromossomo[i] = 0;
                }
	}
        
	public double getAptidao() {
		return aptidao;
	}

	public int[] getGenes() {
		return cromossomo;
	}

	private void avaliar() {
		aptidao = 0.0;
                for(int i = 0; i < cromossomo.length; i++){
                    if(cromossomo[i] == 1){
                        aptidao += VALOR[i];
                    }
                }
	}

	@Override
	public String toString() {
		return "Cromossomo " + Arrays.toString(getGenes()) + " Aptidao: " + aptidao + " Peso: " + getPesoTotal() + "\n";
	}

	@Override
	public int compareTo(Individuo i) {
		return this.aptidao.compareTo(i.aptidao);
	}
}
