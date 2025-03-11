# Advanced-Data-Structure-Analysis

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

Para a verificação do tempo de execução utilizamos a _JMH_ (_Java Microbenchmarking Harness_) que é uma ferramenta especializada para realizar _microbenchmarks_ em Java. Com o auxílio dela poderemos medir com precisão o desempenho das operações citadas acima. É importante salientar que visando um tempo de execução preciso foram feitos 50 testes, e o dado final analisado é a mediana desses testes. Para o cálculo de memória utilizada em cada operação utilizaremos a _API Runtime_, uma interface para interagir com o ambiente de execução da _JVM_, na qual permite monitorar e gerenciar recursos do sistema, como uso de memória.

As cargas são geradas utilizando a biblioteca _random_ do _python_ com uma _seed_ (número qualquer que escolhemos), o _script_ gera as cargas na pasta data em um arquivo .csv de apenas uma coluna de dados sintéticos. São gerados 4 arquivos, um de dados ordenados e com repetições, ordenados sem repetições, desordenados com repetição e sem repetição. Cada arquivo tem 10⁶ dados sintéticos.

Os testes de estresse da máquina foram feitos no ambiente do _IntelliJ_ instalado em uma máquina do LCC da UFCG usando diferentes cargas na estrutura de _SplayTree_, variando 10⁵ e 10⁷, observando os erros de memória e o tempo de execução usando a biblioteca _NanoTime_, que embora seja menos precisa que a _JMH_, seja mais simples de utilizar para esse teste. Portanto, foi chegado na conclusão de que 10⁶ possibilitou um bom tempo de execução com uma boa quantidade de elementos.

## Estruturas Analisadas

### BTree
.
.
.

### SplayTree
.
.
.


### TreeMap
.
.
.


## Créditos

A implementação da SplayTree utilizada neste projeto foi baseada no repositório desenvolvido por Pedro Oliveira "cpdomina":

https://github.com/cpdomina/SplayTree/tree/master
