import org.openjdk.jmh.annotations.Benchmark;
import advanced_data_structure.io.SplayTreeFileReader;

public class SplayTreeBenchmark {

    @Benchmark
    public void benchmarkInsertion(SplayTreeBenchmarkState state) {
        for (Integer value : state.insertionData) {
            state.tree.insert(value);
        }
    }
}
