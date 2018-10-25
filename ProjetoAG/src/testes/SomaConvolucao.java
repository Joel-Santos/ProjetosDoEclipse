package testes;
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
                if((i-j) >= 0 && (i-j) < x.length)
                    soma[i] += h[j] * x[i-j]; 
            }
        }
        return soma;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        // TODO code application logic here
        int[] x = new int[]{177,40}; 
        int[] h = new int[]{134,78}; 
        int[] soma = somaConvolucao(x, h);
        for(int i : soma)
            System.out.println(i);
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
