## GitHub Page

Este [_GitHub Page_](https://tamiressantiago.github.io/Analise-de-Estruturas-de-Dados-Avancadas/) contém a metodologia utilizada, uma explicação detalhada das três estruturas de árvore escolhidas (BTree, SplayTree e TreeMap), além da análise dos resultados obtidos a partir dos testes realizados. O conteúdo inclui uma visão teórica e prática de cada uma dessas estruturas, abordando suas características. Também são apresentados os experimentos e comparações de desempenho, proporcionando uma compreensão profunda de como essas árvores se comportam em diferentes cenários e qual é sua eficiência em termos de tempo e espaço.


## Como executar os testes na sua máquina:
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
## Estrutura do Repositório

- `docs/`: Contém os arquivos referentes ao GitHub Pages, incluindo HTML e CSS.
- `results/`: Armazena os resultados dos testes de benchmark, disponíveis em formato de tabela `.csv` e em `.pdf`.
- `src/`: Contém os códigos utilizados neste projeto.  
  - Na pasta `python/`, encontra-se o script de geração de cargas.  
  - Na pasta `main/` do Java, estão as implementações das estruturas BTree e SplayTree, além dos testes de benchmark.
- `pom.xml`: Responsável por configurar o JMH através do Maven.
