package recipes.service;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepositoryImpl;

@Service
public class RecipeService {
    private final RecipeRepositoryImpl recipeRepository;

    public RecipeService(RecipeRepositoryImpl recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.get(id);
    }

    public long addRecipe(Recipe recipe) {
        return recipeRepository.add(recipe);
    }
}
