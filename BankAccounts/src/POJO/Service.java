package POJO;

import java.util.ArrayList;
import java.util.Set;

public interface Service {
void addBankAccount(BankAccount bankAccount);
void addCustomer(Customer customer);
ArrayList<BankAccount> viewAllAccounts();
ArrayList<Customer> viewAllCustomers();
BankAccount searchAccountById(int num);
boolean FundTransfer(BankAccount sender, BankAccount reciever, double amount);
}