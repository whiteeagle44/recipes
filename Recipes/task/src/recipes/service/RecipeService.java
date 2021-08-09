package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public long addRecipe(Recipe recipe) {
        recipe = recipeRepository.save(recipe);
        return recipe.getId();
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
