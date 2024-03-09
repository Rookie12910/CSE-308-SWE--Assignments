import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class NewThread implements Runnable{
    ServerSocket serverSocket;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    NewThread(ServerSocket ss, Socket s) throws IOException {
        this.serverSocket = ss;
        this.socket = s;
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {

        while (true) {
            try {

                String request = (String)ois.readObject();

                if ("getStocks".equals(request)) {
                    ArrayList<Stock> stockList = new ArrayList<>();
                    for(Stock stock: Server.stocks)
                    {
                        stockList.add(new Stock(stock.getName(),stock.getCount(),stock.getPrice()));
                    }
                    oos.writeObject(stockList);
                    oos.flush();
                }


                if ("addSubscriber".equals(request)) {
                    User user = (User) ois.readObject();
                    user.getClient().setSocket(socket);
                    user.getClient().setOos(oos);
                    user.getClient().setOis(ois);
                    String stockName = (String) ois.readObject();
                    for (Stock stock : Server.stocks) {
                        if (stock.getName().equals(stockName)) {
                            stock.addSubscriber(user);
                            break;
                        }
                    }
                }

                if ("removeSubscriber".equals(request)) {
                    User user = (User) ois.readObject();
                    String stockName = (String) ois.readObject();
                    for (Stock stock : Server.stocks) {
                        if (stock.getName().equals(stockName)) {
                            stock.removeSubscriber(user);
                            break;
                        }
                    }
                }
                if ("increasePrice".equals(request)) {
                    String stockName = (String) ois.readObject();
                    double incAmount = (double) ois.readObject();
                    for(Stock stock : Server.stocks)
                    {
                        if (stock.getName().equals(stockName)) {
                            stock.setPrice(stock.getPrice() + incAmount);
                        }
                    }
                    for (Stock stock : Server.stocks) {
                        if (stock.getName().equals(stockName)) {
                            for (User user : stock.getSubscribers()) {
                                if (user != null && user.getClient().getOos()!=null) {
                                    user.getClient().getOos().writeObject("Price of " + stockName + " has been increased by " + incAmount+"; current price: "+stock.getPrice());
                                } else {
                                    System.out.println("No subscriber found!");
                                }


                            }
                            break;
                        }
                    }
                }

                if ("decreasePrice".equals(request)) {
                    String stockName = (String) ois.readObject();
                    double decAmount = (double) ois.readObject();
                    for (Stock stock : Server.stocks) {
                        if (stock.getName().equals(stockName)) {
                            stock.setPrice(stock.getPrice() - decAmount);
                            for (User user : stock.getSubscribers()) {
                                if (user != null && user.getClient().getOos()!=null) {
                                    user.getClient().getOos().writeObject("Price of " + stockName + " has been decreased by " + decAmount+"; current price: "+stock.getPrice());
                                } else {
                                    System.out.println("No subscriber found!");
                                }

                            }
                            break;
                        }
                    }
                }

                if ("changeCount".equals(request)) {
                    String stockName = (String) ois.readObject();
                    int newCount = (int) ois.readObject();
                    for (Stock stock : Server.stocks) {
                        if (stock.getName().equals(stockName)) {
                            stock.setCount(newCount);
                            for (User user : stock.getSubscribers()) {
                                if (user != null && user.getClient().getOos()!=null) {
                                    user.getClient().getOos().writeObject("Count of " + stockName + " has been changed to " + newCount+"; current count: "+stock.getCount());

                                } else {
                                    System.out.println("No subscriber found!");
                                }

                            }
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


public class Server {

    public static ArrayList<Stock> stocks = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        inputStocks();
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");
        while(true)
        {
            Socket socket = serverSocket.accept();
            NewThread newThread = new NewThread(serverSocket,socket);
        }
    }

    public static void inputStocks()
    {
        String filePath = "init_stocks.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                String name  = words[0];
                int count = Integer.parseInt(words[1]);
                double price = Double.parseDouble(words[2]);
                Stock stock = new Stock(name, count, price);
                stocks.add(stock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
