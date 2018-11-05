package AgPrisioneiro;
/**
 * Classe contêiner das configurações da simulação.
 * @author Patrizia Chermont
 *
 */
public class GAConfig {
	
	/**
	 * Método construtor privado.
	 */
	private GAConfig(){		
	}
	
	/**
	 * Tempo de cadeia se ambos cooperarem.
	 */
	public static double tCalados;
	
	/**
	 * Tempo de cadeia se o indivíduo cooperar e for delatado.
	 */
	public static double tDelatado;
	
	/**
	 * Tempo de cadeia se ambos delatarem um ao outro.
	 */
	public static double tReciproco;
	
	/**
	 * Tempo de cadeia se o indivíduo delatar e não for denunciado. 
	 */
	public static double tDelator;
	
	/**
	 * Premiação por cooperações seguidas.
	 */
	public static double delta;
	
	/**
	 * Número de prisioneiros na população.
	 */
	public static int nPrisioneiros;
	
	/**
	 * Número de genes em cada prisioneiro.
	 */
	public static int nGenes;
	
	/**
	 * Número de cooperações seguidas para bonificação.
	 */
	public static int nSeguidos;
	
	/**
	 * Número de épocas de simulação.
	 */
	public static int nEpocas;
	
	/**
	 * Probabilidade de mutação em cada gene.
	 */
	public static double pMutacao;	

}