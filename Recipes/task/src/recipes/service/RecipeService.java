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

    public long add(Recipe recipe, UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername()).get();
        recipe.setUser(user);
        user.getRecipes().add(recipe);
        userRepository.save(user);
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

    public void update(Long id, Recipe recipe, UserDetails userDetails) {
        Recipe oldRecipe = recipeRepository.findById(id).orElseThrow();

        User user = userRepository.findUserByEmail(userDetails.getUsername()).get();
        if (!oldRecipe.getUser().equals(user)) {
            throw new AccessControlException("Only the original author of the recipe is allowed to update it.");
        }
        oldRecipe.setName(recipe.getName());
        oldRecipe.setCategory(recipe.getCategory());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setDirections(recipe.getDirections());
        recipeRepository.save(oldRecipe);
    }

    public void delete(Long id, UserDetails userDetails) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        recipe.orElseThrow(() -> new EmptyResultDataAccessException(1));

        User user = userRepository.findUserByEmail(userDetails.getUsername()).get();
        if (!recipe.get().getUser().equals(user)) {
            throw new AccessControlException("Only the original author of the recipe is allowed to delete it.");
        }
        recipeRepository.deleteById(id);
        user.getRecipes().remove(recipe.get());
    }
}
