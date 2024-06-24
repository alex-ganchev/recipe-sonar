package digitalrazgrad.recipes.service;

import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationService {
    ProductRepository productRepository;

    public ValidationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String checkSaveSuccess(Object object) {
        if (object != null) {
            return "Успешен запис в базата данни!";
        } else {
            return "Неуспешен запис в базата данни! :(";
        }
    }

    public String checkDeleteSuccess(boolean flag) {
        if (!flag) {
            return "Успешено изтриване от базата данни!";
        } else {
            return "Неуспешено изтриване от базата данни!";
        }
    }

       public Boolean checkDuplicateProductName(Product product) {
           if(productRepository.findByName(product.getName()).isEmpty() || productRepository.findByName(product.getName()).get(0).getId().equals(product.getId())){
               return false;
           }
            return true;
        }

    public Boolean checkSafeDelete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent() && optionalProduct.get().getRecipeList().isEmpty()) {
            return true;
        }
        return false;
    }
}
