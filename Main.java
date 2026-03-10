public class Main {
    public static void main(String[] args){
        ExpenseRepository repository = new InMemoryExpenseRepository();
        ExpenseService service = new ExpenseService(repository);
        ExpenseConsoleUI ui = new ExpenseConsoleUI(service);

        ui.start();
        
    }
    
}
