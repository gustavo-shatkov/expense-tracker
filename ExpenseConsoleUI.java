import java.util.Scanner;

public class ExpenseConsoleUI {
    private final ExpenseService service;
    private final Scanner scanner;

    public ExpenseConsoleUI(ExpenseService service){
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice){ 
                case 1 -> promptAddExpense();
                case 2 -> listExpense();
                case 3 -> showTotal();
                case 4 -> running = false;
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Saindo do Expense Tracker. Até logo!");
    }
    
    private void printMenu(){
        System.out.println("\n--- Expense Tracker ---");
        System.out.println("1. Adicionar Despesa");
        System.out.println("2. Listar Despesa");
        System.out.println("3. Ver Total Gasto");
        System.out.println("4. Sair");
        System.out.println("Escolha uma opção: ");
    }

    private int getUserChoice(){
        try{
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void promptAddExpense(){
        System.out.println("Descrição: ");
        String description = scanner.nextLine();

        System.out.println("Valor (ex: 50.50): ");
        double amount;
        try{
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Erro: Digite um valor numérico válido.");
            return;
        }

        System.out.println("Categoria (ex: Alimentação, Transporte): ");
        String category = scanner.nextLine();

        try{ 
            service.addExpense(description, amount, category);
            System.out.println("Despesa adicionada com sucesso!");
        } catch (IllegalAccessException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listExpense(){
        var expenses = service.getAllExpenses();
        if (expenses.isEmpty()){
            System.out.println("Nenhuma despesa registrada.");
            return;
        }
        System.out.println("\n--- Suas Despesas ---");
        for (Expense e : expenses){
            System.out.printf("[%s] %s - R$ %.2f (%s)\n", e.date(), e.description(), e.amount(), e.category());
        }
    }
    
    private void showTotal(){
        double total = service.calculateTotalExpenses();
        System.out.printf("\n Total Gasto: R$ %.2f\n", total);
    }
}
