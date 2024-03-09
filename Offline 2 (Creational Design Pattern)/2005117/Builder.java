interface Builder{
    void setShakeType(String type);
    void addIngredient(String ingredient);
    void setBasePrice(int basePrice);
    void lactoseFree();
    void addCandy();
    void addCookie();
    void setTotalPrice();
    Shake getShake();
}
