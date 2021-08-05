package recipes.repository;

import org.springframework.stereotype.Repository;
import recipes.model.Recipe;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {
    private final Map<Long, Recipe> recipes = new HashMap<>();
    private static Long RECIPES_COUNT = 1L;

    @Override
    public Long add(Recipe recipe) {
        recipes.put(RECIPES_COUNT, recipe);
        return RECIPES_COUNT++;
    }

    @Override
    public Recipe get(Long id) {
        return recipes.get(id);
    }
}
