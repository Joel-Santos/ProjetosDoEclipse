/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemadamochila;

/**
 *
 * @author Laura
 */
public class Teste {
    
    public static void main(String[] args) {
        Populacao p = new Populacao();
        p.iniciarPopulacao(100);
        for(int i = 0; i < p.getNumIndividuos(); i++)
            System.out.println(p.getIndividuo(i));
    }
    
}
