package fase4;

static class Pessoa {
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

    Static Map<String,List<String>>

    agruparPorCidade(List<Pessoa> pessoas){

 }

public class Main {
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
