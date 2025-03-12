package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneralFileReader {
    public static List<Integer> readValues(String fileName) {
        List<Integer> values = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    int value = Integer.parseInt(line.trim());
                    values.add(value);
                } catch (NumberFormatException e) {
                    System.out.println("Ignorando entrada inválida: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return values;
    }
}
