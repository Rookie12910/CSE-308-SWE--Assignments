import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

    abstract class User implements Serializable{
    abstract void subscribeTo(String stockName) throws Exception;
    abstract void unsubscribeTo(String stockName) throws Exception;
    abstract void view() throws Exception;
    abstract void showAllStocks() throws Exception;
    abstract void Notify() ;
    abstract void increasePrice(String stockName, double incAmount) throws Exception;
    abstract public void decreasePrice(String stockName, double decAmount) throws Exception;
    abstract void changeCount(String stockName,int newCount) throws Exception;
    abstract Client getClient();
    abstract String getUserName();
}
class Buyer extends User{
    String userName;
    Client client;
    ArrayList<String> subscribedStocks = new ArrayList<>();

    Buyer(String name) throws Exception {
        this.userName = name;
        client = new Client();
    }
    public void subscribeTo(String stockName) throws Exception {
        subscribedStocks.add(stockName);
        client.reqToAddSubscriber(this,stockName);
    }
    public void unsubscribeTo(String stockName) throws Exception {
        for(int i = 0;i<subscribedStocks.size();i++)
        {
           if(subscribedStocks.get(i).equals(stockName))
           {
               subscribedStocks.remove(i);
               client.reqToRemoveSubscriber(this,stockName);
           }
        }
    }
    public void view() throws Exception
    {
        System.out.println("You have subscribed to : ");
        ArrayList<Stock> stocks = client.getStocks();
        for(Stock stock : stocks)
        {
            for(String stockName : subscribedStocks)
            {
                if(stock.getName().equals(stockName))
                System.out.println(stock.getName()+" "+stock.getCount()+" "+stock.getPrice());
            }
        }

    }

    public void showAllStocks() throws Exception
    {
        System.out.println("Available stocks : ");
        ArrayList<Stock> stocks = client.getStocks();
        for(Stock stock : stocks)
        {
            System.out.println(stock.getName()+" "+stock.getCount()+" "+stock.getPrice());
        }

    }

    public void Notify()
    {
        this.getClient().showNotification();
    }

    public void increasePrice(String stockName, double incAmount) throws Exception
    {
        System.out.println("Invalid command for this user!");
    }
    public void decreasePrice(String stockName, double decAmount) throws Exception
    {
        System.out.println("Invalid command for this user!");
    }
    public void changeCount(String stockName,int newCount) throws Exception
    {
        System.out.println("Invalid command for this user!");
    }

    public Client getClient() {
        return client;
    }

    public String getUserName() {
        return userName;
    }
}



class SystemAdministrator extends User{
    String name;
    transient Client client;
    SystemAdministrator(String name) throws Exception {
        this.name = name;
        client = new Client();
    }
    public void increasePrice(String stockName, double incAmount) throws Exception {
        client.reqToIncreasePrice(stockName, incAmount);
    }
    public void decreasePrice(String stockName, double decAmount) throws Exception
    {
        client.reqToDecreasePrice(stockName, decAmount);
    }
    public void changeCount(String stockName,int newCount) throws Exception
    {
        client.reqToChangeCount(stockName,newCount);
    }

    public void subscribeTo(String stockName) throws Exception
    {
        System.out.println("Invalid command for this user!");
    }
    public void unsubscribeTo(String stockName) throws Exception
    {
        System.out.println("Invalid command for this user!");
    }
    public void view() throws Exception
    {
        System.out.println("Invalid command for this user!");
    }
    public void showAllStocks() throws Exception
    {
        System.out.println("Available stocks : ");
        ArrayList<Stock> stocks = client.getStocks();
        for(Stock stock : stocks)
        {
            System.out.println(stock.getName()+" "+stock.getCount()+" "+stock.getPrice());
        }

    }
    public void Notify()
    {
        System.out.println("Invalid command for this user!");
    }

    public String getUserName() {
        return name;
    }

    public Client getClient() {
        return client;
    }
}