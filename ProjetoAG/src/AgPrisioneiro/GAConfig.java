package AgPrisioneiro;
/**
 * Classe cont�iner das configura��es da simula��o.
 * @author Patrizia Chermont
 *
 */
public class GAConfig {
	
	/**
	 * M�todo construtor privado.
	 */
	private GAConfig(){		
	}
	
	/**
	 * Tempo de cadeia se ambos cooperarem.
	 */
	public static double tCalados;
	
	/**
	 * Tempo de cadeia se o indiv�duo cooperar e for delatado.
	 */
	public static double tDelatado;
	
	/**
	 * Tempo de cadeia se ambos delatarem um ao outro.
	 */
	public static double tReciproco;
	
	/**
	 * Tempo de cadeia se o indiv�duo delatar e n�o for denunciado. 
	 */
	public static double tDelator;
	
	/**
	 * Premia��o por coopera��es seguidas.
	 */
	public static double delta;
	
	/**
	 * N�mero de prisioneiros na popula��o.
	 */
	public static int nPrisioneiros;
	
	/**
	 * N�mero de genes em cada prisioneiro.
	 */
	public static int nGenes;
	
	/**
	 * N�mero de coopera��es seguidas para bonifica��o.
	 */
	public static int nSeguidos;
	
	/**
	 * N�mero de �pocas de simula��o.
	 */
	public static int nEpocas;
	
	/**
	 * Probabilidade de muta��o em cada gene.
	 */
	public static double pMutacao;	

}