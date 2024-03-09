import java.io.Serializable;
import java.util.ArrayList;

public class Stock implements Serializable {
    String name;
    int count;
    double price;

    ArrayList<User> subscribers = new ArrayList<>();
    Stock(String name, int count, double price)
    {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public void addSubscriber(User user)
    {
        subscribers.add(user);
    }
    public void removeSubscriber(User user)
    {
        subscribers.remove(user);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<User> getSubscribers() {
        return subscribers;
    }
}
