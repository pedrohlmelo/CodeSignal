# Kit CodeSignal — Industry Coding Framework (Java)

> Consulta rápida. 

---

## 1. Protocolo dos 90 minutos

### Antes de escrever qualquer linha (2 min)

1. **Abra a interface** (`XxxService.java` ou similar, o arquivo com 🔒). As assinaturas ali são o contrato absoluto. Tipo de retorno é `String`? `Optional`? `List`? Isso decide seu design.
2. **Abra o arquivo de teste do nível.** Quando o enunciado for ambíguo, a assertion decide.
3. **Leia os 4 níveis inteiros** (ou quantos estiverem visíveis). Você implementa um por vez, mas modela pensando no último.
4. **`import java.util.*;`** no topo. Elimina uma categoria inteira de erro.

### Alocação de tempo

| Nível | Tempo alvo | O que costuma cair |
|---|---|---|
| L1 | 10–15 min | CRUD básico + casos de borda |
| L2 | 20 min | Contagem, filtro, top-N, ordenação |
| L3 | 25 min | Estado temporal (TTL, expiração), histórico |
| L4 | 30 min | Rollback, backup/restore, merge de estado |


### Regras que não se quebram

- ❌ Nunca mude assinaturas de métodos da interface
- ❌ Nunca renomeie a classe de implementação
- ❌ Nunca deixe o `SandboxTests` com erro de sintaxe (quebra o build inteiro → zero)
- ❌ Nunca clique no botão de **reset** (canto sup. direito) — apaga tudo
- ✅ Métodos que implementam interface precisam ser `public`

---

## 2. Sintaxe essencial

### List

```java
List<String> l = new ArrayList<>();

l.add("a");                      // adiciona no fim
l.add(0, "x");                   // insere na posição
l.get(i);                        // lê por índice
l.set(i, "novo");                // substitui
l.size();                        // quantidade
l.isEmpty();                     // vazio?
l.contains("a");                 // tem?
l.indexOf("a");                  // posição, ou -1

l.remove(2);                     // ⚠️ remove por ÍNDICE
l.remove("a");                   // remove por valor (String)
l.remove(Integer.valueOf(5));    // ⚠️ List<Integer>: força remoção por VALOR

Collections.sort(l);             // ordena in-place (natural)
Collections.reverse(l);
Collections.swap(l, i, j);
List<String> copia = new ArrayList<>(l);   // cópia defensiva

for (String s : l) { ... }
```

**Pegadinha nº 1 do Java:** `List<Integer>` tem `remove(int index)` e `remove(Object o)`.
`lista.remove(5)` remove a **posição** 5. `lista.remove(Integer.valueOf(5))` remove o **valor** 5.

### Map — o cavalo de batalha do ICA

```java
Map<String, Integer> m = new HashMap<>();

m.put("a", 1);
m.get("a");                      // null se não existe
m.getOrDefault("a", 0);          // ⭐ evita null check
m.containsKey("a");
m.remove("a");
m.size();
m.isEmpty();

// contador de frequência ⭐⭐⭐
m.merge("a", 1, Integer::sum);   // se não existe põe 1; senão soma

// agrupar ⭐⭐⭐
Map<String, List<String>> grupos = new HashMap<>();
grupos.computeIfAbsent("cat", k -> new ArrayList<>()).add("item");

// iterar
for (Map.Entry<String, Integer> e : m.entrySet()) {
    String k = e.getKey();
    int v = e.getValue();
}
for (String k : m.keySet()) { ... }
for (int v : m.values()) { ... }
```

`m.size()` = número de **chaves**, não de elementos totais. Se usar Map como multiset, mantenha um contador separado.

### TreeMap — quando precisa de ordem

```java
TreeMap<String, Integer> t = new TreeMap<>();   // chaves sempre ordenadas

t.firstKey();  t.lastKey();
t.firstEntry(); t.lastEntry();
t.floorKey("m");        // maior chave <= "m"
t.ceilingKey("m");      // menor chave >= "m"
t.headMap("m");         // todas < "m"
t.tailMap("m");         // todas >= "m"
t.subMap("a", "m");     // faixa

for (Map.Entry<String,Integer> e : t.entrySet()) { ... }  // em ordem
```

Use quando o enunciado pedir faixa, prefixo, "maior que", ou ordem natural. `put`/`get`/`remove` em O(log n).

### Set

```java
Set<String> s = new HashSet<>();     // sem ordem
Set<String> t = new TreeSet<>();     // ordenado
Set<String> l = new LinkedHashSet<>(); // ordem de inserção

s.add("a");        // false se já existia
s.contains("a");
s.remove("a");
s.addAll(outro);   // união
s.retainAll(outro); // interseção
s.removeAll(outro); // diferença
```

### Optional

```java
// PRODUZINDO (é o que você faz)
return Optional.empty();          // sem valor
return Optional.of(x);            // com valor (x não pode ser null)
return Optional.ofNullable(x);    // empty se x for null

// CONSUMINDO (é o que os testes fazem)
op.isPresent();  op.isEmpty();
op.get();
op.orElse(0);
op.orElseGet(() -> calcular());
op.ifPresent(v -> System.out.println(v));
```

### String

```java
s.length();  s.charAt(i);  s.isEmpty();  s.isBlank();
s.substring(2);  s.substring(2, 5);
s.indexOf("x");  s.contains("x");
s.startsWith("pre");  s.endsWith("fim");   // ⭐ busca por prefixo
s.split(",");        // vira String[]
s.trim();  s.toLowerCase();  s.toUpperCase();
s.equals(outro);     // ⚠️ NUNCA use == pra comparar String
s.compareTo(outro);  // <0, 0, >0
String.join(", ", lista);
String.format("%s tem %d", nome, qtd);
```

---

## 3. Comparator — o que mais aparece nos níveis 2 e 3

O padrão *"retorne os top N ordenados por X decrescente, desempatando por nome"* cai em quase toda prova.

### Formas de criar

```java
// por campo (getter)
Comparator.comparing(Item::getNome)               // objeto (String, etc)
Comparator.comparingInt(Item::getQtd)             // int — evita boxing
Comparator.comparingLong(Item::getTimestamp)
Comparator.comparingDouble(Item::getPreco)

// inverter
Comparator.comparingInt(Item::getQtd).reversed()

// desempate ⭐
Comparator.comparingInt(Item::getQtd).reversed()
          .thenComparing(Item::getNome)

// lambda direto
(a, b) -> a.getQtd() - b.getQtd()                 // crescente
(a, b) -> b.getQtd() - a.getQtd()                 // decrescente

// ordem natural
Comparator.naturalOrder()
Comparator.reverseOrder()
```

### Aplicando

```java
lista.sort(comparator);                       // in-place
Collections.sort(lista, comparator);          // idem
lista.sort(null);                             // ordem natural
```

### Ordenando entradas de um Map ⭐⭐⭐

Esse bloco resolve "top N por frequência, desempate alfabético":

```java
Map<String, Integer> contagem = ...;

List<Map.Entry<String, Integer>> entradas = new ArrayList<>(contagem.entrySet());

entradas.sort(
    Map.Entry.<String,Integer>comparingByValue().reversed()
        .thenComparing(Map.Entry.comparingByKey())
);

List<String> topN = new ArrayList<>();
for (int i = 0; i < Math.min(n, entradas.size()); i++) {
    topN.add(entradas.get(i).getKey());
}
```

Se o `Map.Entry.<String,Integer>comparingByValue()` confundir, a versão explícita faz o mesmo:

```java
entradas.sort((a, b) -> {
    int cmp = b.getValue() - a.getValue();      // valor decrescente
    if (cmp != 0) return cmp;
    return a.getKey().compareTo(b.getKey());    // desempate: chave crescente
});
```



### A regra do sinal

Um comparator devolve `int`:
- **negativo** → `a` vem antes de `b`
- **zero** → empate
- **positivo** → `a` vem depois de `b`

Crescente: `a - b`. Decrescente: `b - a`.
⚠️ `a - b` pode estourar com valores muito grandes. Em prova real não acontece, mas `Integer.compare(a, b)` é a versão segura.

---

## 4. Padrões recorrentes

### Contador de frequência
```java
Map<String, Integer> freq = new HashMap<>();
for (String s : palavras) {
    freq.merge(s, 1, Integer::sum);
}
```

### Agrupar por chave
```java
Map<String, List<Pessoa>> porCidade = new HashMap<>();
for (Pessoa p : pessoas) {
    porCidade.computeIfAbsent(p.getCidade(), k -> new ArrayList<>()).add(p);
}
```

### Multiset ordenado (valor → quantidade)
```java
TreeMap<Integer, Integer> counts = new TreeMap<>();
int size = 0;

void add(int v) {
    counts.merge(v, 1, Integer::sum);
    size++;
}

boolean delete(int v) {
    Integer c = counts.get(v);
    if (c == null) return false;
    if (c == 1) counts.remove(v);      // ⚠️ chave zerada TEM que sair
    else counts.put(v, c - 1);
    size--;
    return true;
}
```

### Busca por prefixo
```java
List<String> resultado = new ArrayList<>();
for (Map.Entry<String, X> e : mapa.entrySet()) {
    if (e.getKey().startsWith(prefixo)) {
        resultado.add(e.getKey());
    }
}
```

### TTL / expiração (padrão do Level 3)
```java
class Registro {
    String valor;
    long criadoEm;
    Long expiraEm;   // null = nunca expira

    boolean vivoEm(long t) {
        return expiraEm == null || t < expiraEm;
    }
}

// no get:
Registro r = mapa.get(chave);
if (r == null || !r.vivoEm(agora)) return Optional.empty();
return Optional.of(r.valor);
```



### Histórico de versões (padrão do Level 4)
```java
class Entrada {
    long timestamp;
    String valor;
}

Map<String, List<Entrada>> historico = new HashMap<>();

void set(String k, String v, long t) {
    historico.computeIfAbsent(k, x -> new ArrayList<>())
             .add(new Entrada(t, v));
}

// versão vigente no instante t: última entrada com timestamp <= t
```

**Índice da mediana (menor dos dois centrais):** `(n - 1) / 2` — funciona para par e ímpar.

