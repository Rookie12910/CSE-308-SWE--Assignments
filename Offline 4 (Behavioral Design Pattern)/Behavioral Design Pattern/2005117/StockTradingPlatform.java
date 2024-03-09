import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class StockTradingPlatform {
    public static ArrayList<User> users = new ArrayList<>();
    public static void main(String[] args) throws Exception {

        initialize();

        User currentUser = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Enter your command: ");
            String input = scanner.nextLine();
            String[] params = input.split(" ");

            switch (params[0].toLowerCase()) {
                case "login":
                    if (params.length == 2) {
                        String name = params[1];
                        currentUser = getUser(name);
                        if(currentUser==null)
                        {
                            System.out.println("Invalid user!");
                        }
                        else
                        {
                            currentUser.getClient().setLoggedIn(true);
                            currentUser.showAllStocks();
                            if(currentUser instanceof Buyer)
                            {
                                currentUser.Notify();
                            }
                        }
                    } else {
                        System.out.println("Invalid command for login!");
                    }
                    break;

                case "logout":
                    currentUser.getClient().setLoggedIn(false);
                    currentUser = null;
                    break;

                case "s":
                    if (params.length == 2) {
                        String stockName = params[1];
                        currentUser.subscribeTo(stockName);
                    } else {
                        System.out.println("Invalid command for subscribing!");
                    }
                    break;

                case "u":
                    if (params.length == 2) {
                        String stockName = params[1];
                        currentUser.unsubscribeTo(stockName);
                    } else {
                        System.out.println("Invalid command for unsubscribing!");
                    }
                    break;

                case "v":
                    if (params.length == 1) {
                        currentUser.view();
                    } else {
                        System.out.println("Invalid command for view!");
                    }
                    break;

                case "i":
                    if (params.length == 3) {
                        String stockName = params[1];
                        double incAmount = Double.parseDouble(params[2]);
                        currentUser.increasePrice(stockName,incAmount);
                    } else {
                        System.out.println("Invalid command for price increase!");
                    }
                    break;

                case "d":
                    if (params.length == 3) {
                        String stockName = params[1];
                        double decAmount = Double.parseDouble(params[2]);
                        currentUser.decreasePrice(stockName,decAmount);
                        }

                     else {
                        System.out.println("Invalid command for price decrease!");
                    }
                    break;

                case "c":
                    if (params.length == 3) {
                        String stockName = params[1];
                        int newCount = Integer.parseInt(params[2]);
                        if(newCount<0)
                        {
                            System.out.println("Count can not be negative!");
                        }
                        else {
                            currentUser.changeCount(stockName,newCount);
                        }

                    }
                         else {
                        System.out.println("Invalid command for count change!");
                    }
                    break;

                case "exit":
                    System.out.println("closing....!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }

        scanner.close();
    }

    public static void initialize() throws Exception {
        User admin = new SystemAdministrator("admin");
        User user1 = new Buyer("user1");
        User user2 = new Buyer("user2");
        User user3 = new Buyer("user3");
        users.add(admin);
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }
    public static User getUser(String name)
    {
        for(User user: users)
        {
            if(user.getUserName().equalsIgnoreCase(name))
            {
                return user;
            }
        }
        return null;
    }
}
