package prisioneiroV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe de representaÃ§Ã£o da PopulaaÃ§Ã£o de indivÃ­duos prisioneiros.
 * @author Patrizia Chermont
 *
 */
public class Populacao{

	/**
	 * Lista de Prisioneiros da populaÃ§Ã£o.
	 */
	private List<Prisioneiro> prisioneiros;

	/**
	 * Construtor pÃºblico vazio.
	 */
	public  Populacao(){
	}
	
	/**
	 * Gera uma nova populaÃ§Ã£o de prisioneiros com cadeia aleatÃ³ria de genes.
	 */
	public void inicializar(){
		prisioneiros = new ArrayList<Prisioneiro>(GAConfig.nPrisioneiros);
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			prisioneiros.add(new Prisioneiro());
		}
	}
	
	/**
	 * Realiza um torneio na populaÃ§Ã£o.
	 * Cada indivÃ­duo Ã© comparado com um comparsa. Aquele que tiver menor
	 * fitness Ã© eliminado da populaÃ§Ã£o.
	 */
	public void torneio(){
		List<Prisioneiro> vencedores = new ArrayList<Prisioneiro>();
		for (int i = 0; i < GAConfig.nPrisioneiros; i = i + 2){
			vencedores.add(
					prisioneiros.get(i).disputar(
							prisioneiros.get(i+1)
					)
			);
		}
		prisioneiros = vencedores;
	}
	
	/**
	 * Cria uma nova geraÃ§Ã£o de indivÃ­duos. Como os pais serÃ£o substituÃ­dos,
	 * sÃ£o gerados 4 filhos para cada casal, sendo duas duplas de gÃªmeos.
	 */
	public void geracao(){
		List<Prisioneiro> filhos = new ArrayList<Prisioneiro>();
		for (int i = 0; i < GAConfig.nPrisioneiros/2; i = i + 2){
			filhos.addAll(
					prisioneiros.get(i).gerar(
							prisioneiros.get(i+1)
					)
			);
		}
		prisioneiros = filhos;
	}
	
	/**
	 * Calcula o fitness de um indivÃ­duo comparado com toda a populaÃ§Ã£o.
	 * @param n Ã�ndice do indivÃ­duo.
	 * @return SomatÃ³ria dos Fitness comparado com todos os indivÃ­duos.
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
	 * Ã�ndice de cooperaÃ§Ã£o da populaÃ§Ã£o
	 * @return NÃºmero entre 0 e 1 que representa a razÃ£o CooperaÃ§Ãµes/Total.
	 */
	public double cooperacoes(){
		double total = 0;
		for (Prisioneiro p : prisioneiros){
			for (int i = 0; i < GAConfig.nGenes; i++){
				if (p.cooperou(i)) total++;
			}
		}
		return total/(GAConfig.nGenes * GAConfig.nPrisioneiros);
	}
	
	/**
	 * Tempo total de cadeia do indivÃ­duo se fosse confrontado com todos os
	 * outros indivÃ­duos.
	 * @param n Ã�ndice do indivÃ­duo 
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
	 * SomatÃ³ria total do fitness de cada indivÃ­duo com todos os outros
	 * prisioneiros.
	 * @return Fitness total dos indivÃ­duos.
	 */
	public double fitnessTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += fitnessTotal(i);
		}
		return total;
	}
	
	/**
	 * SomatÃ³ria do tempo total de cadeia de cada indivÃ­duo quando confrontado
	 * com toda a populaÃ§Ã£o.
	 * @return Tempo total de cadeia da populaÃ§Ã£o.
	 */
	public double tempoTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += tempoTotal(i);
		}
		return total;
	}
	
	/**
	 * Tempo mÃ©dio de prisÃ£o da populaÃ§Ã£o
	 * @return Tempo mÃ©dio, em anos.
	 */
	public double tempoMedio(){
		return tempoTotal()/
		(GAConfig.nPrisioneiros * (GAConfig.nPrisioneiros-1) * GAConfig.nGenes);
	}
	
	/**
	 * Calcula o fitness de um indivÃ­duo comparado com metade da populaÃ§Ã£o.
	 * Se o Ã­ndice n for par, entÃ£o o indivÃ­duo Ã© comparado com todos os
	 * indivÃ­duos de Ã­ndice Ã­mpar, e vice-versa.
	 * @param n Ã�ndice do indivÃ­duo.
	 * @return SomatÃ³ria dos Fitness comparado com metade dos indivÃ­duos.
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
	 * Embaralha a posiÃ§Ã£o dos indivÃ­duos na populaÃ§Ã£o para diversificar
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
	 * Realiza o cÃ¡lculo do fitness de cada indivÃ­duo (com todos ou metade da
	 * populaÃ§Ã£o) e remove a metade menos apta da populaÃ§Ã£o.
	 * @param completo Caso a comparaÃ§Ã£o seja com toda a populaÃ§Ã£o, este
	 * parÃ¢metro dever ser true. Caso a comparaÃ§Ã£o seja somente com metade da
	 * populaÃ§Ã£o, entÃ£o deve ser false.
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
	 * Reporta a cadeia de genes de todos os indivÃ­duos.
	 * @return Cadeia e genes de todos os indivÃ­duos.
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
	 * Retorna o Ãºltimo prisioneiro da lista
	 * @return Ãšltimo prisioneiro.
	 */
	public Prisioneiro ultimo(){
		return prisioneiros.get(prisioneiros.size()-1);
	}	
}