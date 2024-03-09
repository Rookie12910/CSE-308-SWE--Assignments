import java.util.*;

public class OrderManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean orderInProgress = false;
        List<Shake> shakesOrdered = new ArrayList<>();

        while (true) {
            System.out.println("Press 'o' to open an order, 'e' to close an order, or 'q' to quit:");
            char choice = scanner.next().charAt(0);

            if (Character.toLowerCase(choice) == 'q' ) {
                break;
            } else if (Character.toLowerCase(choice) == 'o') {
                if (orderInProgress) {
                    System.out.println("You have an order in progress. Do you want to add something else? (Y/N)");
                    char continueChoice = scanner.next().charAt(0);
                    if (continueChoice == 'N' || continueChoice == 'n') {
                        orderInProgress = false;
                        continue;
                    }
                }
                orderInProgress = true;
                System.out.println("Choose a shake to add to your order:");
                System.out.println("1. Chocolate Shake");
                System.out.println("2. Coffee Shake");
                System.out.println("3. Strawberry Shake");
                System.out.println("4. Vanilla Shake");
                System.out.println("5. Zero Shake");
                int shakeChoice = scanner.nextInt();

                shakeBuilder builder = new shakeBuilder();
                switch (shakeChoice) {
                    case 1:
                        Director.chocolateShakeBuilder(builder);
                        break;
                    case 2:
                        Director.coffeeShakeBuilder(builder);
                        break;
                    case 3:
                        Director.strawberryShakeBuilder(builder);
                        break;
                    case 4:
                        Director.vanillaShakeBuilder(builder);
                        break;
                    case 5:
                        Director.zeroShakeBuilder(builder);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }

                while(true){
                    System.out.println("Do you want the shake to be lactose-free? (Y/N)");
                    char customizeChoice = scanner.next().charAt(0);
                    if(Character.toLowerCase(customizeChoice)=='y'){
                        builder.lactoseFree();
                        break;
                    }
                    else if(Character.toLowerCase(customizeChoice)=='n'){
                        break;
                    }
                    else{
                        System.out.println("Invalid choice! Try again!");
                    }
                }

                while(true){
                    System.out.println("Do you want to add candy on top? (Y/N)");
                    char customizeChoice = scanner.next().charAt(0);
                    if(Character.toLowerCase(customizeChoice)=='y'){
                        builder.addCandy();
                        break;
                    }
                    else if(Character.toLowerCase(customizeChoice)=='n'){
                        break;
                    }
                    else{
                        System.out.println("Invalid choice! Try again!");
                    }
                }
                while(true){
                    System.out.println("Do you want to add cookie on top? (Y/N)");
                    char customizeChoice = scanner.next().charAt(0);
                    if(Character.toLowerCase(customizeChoice)=='y'){
                        builder.addCookie();
                        break;
                    }
                    else if(Character.toLowerCase(customizeChoice)=='n'){
                        break;
                    }
                    else{
                        System.out.println("Invalid choice! Try again!");
                    }
                }

                Shake shake = builder.getShake();
                shakesOrdered.add(shake);
                System.out.println("Shake added to the order.");
            } else if (choice == 'e') {
                if (shakesOrdered.isEmpty()) {
                    System.out.println("Empty order! Please add something to the order.");
                    continue;
                }
                orderInProgress = false;
                System.out.println("Order closed. Here are the details:");
                System.out.println();
                int totalOrderPrice = 0;
                for (Shake shake : shakesOrdered) {
                    shake.printDetails();
                    System.out.println();
                    totalOrderPrice += shake.getTotalPrice();
                }
                System.out.println("Total Order Price: " + totalOrderPrice+ " tk");
                System.out.println();
                shakesOrdered.clear();
            } else {
                System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
}
