import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvExpenseRepository implements ExpenseRepository {
    private static final String FILE_PATH = "expenses.csv";
    private static final String DELIMITER = ",";

    public CsvExpenseRepository(){
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
        System.err.println("Erro ao criar o arquivo de dados: " + e.getMessage());
        }
    }     

    @Override
    public void save(Expense expense) {
        Path path = Paths.get(FILE_PATH);
        // Abre o arquivo no modo APPEND (adicionar ao final)
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            String line = String.join(DELIMITER,
                    expense.id(),
                    expense.description(),
                    String.valueOf(expense.amount()),
                    expense.category(),
                    expense.date().toString()
            );
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar a despesa: " + e.getMessage());
        }
    }

    @Override
    public List<Expense> findAll() {
        List<Expense> expenses = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Ignora linhas em branco
                
                String[] parts = line.split(DELIMITER);
                if (parts.length == 5) {
                    Expense expense = new Expense(
                            parts[0],                                 // id
                            parts[1],                                 // description
                            Double.parseDouble(parts[2]),             // amount
                            parts[3],                                 // category
                            LocalDate.parse(parts[4])                 // date
                    );
                    expenses.add(expense);
                }
            }
        } catch (IOException | NumberFormatException | java.time.format.DateTimeParseException e) {
            System.err.println("Erro ao ler as despesas: " + e.getMessage());
        }
        
        return expenses;
    }
}
