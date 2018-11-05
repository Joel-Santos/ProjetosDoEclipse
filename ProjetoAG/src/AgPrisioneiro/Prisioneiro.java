package AgPrisioneiro;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problemadamochila.Individuo;

/**
 * Classe de representa��o do indiv�duo prisioneiro.
 * @author Patrizia Chermont
 *
 */
public class Prisioneiro {
	
	/**
	 * Genes do indiv�duo na forma booleana. Cooperou = true, Delatou = false.
	 */
	private int genes[];
	
	/**
	 * Gerador de N�meros Aleat�rios.
	 */
	private Random rng;
	
	/**
	 * �ltimo fitness calculado do indiv�duo.
	 */
	private double fitness;
	
	/**
	 * N�mero aleat�rio para ordena��o embaralhada.
	 */
	private int iRandom;
	
	/**
	 * M�todo construtor da classe.
	 * Inicializa um novo prisioneiro com uma cadeia aleat�ria de genes. 
	 */
	public Prisioneiro(){
		genes = new int[GAConfig.nGenes];
		rng = new Random();
		
		for (int i = 0; i < GAConfig.nGenes; i++){
			if(rng.nextBoolean())
				genes[i] = 1; 
			else
				genes[i]=0;
		}
		
		iRandom = rng.nextInt();
		setFitness(0);
	}
	
	/**
	 * M�todo construtor da classe.
	 * Inicializa um novo prisioneiro com uma cadeia definida de genes.
	 * @param genes Cadeia booleana de coopera��es (true) e dela��es (false).
	 */
	public Prisioneiro (int[] genes){
		this.genes = genes;
		rng = new Random();
		iRandom = rng.nextInt();
		setFitness(0);
	}
	
	/**
	 * Tempo de cadeia que o prisioneiro � sentenciado quando confrontado com
	 * outro prisioneiro.
	 * @param outro Comparsa a ser interrogado junto com o Prisioneiro.
	 * @return O tempo de cadeia sentenciado.
	 */
	public double tempo(Prisioneiro outro){
		
		double tempo = 0;
		
		for (int i = 0; i < GAConfig.nGenes; i++){
			if (this.cooperou(i) == 0 && outro.cooperou(i) == 0){
				tempo += GAConfig.tCalados;
			} else if (this.cooperou(i) ==1 && outro.cooperou(i)==0){
				tempo += GAConfig.tDelatado;
			} else if (this.cooperou(i)==0 && outro.cooperou(i) == 1){
				tempo += GAConfig.tDelator;
			} else {
				tempo += GAConfig.tReciproco;
			}
		}

		return tempo;
	}
	
	/**
	 * Fun��o de aptid�o do indiv�duo. Neste algoritmo, o fitness � calculado
	 * tomando por base o tempo de cadeia e o n�mero de coopera��es seguidas.
	 * Para efeitos de ordena��o, tomou-se como f�rmula a express�o:
	 * fitness = 1/tempo + deltas, na qual deltas � a premia��o por coopera��es
	 * consecutivas.
	 * @param outro Comparsa interrogado
	 * @return Aptid�o do indiv�duo em ter menos tempo de cadeia.
	 */
	public double fitness(Prisioneiro outro){
		
		double deltas	= 0;
		int seguidos	= 0;
		double tempo = tempo(outro);
		
		for (int i = 0; i < GAConfig.nGenes; i++){
			if (cooperou(i)==1) {
				seguidos++;
				if (seguidos == GAConfig.nSeguidos){
					seguidos = 0;
					deltas = deltas + GAConfig.delta;
				}
			} else {
				seguidos = 0;
			}
		}
		
		// Pequeno hack para evitar divis�o por zero.
		// Improv�vel, mas n�o imposs�vel.
		if (tempo == 0) tempo = 0.000001;
		
		setFitness(1/tempo + deltas);		
		return getFitness();		
	}
	
	/**
	 * Indica se o indiv�duo cooperou o delatou com a informa��o.
	 * @param i Posi��o da cadeia de genes.
	 * @return true, se o indiv�duo cooperou, ou false, caso contr�rio.
	 */
	public int cooperou(int i){
		return gene(i);
	}
	
	/**
	 * Gene do indiv�duo.
	 * @param i Posi��o da cadeia de genes.
	 * @return Valor do gene (true ou false).
	 */
	public int gene(int i){
		return genes[i];
	}
	
	/**
	 * Gera filhos com um parceiro. Ser� utilizado apenas um ponto de
	 * cruzamento na metade da cadeia de genes. O m�todo gera 4 novos filhos,
	 * sendo duas duplas de g�meos.
	 * @param parceiro Parceiro utilizado no cruzamento de genes.
	 * @return Lista de 4 novos filhos, sendo duas duplas de g�meos.
	 */
	public List<Prisioneiro> gerar(Prisioneiro pai1,Prisioneiro pai2){
		
		int[] genes1 = pai1.genes;
		int[] genes2 = pai2.genes;
		
		
		
		//Soma convolu��o
		int[] convolucao = somaConvolucao(genes1, genes2);
		
		
		/*boolean[] genes1 = new boolean[GAConfig.nGenes];
		boolean[] genes2 = new boolean[GAConfig.nGenes];
		boolean[] genes3 = new boolean[GAConfig.nGenes];
		boolean[] genes4 = new boolean[GAConfig.nGenes];
		
		int metade = GAConfig.nGenes / 2;*/
		
		List<Prisioneiro> filhos = new ArrayList<Prisioneiro>(2);

		
		for (int i = 0; i < GAConfig.nGenes; i++){			
			
			// Crossover entre os gen�tipos
			
			genes1[i] = convolucao[i];
			/*genes1[i] = (i < metade)? this.gene(i) : parceiro.gene(i);
			genes2[i] = (i < metade)? parceiro.gene(i) : this.gene(i);
			genes3[i] = (i < metade)? this.gene(i) : parceiro.gene(i);
			genes4[i] = (i < metade)? parceiro.gene(i) : this.gene(i);*/	
			
			//Aplicacao de muta��o no gene
			if (rng.nextDouble() < GAConfig.pMutacao){
				if(genes[1]==1)
					genes1[i]=0;
				else
					genes[i]=1;
			}
			/*if (rng.nextDouble() < GAConfig.pMutacao) genes2[i] = !genes2[i];
			if (rng.nextDouble() < GAConfig.pMutacao) genes3[i] = !genes3[i];
			if (rng.nextDouble() < GAConfig.pMutacao) genes4[i] = !genes4[i];*/						
		}
		
		filhos.add(new Prisioneiro(genes1));
		filhos.add(new Prisioneiro(genes1));
		//filhos.add(new Prisioneiro(genes2));
		//filhos.add(new Prisioneiro(genes4));
		
		return filhos;
	}
	

	//SOMA CONVOLU��O COM UMA TENTATIVA DE RAMPA
		public static int[] somaConvolucao(int[] geneFilho1, int[] geneFilho2){
	        int[] soma = new int[geneFilho1.length + geneFilho2.length - 1];
	   
	        for(int i = 0; i < soma.length; i++){
	            for(int j = 0; j < geneFilho2.length; j++){
	                if((i-j) >= 0 && (i-j) < geneFilho1.length){
	                   soma[i] += (int) geneFilho2[j] * geneFilho1[i-j];
	                    //System.out.println(soma[i]);
	                    if(soma[i]>7){
	                    	soma[i]=1;
	                    }else{
	                    	soma[i]=0;
	                    }
	                }
	            }
	        }
	        return soma;
	  }	
	
	
	
	/**
	 * Realiza uma disputa entre dois indiv�duos. 
	 * @param outro Comparsa com qual ser� realizada a disputa.
	 * @return O indiv�duo vencedor (o comparsa ou o pr�rpio indiv�duo).
	 */
	public Prisioneiro disputar(Prisioneiro outro1, Prisioneiro outro2){
		boolean venci = (this.fitness(outro1) > this.fitness(outro2)); 
//		System.out.println(
//				this + " X " + outro +
//				" <=> " +
//				this.tempo(outro) + " X " + outro.tempo(this) +
//				"(" + this.getFitness() + " <=> " + outro.getFitness() + ")");
		return venci? this : outro1;
	}
	
	/**
	 * Sobrescrita do m�todo toString para realizar relat�rio.
	 * @return A cadeia de genes do indiv�duo.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		double c = 0;
		for (int i = 0; i < GAConfig.nGenes; i++){
			if(gene(i)==1){
				sb.append("C");
				c++;
			} else {
				sb.append("D");
			}
		}
		sb.append(": C="+c/GAConfig.nGenes);
		sb.append(", F="+getFitness());
		return sb.toString();
	}

	/**
	 * M�todo get do n�mero aleat�rio para ordena��o embaralhada.
	 * @return o n�mero aleat�rio do Indiv�duo.
	 */
	public int getRandom() {
		return iRandom;
	}

	/**
	 * M�todo set do Fitness do indiv�duo.
	 * @param fitness Aptid�o do indiv�duo.
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;	
	}
	
	/**
	 * M�todo GET do fitness do indiv�duo.
	 * @return Aptd�o do indiv�duo
	 */
	public double getFitness(){
		return this.fitness;
	}
	
}