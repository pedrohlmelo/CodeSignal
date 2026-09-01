package fase00;
import java.util.*;

public class Main {
    private final List<Integer> ids = new ArrayList<>();
    private final Map<Integer, String> usuario = new HashMap<>();

    //add na lista
    public int addLista(int value){
        // podem pedir numeros unicos

        //if(ids.contains(value)){
            //ecxeption
        //}

        ids.add(value);
        return ids.size(); // aqui normalmente pedem algum retorno, seja do tamanho da lista,
                            //  valor adicionado, etc.
    }

    //remover na lista

    public boolean removeLista(int value){
        //se o valor nao existir
        if(!ids.contains(value)){
            return false; // nao deu para remover
        }

        ids.remove(Integer.valueOf(value)); // para remover por valor, se fosse por indice seria
                                            // ids.remove(indice)
                                            // e colocarmos return ids.remove(Integer.valueOf(value)); ele ja retorna true ou false
        return true;
    }

    // adicionar no Hash Map

    public boolean addHash(int value, String name){
        //a chave da Hash é o inteiro, portanto deve-se conferir se o id ja existe 
        if(usuario.containsKey(value)){
            return false;
        }
        usuario.put(value, name);
        return true;
        // lembrando que podem pedir outro tipo de retorno, como uma lista, outro HashMap,
        // vale treinar conversoes e retorno dessas estruturas
    }

    // remover no HashMap

    public boolean removeHash(int value){// remove por id
        if(usuario.containsKey(value)){
            usuario.remove(value); // so passando a chave ele ja remove todo o conteudo
            return true; 
        }

        return false; // outro tipo de conferencia / retorno que podem pedir é envolvendo lista
                      // ou hash vazio (.isEmpty), com optional, que ficaria return Optional.isEmpty();
                      // para retornar valor no optional -> return Optional.valueOf(valor);
    }



}
