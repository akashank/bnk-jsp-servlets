package moneymoney;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import POJO.BankAccount;
import POJO.Customer;

@WebServlet("*.app")
public class AppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceImpl serviceImpl = new ServiceImpl();
	BankAccount account = null;
	MMCustomer activecustomer = null;
	RequestDispatcher dispatcher = null;
	Map<String, String> loginDetails = new HashMap<>();
	ServletRequest session;


    
	protected  void doGet(HttpServletRequest request, HttpServletResponse response)
		 {
		String action = request.getServletPath();
		switch (action) {
		case "/addNewAccount.app":
			try {
				response.sendRedirect("addNewAccount.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/add.app":
			String name = request.getParameter("customerName");
			String email = request.getParameter("customerEmail");
			String dob = request.getParameter("dob");
			String contact = request.getParameter("contact");
			activecustomer = new MMCustomer(name, Long.parseLong(contact), dob, email);
			String choice = request.getParameter("accountType");
			String balance, odLimit = null;
			boolean sal;
			if (choice.equals("savingaccount")) {
				String type = request.getParameter("salary");
				System.out.println(type);
				if (type.equals("Blue")) {
					sal = true;
					balance = request.getParameter("salbal");
				} else {
					balance = request.getParameter("bal");
					sal = false;
				}
				account = new MMSavingsAccount(activecustomer, Double.parseDouble(balance), sal);
			} else {
				balance = request.getParameter("cbal");
				odLimit = request.getParameter("odLimit");
				account = new MMCurrentAccount(activecustomer, Double.parseDouble(balance),
						Double.parseDouble(odLimit));
			}
			System.out.println(name + "" + email + "" + dob + "" + contact + "" + choice + "" + balance + "" + odLimit
					+ "" + account.getAccountNumber());
			activecustomer.setAccountNumber(account.getAccountNumber());
			serviceImpl.addBankAccount(account);
			serviceImpl.addCustomer(activecustomer);
			request.setAttribute("accNo", account.getAccountNumber());
			dispatcher = request.getRequestDispatcher("displayAccount.app");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/searchAcc.app":
			int num = Integer.parseInt(request.getParameter("accNo"));
			request.setAttribute("accNo", num);
			dispatcher = request.getRequestDispatcher("displayAccount.app");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/displayAccount.app":
			if (serviceImpl.searchAccountById(((int) request.getAttribute("accNo"))) == null) {
				try {
					response.sendRedirect("AccountNotFound.jsp");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			} else {
				account = serviceImpl.searchAccountById((int) request.getAttribute("accNo"));
				String classType = account.getClass().getSimpleName();
				System.out.println(classType);
				request.setAttribute("bankAccount", account);
				request.setAttribute("classType", classType);
				dispatcher = request.getRequestDispatcher("ViewAccount.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (ServletException | IOException e) {
					
					e.printStackTrace();
				}
			}
			break;
		case "/withdrawAmount.app":
			try {
				response.sendRedirect("withdraw.jsp");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/depositAmount.app":
			try {
				response.sendRedirect("deposit.jsp");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/withdraw.app":
			int accNo = Integer.parseInt(request.getParameter("accNo"));
			double amount = Double.parseDouble(request.getParameter("withdrawamount"));
			String msg = serviceImpl.withdraw(accNo, amount);
			System.out.println(msg);
			request.setAttribute("accNo", accNo);
			dispatcher = request.getRequestDispatcher("displayAccount.app");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/deposit.app":
			accNo = Integer.parseInt(request.getParameter("accNo"));
			amount = Double.parseDouble(request.getParameter("depositamount"));
			serviceImpl.deposit(accNo, amount);
			request.setAttribute("accNo", accNo);
			System.out.println(accNo + " jkbdabk " + amount);
			dispatcher = request.getRequestDispatcher("displayAccount.app");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/searchAccount.app":
			try {
				response.sendRedirect("searchAcc.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/fundTransfer.app":
			try {
				response.sendRedirect("payment.jsp");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case "/transfer.app":
			int sender = Integer.parseInt(request.getParameter("sender"));
			int reciever = Integer.parseInt(request.getParameter("reciever"));
			amount = Double.parseDouble(request.getParameter("amount"));
			System.out.println(sender + " " + amount + " " + reciever);
			msg = serviceImpl.withdraw(sender, amount);
			System.out.println(msg);
			serviceImpl.deposit(reciever, amount);
			request.setAttribute("sender", sender);
			request.setAttribute("reciever", reciever);
			request.setAttribute("amount", amount);
			dispatcher = request.getRequestDispatcher("fundTransfer.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			break;
		case "/ViewAllCustomers.app":
			ArrayList<Customer> cust = serviceImpl.viewAllCustomers();
			request.setAttribute("customers", cust);
			dispatcher = request.getRequestDispatcher("viewAllCustomers.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		default:
			try {
				response.sendRedirect("home.jsp");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}


	
}