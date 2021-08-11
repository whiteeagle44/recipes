package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public long addRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipe = recipeRepository.save(recipe);
        return recipe.getId();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public void updateRecipe(Long id, Recipe recipe) {
        Recipe oldRecipe = recipeRepository.findById(id).orElseThrow();
        oldRecipe.setName(recipe.getName());
        oldRecipe.setCategory(recipe.getCategory());
        oldRecipe.setDate(LocalDateTime.now());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setDirections(recipe.getDirections());
        recipeRepository.save(oldRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
