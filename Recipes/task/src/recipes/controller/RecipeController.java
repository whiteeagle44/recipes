package recipes.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe) {
        Long id = recipeService.addRecipe(recipe);
        return Map.of("id", id);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        return recipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/api/recipe/search", params = "category")
    public List<Recipe> getRecipesByCategory(@RequestParam String category) {
        return recipeService.getRecipesByCategory(category);
    }

    @GetMapping(value = "/api/recipe/search", params = "name")
    public List<Recipe> getRecipesByName(@RequestParam String name) {
        return recipeService.getRecipesByName(name);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> putRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        try {
            recipeService.updateRecipe(id, recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
