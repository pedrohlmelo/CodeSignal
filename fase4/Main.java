
import java.util.*;
 class Pessoa {
    String nome;
    String cidade;

    Pessoa(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    String getNome() {
        return nome;
     }

    String getCidade() {
        return cidade;
    }

}

public class Main {

    static Map<String,List<String>> agruparPorCidade(List<Pessoa> pessoas){
        Map< String, List<String>> grupos = new HashMap<>();
       for(Pessoa p : pessoas){
           String cidade = p.getCidade();
           String nome = p.getNome();
   
           if(grupos.containsKey(cidade)){
              grupos.get(cidade).add(nome);
           }
           else{
               grupos.put(cidade, new ArrayList<>());
               grupos.get(cidade).add(nome);
           }
       }
       return grupos;  
    }
    
    public static void main(String[] args){
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Ana", "BH"));
        pessoas.add(new Pessoa("Bruno", "SP"));
        pessoas.add(new Pessoa("Carla", "BH"));

        System.out.println("esperado: {BH=[Ana, Carla], SP=[Bruno]}");
        System.out.println("obtido:   " + agruparPorCidade(pessoas));

        System.out.println("esperado: {}");
        System.out.println("obtido:   " + agruparPorCidade(new ArrayList<>()));

    }
    

    
}
