package AgPrisioneiro;

/**
 * Classe de execu��o da simula��o.
 * @author Patrizia Chermont
 *
 */
public class Main {

	/**
	 * M�todo principal da classe para execu��o da simula��o.
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**
		 * Par�metros da simula��o
		 * TODO: Obter os par�metros da linha de comando e substituir.
		 */
		GAConfig.tCalados		= .5;
		GAConfig.tDelatado		= 30;
		GAConfig.tReciproco		= 10;
		GAConfig.tDelator		=  0;
		
		GAConfig.nPrisioneiros	= 32;		
		GAConfig.nGenes			= 30;
		GAConfig.nSeguidos		= 5;		
		GAConfig.nEpocas		= 5;
		
		GAConfig.delta			= 0.0;		
		GAConfig.pMutacao 		= 0.01;
		
		Populacao populacao = new Populacao();
		
		/**
		 * Primeira simula��o: Torneio
		 * Algoritmo:
		 * 		a) Inicializar a popula��o inicial com decis�es aleat�rias;
		 * 		b) Executar o torneio, eliminando os perdedores;
		 * 		c) Misturar e Gerar novos indiv�duos, com substitui��o dos pais;
		 * 		d) Repetir (b,c) at� o n�mero de �pocas
		 * 		e) Reportar o tempo total e o �ndice de coopera��es.
		 */
		System.out.println("****************Torneio****************");
		populacao.inicializar();		 
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes());
		for (int i = 0; i < GAConfig.nEpocas; i++){
			populacao.torneio();
			populacao.misturar();
			populacao.geracao();			
		}
		populacao.ordenar();
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes()
				+"\n"+populacao.relatorio()
				);
		
		
		/**
		 * Segunda simula��o: compara com a metade
		 * Algoritmo:
		 * 		a) Inicializar a popula��o inicial com decis�es aleat�rias;
		 * 		b) Executar a compara��o com a metade da popula��o;
		 * 			b.1) Comparar com os pares, se �mpar, e vice-versa.
		 * 		c) Ordenar os �ndiv�duos do mais apto ao menos apto;
		 * 		d) Excluir a metade menos apta da popula��o;
		 * 		e) Misturar e Gerar novos indiv�duos, com substitui��o dos pais;
		 * 		f) Repetir (b,c,d,e) at� o n�mero de �pocas;
		 * 		g) Reportar o tempo total de cadeia e o �ndice de coopera��es.
		 */
		System.out.println("\n****************Metade****************");
		populacao.inicializar();
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes());
		for (int i = 0; i < GAConfig.nEpocas; i++){
			populacao.comparar(false);
			populacao.misturar();
			populacao.geracao();
		}
		populacao.ordenar();
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes()
				+"\n"+populacao.relatorio()
				);
		
		/**
		 * Terceira simula��o: compara com toda a popula��o
		 * Algoritmo:
		 * 		a) Inicializar a popula��o inicial com decis�es aleat�rias;
		 * 		b) Executar a compara��o do indiv�duo com toda a popula��o;
		 * 		c) Ordenar os �ndiv�duos do mais apto ao menos apto;
		 * 		d) Excluir a metade menos apta da popula��o;
		 * 		e) Misturar e Gerar novos indiv�duos, com substitui��o dos pais;
		 * 		f) Repetir (b,c,d,e) at� o n�mero de �pocas;
		 * 		g) Reportar o tempo total de cadeia e o �ndice de coopera��es.
		 */
		System.out.println("\n****************Toda a Popula��o****************");
		populacao.inicializar();
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes());
		for (int i = 0; i < GAConfig.nEpocas; i++){
			populacao.comparar(true);
			populacao.misturar();
			populacao.geracao();			
		}
		populacao.ordenar();
		System.out.println(
				populacao.tempoMedio()+", C = "+populacao.cooperacoes()
				+"\n"+populacao.relatorio()
				);		
		
		/**
		 * Terminar o programa.
		 */
		System.exit(0);
	}
}
