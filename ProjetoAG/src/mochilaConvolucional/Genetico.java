package mochilaConvolucional;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genetico {
	//configuracao dos parametros do algoritmo genetico
	public static final double TAXADEMUTACAO = 0.5;
	static final double TAXADECRUZAMENTO = 0.9;
	static final boolean ELITISMO = false;
	static final int TAMANHODAPOPULACAO = 8;
	static final int MAXIMODEGERACOES = 50;
	//FileWriter fileWriter = null;
	//private static final String FILE_HEADER = "Geração,Melhor Cromossomo,Aptidão,Peso";
        //-------------------------------------------------
        
	private Populacao populacao;
        private final Random r;
	private int contEstagnar;
	private double melhorAptidaoAnterior;

        public Genetico(){
            populacao = new Populacao();
            r = new Random();
            contEstagnar = 0;
            melhorAptidaoAnterior = -1;
        }
        
        
        
        
        //Fluxo principal do algoritmo
	public void Iniciar(){
		int geracao = 0;
		populacao.iniciarPopulacao(TAMANHODAPOPULACAO);

		do {
                        System.out.println("Geracao " + geracao +  "| Melhor " + populacao.getMelhor());
                        
			
                        populacao = gerarPopulacao();

			contaEstagnacao();
			if(contEstagnar >= 100)
				break;

		} while (++geracao < MAXIMODEGERACOES);
	}
	
        //Gera nova populacao de solucoes
	private Populacao gerarPopulacao() {
		Populacao novaPopulacao = new Populacao();

		if (ELITISMO) {
			novaPopulacao.setIndividuo(populacao.getMelhor());
		}

		// insere novos individuos na nova populacao, ate atingir o tamanho maximo
		while (novaPopulacao.getNumIndividuos() <= TAMANHODAPOPULACAO) {
			ArrayList<Individuo> filhos = cruzamento(selecaoRoleta());
			novaPopulacao.setIndividuos(filhos);
		}
		
		novaPopulacao.ordenarPopulacao();
		return novaPopulacao;
	}


	private ArrayList<Individuo> selecaoTorneioBinario(){
		ArrayList<Individuo> pais = new ArrayList<>();
		int a, b;
		//repete esse laco 2 vezes para pegar 2 pais
		for (int i = 0; i < 2; i++) {
			a = r.nextInt(populacao.getNumIndividuos());
			b = r.nextInt(populacao.getNumIndividuos());
			//considerando que a populacao esta ordenada, o individuo na posicao menor eh melhor
			if (a < b)
				pais.add(populacao.getIndividuo(a));
			else
				pais.add(populacao.getIndividuo(b));
		}

		return pais;
	}
        
        //selecao por roleta para problema de MINIMIZACAO
        private ArrayList<Individuo> selecaoRoleta(){
		ArrayList<Individuo> pais = new ArrayList<>();
		
                double totalAptidoes = 0;
                double[] percentual = new double[populacao.getNumIndividuos()];
                double[] fatias = new double[populacao.getNumIndividuos()];
                
                //soma todas as aptidoes
                for(int i = 0; i < populacao.getNumIndividuos(); i++){
                    totalAptidoes += 1/populacao.getIndividuo(i).getAptidao();
                }
                
                //calcula o percentual de cada individuo no total das aptidoes
                for(int i = 0; i < populacao.getNumIndividuos(); i++){
                    percentual[i] = (1/populacao.getIndividuo(i).getAptidao())/totalAptidoes;
                }
                
                //calcula a fatia da roleta para cada individuo de acordo com seu percentual
                for(int i = 0; i < populacao.getNumIndividuos(); i++){
                    if(i == 0)
                        fatias[i] = percentual[i];
                    else
                        fatias[i] = fatias[i-1] + percentual[i];
                }
                
                //roda a roleta 2 vezes, para selecionar 2 pais
                for(int i = 0; i < 2; i++){
                    pais.add(populacao.getIndividuo(rodaRoleta(fatias)));
                }
		return pais;
	}
        
        private int rodaRoleta(double[] fatias){
            double random = new Random().nextDouble();
            for(int i = 0; i < fatias.length; i++){
                 if(random < fatias[i])
                    return i;
            }
            return 0;
        }

        //cruzamento com 2 pontos de corte aleatÃƒÂ³rios
	private ArrayList<Individuo> cruzamento(ArrayList<Individuo> pais) {
		
		int[] pai0 = pais.get(0).getGenes();
		int[] pai1 = pais.get(1).getGenes();
		
		//Soma convolução
		int[] convolucao = somaConvolucao(pai0, pai1);
		//Saida Normalizada
		int[] convNormalizada =  normalizarSaida(convolucao);
		
          
		
				int[] filho0 = new int[8];   
               // System.arraycopy(convolucao,0,filho0,0,7);
				for(int i=0; i<filho0.length;i++)
		        	filho0[i] = convNormalizada[i]; //Inicio do cromossomo

                int[] filho1 = new int[8];
                int k=4;
                for(int i=0; i<filho1.length; i++, k++)
                	filho1[i] = convNormalizada[k];	 //Meio do cromossomo	
                
                int[] filho2 = new int[8]; 
                //System.arraycopy(convolucao,7,filho2,0,7);
                int j=convolucao.length/2;
                for(int i=0; i<filho1.length; i++, j++)
                	filho2[i] = convNormalizada[j];     //Metade/fim do cromossomo
                
                
                
               // int[] filho1 = new int[8];
    
                int pontoCorte1, qtdGenes, pontoCorte2;

	/*	if (r.nextDouble() <= TAXADECRUZAMENTO) {
                        pontoCorte1 = r.nextInt(7) + 1; //gera numero de 1 a 7 (indices possiveis do cromossomo) 
                        
                        qtdGenes = r.nextInt(8-pontoCorte1);//qtd de genes que serao trocados, depende do ponto de corte
                        
                        pontoCorte2 = pontoCorte1 + qtdGenes;

			//System.arraycopy(pai0, 0, filho0, 0, pontoCorte1);
                        System.arraycopy(pai0, pontoCorte1, filho1, pontoCorte1, qtdGenes);
                        
                        System.arraycopy(pai1, 0, filho1, 0, pontoCorte1);
                        //System.arraycopy(pai1, pontoCorte1, filho0, pontoCorte1, qtdGenes);
                        
                        if(pontoCorte2 < 8){
                            System.arraycopy(pai1, pontoCorte2, filho1, pontoCorte2, 8-pontoCorte2);
                          //  System.arraycopy(pai0, pontoCorte2, filho0, pontoCorte2, 8-pontoCorte2);
                        }
            	}*/ 
                
       //passando gemeos       
		ArrayList<Individuo> filhos = new ArrayList<>();
		filhos.add(new Individuo(filho1));
		filhos.add(new Individuo(filho1));

		return filhos;
	}

	private void contaEstagnacao(){
		if (melhorAptidaoAnterior <= -1 || populacao.getMelhor().getAptidao() != melhorAptidaoAnterior) {
			melhorAptidaoAnterior = populacao.getMelhor().getAptidao();
		//	System.out.println( populacao.getMelhor().getAptidao());
			contEstagnar = 1;
		} else {
			contEstagnar++;
		}
	}
	
	//SOMA CONVOLUÃ‡ÃƒO COM UMA TENTATIVA DE RAMPA
	public static int[] somaConvolucao(int[] geneFilho1, int[] geneFilho2){
        int[] soma = new int[geneFilho1.length + geneFilho2.length - 1];
   
        for(int i = 0; i < soma.length; i++){
            for(int j = 0; j < geneFilho2.length; j++){
                if((i-j) >= 0 &&(i-j) < geneFilho1.length){
                    soma[i] += geneFilho2[j] * geneFilho1[i-j];
                    //System.out.println(soma[i]);
                   /* if(soma[i]>1){
                    	soma[i]=0;
                    }else{
                    	soma[i]=1;
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
