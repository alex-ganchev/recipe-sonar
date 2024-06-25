package digitalrazgrad.recipes;

import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.entity.Recipe;
import digitalrazgrad.recipes.repository.ProductRepository;
import digitalrazgrad.recipes.service.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {
    @InjectMocks
    ValidationService validationService;
    @Mock
    ProductRepository productRepository;

    @Test
    void testCheckSaveSuccessWithCorrectInput() {
        //GIVEN
        Object testObject = new Object();
        //WHEN
        String result = validationService.checkSaveSuccess(testObject);
        //THEN
        Assertions.assertEquals("Успешен запис в базата данни!", result);
    }
    @Test
    void testCheckSaveSuccessWithIncorrectInput() {
        //GIVEN
        Object testObject = null;
        //WHEN
        String result = validationService.checkSaveSuccess(testObject);
        //THEN
        Assertions.assertEquals("Неуспешен запис в базата данни! :(", result);
    }
    @Test
    void testCheckDuplicateProductNameReturnFalseWhenTestWithNoDuplicateName(){
        //GIVEN
        Product testProduct = new Product();
        List<Product> emptyList = new ArrayList<>();
        when(productRepository.findByName(testProduct.getName())).thenReturn(emptyList);
        //WHEN
        boolean result = validationService.checkDuplicateProductName(testProduct);
        //THEN
       Assertions.assertFalse(result);
    }
    @Test
    void testCheckDuplicateProductNameReturnTrueWhenTestWithDuplicateNameAndDifferentId(){
        //GIVEN
        Product testProduct = new Product();
        Product existProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("SameName");
        existProduct.setId(2L);
        existProduct.setName("SameName");
        List<Product> list = new ArrayList<>();
        list.add(existProduct);
        when(productRepository.findByName(testProduct.getName())).thenReturn(list);
        //WHEN
        boolean result = validationService.checkDuplicateProductName(testProduct);
        //THEN
        Assertions.assertTrue(result);
    }
    @Test
    void testCheckDuplicateProductNameReturnFalseWhenTestWithDuplicateNameAndSameId(){
        //GIVEN
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("SameName");
        List<Product> list = new ArrayList<>();
        list.add(testProduct);
        when(productRepository.findByName(testProduct.getName())).thenReturn(list);
        //WHEN
        boolean result = validationService.checkDuplicateProductName(testProduct);
        //THEN
        Assertions.assertFalse(result);
    }
    @Test
    void testCheckSafeDeleteReturnTrueWhenIdIsCorrectAndRecipeListIsEmpty() {
        //GIVEN
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setRecipeList(new HashSet<>());
        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        //WHEN
        boolean result = validationService.checkSafeDelete(testProduct.getId());
        //THEN
        Assertions.assertTrue(result);
    }
    @Test
    void testCheckSafeDeleteReturnFalseWhenIdIsCorrectAndRecipeListIsNotEmpty() {
        //GIVEN
        Product testProduct = new Product();
        testProduct.setId(1L);
        Set<Recipe> recipesSet = new HashSet<>();
        recipesSet.add(new Recipe());
        testProduct.setRecipeList(recipesSet);
        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        //WHEN
        boolean result = validationService.checkSafeDelete(testProduct.getId());
        //THEN
        Assertions.assertFalse(result);
    }
    @Test
    void testCheckSafeDeleteReturnFalseWhenIdIsIncorrectAndRecipeListIsEmpty() {
        //GIVEN
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setRecipeList(new HashSet<>());
        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.empty());
        //WHEN
        boolean result = validationService.checkSafeDelete(testProduct.getId());
        //THEN
        Assertions.assertFalse(result);
    }
    @Test
    void testCheckDeleteSuccessWithCorrectInput() {
        //GIVEN
        boolean testFlag = false;
        //WHEN
        String result = validationService.checkDeleteSuccess(testFlag);
        //THEN
        Assertions.assertEquals("Успешено изтриване от базата данни!", result);
    }
    @Test
    void testCheckDeleteSuccessWithIncorrectInput() {
        //GIVEN
        boolean testFlag = true;
        //WHEN
        String result = validationService.checkDeleteSuccess(testFlag);
        //THEN
        Assertions.assertEquals("Неуспешено изтриване от базата данни!", result);
    }
}
