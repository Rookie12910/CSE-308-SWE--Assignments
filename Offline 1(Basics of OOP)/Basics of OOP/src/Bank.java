import java.util.ArrayList;
import java.util.List;

class Bank {
    private double internalFunds;
    private int yearCount;
    private List<Account> accounts;
    private List<Employee> employees;
    private List<Account>  loanRequests;
    public Bank() {
        internalFunds = 1000000; // Initial internal funds
        yearCount = 0;
        accounts = new ArrayList<>();
        employees = new ArrayList<>();
        loanRequests = new ArrayList<>();
        initializeEmployees();
    }

    private void initializeEmployees() {
        employees.add(new ManagingDirector("MD"));
        employees.add(new Officer("S1"));
        employees.add(new Officer("S2"));
        for (int i = 1; i <= 5; i++) {
            employees.add(new Cashier("C"+Integer.toString(i)));
        }
        System.out.println("Bank created; MD, S1,S2,C1,C2,C3,C4,C5 created");
    }

    public Account createAccount(String holderName, String accountType,  double initialDeposit) {
        for (Account acc : accounts) {
            if (acc.getHolderName().equals(holderName)) {
                System.out.println("Account Creation Failed! This user already has an account!");
                return null;
            }
        }
        Account newAccount;
        switch (accountType.toLowerCase()) {
            case "savings":
                newAccount = new SavingsAccount(holderName, initialDeposit);
                break;
            case "student":
                newAccount = new StudentAccount(holderName, initialDeposit);
                break;
            case "fixed":
                if (initialDeposit >= 100000) {
                    newAccount = new FixedDepositAccount(holderName, initialDeposit);
                } else {
                    System.out.println("Account Creation Failed! Initial deposit for a Fixed deposit account must be at least 100,000$");
                    return null;
                }
                break;
            default:
                System.out.println("Invalid account type!");
                return null;
        }
        newAccount.setAccCreationYear(yearCount);
        accounts.add(newAccount);
        System.out.println(accountType + " account for " + holderName + " created; initial balance " + initialDeposit+"$");
        return newAccount;
    }

    public void deposit(Account currentUser, double amount) {
        if(currentUser==null){
            System.out.println("Invalid user!");
            return;
        }
        boolean success;
        success = currentUser.deposit(amount);
        if(success){
            internalFunds += amount;
            System.out.println(amount + "$ deposited; current balance " + currentUser.getBalance()+"$");
        }
        else{
            System.out.println("Invalid transaction; current balance " + currentUser.getBalance()+"$");
        }
    }

    public void withdraw(Account currentUser, double amount){
        if(currentUser==null){
            System.out.println("Invalid user!");
            return;
        }
        boolean success;
        success = currentUser.withdraw(amount);
        if(success){
            System.out.println(amount+"$ withdrawn successfully; current balance "+currentUser.getBalance()+"$");
            internalFunds-=amount;
        }
        else{
            System.out.println("Invalid transaction; current balance "+currentUser.getBalance()+"$");
        }
    }

    public void requestLoan(Account currentUser, double amount){
        if(currentUser==null){
            System.out.println("Invalid user!");
            return;
        }
        boolean success;
        success = currentUser.requestLoan(this,amount);
        if(success){
            System.out.println("Loan request successful, sent for approval");
        }
        else{
            System.out.println("Invalid Loan Request!");
        }
    }
    public void queryBalance(Account currentUser){
        if(currentUser==null){
            System.out.println("Invalid user!");
            return;
        }
        String str = "Current balance "+currentUser.getBalance()+"$;";
        if(currentUser.getLoanStatus()){
            str+=" loan "+currentUser.getLoanAmount()+"$";
        }
        System.out.println(str);
    }

    public void Lookup(Employee emp, String userName){
        Account acc = findAccount(userName);
        if(emp==null || acc==null)
        {
            System.out.println("Invalid user!");
            return;
        }
        boolean success;
        success =  emp.Lookup();
        if(success){
            System.out.println(acc.getHolderName()+"'s current balance "+acc.getBalance()+"$");
        }
        else{
            System.out.println("You don't have permission for this operation");
        }
    }

    public void approveLoan(Employee emp){
        if(emp==null){
            System.out.println("Invalid employee!");
            return;
        }
        boolean success;
        success =  emp.approveLoan(this);
        if(success){
            System.out.println("Loan for all users approved");
            loanRequests.clear();
        }
        else{
            System.out.println("You don't have permission for this operation");
        }
    }

    public void seeInternalFund(Employee emp){
        if(emp==null){
            System.out.println("Invalid employee!");
            return;
        }
        boolean success;
        success = emp.seeInternalFund();
        if(success){
            System.out.println("Internal fund of this bank is "+this.getInternalFunds()+"$");
        }
        else{
            System.out.println("You don't have permission for this operation");
        }
    }

    public void increaseYearCount(){
        yearCount++;
        System.out.println("1 year passed");
        for(Account acc: accounts){
            if(acc instanceof FixedDepositAccount && yearCount>acc.getAccCreationYear()){
                ((FixedDepositAccount) acc).setMatured(true);
            }
            acc.setBalance(acc.getBalance()+(acc.getBalance()*acc.getInterestRate()));
            if(acc.getLoanStatus()){
                acc.setBalance(acc.getBalance() - (acc.getLoanAmount()* acc.getLoanInterestRate()));
            }
            if(acc instanceof SavingsAccount || acc instanceof FixedDepositAccount){
                if(acc.getBalance()<500){
                    acc.setLoanStatus(true);
                    acc.setLoanAmount(acc.getLoanAmount()+500);
                }
                else{
                    acc.setBalance(acc.getBalance()-500);
                }
            }
        }
    }

    public void changeInterestRate(Employee emp,String accType,double newRate){
        boolean success;
        success = emp.changeInterestRate(this,accType,newRate);
        if(success){
            System.out.println("Interest rate for "+accType+" account has been successfully changed");
        }
        else {
            System.out.println("You don't have permission for this operation");
        }
    }



    // Helper functions
    public Account findAccount(String holderName){
        for (Account user : accounts) {
            if (user.getHolderName().equals(holderName)) {
                return user;
            }
        }
        return null;
    }
    public Employee findEmployee(String employeeName){
        for (Employee emp : employees) {
            if (emp.getEmployeeName().equalsIgnoreCase(employeeName)) {
                return emp;
            }
        }
        return null;
    }

    public double getInternalFunds() {
        return internalFunds;
    }

    public void addLoanRequest(Account account) {
        loanRequests.add(account);
    }

    public List<Account> getLoanRequests() {
        return loanRequests;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

