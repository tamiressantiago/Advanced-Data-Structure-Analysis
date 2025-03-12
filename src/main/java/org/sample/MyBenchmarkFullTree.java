package org.sample;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import reader.GeneralFileReader;
import structure.SplayTree;

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MyBenchmarkFullTree {

    private SplayTree<Integer> tree;
    private List<Integer> values;
    private int extraValue;   // Valor que não está presente na árvore
    private int searchValue;  // Valor presente na árvore para a operação de busca

    /**
     * Preenche a árvore com os valores lidos do arquivo. 
     * O setup é feito no nível de Invocation para que cada benchmark 
     * inicie com a árvore completa e sem alterações de execuções anteriores.
     */
    @Setup(Level.Invocation)
    public void setup() {
        values = GeneralFileReader.readValues("data/dados_com_repeticoes_desordenados.txt");
        tree = new SplayTree<>();
        for (int value : values) {
            tree.insert(value);
        }
        // Define um valor extra (supondo que os valores são inteiros positivos)
        extraValue = values.stream().max(Integer::compareTo).orElse(0) + 1;
        // Escolhe um valor presente na árvore para busca e remoção
        searchValue = values.get(values.size() / 2);
    }

    /**
     * Benchmark que adiciona um elemento novo à árvore.
     */
    @Benchmark
    public void benchmarkAdd() {
        tree.insert(extraValue);
    }

    /**
     * Benchmark que realiza a busca de um elemento existente na árvore.
     * Usa o método 'contains', que internamente utiliza 'find'.
     */
    @Benchmark
    public void benchmarkSearch() {
        tree.contains(searchValue);
    }

    /**
     * Benchmark que remove um elemento existente da árvore.
     */
    @Benchmark
    public void benchmarkRemove() {
        tree.remove(searchValue);
    }
}