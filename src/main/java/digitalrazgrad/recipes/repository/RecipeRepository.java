package digitalrazgrad.recipes.repository;

import digitalrazgrad.recipes.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
