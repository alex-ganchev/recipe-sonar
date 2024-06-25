package digitalrazgrad.recipes.service;

import digitalrazgrad.recipes.entity.DishType;
import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.entity.ProductType;
import digitalrazgrad.recipes.entity.Recipe;
import digitalrazgrad.recipes.repository.ProductRepository;
import digitalrazgrad.recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RecipeService {
    private ProductRepository productRepository;
    private RecipeRepository recipeRepository;
    private ValidationService validationService;
    private static final DishType[] dishTypeList = DishType.values();
    private static final String TYPE_LIST = "typeList";
    private static final String PRODUCT_LIST = "productList";
    private static final String MESSAGE = "message";

    public RecipeService(ProductRepository productRepository, RecipeRepository recipeRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
        this.validationService = validationService;
    }

    public String addRecipe(Recipe recipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PRODUCT_LIST, productRepository.findAll());
            model.addAttribute(TYPE_LIST, dishTypeList);
            return "/recipe/add";
        }
        recipe.setRating(calculateRecipeRating(recipe));
        model.addAttribute(MESSAGE, validationService.checkSaveSuccess(recipeRepository.save(recipe)));
        model.addAttribute("recipe", new Recipe());
        model.addAttribute(PRODUCT_LIST, productRepository.findAll());
        model.addAttribute(TYPE_LIST, dishTypeList);
        return "/recipe/add";
    }

    public String editRecipe(Long id, Model model) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipe", recipe);
            model.addAttribute(PRODUCT_LIST, productRepository.findAll());
            model.addAttribute(TYPE_LIST, dishTypeList);
            return "/recipe/edit";
        }
        return "/recipe/list";
    }

    public String editRecipe(Recipe recipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(PRODUCT_LIST, productRepository.findAll());
            model.addAttribute(TYPE_LIST, dishTypeList);
            return "/recipe/edit";
        }
        recipe.setRating(calculateRecipeRating(recipe));
        model.addAttribute(MESSAGE, validationService.checkSaveSuccess(recipeRepository.save(recipe)));
        return "redirect:/recipe/list";
    }

    public int calculateRecipeRating(Recipe recipe) {
        int rating = 3;
        switch (recipe.getDishType()) {
            case SOUP: {
                rating = calculateRatingCaseSoup(rating, recipe);
                break;
            }
            case SALAD: {
                rating = calculateRatingCaseSalad(rating, recipe);
                break;
            }
            case DESSERT: {
                rating = calculateRatingCaseDesert(rating, recipe);
                break;
            }
            default: {
                rating = calculateRatingCaseDefault(rating, recipe);
            }
            break;
        }
        return rating;
    }

    private int calculateRatingCaseSoup(int rating, Recipe recipe) {
        for (Product product : recipe.getProductList()
        ) {
            if (product.getType().equals(ProductType.VEGETABLE)) {
                rating++;
            } else if (product.getType().equals(ProductType.FRUIT)) {
                rating -= 2;
            }
        }
        return rating;
    }

    private int calculateRatingCaseSalad(int rating, Recipe recipe) {
        for (Product product : recipe.getProductList()
        ) {
            if (product.getType().equals(ProductType.VEGETABLE) || product.getType().equals(ProductType.FRUIT)) {
                rating++;
            }

        }
        return rating;
    }

    private int calculateRatingCaseDesert(int rating, Recipe recipe) {
        for (Product product : recipe.getProductList()
        ) {
            if (product.getName().equalsIgnoreCase("шоколад")) {
                rating += 3;
            }
            if (product.getType().equals(ProductType.VEGETABLE)) {
                rating -= 2;
            } else if (product.getType().equals(ProductType.FRUIT)) {
                rating++;
            }
        }
        return rating;
    }

    private int calculateRatingCaseDefault(int rating, Recipe recipe) {
        for (Product product : recipe.getProductList()
        ) {
            if (product.getType().equals(ProductType.VEGETABLE)) {
                rating++;
            } else if (product.getType().equals(ProductType.FRUIT)) {
                rating++;
            } else {
                rating--;
            }
        }
        return rating;
    }
}
