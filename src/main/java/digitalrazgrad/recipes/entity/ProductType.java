package digitalrazgrad.recipes.entity;

public enum ProductType {
    FRUIT("Плод"),
    VEGETABLE("Зеленчук"),
    SPICE("Подправка"),
    ANIMAL("Животински продукт"),
    PLANT("Растителен продукт"),
    DAIRY("Млечен продукт"),
    LIQUID("Течност");
    private final String label;

    ProductType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}

