package moneymoney;

import java.util.ArrayList;

import POJO.BankAccount;
import POJO.Customer;
import POJO.PaymentGateway;
import POJO.Service;

public class ServiceImpl implements Service {
DAOImpl dao = new DAOImpl();

@Override
public void addBankAccount(BankAccount bankAccount) {
System.out.println(bankAccount);
dao.addBankAccount(bankAccount);
}

@Override
public ArrayList<BankAccount> viewAllAccounts(){
return dao.viewAllAccounts();
}

@Override
public boolean FundTransfer(BankAccount sender,BankAccount reciever,double amount) {
return PaymentGateway.transfer(sender, reciever, amount);
}
@Override
public void addCustomer(Customer customer) {
dao.addCustomer(customer);
}
@Override
public ArrayList<Customer> viewAllCustomers() {

return dao.viewAllCustomers();
}
@Override
public BankAccount searchAccountById(int num) {

return dao.searchAccountById(num);
}
public String withdraw(int accNo,double amount) {
BankAccount account = searchAccountById(accNo);
return account.withdraw(amount);
}
public void deposit(int accNo,double amount) {
BankAccount account = searchAccountById(accNo);
account.deposit(amount);
}


}