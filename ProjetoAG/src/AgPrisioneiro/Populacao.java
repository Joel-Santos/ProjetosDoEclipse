package AgPrisioneiro;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe de representação da Populaação de indivíduos prisioneiros.
 * @author Patrizia Chermont
 *
 */
public class Populacao{

	/**
	 * Lista de Prisioneiros da população.
	 */
	private List<Prisioneiro> prisioneiros;

	/**
	 * Construtor público vazio.
	 */
	public  Populacao(){
	}
	
	/**
	 * Gera uma nova população de prisioneiros com cadeia aleatória de genes.
	 */
	public void inicializar(){
		prisioneiros = new ArrayList<Prisioneiro>(GAConfig.nPrisioneiros);
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			prisioneiros.add(new Prisioneiro());
		}
	}
	
	/**
	 * Realiza um torneio na população.
	 * Cada indivíduo é comparado com um comparsa. Aquele que tiver menor
	 * fitness é eliminado da população.
	 */
	public void torneio(){
		List<Prisioneiro> vencedores = new ArrayList<Prisioneiro>();
		for (int i = 0; i < GAConfig.nPrisioneiros; i = i + 2){
			vencedores.add(
					prisioneiros.get(0).disputar(
							prisioneiros.get(i),prisioneiros.get(i+1)
					)
			);
		}
		prisioneiros = vencedores;
	}
	
	/**
	 * Cria uma nova geração de indivíduos. Como os pais serão substituídos,
	 * são gerados 4 filhos para cada casal, sendo duas duplas de gêmeos.
	 */
	public void geracao(){
		List<Prisioneiro> filhos = new ArrayList<Prisioneiro>();
		for (int i = 0; i < GAConfig.nPrisioneiros/2; i = i + 2){
			filhos.addAll(
					prisioneiros.get(0).gerar(
							prisioneiros.get(i),prisioneiros.get(i+1)
					)
			);
		}
	
		prisioneiros = filhos;
	}
	
	/**
	 * Calcula o fitness de um indivíduo comparado com toda a população.
	 * @param n Índice do indivíduo.
	 * @return Somatória dos Fitness comparado com todos os indivíduos.
	 */
	public double fitnessTotal(int n){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			if (i != n){
				total += prisioneiros.get(i).fitness(prisioneiros.get(n));
				total += prisioneiros.get(n).fitness(prisioneiros.get(i));
			}
		}
		return total;
	}
	
	/**
	 * Índice de cooperação da população
	 * @return Número entre 0 e 1 que representa a razão Cooperações/Total.
	 */
	public double cooperacoes(){
		double total = 0;
		for (Prisioneiro p : prisioneiros){
			for (int i = 0; i < GAConfig.nGenes; i++){
				if (p.cooperou(i)==1) total++;
			}
		}
		return total/(GAConfig.nGenes * GAConfig.nPrisioneiros);
	}
	
	/**
	 * Tempo total de cadeia do indivíduo se fosse confrontado com todos os
	 * outros indivíduos.
	 * @param n Índice do indivíduo 
	 * @return Tempo total de cadeia
	 */
	public double tempoTotal(int n){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			if (i != n){
				total += prisioneiros.get(n).tempo(prisioneiros.get(i));
			}
		}
		return total;		
	}
	
	/**
	 * Somatória total do fitness de cada indivíduo com todos os outros
	 * prisioneiros.
	 * @return Fitness total dos indivíduos.
	 */
	public double fitnessTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += fitnessTotal(i);
		}
		return total;
	}
	
	/**
	 * Somatória do tempo total de cadeia de cada indivíduo quando confrontado
	 * com toda a população.
	 * @return Tempo total de cadeia da população.
	 */
	public double tempoTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += tempoTotal(i);
		}
		return total;
	}
	
	/**
	 * Tempo médio de prisão da população
	 * @return Tempo médio, em anos.
	 */
	public double tempoMedio(){
		return tempoTotal()/
		(GAConfig.nPrisioneiros * (GAConfig.nPrisioneiros-1) * GAConfig.nGenes);
	}
	
	/**
	 * Calcula o fitness de um indivíduo comparado com metade da população.
	 * Se o índice n for par, então o indivíduo é comparado com todos os
	 * indivíduos de índice ímpar, e vice-versa.
	 * @param n Índice do indivíduo.
	 * @return Somatória dos Fitness comparado com metade dos indivíduos.
	 */
	public double fitnessMetade(int n){
		double total = 0;
		int resto = 1 - n % 2;		
		for (int i = 0; i < GAConfig.nPrisioneiros / 2; i++){
			total += prisioneiros.get(2*i+resto).fitness(prisioneiros.get(n));
			total += prisioneiros.get(n).fitness(prisioneiros.get(2*i+resto));
		}
		return total;
	}

	/**
	 * Embaralha a posição dos indivíduos na população para diversificar
	 * o cruzamento.
	 */
	public void misturar() {
		Collections.sort(prisioneiros, new Comparator<Prisioneiro>() {
			@Override
			public int compare(Prisioneiro p1, Prisioneiro p2) { 
				return Integer.compare(p1.getRandom(), p2.getRandom());
			}
		});
	}

	/**
	 * Realiza o cálculo do fitness de cada indivíduo (com todos ou metade da
	 * população) e remove a metade menos apta da população.
	 * @param completo Caso a comparação seja com toda a população, este
	 * parâmetro dever ser true. Caso a comparação seja somente com metade da
	 * população, então deve ser false.
	 */
	public void comparar(boolean completo) {
		
		Prisioneiro p;
		
		for(int i = 0; i < GAConfig.nPrisioneiros; i++){
			p = prisioneiros.get(i);
			if (completo){
				p.setFitness(fitnessTotal(i));
			} else {
				p.setFitness(fitnessMetade(i));
			}
		}
		
		Collections.sort(prisioneiros, new Comparator<Prisioneiro>() {
			@Override
			public int compare(Prisioneiro p1, Prisioneiro p2) {
				return Double.compare(p1.getFitness(), p2.getFitness());
			}
			
		});
		
		for (int i = 0; i < GAConfig.nPrisioneiros/2; i++){
			prisioneiros.remove(GAConfig.nPrisioneiros/2);
		}
	}
	
	
	
	/**
	 * Reporta a cadeia de genes de todos os indivíduos.
	 * @return Cadeia e genes de todos os indivíduos.
	 */
	public String relatorio(){
		StringBuilder sb = new StringBuilder();
		for (Prisioneiro p : prisioneiros){
			sb.append(p);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Ordena todos os prisioneiros por ordem de fitness.
	 */
	public void ordenar(){
		fitnessTotal();
		Collections.sort(prisioneiros, new Comparator<Prisioneiro>() {
			@Override
			public int compare(Prisioneiro p1, Prisioneiro p2) {
				return Double.compare(p1.getFitness(), p2.getFitness());
			}
		});
	}
	
	/**
	 * Retorna o primeiro prisioneiro da lista
	 * @return Primeiro prisioneiro.
	 */
	public Prisioneiro primeiro(){
		return prisioneiros.get(0);
	}
	
	/**
	 * Retorna o último prisioneiro da lista
	 * @return Último prisioneiro.
	 */
	public Prisioneiro ultimo(){
		return prisioneiros.get(prisioneiros.size()-1);
	}	
}