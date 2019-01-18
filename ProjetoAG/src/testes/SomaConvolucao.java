package testes;

import java.util.Arrays;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Aluno
 */
public class SomaConvolucao {

    public static int[] somaConvolucao(int[]x, int[]h){
        int[] soma = new int[x.length + h.length - 1];
   
        for(int i = 0; i < soma.length; i++){
            for(int j = 0; j < h.length; j++){
                if((i-j) >= 0 && (i-j) < x.length){
                    soma[i] += h[j] * x[i-j]; 
                    	
                    
                }
            }
        }
        return soma;
    }
    
	
	public static int[] normalizar(int[] soma){
		int[] ConvNormalizada = new int[soma.length];
		int min = Arrays.stream(soma).min().getAsInt();
        int max = Arrays.stream(soma).max().getAsInt();
		
		for(int i=0; i<ConvNormalizada.length; i++){
			
			ConvNormalizada[i] = Math.abs((soma[i]-min)/(max-min));
			
		}
			
		return ConvNormalizada;
	}
	
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // TODO code application logic here
        int[] x = new int[]{1,1,1,1,0,0,1,0}; 
        int[] h = new int[]{0,1,1,0,1,1,0,1}; 
        int[] soma = somaConvolucao(x, h);
        
        int[] ConvNormalizada = normalizar(soma);
        
        
        for(int i : soma)
            System.out.print(i);

        
        
        System.out.println("---");         
        
        System.out.println("\nSaida normalizada");
        for(int i : ConvNormalizada)
        System.out.print(i);
        
        
        
        
        int[] filho0 = new int[8]; 
        for(int i=0; i<filho0.length;i++)
        	filho0[i] = soma[i];
        
        int min = Arrays.stream(soma).min().getAsInt();
        int max = Arrays.stream(soma).max().getAsInt();
        
        System.out.println("\nmin = "+min);
        System.out.println("max = "+max);
        
   //     System.arraycopy(soma,0,filho0,0,7);
        
        int[] filho1 = new int[8]; 
      //  System.arraycopy(soma,7,filho2,0,7);        
 
        int j=soma.length/2;
        for(int i=0; i<filho1.length; i++, j++)
        	filho1[i] = soma[j];
 
        int[] filho2 = new int[8]; 
        //  System.arraycopy(soma,7,filho2,0,7);        
   
          int k=4;
          for(int i=0; i<filho2.length; i++, k++)
          	filho2[i] = soma[k];
   
            
        
          
      System.out.println("FILHO0");  
        for(int i : filho0)
            System.out.print(i);
        
     
       System.out.println("---");  
        System.out.println("FILHO1"); 
        for(int i : filho1)
            System.out.print(i);
        
        System.out.println("---");  
        System.out.println("FILHO2"); 
        for(int i : filho2)
            System.out.print(i);  
        
        
       
        /*

	r[0] ={3,2} --> 1*3 , 1*2 
	r[1] ={0,9,6} --> 3*3 , 3*2
	S = {3,11,6}

	3x1  + 2x0 = 3
	3x3  + 2x1 = 11
	3x0  + 2x3 = 6


S[0] = 3;
*/

/*

int i = 0;
int j = 0;
int tam = ((h.length)+(x.length))-1; //Sinal resultante
int[] s = new int[tam];
int cont = 0;



while(cont<tam){
        //System.out.println("valor do J ="+j);
        //System.out.println("valor do cont ="+cont);
    if(j==0){
            s[cont] = (x[i]*h[j]);
           System.out.println(s[cont]);
            j++;
            cont++;
    }else if(j==4){
        s[cont] = (x[i+1]*h[j-1]);
        System.out.println(s[cont]);
         cont++;
    }else{
   // System.out.println("valor do J ="+j);
     System.out.println("calculo ="+((x[i]*h[j])+(x[i+1]*h[j-1])));
    	//s[cont] = ((x[i]*h[j])+(x[i+1]*h[j-1])); //1*1 + 2*1 = 3
            j++;
            cont++;
    }
    }

/*    for(int k=0; k<tam; k++){
               System.out.println(s[k]);
    }

*/
        
       
    }
    
}
