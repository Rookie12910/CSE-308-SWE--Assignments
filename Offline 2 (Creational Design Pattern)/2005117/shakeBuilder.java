import java.util.ArrayList;
import java.util.List;

class shakeBuilder implements Builder {
    private String shakeType;
    private List<String> ingredients = new ArrayList<>();
    private List<String> customizations = new ArrayList<>();
    private int basePrice;
    private int totalPrice;
    boolean lactoseFree = false;
    boolean candyOnTop = false;
    boolean cookieOnTop = false;

    public void setShakeType(String type) {
        this.shakeType = type;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void lactoseFree() {
        int index = ingredients.indexOf("milk");
        if (index != -1) {
            ingredients.set(index, "almond milk");
            customizations.add("Made lactose-free; extra charge : "+priceList.lactoseFree_Charge+" tk");
            this.lactoseFree = true;
        } else {
            return;
        }
    }

    public void addCandy() {
        customizations.add("candy on top; extra charge : "+priceList.candyOnTop_Charge+" tk");
        this.candyOnTop = true;
    }

    public void addCookie() {
        customizations.add("cookie on top; extra charge: "+priceList.cookieOnTop_Charge+" tk");
        this.cookieOnTop = true;
    }

    public void setTotalPrice() {
        totalPrice = this.basePrice;
        if (lactoseFree) totalPrice += priceList.lactoseFree_Charge;
        if (candyOnTop) totalPrice += priceList.candyOnTop_Charge;
        if (cookieOnTop) totalPrice += priceList.cookieOnTop_Charge;
    }

    public Shake getShake() {
        List<String> ingredientsCopy = new ArrayList<>(ingredients);
        List<String> customizationsCopy = new ArrayList<>(customizations);
        this.setTotalPrice();
        return new Shake(shakeType, ingredientsCopy, customizationsCopy, basePrice, totalPrice);
    }
}
class Director{
    public static void chocolateShakeBuilder(shakeBuilder builder){
        builder.setShakeType("Chocolate shake");
        builder.addIngredient("milk");
        builder.addIngredient("sugar");
        builder.addIngredient("chocolate syrup");
        builder.addIngredient("chocolate ice cream");
        builder.setBasePrice(priceList.chocolate_Shake_Base_Price);
    }
    public static void coffeeShakeBuilder(shakeBuilder builder){
        builder.setShakeType("Coffee shake");
        builder.addIngredient("milk");
        builder.addIngredient("sugar");
        builder.addIngredient("coffee");
        builder.addIngredient("jello");
        builder.setBasePrice(priceList.coffee_Shake_Base_Price);
    }
    public static void strawberryShakeBuilder(shakeBuilder builder){
        builder.setShakeType("Strawberry shake");
        builder.addIngredient("milk");
        builder.addIngredient("sugar");
        builder.addIngredient("strawberry syrup");
        builder.addIngredient("strawberry ice cream");
        builder.setBasePrice(priceList.strawberry_Shake_Base_Price);
    }
    public static void vanillaShakeBuilder(shakeBuilder builder){
        builder.setShakeType("vanilla shake");
        builder.addIngredient("milk");
        builder.addIngredient("sugar");
        builder.addIngredient("vanilla flavouring");
        builder.addIngredient("jello");
        builder.setBasePrice(priceList.vanilla_Shake_Base_Price);
    }
    public static void zeroShakeBuilder(shakeBuilder builder){
        builder.setShakeType("Strawberry shake");
        builder.addIngredient("milk");
        builder.addIngredient("sweetener");
        builder.addIngredient("vanilla flavouring");
        builder.addIngredient("sugar-free jello");
        builder.setBasePrice(priceList.zero_Shake_Base_Price);
    }
}
