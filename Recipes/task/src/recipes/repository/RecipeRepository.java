package recipes.repository;

import recipes.model.Recipe;

public interface RecipeRepository {
    Long add(Recipe recipe);
    Recipe get(Long id);
}
