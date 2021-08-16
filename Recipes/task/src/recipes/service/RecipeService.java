package recipes.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public long add(Recipe recipe, UserDetails user) {
        recipe.setUser(userRepository.findUserByEmail(user.getUsername()).get());
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

    public void update(Long id, Recipe recipe, User author) {
        Recipe oldRecipe = recipeRepository.findById(id).orElseThrow();
        if (!oldRecipe.getUser().equals(author)) {
            throw new AccessControlException("Only the original author of the recipe is allowed to update it.");
        }
        oldRecipe.setName(recipe.getName());
        oldRecipe.setCategory(recipe.getCategory());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setDirections(recipe.getDirections());
        recipeRepository.save(oldRecipe);
    }

    public void delete(Long id, User author) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        recipe.orElseThrow(() -> new EmptyResultDataAccessException(1));
        if (!recipe.get().getUser().equals(author)) {
            throw new AccessControlException("Only the original author of the recipe is allowed to delete it.");
        }
        recipeRepository.deleteById(id);
    }
}
