package problemadamochila;

import java.util.ArrayList;
import java.util.Collections;

public class Populacao {
	private final ArrayList<Individuo> individuos;
        private Individuo melhor;

	public Populacao(){
		individuos = new ArrayList<>();
	}
	
	public void iniciarPopulacao(int tamPop) {
		for (int i = 0; i < tamPop; i++) {
			individuos.add(new Individuo());
		}
		ordenarPopulacao();
	}

	public void ordenarPopulacao() {
                //Ordem inversa para problemas de maximização
		Collections.sort(individuos, Collections.reverseOrder());
                //validar aqui para melhorar o desempenho
                //nesse caso, o melhor individuo eh aquele com maior aptidao que for valido
               for(Individuo i : individuos)
                    if(i.validar()){
                        melhor = i;
                        break;
                    }
	}

	public Individuo getIndividuo(int pos) {
		return individuos.get(pos);
	}

	// coloca um individuo na proxima posicao disponivel da populacao
	public void setIndividuo(Individuo individuo) {
		individuos.add(individuo);
	}
	
	public void setIndividuos(ArrayList<Individuo> filhos){
		individuos.addAll(filhos);
	}

	// numero de individuos existentes na populacao
	public int getNumIndividuos() {
		return individuos.size();
	}

        public Individuo getMelhor() {
            return melhor;
        }
}
