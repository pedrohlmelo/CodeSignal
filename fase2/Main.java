package fase2;
import java.util.*;


public class Main {

    static List<String> palavras = new ArrayList<>();

    static Map<String, Integer> frequencia(List<String> palavras) {
        Map<String, Integer> freq = new HashMap<>();
        for (int i = 0; i < palavras.size(); i++) {
            String p = palavras.get(i);
            if (freq.containsKey(p)) {
                int atual = freq.get(p);
                freq.put(p, atual + 1);
            } else {
                freq.put(p, 1);
            }
        }
        return freq;
    }

    static void addP(String palavra) {
        palavras.add(palavra);
    }
    public static List<String> topN(Map<String, Integer> freq, int n){
          List <String> usados = new ArrayList<>();
          List <String> resultado = new ArrayList<>();

          for(int i =0; i < n; i++){
            String melhor = null;
            int melhorN = -1;
        

            for(String nome : freq.keySet()){
                if(usados.contains(nome)){
                    continue;
                }
               int qtd = freq.get(nome);

            if(melhor == null){
                melhor = nome;
                melhorN = qtd;

            }
            else if(qtd > melhorN){
                melhor = nome;
                melhorN = qtd;
            }

            else if(qtd == melhorN && nome.compareTo(melhor) < 0){
                melhor = nome;
            }

            if(melhor == null){
                break;
            }

            }
            resultado.add(melhor);
            usados.add(melhor);
          }

          return resultado;

          

    }

    public static void main(String[] args) {
        //addP("Arroz");
        //addP("Arroz");
        //addP("Feijao");

        Map<String, Integer> freq = new HashMap<>();
        freq.put("arroz", 5);
        freq.put("feijao", 3);
        freq.put("batata", 5);
        freq.put("cebola", 1);
        freq.put("alho", 3);
        System.out.println(topN(freq,3));
        //System.out.println(freq);

    }
}