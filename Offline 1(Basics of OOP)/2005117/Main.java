import java.util.*;

public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        Account currentUser = null;
        Employee currentEmployee = null;

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("Enter your command: ");
            String input = scanner.nextLine();
            String[] params = input.split(" ");

            switch (params[0].toLowerCase()) {
                case "create":
                    if (params.length >= 4) {
                        String name = params[1];
                        String accountType = params[2];
                        double initialDeposit = Double.parseDouble(params[3]);
                        currentUser = bank.createAccount(name, accountType, initialDeposit);
                    } else {
                        System.out.println("Invalid command for account creation!");
                    }
                    break;

                case "deposit":
                    if (params.length == 2) {
                        double amount = Double.parseDouble(params[1]);
                        bank.deposit(currentUser, amount);
                    } else {
                        System.out.println("Invalid command for deposit!");
                    }
                    break;

                case "withdraw":
                    if (params.length == 2) {
                        double amount = Double.parseDouble(params[1]);
                        bank.withdraw(currentUser, amount);
                    } else {
                        System.out.println("Invalid command for withdrawal!");
                    }
                    break;

                case "query":
                    if (params.length == 1) {
                        bank.queryBalance(currentUser);
                    } else {
                        System.out.println("Invalid command for querying balance!");
                    }
                    break;

                case "request":
                    if (params.length == 2) {
                        double amount = Double.parseDouble(params[1]);
                        bank.requestLoan(currentUser, amount);
                    } else {
                        System.out.println("Invalid command for loan request!");
                    }
                    break;

                case "close":
                    if (params.length == 1) {
                        if(currentUser!=null){
                            System.out.println("Transaction Closed for "+currentUser.getHolderName());
                            currentUser = null;
                        }
                        else if(currentEmployee!=null){
                            System.out.println("Operations for "+currentEmployee.getEmployeeName()+" closed");
                            currentEmployee = null;
                        }

                    } else {
                        System.out.println("Invalid command for closing transaction!");
                    }
                    break;

                case "open":
                    if (params.length == 2) {
                        String name = params[1];
                        currentEmployee = bank.findEmployee(name);
                        if(currentEmployee!=null){
                            String str = currentEmployee.getEmployeeName()+" active;";
                            if(bank.getLoanRequests().size()>0) {
                                str+=" there are loan approvals pending";
                            }
                            System.out.println(str);
                        }
                        else {
                            currentUser = bank.findAccount(name);
                            System.out.println("Welcome back, "+currentUser.getHolderName());
                        }
                    } else {
                        System.out.println("Invalid command for opening transaction!");
                    }
                    break;

                case "inc":
                    bank.increaseYearCount();
                    break;

                case "lookup":
                    if (params.length == 2) {
                        String name = params[1];
                        bank.Lookup(currentEmployee, name);
                    } else {
                        System.out.println("Invalid command for lookup!");
                    }
                    break;

                case "approve":
                    if (params.length == 2) {
                        bank.approveLoan(currentEmployee);
                    } else {
                        System.out.println("Invalid command for Loan approval!");
                    }
                    break;

                case "change":
                    if (params.length == 3) {
                        String accType = params[1];
                        double newRate = Double.parseDouble(params[2]);
                        bank.changeInterestRate(currentEmployee,accType,newRate);

                    } else {
                        System.out.println("Invalid command for Changing Interest Rate!");
                    }
                    break;

                case "see":
                    if (params.length == 1) {
                       bank.seeInternalFund(currentEmployee);
                    } else {
                        System.out.println("Invalid command for checking internal fund!");
                    }
                    break;

                case "exit":
                    System.out.println("Bank closed....!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }

        scanner.close();

    }
}
