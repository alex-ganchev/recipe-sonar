package digitalrazgrad.recipes.entity;

public enum DishType {
    SALAD ("Салата"),
    SOUP ("Супа"),
    STARTER ("Предястие"),
    MAIN ("Основно"),
    DESSERT ("Десерт");
    private String label;

    DishType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
