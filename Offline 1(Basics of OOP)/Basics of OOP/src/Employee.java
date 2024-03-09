abstract class Employee {

    private String employeeName;
    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }
    public abstract boolean Lookup();
    public abstract boolean approveLoan(Bank bank);
    public abstract boolean seeInternalFund();
    public abstract boolean changeInterestRate(Bank bank,String type, double newRate);
    public String getEmployeeName() {
        return employeeName;
    }
}




class ManagingDirector extends Employee  {
    public ManagingDirector(String employeeName) {
        super(employeeName);
    }

    public boolean Lookup(){
        return true;
    }
    public boolean approveLoan(Bank bank){
        for(Account acc:bank.getLoanRequests()){
            acc.setLoanStatus(true);
            acc.setBalance(acc.getBalance()+acc.getLoanAmount());
        }
        return true;
    }
    public boolean seeInternalFund(){
        return true;
    }
    public boolean changeInterestRate(Bank bank,String type,double newRate){
        newRate = newRate/100;
        if(type.equalsIgnoreCase("Student")){
            StudentAccount.setStudentInterestRate(newRate);
            for(Account acc: bank.getAccounts()){
                if(acc instanceof StudentAccount){
                    acc.setInterestRate(newRate);
                }
            }
        }
        else if(type.equalsIgnoreCase("Savings")){
            SavingsAccount.setSavingsInterestRate(newRate);
            for(Account acc: bank.getAccounts()){
                if(acc instanceof SavingsAccount){
                    acc.setInterestRate(newRate);
                }
            }
        }
        else if(type.equalsIgnoreCase("Fixed")){
            FixedDepositAccount.setFixedInterestRate(newRate);
            for(Account acc: bank.getAccounts()){
                if(acc instanceof FixedDepositAccount){
                    acc.setInterestRate(newRate);
                }
            }
        }
        else{
            System.out.println("Invalid account type");
            return false;
        }
        return true;
    }
}

class Officer extends Employee  {

    public boolean Lookup(){
        return true;
    }
    public Officer(String employeeName) {
        super(employeeName);
    }
    public boolean approveLoan(Bank bank){
        for(Account acc:bank.getLoanRequests()){
            acc.setLoanStatus(true);
            acc.setBalance(acc.getBalance()+acc.getLoanAmount());
        }
        return true;
    }
    public boolean seeInternalFund(){
        return false;
    }
    public boolean changeInterestRate(Bank bank,String type,double newRate){
       return false;
    }
}

class Cashier extends Employee {

    public boolean Lookup(){
        return true;
    }
    public Cashier(String employeeName) {
        super(employeeName);
    }

    public boolean approveLoan(Bank bank){
        return false;
    }
    public boolean seeInternalFund(){
        return false;
    }
    public boolean changeInterestRate(Bank bank,String type,double newRate){
        return false;
    }
}