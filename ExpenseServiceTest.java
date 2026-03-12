import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ExpenseServiceTest {

    private ExpenseRepository fakeRepository;
    private ExpenseService expenseService;


    @BeforeEach
    void setUp(){

        fakeRepository =  new InMemoryExpenseRepository();
        expenseService = new ExpenseService(fakeRepository);

    }

    @Test 
    void deveAdicionarDespesaComSucesso(){
//Ação
        expenseService.addExpense("Almoço", 15.50, "Alimentação");
        List<Expense> expenses = expenseService.getAllExpenses();

//Verificação
        assertEquals(1, expenses.size(), "Deve conter exatamente 1 despesa.");
        assertEquals("Almoço", expenses.get(0).description());
        assertEquals(15.50, expenses.get(0).amount());
    }

    @Test 
    void deveLancarExcecaoQuandoValorForInvalido(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.addExpense("Uber", -5.0, "Transporte");
        });

        assertEquals("O valor deve ser maior que zero.", exception.getMessage());
        assertTrue(expenseService.getAllExpenses().isEmpty());
    }

    @Test 
    void deveLancarExcecaoQuandoDescricaoForVazia(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.addExpense("  ", 50.0, "Lazer");
        });

        assertEquals("A descrição não pode estar vazia.", exception.getMessage());
    }

    @Test 
    void deveCalcularOTotalDeDespesasCorretamente(){
        expenseService.addExpense("Café", 2.50, "Alimentação");
        expenseService.addExpense("Bilhete Metro", 20.00, "Transporte");
        expenseService.addExpense("Livro", 15.00, "Educação");

        double total = expenseService.calculateTotalExpenses();

        assertEquals(37.50, total, "O total das despesas deve ser 37.50");
    }

}
