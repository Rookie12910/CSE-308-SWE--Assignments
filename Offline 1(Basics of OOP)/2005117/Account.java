abstract  class Account {
    private String holderName;
    private double balance;
    boolean loanStatus ;
    private double loanAmount;
    private int accCreationYear;
    private double interestRate;
    private double loanInterestRate;
    public Account(String holderName, double initialDeposit, double interestRate) {
        this.holderName = holderName;
        this.balance = initialDeposit;
        this.interestRate = interestRate;
        this.loanStatus = false;
        this.loanInterestRate = 0.10;
    }

    public abstract boolean deposit(double amount);
    public abstract boolean withdraw(double amount);

    public abstract boolean requestLoan(Bank bank,double amount);

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccCreationYear(int accCreationYear) {
        this.accCreationYear = accCreationYear;
    }

    public boolean getLoanStatus() {
        return loanStatus;
    }
    public void setLoanStatus(boolean loanStatus) {
        this.loanStatus = loanStatus;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getAccCreationYear() {
        return accCreationYear;
    }

    public double getLoanInterestRate() {
        return loanInterestRate;
    }
}

class SavingsAccount extends Account {

    public static double savingsInterestRate = 0.1;
    public SavingsAccount(String holderName, double initialDeposit) {
        super(holderName, initialDeposit,savingsInterestRate);
    }

    public boolean deposit(double amount){
        this.setBalance(this.getBalance()+amount);
        return true;
    }

    public boolean withdraw(double amount){
        if((this.getBalance() - amount) < 1000){
            return false;
        }
        else{
            if (amount <= this.getBalance()) {
            this.setBalance(this.getBalance()-amount);
            return true;
        }
        return false;
        }
    }

    public boolean requestLoan(Bank bank,double amount){
       if(amount > 10000) {
           return false;
       }
       else{
           bank.addLoanRequest(this);
           this.setLoanAmount(amount);
           return true;
       }
    }

    public static void setSavingsInterestRate(double savingsInterestRate) {
        SavingsAccount.savingsInterestRate = savingsInterestRate;
    }
}


class StudentAccount extends Account {

    private static double studentInterestRate = 0.05;
    public StudentAccount(String holderName, double initialDeposit) {
        super(holderName, initialDeposit,studentInterestRate);
    }

    public boolean deposit(double amount){
        this.setBalance(this.getBalance()+amount);
        return true;
    }
    public boolean withdraw(double amount){
        if(amount > 10000){
            return false;
        }
        else{
            if (amount <= this.getBalance()) {
                this.setBalance(this.getBalance()-amount);
                return true;
            }
            return false;
        }
    }


    public boolean requestLoan(Bank bank,double amount){
        if(amount > 1000) {
            return false;
        }
        else{
            bank.addLoanRequest(this);
            this.setLoanAmount(amount);
            return true;
        }
    }

    public static void setStudentInterestRate(double studentInterestRate) {
        StudentAccount.studentInterestRate = studentInterestRate;
    }
}

class FixedDepositAccount extends Account {

    private static double fixedInterestRate  = 0.15;
    private boolean isMatured;

    public FixedDepositAccount(String holderName, double initialDeposit) {
        super(holderName, initialDeposit,fixedInterestRate);
        this.isMatured = false;
    }


    public boolean deposit(double amount) {
        if (amount < 50000) {
            return false;
        }
        else{
            this.setBalance(this.getBalance()+amount);
            return true;
        }
    }


    public boolean withdraw(double amount) {
        if (isMatured) {
                if (amount <= this.getBalance()) {
                    this.setBalance(this.getBalance()-amount);
                    return true;
                }
                return false;
        }
        return false;
    }


    public boolean requestLoan(Bank bank,double amount){
        if(amount > 100000) {
            return false;
        }
        else{
            bank.addLoanRequest(this);
            this.setLoanAmount(amount);
            return true;
        }
    }

    public static void setFixedInterestRate(double fixedInterestRate) {
        FixedDepositAccount.fixedInterestRate = fixedInterestRate;
    }

    public void setMatured(boolean matured) {
        isMatured = matured;
    }
}

