package prisioneiroV2;

/**
 * Classe de execução da simulação.
 * @author Patrizia Chermont
 *
 */
public class Main {

	/**
	 * Método principal da classe para execução da simulação.
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**
		 * Parâmetros da simulação
		 * TODO: Obter os parâmetros da linha de comando e substituir.
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
		GAConfig.pMutacao 		= 0.5;
		
		Populacao populacao = new Populacao();
		
		/**
		 * Primeira simulação: Torneio
		 * Algoritmo:
		 * 		a) Inicializar a população inicial com decisões aleatórias;
		 * 		b) Executar o torneio, eliminando os perdedores;
		 * 		c) Misturar e Gerar novos indivíduos, com substituição dos pais;
		 * 		d) Repetir (b,c) até o número de épocas
		 * 		e) Reportar o tempo total e o índice de cooperações.
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
		 * Segunda simulação: compara com a metade
		 * Algoritmo:
		 * 		a) Inicializar a população inicial com decisões aleatórias;
		 * 		b) Executar a comparação com a metade da população;
		 * 			b.1) Comparar com os pares, se ímpar, e vice-versa.
		 * 		c) Ordenar os índivíduos do mais apto ao menos apto;
		 * 		d) Excluir a metade menos apta da população;
		 * 		e) Misturar e Gerar novos indivíduos, com substituição dos pais;
		 * 		f) Repetir (b,c,d,e) até o número de épocas;
		 * 		g) Reportar o tempo total de cadeia e o índice de cooperações.
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
		 * Terceira simulação: compara com toda a população
		 * Algoritmo:
		 * 		a) Inicializar a população inicial com decisões aleatórias;
		 * 		b) Executar a comparação do indivíduo com toda a população;
		 * 		c) Ordenar os índivíduos do mais apto ao menos apto;
		 * 		d) Excluir a metade menos apta da população;
		 * 		e) Misturar e Gerar novos indivíduos, com substituição dos pais;
		 * 		f) Repetir (b,c,d,e) até o número de épocas;
		 * 		g) Reportar o tempo total de cadeia e o índice de cooperações.
		 */
		System.out.println("\n****************Toda a População****************");
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
