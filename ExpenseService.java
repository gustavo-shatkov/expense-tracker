import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository){
        this.repository = repository;
    }

    public void addExpense(String description, double amount, String category){
        if (amount <= 0){
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if (description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("A descrição não pode estar vazia.");
        }

        String id = UUID.randomUUID().toString();
        Expense expense = new Expense(id, description, amount, category, LocalDate.now());
        repository.save(expense);
    }
    public List<Expense> getAllExpenses(){
        return repository.findAll();
    }

    public double calculateTotalExpenses(){
        return repository.findAll().stream()
               .mapToDouble(Expense :: amount)
               .sum();
    }
}
