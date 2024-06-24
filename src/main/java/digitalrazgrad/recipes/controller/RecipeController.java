package digitalrazgrad.recipes.controller;

import digitalrazgrad.recipes.service.RecipeService;
import digitalrazgrad.recipes.service.ValidationService;
import digitalrazgrad.recipes.entity.DishType;
import digitalrazgrad.recipes.entity.Recipe;
import digitalrazgrad.recipes.repository.ProductRepository;
import digitalrazgrad.recipes.repository.RecipeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private ProductRepository productRepository;
    private RecipeRepository recipeRepository;
    private ValidationService validationService;
    private RecipeService recipeService;

    public RecipeController(ProductRepository productRepository, RecipeRepository recipeRepository, ValidationService validationService, RecipeService recipeService) {
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
        this.validationService = validationService;
        this.recipeService = recipeService;
    }

    @GetMapping("/add")
    private String addRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("productList", productRepository.findAll());
        model.addAttribute("typeList", DishType.values());
        return "/recipe/add";
    }

    @PostMapping("/add")
    private String addRecipe(@Valid @ModelAttribute Recipe recipe, BindingResult bindingResult, Model model) {
        return recipeService.addRecipe(recipe, bindingResult, model);
    }

    @GetMapping("/list")
    private String listAllRecipes(Model model) {
        model.addAttribute("recipesList", recipeRepository.findAll());
        return "/recipe/list";
    }

    @PostMapping("/delete")
    private String deleteRecipe(@RequestParam Long id, Model model) {
        recipeRepository.deleteById(id);
        model.addAttribute("message", validationService.checkDeleteSuccess(recipeRepository.existsById(id)));
        model.addAttribute("recipesList", recipeRepository.findAll());
        return "/recipe/list";
    }

    @GetMapping("/edit")
    private String editRecipe(@RequestParam Long id, Model model) {
        return recipeService.editRecipe(id, model);
    }

    @PostMapping("/edit")
    private String editRecipe(@Valid @ModelAttribute Recipe recipe, BindingResult bindingResult, Model model) {
        return recipeService.editRecipe(recipe, bindingResult, model);
    }
}
