package digitalrazgrad.recipes;

import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.repository.ProductRepository;
import digitalrazgrad.recipes.service.ProductService;
import digitalrazgrad.recipes.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    ValidationService validationService;
    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;

    @Test
    void testAddProductReturnValidResultWithCorrectInput() {
        //GIVEN
        Product testProduct = new Product();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(validationService.checkDuplicateProductName(testProduct)).thenReturn(false);
        //WHEN
        productService.addProduct(testProduct,bindingResult,model);
        //THEN
        verify(productRepository, times(1)).save(testProduct);
        verify(model, times(3)).addAttribute(anyString(),any());
    }
    @Test
    void testAddProductReturnValidResultWithIncorrectInput() {
        //GIVEN
        Product testProduct = new Product();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        productService.addProduct(testProduct,bindingResult,model);
        //THEN
        verify(productRepository, never()).save(testProduct);
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    void testDeleteProductReturnValidResultWithCorrectInput() {
        //GIVEN
        Product testProduct = new Product();
        when(validationService.checkSafeDelete(testProduct.getId())).thenReturn(true);
        //WHEN
        productService.deleteProduct(testProduct.getId(), model);
        //THEN
        verify(productRepository, times(1)).deleteById(testProduct.getId());
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    void testDeleteProductReturnValidResultWithIncorrectInput() {
        //GIVEN
        Product testProduct = new Product();
        when(validationService.checkSafeDelete(testProduct.getId())).thenReturn(false);
        //WHEN
        productService.deleteProduct(testProduct.getId(), model);
        //THEN
        verify(productRepository, never()).deleteById(testProduct.getId());
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    void testEditProductReturnValidResultWithCorrectIdGet() {
        //GIVEN
        Product testProduct = new Product();
        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        //WHEN
        productService.editProduct(testProduct.getId(), model);
        //THEN
        verify(model, times(2)).addAttribute(anyString(),any());
    }
    @Test
    void testEditProductReturnValidResultWithIncorrectIdGet() {
        //GIVEN
        Product testProduct = new Product();
        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.empty());
        //WHEN
        productService.editProduct(testProduct.getId(), model);
        //THEN
        verify(model, never()).addAttribute(anyString(),any());
    }
    @Test
    void testEditProductReturnValidResultWithCorrectInputPost() {
        //GIVEN
        Product testProduct = new Product();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(validationService.checkDuplicateProductName(testProduct)).thenReturn(false);
        //WHEN
        productService.editProduct(testProduct,bindingResult,model);
        //THEN
        verify(productRepository, times(1)).save(testProduct);
        verify(model, times(1)).addAttribute(anyString(),any());
    }
    @Test
    void testEditProductReturnValidResultWithIncorrectInputPost() {
        //GIVEN
        Product testProduct = new Product();
        when(bindingResult.hasErrors()).thenReturn(true);
        //WHEN
        productService.editProduct(testProduct,bindingResult,model);
        //THEN
        verify(productRepository, never()).save(testProduct);
        verify(model, times(2)).addAttribute(anyString(),any());
    }
}
