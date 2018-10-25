package algoritmogenetico;

import java.util.Arrays;
import java.util.Random;

public class Testes {

	public static void main(String[] args) {
            for(int i = 0; i < 10; i++)    
                testaCruzamento();
                //selecaoRoleta();

	}
        
        private static void testaCruzamento() {
                int[] pai0 = {1, 2, 3, 4, 5, 6, 7, 8};
                int[] pai1 = {10, 20, 30, 40, 50, 60, 70, 80};

                int[] filho0 = new int[8];
                int[] filho1 = new int[8];

                int pontoCorte1, qtdGenes, pontoCorte2;
                Random r = new Random();

                pontoCorte1 = r.nextInt(7) + 1; //gera numero de 1 a 7 (indices possiveis do cromossomo) 

                qtdGenes = r.nextInt(8 - pontoCorte1);//qtd de genes que serao trocados, depende do ponto de corte

                pontoCorte2 = pontoCorte1 + qtdGenes;
                
                System.out.println("ponto de corte 1: " + pontoCorte1 + " qtdgenes: " + qtdGenes + " ponto de corte 2: " + pontoCorte2);

                System.arraycopy(pai0, 0, filho0, 0, pontoCorte1);
                System.arraycopy(pai0, pontoCorte1, filho1, pontoCorte1, qtdGenes);

                System.arraycopy(pai1, 0, filho1, 0, pontoCorte1);
                System.arraycopy(pai1, pontoCorte1, filho0, pontoCorte1, qtdGenes);
                
                if (pontoCorte2 < 8) {
                    System.arraycopy(pai0, pontoCorte2, filho0, pontoCorte2, 8 - pontoCorte2);
                    System.arraycopy(pai1, pontoCorte2, filho1, pontoCorte2, 8 - pontoCorte2);
                }
                
                System.out.println("Filho 0: " + Arrays.toString(filho0));
                System.out.println("Filho 1: " + Arrays.toString(filho1));
    }
        
        private static void selecaoRoleta(){
                double total = 0;
                double[] aptidao = {0.90, 1.00, 0.95, 1.50};
                double[] percentual = new double[aptidao.length];
                double[] fatias = new double[aptidao.length];
                
                System.out.println("\nRoleta:\n"+Arrays.toString(aptidao));
                
                for(int i = 0; i < aptidao.length; i++){
                    total += 1/aptidao[i];
                }
                
                System.out.println("Total: "+total);
                
                System.out.println("\nPercentual:");
                for(int i = 0; i < aptidao.length; i++){
                    percentual[i] = (1/aptidao[i])/total;
                    System.out.println(percentual[i]);
                }
               
                System.out.println("\nFatias:");
                for(int i = 0; i < fatias.length; i++){
                    if(i == 0)
                        fatias[i] = percentual[i];
                    else
                        fatias[i] = fatias[i-1] + percentual[i];
                    System.out.println(fatias[i]);
                }
                
                for(int i = 0; i < 2; i++){
                    System.out.println("Solucao "+rodarRoleta(fatias));
                }
	}
        
        private static int rodarRoleta(double[] fatias){
            double random = new Random().nextDouble();
            System.out.println("\nRodar Roleta: "+random);
            for(int i = 0; i < fatias.length; i++){
                if(random < fatias[i])
                    return i;
            }
            return 0;
        }

}
