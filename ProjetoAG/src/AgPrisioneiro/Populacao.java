package AgPrisioneiro;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe de representa��o da Populaa��o de indiv�duos prisioneiros.
 * @author Patrizia Chermont
 *
 */
public class Populacao{

	/**
	 * Lista de Prisioneiros da popula��o.
	 */
	private List<Prisioneiro> prisioneiros;

	/**
	 * Construtor p�blico vazio.
	 */
	public  Populacao(){
	}
	
	/**
	 * Gera uma nova popula��o de prisioneiros com cadeia aleat�ria de genes.
	 */
	public void inicializar(){
		prisioneiros = new ArrayList<Prisioneiro>(GAConfig.nPrisioneiros);
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			prisioneiros.add(new Prisioneiro());
		}
	}
	
	/**
	 * Realiza um torneio na popula��o.
	 * Cada indiv�duo � comparado com um comparsa. Aquele que tiver menor
	 * fitness � eliminado da popula��o.
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
	 * Cria uma nova gera��o de indiv�duos. Como os pais ser�o substitu�dos,
	 * s�o gerados 4 filhos para cada casal, sendo duas duplas de g�meos.
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
	 * Calcula o fitness de um indiv�duo comparado com toda a popula��o.
	 * @param n �ndice do indiv�duo.
	 * @return Somat�ria dos Fitness comparado com todos os indiv�duos.
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
	 * �ndice de coopera��o da popula��o
	 * @return N�mero entre 0 e 1 que representa a raz�o Coopera��es/Total.
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
	 * Tempo total de cadeia do indiv�duo se fosse confrontado com todos os
	 * outros indiv�duos.
	 * @param n �ndice do indiv�duo 
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
	 * Somat�ria total do fitness de cada indiv�duo com todos os outros
	 * prisioneiros.
	 * @return Fitness total dos indiv�duos.
	 */
	public double fitnessTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += fitnessTotal(i);
		}
		return total;
	}
	
	/**
	 * Somat�ria do tempo total de cadeia de cada indiv�duo quando confrontado
	 * com toda a popula��o.
	 * @return Tempo total de cadeia da popula��o.
	 */
	public double tempoTotal(){
		double total = 0;
		for (int i = 0; i < GAConfig.nPrisioneiros; i++){
			total += tempoTotal(i);
		}
		return total;
	}
	
	/**
	 * Tempo m�dio de pris�o da popula��o
	 * @return Tempo m�dio, em anos.
	 */
	public double tempoMedio(){
		return tempoTotal()/
		(GAConfig.nPrisioneiros * (GAConfig.nPrisioneiros-1) * GAConfig.nGenes);
	}
	
	/**
	 * Calcula o fitness de um indiv�duo comparado com metade da popula��o.
	 * Se o �ndice n for par, ent�o o indiv�duo � comparado com todos os
	 * indiv�duos de �ndice �mpar, e vice-versa.
	 * @param n �ndice do indiv�duo.
	 * @return Somat�ria dos Fitness comparado com metade dos indiv�duos.
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
	 * Embaralha a posi��o dos indiv�duos na popula��o para diversificar
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
	 * Realiza o c�lculo do fitness de cada indiv�duo (com todos ou metade da
	 * popula��o) e remove a metade menos apta da popula��o.
	 * @param completo Caso a compara��o seja com toda a popula��o, este
	 * par�metro dever ser true. Caso a compara��o seja somente com metade da
	 * popula��o, ent�o deve ser false.
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
	 * Reporta a cadeia de genes de todos os indiv�duos.
	 * @return Cadeia e genes de todos os indiv�duos.
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
	 * Retorna o �ltimo prisioneiro da lista
	 * @return �ltimo prisioneiro.
	 */
	public Prisioneiro ultimo(){
		return prisioneiros.get(prisioneiros.size()-1);
	}	
}