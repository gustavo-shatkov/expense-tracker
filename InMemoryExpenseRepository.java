import java.util.ArrayList;
import java.util.List;

public class InMemoryExpenseRepository implements ExpenseRepository {
    private final List<Expense> expenses = new ArrayList<>();

    @Override
    public void save (Expense expense){
        expenses.add(expense);
    }

    @Override
    public List<Expense> findAll(){
        return new ArrayList<>(expenses);
    }
}
