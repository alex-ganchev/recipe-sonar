package digitalrazgrad.recipes.repository;

import digitalrazgrad.recipes.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(String name);
}
