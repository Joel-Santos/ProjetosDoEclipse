package prisioneiroV2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Classe de representaÃ§ao do individuo prisioneiro.
 *
 * @author Patrizia Chermont
 *
 */
public class Prisioneiro {

    /**
     * Genes do individuo na forma booleana. Cooperou = true, Delatou = false.
     */
    private int genes[];

    /**
     * Gerador de NÃƒÂºmeros AleatÃƒÂ³rios.
     */
    private Random rng;

    /**
     * ÃƒÅ¡ltimo fitness calculado do indivÃƒÂ­duo.
     */
    private double fitness;

    /**
     * NÃƒÂºmero aleatÃƒÂ³rio para ordenaÃƒÂ§ÃƒÂ£o embaralhada.
     */
    private int iRandom;

    /**
     * MÃƒÂ©todo construtor da classe. Inicializa um novo prisioneiro com uma
     * cadeia aleatÃƒÂ³ria de genes.
     */
    public Prisioneiro() {
        genes = new int[GAConfig.nGenes];
        rng = new Random();

        for (int i = 0; i < GAConfig.nGenes; i++) {
            if (rng.nextBoolean()) {
                genes[i] = 1;
            } else {
                genes[i] = 0;
            }
        }

        iRandom = rng.nextInt();
        setFitness(0);
    }

    /**
     * MÃƒÂ©todo construtor da classe. Inicializa um novo prisioneiro com uma
     * cadeia definida de genes.
     *
     * @param genes Cadeia booleana de cooperaÃƒÂ§ÃƒÂµes (true) e delaÃƒÂ§ÃƒÂµes (false).
     */
    public Prisioneiro(int[] genes) {
        this.genes = genes;
        rng = new Random();
        iRandom = rng.nextInt();
        setFitness(0);
    }

    /**
     * Tempo de cadeia que o prisioneiro ÃƒÂ© sentenciado quando confrontado com
     * outro prisioneiro.
     *
     * @param outro Comparsa a ser interrogado junto com o Prisioneiro.
     * @return O tempo de cadeia sentenciado.
     */
    public double tempo(Prisioneiro outro) {

        double tempo = 0;

        for (int i = 0; i < GAConfig.nGenes; i++) {
            if (this.cooperou(i) && outro.cooperou(i)) {
                tempo += GAConfig.tCalados;
            } else if (this.cooperou(i) && !outro.cooperou(i)) {
                tempo += GAConfig.tDelatado;
            } else if (!this.cooperou(i) && outro.cooperou(i)) {
                tempo += GAConfig.tDelator;
            } else {
                tempo += GAConfig.tReciproco;
            }
        }

        return tempo;
    }

    /**
     * FunÃƒÂ§ÃƒÂ£o de aptidÃƒÂ£o do indivÃƒÂ­duo. Neste algoritmo, o fitness ÃƒÂ© calculado
     * tomando por base o tempo de cadeia e o nÃƒÂºmero de cooperaÃƒÂ§ÃƒÂµes seguidas.
     * Para efeitos de ordenaÃƒÂ§ÃƒÂ£o, tomou-se como fÃƒÂ³rmula a expressÃƒÂ£o: fitness =
     * 1/tempo + deltas, na qual deltas ÃƒÂ© a premiaÃƒÂ§ÃƒÂ£o por cooperaÃƒÂ§ÃƒÂµes
     * consecutivas.
     *
     * @param outro Comparsa interrogado
     * @return AptidÃƒÂ£o do indivÃƒÂ­duo em ter menos tempo de cadeia.
     */
    public double fitness(Prisioneiro outro) {

        double deltas = 0;
        int seguidos = 0;
        double tempo = tempo(outro);

        for (int i = 0; i < GAConfig.nGenes; i++) {
            if (cooperou(i)) {
                seguidos++;
                if (seguidos == GAConfig.nSeguidos) {
                    seguidos = 0;
                    deltas = deltas + GAConfig.delta;
                }
            } else {
                seguidos = 0;
            }
        }

        // Pequeno hack para evitar divisÃƒÂ£o por zero.
        // ImprovÃƒÂ¡vel, mas nÃƒÂ£o impossÃƒÂ­vel.
        if (tempo == 0) {
            tempo = 0.000001;
        }

        setFitness(1 / tempo + deltas);
        return getFitness();
    }

    /**
     * Indica se o indivÃƒÂ­duo cooperou o delatou com a informaÃƒÂ§ÃƒÂ£o.
     *
     * @param i PosiÃƒÂ§ÃƒÂ£o da cadeia de genes.
     * @return true, se o indivÃƒÂ­duo cooperou, ou false, caso contrÃƒÂ¡rio.
     */
    public boolean cooperou(int i) {
        return gene(i) == 1;
    }

    /**
     * Gene do indivÃƒÂ­duo.
     *
     * @param i PosiÃƒÂ§ÃƒÂ£o da cadeia de genes.
     * @return Valor do gene (true ou false).
     */
    public int gene(int i) {
        return genes[i];
    }

    /**
     * Gera filhos com um parceiro. SerÃƒÂ¡ utilizado apenas um ponto de cruzamento
     * na metade da cadeia de genes. O mÃƒÂ©todo gera 4 novos filhos, sendo duas
     * duplas de gÃƒÂªmeos.
     *
     * @param parceiro Parceiro utilizado no cruzamento de genes.
     * @return Lista de 4 novos filhos, sendo duas duplas de gÃƒÂªmeos.
     */
    public List<Prisioneiro> gerar(Prisioneiro parceiro) {

        int[] genes1 = new int[GAConfig.nGenes];
        int[] genes2 = new int[GAConfig.nGenes];
        int[] genes3 = new int[GAConfig.nGenes];
        int[] genes4 = new int[GAConfig.nGenes];

        int metade = GAConfig.nGenes / 2;

        List<Prisioneiro> filhos = new ArrayList<>(2);

        for (int i = 0; i < GAConfig.nGenes; i++) {
            // Crossover entre os genÃƒÂ³tipos
            genes1[i] = (i < metade) ? this.gene(i) : parceiro.gene(i);
            genes2[i] = (i < metade) ? parceiro.gene(i) : this.gene(i);
            genes3[i] = (i < metade) ? this.gene(i) : parceiro.gene(i);
            genes4[i] = (i < metade) ? parceiro.gene(i) : this.gene(i);

            //Aplicacao de mutaÃƒÂ§ÃƒÂ£o no gene
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes1[i] = (genes1[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes2[i] = (genes2[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes3[i] = (genes3[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes4[i] = (genes4[i] == 1 ? 0 : 1);
            }
        }

        filhos.add(new Prisioneiro(genes1));
        filhos.add(new Prisioneiro(genes3));
        filhos.add(new Prisioneiro(genes2));
        filhos.add(new Prisioneiro(genes4));

        return filhos;
    }

    /**
     * Gera filhos com um parceiro utilizando a soma convolucional
     *
     * @param parceiro Parceiro utilizado no cruzamento de genes.
     * @return Lista de 4 novos filhos, sendo duas duplas de gÃƒÂªmeos.
     */
    public List<Prisioneiro> gerarConvolucional(Prisioneiro parceiro) {

        // Crossover entre os genotipos
        int[] genes = normalizarSaida(somaConvolucao(this.genes, parceiro.genes));
              
        int[] filho1 = new int[GAConfig.nGenes];
        int k=15;
        for(int i=0; i<GAConfig.nGenes; i++, k++)
        	filho1[i] = genes[k];	 //Meio do cromossomo	
        
        int[] genes1 = filho1;
        int[] genes2 = filho1;//normalizarSaida(somaConvolucao(this.genes, parceiro.genes));
        int[] genes3 = filho1;//normalizarSaida(somaConvolucao(this.genes, parceiro.genes));
        int[] genes4 = filho1;//normalizarSaida(somaConvolucao(this.genes, parceiro.genes));

        List<Prisioneiro> filhos = new ArrayList<>(2);

        for (int i = 0; i < genes1.length ;i++) {

            //Aplicacao de mutacao no gene
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes1[i] = (genes1[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes2[i] = (genes2[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes3[i] = (genes3[i] == 1 ? 0 : 1);
            }
            if (rng.nextDouble() < GAConfig.pMutacao) {
                genes4[i] = (genes4[i] == 1 ? 0 : 1);
            }
        }

        filhos.add(new Prisioneiro(genes1));
        filhos.add(new Prisioneiro(genes3));
        filhos.add(new Prisioneiro(genes2));
        filhos.add(new Prisioneiro(genes4));

        return filhos;
    }

    /**
     * Realiza uma disputa entre dois indivÃƒÂ­duos.
     *
     * @param outro Comparsa com qual serÃƒÂ¡ realizada a disputa.
     * @return O indivÃƒÂ­duo vencedor (o comparsa ou o prÃƒÂ³rpio indivÃƒÂ­duo).
     */
    public Prisioneiro disputar(Prisioneiro outro) {
        boolean venci = (this.fitness(outro) > outro.fitness(this));
//		System.out.println(
//				this + " X " + outro +
//				" <=> " +
//				this.tempo(outro) + " X " + outro.tempo(this) +
//				"(" + this.getFitness() + " <=> " + outro.getFitness() + ")");
        return venci ? this : outro;
    }

    /**
     * Sobrescrita do mÃƒÂ©todo toString para realizar relatÃƒÂ³rio.
     *
     * @return A cadeia de genes do individuo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double c = 0;
        for (int i = 0; i < GAConfig.nGenes; i++) {
            if (gene(i) == 1) {
                sb.append("C");
                c++;
            } else {
                sb.append("D");
            }
        }
        sb.append(": C=" + c / GAConfig.nGenes);
        sb.append(", F=" + getFitness());
        return sb.toString();
    }

    /**
     * MÃƒÂ©todo get do nÃƒÂºmero aleatÃƒÂ³rio para ordenaÃƒÂ§ÃƒÂ£o embaralhada.
     *
     * @return o nÃƒÂºmero aleatÃƒÂ³rio do IndivÃƒÂ­duo.
     */
    public int getRandom() {
        return iRandom;
    }

    /**
     * MÃƒÂ©todo set do Fitness do indivÃƒÂ­duo.
     *
     * @param fitness AptidÃƒÂ£o do indivÃƒÂ­duo.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * MÃƒÂ©todo GET do fitness do indivÃƒÂ­duo.
     *
     * @return AptdÃƒÂ£o do indivÃƒÂ­duo
     */
    public double getFitness() {
        return this.fitness;
    }

    //SOMA CONVOLUCAO COM UMA TENTATIVA DE RAMPA
    public static int[] somaConvolucao(int[] geneFilho1, int[] geneFilho2) {
        int[] soma = new int[geneFilho1.length + geneFilho2.length - 1];

        for (int i = 0; i < soma.length; i++) {
            for (int j = 0; j < geneFilho2.length; j++) {
                if ((i - j) >= 0 && (i - j) < geneFilho1.length) {
                    soma[i] += (int) geneFilho2[j] * geneFilho1[i - j];
                    //System.out.println(soma[i]);
                   /* if (soma[i] > 1) {
                        soma[i] = 1;
                    } else {
                        soma[i] = 0;
                    }*/
                }
            }
        }
        return soma;
    }
    
    //SAÃ�DA NORMALIZADA
  	public static int[] normalizarSaida(int[] soma){
  		int[] ConvNormalizada = new int[soma.length];
  		int min = Arrays.stream(soma).min().getAsInt();
          int max = Arrays.stream(soma).max().getAsInt();
  		
  		for(int i=0; i<ConvNormalizada.length; i++){	
  			if(max-min==0){
  				ConvNormalizada[i] =0;
  			}else{
  				ConvNormalizada[i] = Math.abs((soma[i]-min)/(max-min));
  			}
  		}
  			
  		return ConvNormalizada;
  	}


}
