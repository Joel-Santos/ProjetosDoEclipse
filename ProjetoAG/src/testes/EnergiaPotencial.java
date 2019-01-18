package testes;

public class EnergiaPotencial {
 
// 1 1 1 3 4 5 6 7 8 9 10 1 1 1 1
	
	public static float energiaPotencial(int[]x, int tamSignal){ 
		float energia = 0;
		
        for(int i = 0; i < x.length; i++){ 
        	energia += x[i]*x[i];
         }        
        return energia/(2*tamSignal);
    }
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

		int[] x = new int[]{1,0,1,1,0,1,1,1}; 
        int[] h = new int[]{0,1,1,0,1,1,0,1}; 
        int[] soma = SomaConvolucao.somaConvolucao(x, h);
        
        int[] filho0 = new int[8];
        int[] filho1 = new int[8];
        int[] filho2 = new int[8];
        
        
      //Inicio do cromossomo
        for(int i=0; i<filho0.length;i++)
        	filho0[i] = soma[i]; 
        
        
        
        //Meio do cromossomo
        for(int i=((filho1.length)/2)-1, j=0; i<(soma.length-((filho1.length)/2)); i++,j++){
        	//System.out.println(i);
        	filho1[j] = soma[i];
        }
        
        
        //Fim do cromossomo
        for(int i=(filho2.length)-1, k=0; i<soma.length;i++,k++)
        	filho2[k] = soma[i];
        
        
        
        float energiaFilho0 = energiaPotencial(filho0, soma.length);
        float energiaFilho1 = energiaPotencial(filho1, soma.length);
        float energiaFilho2 = energiaPotencial(filho2, soma.length);
         
        
        System.out.println("CONVOLUÇÃO");
        for(int i : soma)
            System.out.print(" "+i);
        
        
        System.out.println("\n");
        System.out.println("Filho 0: " + energiaFilho0);

        System.out.println("\n");
        System.out.println("Filho 1: " + energiaFilho1);
        
        System.out.println("\n");
        System.out.println("Filho 2: " + energiaFilho2);
        
        
        
        
        
	}

}
