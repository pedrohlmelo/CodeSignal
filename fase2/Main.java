package fase1;
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

    public static void main(String[] args) {
        addP("Arroz");
        addP("Arroz");
        addP("Feijao");

        Map<String, Integer> resultado = frequencia(palavras);
        System.out.println(resultado);
    }
}