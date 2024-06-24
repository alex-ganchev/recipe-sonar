package digitalrazgrad.recipes;

import digitalrazgrad.recipes.entity.DishType;
import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.entity.ProductType;
import digitalrazgrad.recipes.entity.Recipe;
import digitalrazgrad.recipes.repository.ProductRepository;
import digitalrazgrad.recipes.repository.RecipeRepository;
import digitalrazgrad.recipes.service.RecipeService;
import digitalrazgrad.recipes.service.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    RecipeService recipeService;
    @Mock
    ProductRepository productRepository;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    ValidationService validationService;
    @Mock
    Model model;
    @Mock
    BindingResult bindingResult;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddRecipeReturnValidResultWithCorrectInput() {
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.DESSERT);
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        recipeService.addRecipe(testRecipe, bindingResult, model);
        //THEN
        verify(recipeRepository, times(1)).save(testRecipe);
        verify(model, times(4)).addAttribute(anyString(), any());
    }

    @Test
    void testAddRecipeReturnValidResultWithIncorrectInput() {
        //GIVEN
        Recipe testRecipe = new Recipe();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        recipeService.addRecipe(testRecipe, bindingResult, model);
        //THEN
        verify(recipeRepository, never()).save(testRecipe);
        verify(model, times(2)).addAttribute(anyString(), any());
    }

    @Test
    void testEditRecipeReturnValidResultWithCorrectInputGet() {
        //GIVEN
        Recipe testRecipe = new Recipe();
        when(recipeRepository.findById(testRecipe.getId())).thenReturn(Optional.of(testRecipe));
        //WHEN
        recipeService.editRecipe(testRecipe.getId(), model);
        //THEN
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testEditRecipeReturnValidResultWithIncorrectInputGet() {
        //GIVEN
        Recipe testRecipe = new Recipe();
        when(recipeRepository.findById(testRecipe.getId())).thenReturn(Optional.empty());
        //WHEN
        recipeService.editRecipe(testRecipe.getId(), model);
        //THEN
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    void testEditRecipeReturnValidResultWithCorrectInputPost() {
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.DESSERT);
        when(bindingResult.hasErrors()).thenReturn(false);
        //WHEN
        recipeService.editRecipe(testRecipe, bindingResult, model);
        //THEN
        verify(recipeRepository, times(1)).save(testRecipe);
        verify(model, times(1)).addAttribute(anyString(), any());
    }
    @Test
    void testEditRecipeReturnValidResultWithIncorrectInputPost() {
        //GIVEN
        Recipe testRecipe = new Recipe();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        recipeService.editRecipe(testRecipe, bindingResult, model);
        //THEN
        verify(recipeRepository, never()).save(testRecipe);
        verify(model, times(2)).addAttribute(anyString(), any());
    }
    @Test
    void testCalculateRecipeRatingWithSoupType(){
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.SOUP);
        //WHEN
        int result = recipeService.calculateRecipeRating(testRecipe);
        //THEN
        Assertions.assertEquals(2,result);
    }
    @Test
    void testCalculateRecipeRatingWithDessertType(){
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.DESSERT);
        //WHEN
        int result = recipeService.calculateRecipeRating(testRecipe);
        //THEN
        Assertions.assertEquals(5,result);
    }
    @Test
    void testCalculateRecipeRatingWithSaladType(){
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.SALAD);
        //WHEN
        int result = recipeService.calculateRecipeRating(testRecipe);
        //THEN
        Assertions.assertEquals(5,result);
    }
    @Test
    void testCalculateRecipeRatingWithMainType(){
        //GIVEN
        Recipe testRecipe = createTestRecipe(DishType.MAIN);
        //WHEN
        int result = recipeService.calculateRecipeRating(testRecipe);
        //THEN
        Assertions.assertEquals(4,result);
    }
    private Recipe createTestRecipe(DishType dishType){
        Recipe testRecipe = new Recipe();
        testRecipe.setDishType(dishType);
        Set<Product> testProductList = new HashSet<>();
        Product testProduct1 = new Product();
        Product testProduct2 = new Product();
        Product testProduct3 = new Product();
        testProduct1.setType(ProductType.VEGETABLE);
        testProduct2.setType(ProductType.FRUIT);
        testProduct3.setType(ProductType.PLANT);
        testProduct1.setName("Салата");
        testProduct2.setName("Ягоди");
        testProduct3.setName("ШоКоЛаД");
        testProductList.add(testProduct1);
        testProductList.add(testProduct2);
        testProductList.add(testProduct3);
        testRecipe.setProductList(testProductList);
        return testRecipe;
    }

}
