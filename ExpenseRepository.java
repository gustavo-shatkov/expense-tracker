import java.util.List;

public interface ExpenseRepository{
    void save(Expense expense);
    List<Expense> findAll();
}
