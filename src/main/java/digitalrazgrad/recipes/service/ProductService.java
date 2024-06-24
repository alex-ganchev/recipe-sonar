package digitalrazgrad.recipes.service;

import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.entity.ProductType;
import digitalrazgrad.recipes.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class ProductService {
    private ValidationService validationService;
    private ProductRepository productRepository;

    public ProductService(ValidationService validationService, ProductRepository productRepository) {
        this.validationService = validationService;
        this.productRepository = productRepository;
    }

    public String addProduct(Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || validationService.checkDuplicateProductName(product)) {
            model.addAttribute("typeList", ProductType.values());
            model.addAttribute("duplicate_message", bindingResult.hasErrors() ? null : "Вече има продукт с това име!");
            return "product/add";
        }
        model.addAttribute("message", validationService.checkSaveSuccess(productRepository.save(product)));
        model.addAttribute("product", new Product());
        model.addAttribute("typeList", ProductType.values());
        // Понеже искам след успешен запис, потребителя да си остане във формата за въвеждане за продукт,
        // затова са и горните два модела. Могат да се спестят само, ако се ползва redirect,
        // но тогава няма да имам съобщението за успешен/неуспешен запис.
        return "/product/add";
    }

    public String deleteProduct(Long id, Model model) {
        if (validationService.checkSafeDelete(id)) {
            productRepository.deleteById(id);
            model.addAttribute("message", validationService.checkDeleteSuccess(productRepository.existsById(id)));
            model.addAttribute("productsList", productRepository.findAll());
            return ("/product/list");
        }
        model.addAttribute("safe_delete_message", "Продукта участва в рецепта и не може да бъде изтрит!");
        model.addAttribute("productsList", productRepository.findAll());
        return ("/product/list");
    }

    public String editProduct(Long id, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            model.addAttribute("product", product);
            model.addAttribute("typeList", ProductType.values());
            return ("/product/edit");
        }
        return ("/product/list");
    }

    public String editProduct(Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || validationService.checkDuplicateProductName(product)) {
            model.addAttribute("typeList", ProductType.values());
            model.addAttribute("duplicate_message", bindingResult.hasErrors() ? null : "Вече има продукт с това име!");
            return "product/edit";
        }
        model.addAttribute("message", validationService.checkSaveSuccess(productRepository.save(product)));
        return "redirect:/product/list";
    }
}
