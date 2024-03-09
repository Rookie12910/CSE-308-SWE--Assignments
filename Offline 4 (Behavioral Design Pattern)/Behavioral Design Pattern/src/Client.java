import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Serializable {
    private static final int PORT = 12345;
    private static final String HOST = "127.0.0.1";
    transient Socket socket;

    transient ObjectOutputStream oos ;

    transient ObjectInputStream ois;

    ArrayList<Stock> stocks = new ArrayList<>();
    ArrayList<String> notifications = new ArrayList<>();
    boolean loggedIn = false;

    public void setLoggedIn(boolean flag) {
        this.loggedIn = flag;
    }

    Client() throws Exception {
        initializeConnection();
        new Thread(() -> {
            try {
                handleServerMessages();
            } catch (Exception e) {
                System.err.println("Exception in handleServerMessages: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();

        reqToGetStocks();
    }

    private void initializeConnection() throws IOException {
        socket = new Socket(HOST, PORT);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    private void handleServerMessages() throws Exception {
        try {
            while (true) {
                try {
                    Object response = ois.readObject();
            if (response instanceof ArrayList<?>) {
                stocks.clear();
                ArrayList<Stock> temp = new ArrayList<>((ArrayList<Stock>) response);
                stocks = temp;

            } else {
                reqToGetStocks();
                String serverMessage = (String) response;
                if(!loggedIn)
                {
                    notifications.add(serverMessage);
                }
                else
                {
                    System.out.println("New notification: " + serverMessage);
                }

            }

                } catch (StreamCorruptedException e) {
                    System.err.println("Error reading object from the server: " + e.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void reqToGetStocks() throws Exception
    {
        oos.writeObject("getStocks");
        oos.flush();
    }
    public ArrayList<Stock> getStocks() throws Exception
    {
        return stocks;
    }

    public void reqToAddSubscriber(User user,String stockName) throws Exception
    {
        oos.writeObject("addSubscriber");
        oos.writeObject(user);
        oos.writeObject(stockName);
        oos.flush();
        reqToGetStocks();
    }
    public void reqToRemoveSubscriber(User user,String stockName) throws Exception
    {

        oos.writeObject("removeSubscriber");
        oos.writeObject(user);
        oos.writeObject(stockName);
        oos.flush();
    }
    public void reqToIncreasePrice(String stockName, double incAmount) throws Exception
    {
        oos.writeObject("increasePrice");
        oos.writeObject(stockName);
        oos.writeObject(incAmount);
        oos.flush();

    }

    public void reqToDecreasePrice(String stockName, double decAmount) throws Exception
    {

        oos.writeObject("decreasePrice");
        oos.writeObject(stockName);
        oos.writeObject(decAmount);
        oos.flush();

    }

    public void reqToChangeCount(String stockName, int newCount) throws Exception
    {
        oos.writeObject("changeCount");
        oos.writeObject(stockName);
        oos.writeObject(newCount);
        oos.flush();

    }

    public void showNotification()
    {
        for(String notification : notifications)
        {
            System.out.println("New notification: "+notification);
        }
        notifications.clear();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }


    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

}
