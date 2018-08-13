package framework;

import java.util.ArrayList;
import java.util.Set;

public interface DAO {

void addBankAccount(POJO.BankAccount bankAccount);
void addCustomer(POJO.Customer customer);

ArrayList<POJO.BankAccount> viewAllAccounts();
ArrayList<POJO.Customer> viewAllCustomers();
POJO.BankAccount searchAccountById(int num);

}
