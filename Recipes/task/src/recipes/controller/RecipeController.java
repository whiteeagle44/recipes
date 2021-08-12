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
@RequestMapping("/api/recipe/")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe) {
        Long id = recipeService.add(recipe);
        return Map.of("id", id);
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.get(id);
        return recipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "search", params = "category")
    public List<Recipe> getRecipesByCategory(@RequestParam String category) {
        return recipeService.getByCategory(category);
    }

    @GetMapping(value = "search", params = "name")
    public List<Recipe> getRecipesByName(@RequestParam String name) {
        return recipeService.getByName(name);
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        try {
            recipeService.update(id, recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
