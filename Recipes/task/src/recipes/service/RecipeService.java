package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public long add(Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }

    public Optional<Recipe> get(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> getByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public void update(Long id, Recipe recipe) {
        Recipe oldRecipe = recipeRepository.findById(id).orElseThrow();
        oldRecipe.setName(recipe.getName());
        oldRecipe.setCategory(recipe.getCategory());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setDirections(recipe.getDirections());
        recipeRepository.save(oldRecipe);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
