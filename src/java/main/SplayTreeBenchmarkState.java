import org.openjdk.jmh.annotations.*;
import advanced_data_structure.io.SplayTreeFileReader;
import java.util.List;

@State(Scope.Benchmark)
public class SplayTreeBenchmarkState {
    public List<Integer> insertionData;
    public SplayTree<Integer> tree;

    @Setup(Level.Trial)
    public void setup() {
        // Carrega os dados do arquivo apenas uma vez
        insertionData = SplayTreeFileReader.readValues("dados_sem_repeticoes_desordenados.csv");
        // Inicializa a árvore (vazia ou com dados iniciais, se necessário)
        tree = new SplayTree<>();
    }
}
