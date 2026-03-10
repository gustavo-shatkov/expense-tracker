import java.time.LocalDate;

public record Expense(String id, String description, double amount, String category, LocalDate date){

}
