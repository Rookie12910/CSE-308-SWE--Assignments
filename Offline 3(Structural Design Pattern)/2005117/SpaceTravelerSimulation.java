import java.util.*;
interface SpaceTraveler {
    void repair();
    void work();
    String getUserName();
}


class CrewMate implements SpaceTraveler {
    String userName;
    public CrewMate(String name){
        this.userName = name;
    }
    @Override
    public void repair() {
        System.out.println("Repairing the spaceship.");
    }

    @Override
    public void work() {
        System.out.println("Doing research.");
    }

    public String getUserName() {
        return userName;
    }
}


abstract class SpaceTravelerDecorator implements SpaceTraveler {
    protected SpaceTraveler decoratedTraveler;

    public SpaceTravelerDecorator(SpaceTraveler traveler) {
        this.decoratedTraveler = traveler;
    }

    public void repair() {
        decoratedTraveler.repair();
    }

    public void work() {
        decoratedTraveler.work();
    }

    public String getUserName(){
        return decoratedTraveler.getUserName();
    }

}

class Imposter extends SpaceTravelerDecorator {

    public Imposter(SpaceTraveler imposter) {
        super(imposter);
    }

    @Override
    public void repair() {
        decoratedTraveler.repair();
        System.out.println("\u001B[31mDamaging the spaceship\u001B[0m.");
    }

    @Override
    public void work(){
        decoratedTraveler.work();
        System.out.println("\u001B[31mAttempting to kill a crewmate\u001B[0m.");
        System.out.println("\u001B[31mSuccessfully killed a crewmate\u001B[0m.");
    }
}


public class SpaceTravelerSimulation {
    public static void main(String[] args) {
        SpaceTraveler currentUser = null;
        List <SpaceTraveler> passengers = new ArrayList<>();
        List <CrewMate> deadCrews = new ArrayList<>();
        int aliveCrewMateCount = 10;
        Random random = new Random();

        for(int i = 1;i<=10;i++){
           passengers.add(new CrewMate("crew"+i));
        }
        for(int i = 1;i<=5;i++){
            passengers.add(new Imposter(new CrewMate("imposter"+i)));
        }

        Scanner scanner = new Scanner(System.in);
        String command;
        boolean running = true;
        while(running){
            System.out.println("Enter your command : ");
            command = scanner.nextLine();
            String[] splitCommand = command.split(" ");
            String action = splitCommand[0];

            switch (action) {
                case "login":
                    if(splitCommand.length==2){
                        if(currentUser!=null){
                            System.out.println("Failed! Another user is already loggedin!");
                            break;
                        }
                        boolean found = false;
                        String name = splitCommand[1];
                        for(SpaceTraveler traveler: passengers) {
                            if (traveler.getUserName().equals(name)) {
                                currentUser = traveler;
                                found = true;
                            }
                        }
                            if(found) {
                                System.out.println("Welcome Crewmate!");
                                if (currentUser instanceof Imposter) {
                                    System.out.println("\u001B[31mWe won’t tell anyone; you are an imposter\u001B[0m.");
                                }
                            } else {
                                boolean isDead = false;
                                for (CrewMate crew:deadCrews){
                                    if(crew.getUserName().equals(name)){
                                        isDead = true;
                                        System.out.println("This crewmate got killed!");
                                    }
                                }
                                if(!isDead){
                                    System.out.println("User not found!");
                                }
                            }

                    }
                    else {
                        System.out.println("Invalid arguments for login command!");
                    }
                    break;

                case "repair":
                    if(splitCommand.length==1){
                        currentUser.repair();
                    }
                    else {
                        System.out.println("Invalid arguments for repair command!");
                    }
                    break;

                case "work":
                    if(splitCommand.length==1){
                        currentUser.work();
                        if(currentUser instanceof Imposter){
                            int crewMateToKill = random.nextInt(aliveCrewMateCount);
                            deadCrews.add((CrewMate) passengers.get(crewMateToKill));
                            passengers.remove(crewMateToKill);
                            aliveCrewMateCount--;
                        }
                    }
                    else {
                        System.out.println("Invalid arguments for work command!");
                    }
                    break;

                case "logout":
                    if(splitCommand.length==1){
                        if(currentUser!=null) {
                            System.out.println("Bye Bye Crewmate.");
                            if (currentUser instanceof Imposter) {
                                System.out.println("\u001B[31mSee you again Comrade Imposter\u001B[0m.");
                            }
                            currentUser = null;
                        } else {
                            System.out.println("No user is logged in!");
                    }
                    }
                    else {
                        System.out.println("Invalid arguments for logout command!");
                    }
                    break;

                case "exit":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid command!");
            }
        }

    }
}
