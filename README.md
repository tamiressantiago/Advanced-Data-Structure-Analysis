# Análise de Estruturas de Dados Avançadas
**Grupo 5 - Segunda entrega do projeto de EDA/LEDA**

Anthony Willy Miranda Pereira - 123210127

Maria Eduarda Viana Cordeiro dos Santos - 123210087

Matheus Adiel Medeiros Lima de Oliveira - 123210171

Natan Hugo Carvalho Evangelista - 123210202

Tamires Santiago Oliveira - 123210205

## Processo da Segunda Entrega

Todo o processo da segunda entrega foi realizada em conjunto, com exceção do estudo individual das estruturas, os pontos realizados foram:
* Estudamos profundamente cada estrutura, entendendo suas propriedades e operações;
* Pesquisamos implementações das estruturas e as testamos para verificar se a implementação estava correta;
* Implementamos o script gerador das cargas em python que gera sempre a mesma carga, para que não seja necessário salvar as cargas no repositório ;
* Aplicamos um teste de estresse da máquina usando apenas a estrutura SplayTree, a partir disso definimos uma carga máxima de 10⁷;
* Aprendemos a utilizar a ferramenta Java Microbenchmark Harness (JMH) junto com o gerenciador de dependências Maven;
* Aplicamos a carga definida em todas as estruturas e percebemos que não era adequada para as demais, então a carga foi reduzida para 5x10⁵;
* Implementamos os testes iniciais de benchmarking [aqui](https://github.com/tamiressantiago/Advanced-Data-Structure-Analysis/blob/main/src/main/java/org/sample/MyBenchmark.java);
* Geramos os testes de remoção, inserção e busca de cada estrutura separadamente [aqui](https://github.com/tamiressantiago/Advanced-Data-Structure-Analysis/tree/main/results).

## Definição para a Próxima Entrega
* Refinamento dos testes, com a tentativa de melhora na precisão dos testes, com aumento de cargas e operações mais específicas;
* Formatar a saída dos resultados dos benchmarks, criando uma tabela para criar gráficos no PowerBI;
* Análise dos resultados, e comparação entre as estruturas. 


## Como usar
1. Verifique primeiramente se você tem o Maven e o java na sua máquina para executar os benchmarks. Para isso, execute os seguintes comandos no terminal:
```
mvn -version
```
```
java -version 
```
2. Agora, na raiz do projeto, execute o script em python para gerar os dados sintéticos:
```
python3 src/python/scripts_data.py
```
3. Ainda na pasta raíz, execute o seguinte comando para compilar e gerar o JAR executável do benchmark:
```
mvn clean verify
```
4. Após compilar com sucesso, execute o seguinte comando para iniciar o benchmark:
``` 
java -jar target/benchmarks.jar
```
5. Caso deseje verificar também a alocação de memória do benchmark, execute:
```
java -jar target/benchmarks.jar -prof gc
``` 

## Metodologia Geral
Considerando as diversas estruturas de dados avançadas que não são abordadas na disciplina de Estrutura de Dados na Universidade Federal de Campina Grande (UFCG) o objetivo deste projeto é analisar, comparar e catalogar estas estruturas em Java:

* _B-Tree_

* _TreeMap_

* _Splay Tree_

Para a análise, comparamos os seguintes aspectos: 

* Tempo de leitura/escrita;
* Velocidade de acesso de um elemento;
* Tempo de inserção e remoção de um elemento;
* Memória utilizada. 

Para a verificação do tempo de execução e memória utilizada utilizamos a _JMH_ (_Java Microbenchmarking Harness_) que é uma ferramenta especializada para realizar _microbenchmarks_ em Java. Com o auxílio dela poderemos medir com precisão o desempenho das operações citadas acima. Os testes das operações do benchmark são executados da seguinte forma: a cada iteração o JMH executa o método do benchmark até quantas vezes ele consegue em um determinado número de tempo(definido para o projeto como 5 segundos), e é feita uma média a partir de quanto tempo demorou para o código terminar sua operação nessa quantidade de segundos, caso a operação seja pesada demais para ser executada no tempo definido, o código continuará sendo executado até que a operação seja feita e irá entregar o tempo dessa única execução. Para garantir que mesmo as operações pesadas demais para serem executadas em apenas 5 segundos sejam testadas um número significativo de vezes para uma maior precisão, foi definido um número mínimo de 50 iterações. Por fim, é feito uma média do tempo de execução e memória dessas 50 iterações(ou mais).

As cargas são geradas utilizando a biblioteca _random_ do _python_ com uma _seed_ (número qualquer que escolhemos), o _script_ gera as cargas na pasta data em um arquivo .csv de apenas uma coluna de dados sintéticos. São gerados 4 arquivos, um de dados ordenados e com repetições, ordenados sem repetições, desordenados com repetição e sem repetição. Cada arquivo tem 10⁶ dados sintéticos. 

Os testes de estresse da máquina foram feitos no ambiente do _IntelliJ_ instalado em uma máquina do LCC da UFCG usando diferentes cargas na estrutura de _SplayTree_, variando 10⁵ e 10⁷, observando os erros de memória e o tempo de execução usando a biblioteca _NanoTime_, que embora seja menos precisa que a _JMH_, seja mais simples de utilizar para esse teste. Portanto, foi chegado na conclusão de que para as três estruturas 10⁶ possibilitou um bom tempo de execução com uma boa quantidade de elementos.

## Árvores

Antes de focarmos nas árvores escolhidas para a análise, devemos entender a importância dessas estruturas para computação. Em programação, árvores se referem à estruturas hierárquicas para armazenamento de dados. Diferentemente das listas que possuem acesso sequencial, as árvores são organizadas de forma ramificada, composta por nós que são conectados por arestas, por isso são denominadas estruturas não-lineares. Cada nó pode ter um ou mais filhos, mas apenas um pai, com exceção da raiz da árvore, que não tem pai. No mundo real esta estrutura é bastante utilizada quando temos uma relação de nível entre os elementos como por exemplo: estruturas de pastas de um sistema operacional, interfaces gráficas e bancos de dados.

![Arvores](https://d19jt43wbam3a2.cloudfront.net/imagens/posts/2019/arvores-estrutura-de-dados/arvores-04.png)

## Estruturas Analisadas

### BTree

A _B-Tree_ é uma árvore auto-balanceada, projetada para gerenciar grandes volumes de dados com eficiência, especialmente em sistemas de armazenamento. Ela evoluiu a partir das árvores de busca binária (BSTs) e das árvores balanceadas (como AVL e _Red-Black Trees_), combinando a organização ordenada das BSTs com o nivelamento de altura das árvores balanceadas. Diferente dessas estruturas, a _B-Tree_ expandiu seu funcionamento ao permitir que cada nó contenha múltiplas chaves e múltiplos filhos, tornando-a ideal para aplicações que exigem operações rápidas de busca, inserção e remoção em grandes conjuntos de dados.

**PROPRIEDADES:**
 * A _BTree_ possui um valor t chamado de ordem da árvore, que define a quantidade mínima de chaves que um nó pode possuir, sendo esta t-1 chaves.
 * Na _BTree_, cada nó possui um atributo booleano que verifica se ele é uma folha ou não. Caso ele seja uma folha, não possuirá filhos. Caso não seja, seu número de filhos será determinado pela ordem da árvore, onde seu número mínimo de filhos será t e o máximo t*2, com exceção da raiz que só poderá ter no mínimo 2 filhos.
 * Se um nó excede o número máximo de chaves, ele é dividido (_split_).
 * Se um nó fica com menos chaves do que é permitido, ele pode ser fundido (_merge_) com outro.


**OPERAÇÕES:**
  
***⭢ Inserção:***\
  A inserção de uma chave em uma _B-Tree_ é feita de forma a manter a estrutura balanceada, o que pode envolver a divisão de nós e a promoção de chaves para o nó pai, se necessário.
   - Comece com a busca pela posição adequada para a chave:
   - Se a árvore estiver vazia, aloque um nó raiz e insira a chave.
   - Se não estiver vazia, encontre o nó folha onde a chave deve ser inserida:
   - Se o nó não estiver cheio, insira a chave nesse nó e a árvore permanece balanceada.
   - Se o nó estiver cheio, o nó é dividido ao meio:
     1. A chave do meio é promovida para o nó pai;
     2. O nó é dividido em dois nós filhos, cada um com metade das chaves;
     3. Se o nó pai também estiver cheio, o processo de divisão pode se propagar recursivamente até a raiz.
     4. Caso a divisão atinja a raiz, uma nova raiz é criada, aumentando a altura da árvore.

***⭢ Remoção:***
  - Comece com a busca pela posição da chave que será removida.
  - Quando encontrar o nó onde a chave que deve ser removida se encontra:
    - _Se o nó for uma folha:_
       1. Se não ferir a propriedade de número mínimo de chaves, basta remover a chave.
       2. Se violar, o nó pegará uma chave do nó irmão imediato da esquerda para a direita, para que se mantenha com a quantidade mínima de chaves.
       3. Se os nós irmãos também tiverem a quantidade mínima de chaves, os nós serão fundidos.
    - _Se o nó não for uma folha:_
       1. Se o filho à sua esquerda tiver mais que o número mínimo de chaves, a chave a ser excluída será substituída pelo seu antecessor imediato.
       2. Se o filho à sua direita tiver mais que o número mínimo de chaves, a chave a ser excluída será substituída pelo seu sucessor imediato.
       3. Se ambos os filhos tiverem o número mínimo de chaves, os nós filhos serão fundidos.
       4. Caso os filhos e os irmãos estiverem com o número mínimo de chaves, fundimos o nó com o irmão e com alguma chave do pai, dessa forma a altura da árvore diminui.


 ***⭢ Busca:***\
    Procurar um elemento em uma _BTree_ é uma forma generalizada da busca em uma BST.
 - A busca começa no nó raiz da árvore percorrendo as chaves ordenadas do nó. Se a chave procurada for encontrada no nó atual, a busca termina com sucesso. Se a chave não for encontrada, determinar qual dos filhos deve ser explorado:
    1. Se a chave for menor que uma chave existente, a busca segue para o filho esquerdo correspondente.
    2. Se for maior, a busca segue para o filho direito apropriado.
    3. Se não encontrar, continuamos descendo na árvore até encontrar a chave ou chegar a uma folha. Se um nó folha for alcançado sem encontrar a chave, significa que ela não está presente na árvore.



**Tabela de custo da _BTree_**

  | Algoritmo   | Caso Médio | Pior Caso |
  |------------|----------------|--------------|
  | Espaço  | O(n)           | O(n)       |
  | Busca     | O(log n)            | O(log n)     |
  | Inserção    | O(log n)           | O(log n)     |
  | Remoção    | O(log n)            | O(log n)     |

### SplayTree
A estrutura de dados avançada _splay tree_, ou árvore _splay_, é uma árvore binária de busca autoajustável, com o adicional de tornar os elementos acessados recentemente, fáceis de acessar novamente. Todas as suas operações básicas, como remoção, inserção e busca, são baseadas em colocar o elemento envolvido nessa operação na raiz, através de rotações (operação _splay_). 
A principal operação da _splay tree_ se baseia em uma sequência de rotações, onde procura deixar junto a raiz os elementos mais usados e recentes. Por consequência, os elementos mais inativos estarão distantes da raiz. Essas rotações podem ser de dois tipos: Rotação simples ou rotação dupla.

A principal vantagem da _splay tree_ está diretamente ligado com o fato dela ser autoajustável. Pois, na medida em que os elementos mais utilizados se movem para próximo da raiz, eles são acessados mais rapidamente e facilmente.

Entretanto, a principal desvantagem é que a altura da splay tree pode ser linear. Onde os nós da árvore são acessados sequencialmente em ordem. Isto deixa a árvore totalmente desbalanceada. Outra desvantagem significativa é que a representação das árvores de splay pode mudar quando nelas são realizadas as operações básicas, pois envolvem reestruturação dinâmica. Ou seja, torna complicado o uso em um ambiente multi-threaded. Para conseguir contornar essa problemática, seria necessário uma adaptação nessa estrutura de dados.

* OPERAÇÕES:

  Na rotação simples temos a rotação Zig (para direita) e a rotação Zag (para esquerda). Já na rotação dupla, temos a rotação Zig-zig (duas rotações para a direita), Zag-zag (duas rotações para a esquerda) e a Zig-zag ou Zag-zig (Rotação direita e esquerda ou esquerda e direita, respectivamente).

  * Na rotação Zig, o filho direito do elemento B, ficará o filho esquerdo do elemento A, que era pai de B. Com a mesma lógica, na rotação Zag, o filho esquerdo do elemento A, ficará o filho direito do elemento B, que era pai de A.
  
    <img src="https://upload.wikimedia.org/wikipedia/commons/5/5b/Zig_e_zag.png" alt="Rotação_zig/zag" height="200">

  * Já na rotação dupla Zig-zig, é realizado duas rotações simples do tipo zig, onde, neste exemplo, para fazer o zig-zig de C, primeiro é realizado o zig no pai de C (que é o B) e posteriormente é feito o zig de C. Da forma análoga ocorre a operação zag-zag, entretanto com duas rotações simples do tipo zag.

    <img src="https://upload.wikimedia.org/wikipedia/commons/0/01/Zig-zig_e_zag-zag.png" alt="Rotação_zig-zig/zag-zag" height="200">

  * Por fim, na rotação dupla Zig-zag, primeiro realiza o zig de C com o pai de C (que é o B), e logo após aplica o zag de C com o avô de C (que é o A). Já o Zag-zig troca apenas a ordem das operações simples, sendo primeiramente realizado o zag e posteriormente o zig.

    <img src="https://upload.wikimedia.org/wikipedia/commons/e/ea/Zig-zag2.png" alt="Rotação_zig-zag" height="200">

    <img src="https://upload.wikimedia.org/wikipedia/commons/b/b1/Zag-zig.png" alt="Rotação_zag-zig" height="200">


* **Tabela de custo da _SplayTree_**

  | Algoritmo   | Caso Médio | Pior Caso |
  |------------|----------------|--------------|
  | Espaço  | O(n)           | O(n)       |
  | Busca     | O(log n)            | O(log n) (amortizado)     |
  | Inserção    | O(log n)           | O(log n) (amortizado)    |
  | Remoção    | O(log n)            | O(log n) (amortizado)    |

### TreeMap

O TreeMap é uma estrutura que armazena dados no formato chave-valor, ou seja, é possível fazer o acesso de um elemento por meio de uma chave única, por exemplo, o acesso a um aluno por meio de seu CPF. No caso do TreeMap, ele implementa a interface SortedMap, interface essa que herda da classe Map(classe que mapeia os valores em chaves). A interface SortedMap tem como intuito ordenar as chaves atribuídas a determinados valores(por ordem crescente, alfabética ou qualquer ordem definida previamente). 

Por exemplo, se fosse necessário fazer um sistema de agenda de eventos, seria necessário acessar os eventos pelas datas, e ter como recuperá-los de acordo com as datas mais recentes ou mais antigas pode ser muito útil para o sistema. Para saber qual o show mais próximo? O TreeMap pode ajudar, atribuindo uma data como chave a um objeto do tipo Evento como valor. É possível notar que por meio dessa ordenação, o TreeMap pode ser utilizado para algumas políticas de _Cache Eviction_.

O Tree Map está disponível para uso por meio da biblioteca padrão do java java.Util, mas para fins didáticos, é necessário saber o funcionamento por trás dele. Diferentemente do HashMap que utiliza estratégias de hashing, a qual o valor da chave é associado a um endereço na memória a qual podemos buscar por meio de operações O(1), o TreeMap é uma estrutura que utiliza a Árvore Rubro-Negra para realizar suas operações de inserção, busca, contains e remoção(entre outras).

A Árvore Rubro Negra é um tipo específico de árvore binária que se mantém balanceada por meio de regras simples. As árvores binárias são estruturas super eficientes, realizando operações em em tempo de O(log(n)) quando estão balanceadas. Para isso, são utilizadas muitas estratégias de balanceamento, todas regidas por determinadas regras.
Nesse cenário,  a Árvore Rubro Negra é uma estrutura de dados capaz de gerar árvores binárias balanceadas, caracterizada por nós de cor preta e vermelha, as cores de cada nó são utilizadas para uma melhor representação da lógica utilizada para as operações nas árvores.

* PROPRIEDADES
 1. Um nó é vermelho ou preto.
 2. A raiz da árvore é sempre preta.
 3. Todas as folhas(nós da extremidade da árvore, também chamados de nil) são pretas, e nulas.
 4. Ambos os filhos de todos os nós vermelhos são pretos.
 5. Todo caminho de um dado nó para qualquer uma de suas folhas descendentes sempre contém o mesmo número de nós pretos.

Os elementos ficam ordenados de uma forma similar a uma árvore binária comum, mas sempre que um nó for inserido ou deletado, a árvore irá se reorganizar de forma que os axiomas sejam respeitados, essa reorganização é o que garante seu balanceamento, com nós se movendo para direita ou para a esquerda, e trocando de cores de acordo com o que satisfaça as regras pré-estabelecidas da árvore, e que não desordene os valores da árvore de forma que um nó contendo um valor x, tenha um filho y a sua direita que é maior do que ele.

Compreendendo a lógica do funcionamento da árvore rubro negra, sempre que um valor é inserido, ele passa por todo o processo de reorganização dentro da árvore, e a árvore ordena de acordo com as chaves dos elementos. Dessa forma, a chave de um elemento de uma TreeMap não está definindo seu endereço direto da memória, mas sim sendo ordenada dentro de uma árvore rubro negra. O acesso a elementos por meio do valor bruto(e não da chave) pode até ser possível em alguns casos, mas quebra a lógica da estrutura e de sua forma de utilização.

Portanto, acerca da complexidade da TreeMap, deve ser levado em consideração que os objetos estão sendo acessados por meio de suas chaves. 
* **Tabela de custo da _TreeMap_**

  | Algoritmo   | Caso Médio | Pior Caso |
  |------------|----------------|--------------|
  | Espaço  | O(n)           | O(n)       |
  | Busca     | O(log n)            | O(log n)     |
  | Inserção    | O(log n)           | O(log n)     |
  | Remoção    | O(log n)            | O(log n)     |

##  Créditos e Referências

* A implementação da SplayTree utilizada neste projeto foi baseada no repositório desenvolvido por Pedro Oliveira "cpdomina": https://github.com/cpdomina/SplayTree/tree/master

* As imagens de exemplos utilizadas na explicação da estrutura de dados SplayTree foi retirada do site: https://pt.wikipedia.org/wiki/%C3%81rvore_splay

* Material de consulta utilizado para a TreeMap: https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html


* A implementação da BTree utilizada neste projeto foi baseada em: https://www.programiz.com/dsa/b-tree

* O estudo e a aplicação do ambiente de testes Java Microbenchmark Harness, ou JMH, foi baseado no artigo do Muhammad Asher Toqeer "TheBackEndGuy": https://thebackendguy.com/posts/performance-analysis-using-jmh/
