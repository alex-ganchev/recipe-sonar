package digitalrazgrad.recipes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "recipes")

public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, message = "Минимален брой символи 3!")
    @Size(max = 255, message = "Максимален брой символи 255!")
    private String name;
    @Lob
    @Size(min = 3, message = "Минимален брой символи 3!")
    @Size(max = 512, message = "Максимален брой символи 512!")
    private String description;
    @Size(min = 1, message = "Изберете поне един продукт!")
    @ManyToMany
    @JoinTable(
            name = "dishes_products",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> productList;

    @Enumerated(EnumType.STRING)
    @Column(name = "dish_type")
    private DishType dishType;
    @Column(nullable = true)
    private int rating;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProductList() {
        return productList;
    }

    public void setProductList(Set<Product> productList) {
        this.productList = productList;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
