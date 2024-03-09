import java.util.List;

class Shake {

    private String shakeType;
    private List<String> baseIngredients;
    private List<String> customizations;
    private int basePrice;
    private int totalPrice;

    public Shake(String type, List<String> baseIngredients,List<String> customizations, int basePrice, int totalPrice) {
        this.shakeType = type;
        this.baseIngredients = baseIngredients;
        this.customizations = customizations;
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
    }

    public String getType() {
        return shakeType;
    }

    public List<String> getIngredients() {
        return baseIngredients;
    }

    public List<String> getCustomizations() {
        return customizations;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void printIngredients(){
        System.out.print("Base ingredients : ");
        List<String> ingredients = getIngredients();
        int size = ingredients.size();
        for(int i = 0; i < size; i++) {
            System.out.print(ingredients.get(i));
            if(i < size - 1) {
                System.out.print(", ");
            }
        }
    }

    public void printCustomizations(){
        if(getCustomizations().size()!=0) {
            System.out.println("Added ingredients : ");
            for(String custom:getCustomizations())
            {
                System.out.println(custom);
            }
        }
    }

    public void printDetails(){
        System.out.println("Shake: " + this.getType());
        this.printIngredients();
        System.out.println();
        this.printCustomizations();
        System.out.println("Base Price: " + this.getBasePrice()+" tk");
        System.out.println("Total Price: " + this.getTotalPrice()+" tk");
    }
}
