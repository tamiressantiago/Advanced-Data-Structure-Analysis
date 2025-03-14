# Advanced-Data-Structure-Analysis
**Grupo 5 - Segunda entrega do projeto de EDA/LEDA**

Anthony Willy Miranda Pereira - 123210127

Maria Eduarda Viana Cordeiro dos Santos - 123210087

Matheus Adiel Medeiros Lima de Oliveira - 123210171

Natan Hugo Carvalho Evangelista - 123210202

Tamires Santiago Oliveira - 123210205

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

Para a verificação do tempo de execução e memória utilizada utilizamos a _JMH_ (_Java Microbenchmarking Harness_) que é uma ferramenta especializada para realizar _microbenchmarks_ em Java. Com o auxílio dela poderemos medir com precisão o desempenho das operações citadas acima. É importante salientar que visando um tempo de execução preciso foram feitos 50 testes, e o dado final analisado é a mediana desses testes. 

As cargas são geradas utilizando a biblioteca _random_ do _python_ com uma _seed_ (número qualquer que escolhemos), o _script_ gera as cargas na pasta data em um arquivo .csv de apenas uma coluna de dados sintéticos. São gerados 4 arquivos, um de dados ordenados e com repetições, ordenados sem repetições, desordenados com repetição e sem repetição. Cada arquivo tem 10⁶ dados sintéticos.

Os testes de estresse da máquina foram feitos no ambiente do _IntelliJ_ instalado em uma máquina do LCC da UFCG usando diferentes cargas na estrutura de _SplayTree_, variando 10⁵ e 10⁷, observando os erros de memória e o tempo de execução usando a biblioteca _NanoTime_, que embora seja menos precisa que a _JMH_, seja mais simples de utilizar para esse teste. Portanto, foi chegado na conclusão de que 10⁶ possibilitou um bom tempo de execução com uma boa quantidade de elementos.

## Árvores

Antes de focarmos nas árvores escolhidas para a análise, devemos entender a importância dessas estruturas para computação. Em programação, árvores se referem à estruturas hierárquicas para armazenamento de dados. Diferentemente das listas que possuem acesso sequencial, as árvores são organizadas de forma ramificada, composta por nós que são conectados por arestas, por isso são denominadas estruturas não-lineares. Cada nó pode ter um ou mais filhos, mas apenas um pai, com exceção da raiz da árvore, que não tem pai. No mundo real esta estrutura é bastante utilizada quando temos uma relação de nível entre os elementos como por exemplo: estruturas de pastas de um sistema operacional, interfaces gráficas e bancos de dados.

![Arvores](https://d19jt43wbam3a2.cloudfront.net/imagens/posts/2019/arvores-estrutura-de-dados/arvores-04.png)

## Estruturas Analisadas

### BTree

A _B-Tree_ é uma árvore auto-balanceada, projetada para gerenciar grandes volumes de dados com eficiência, especialmente em sistemas de armazenamento. Ela evoluiu a partir das árvores de busca binária (BSTs) e das árvores balanceadas (como AVL e _Red-Black Trees_), combinando a organização ordenada das BSTs com o nivelamento de altura das árvores balanceadas. Diferente dessas estruturas, a _B-Tree_ expandiu seu funcionamento ao permitir que cada nó contenha múltiplas chaves e múltiplos filhos, tornando-a ideal para aplicações que exigem operações rápidas de busca, inserção e remoção em grandes conjuntos de dados.

* PROPRIEDADES:

  * A _BTree_ possui um valor t chamado de ordem da árvore, que define a quantidade mínima de chaves que um nó pode possuir, sendo esta t-1 chaves.
  * Na _BTree_, cada nó possui um atributo booleano que verifica se ele é uma folha ou não. Caso ele seja uma folha, não possuirá filhos. Caso não seja, seu número de filhos será determinado pela ordem da árvore, onde seu número mínimo de filhos será t e o máximo t*2, com exceção da raiz que só poderá ter no mínimo 2 filhos.
  * Se um nó excede o número máximo de chaves, ele é dividido (_split_).
  * Se um nó fica com menos chaves do que é permitido, ele pode ser fundido (_merge_) com outro.


* OPERAÇÕES:
  * Inserção:
    
    A inserção de uma chave em uma _B-Tree_ é feita de forma a manter a estrutura balanceada, o que pode envolver a divisão de nós e a promoção de chaves para o nó pai, se necessário.
    * Comece com a busca pela posição adequada para a chave.
    * Se a árvore estiver vazia, aloque um nó raiz e insira a chave.
    * Se não estiver vazia, encontre o nó folha onde a chave deve ser inserida:
        * 1- Se o nó não estiver cheio, insira a chave nesse nó e a árvore permanece balanceada.
        * 2- Se o nó estiver cheio, o nó é dividido ao meio:
           * A chave do meio é promovida para o nó pai;
           * O nó é dividido em dois nós filhos, cada um com metade das chaves;
           * Se o nó pai também estiver cheio, o processo de divisão pode se propagar recursivamente até a raiz.
           * Caso a divisão atinja a raiz, uma nova raiz é criada, aumentando a altura da árvore.

  * Remoção:
    * Comece com a busca pela posição adequada para a chave.
    * Quando encontrar o nó onde a chave deve ser removida:
       * 1- Se o nó for uma folha:
           *  Se não ferir a propriedade de número mínimo de chaves, basta remover a chave.
           *  Se violar, o nó pegará uma chave do nó irmão imediato da esquerda para a direita, para que se mantenha com a quantidade mínima de chaves.
           * Se os nós irmãos também tiverem a quantidade mínima de chaves, os nós serão fundidos.
       * 2-  Se o nó não for uma folha:
           *  Se o filho à sua esquerda tiver mais que o número mínimo de chaves, a chave a ser excluída será substituída pelo seu antecessor imediato.
           *  Se o filho à sua direita tiver mais que o número mínimo de chaves, a chave a ser excluída será substituída pelo seu sucessor imediato.
           *  Se ambos os filhos tiverem o número mínimo de chaves, os nós filhos serão fundidos.
           *  Caso os filhos e os irmãos estiverem com o número mínimo de chaves, fundimos o nó com o irmão e com alguma chave do pai, dessa forma a altura da árvore diminui.


  * Busca:
    
    Procurar um elemento em uma _BTree_ é uma forma generalizada da busca em uma BST.
     * A busca começa no nó raiz da árvore percorrendo as chaves ordenadas do nó. Se a chave procurada for encontrada no nó atual, a busca termina com sucesso. Se a chave não for encontrada, determinar qual dos filhos deve ser explorado:
        *  Se a chave for menor que uma chave existente, a busca segue para o filho esquerdo correspondente.
        *  Se for maior, a busca segue para o filho direito apropriado.
        *  Se não encontrar, continuamos descendo na árvore até encontrar a chave ou chegar a uma folha. Se um nó folha for alcançado sem encontrar a chave, significa que ela não está presente na árvore.



* **Tabela de custo da _BTree_**

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
.
.
.

## Como usar

1. Em "/src/python" execute o script em python para gerar os dados sintéticos.
2. (Opcional) Altere as cargas no script python ou os números de testes em "/src/main/java/org/sample/MyBenchmark.java"
3. Na pasta raíz, execute o comando $ mvn clean verify
4. Na mesma pasta, execute o comando $ java -jar target/benchmarks.jar
5. (Opcional) Adicione "-prof gc" ao comando acima para verificar a alocação de memória também 

## Créditos

A implementação da SplayTree utilizada neste projeto foi baseada no repositório desenvolvido por Pedro Oliveira "cpdomina": https://github.com/cpdomina/SplayTree/tree/master

As imagens de exemplos utilizadas na explicação da estrutura de dados SplayTree foi retirada do site: https://pt.wikipedia.org/wiki/%C3%81rvore_splay

A implementação da BTree utilizada neste projeto foi baseada em: https://www.programiz.com/dsa/b-tree

O estudo e a aplicação do ambiente de testes Java Microbenchmark Harness, ou JMH, foi baseado no artigo do Muhammad Asher Toqeer "TheBackEndGuy": https://thebackendguy.com/posts/performance-analysis-using-jmh/
