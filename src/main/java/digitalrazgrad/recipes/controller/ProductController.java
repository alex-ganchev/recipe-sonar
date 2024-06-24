package digitalrazgrad.recipes.controller;

import digitalrazgrad.recipes.service.ProductService;
import digitalrazgrad.recipes.entity.Product;
import digitalrazgrad.recipes.entity.ProductType;
import digitalrazgrad.recipes.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    private ProductService productService;

    ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/add")
    private String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("typeList", ProductType.values());
        return "product/add";
    }

    @PostMapping("/add")
    private String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        return productService.addProduct(product, bindingResult, model);
    }

    @GetMapping("/list")
    private String listAllProducts(Model model) {
        model.addAttribute("productsList", productRepository.findAll());
        return "/product/list";
    }

    @PostMapping("/delete")
    private String deleteProduct(@RequestParam Long id, Model model) {
        return productService.deleteProduct(id, model);
    }

    @GetMapping("/edit")
    private String editProduct(@RequestParam Long id, Model model) {
        return productService.editProduct(id, model);
    }

    @PostMapping("/edit")
    private String editProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        return productService.editProduct(product, bindingResult, model);
    }
}
