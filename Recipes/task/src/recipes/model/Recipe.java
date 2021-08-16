package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "name should not be blank")
    private String name;
    @NotBlank(message = "category should not be blank")
    private String category;
    @UpdateTimestamp
    private LocalDateTime date;
    @NotBlank(message = "description should not be blank")
    private String description;
    @NotEmpty(message = "ingredients should not be blank")
    @Size(min = 1, message = "ingredients must contain at least one element")
    private String[] ingredients;
    @NotEmpty(message = "directions should not be blank")
    @Size(min = 1, message = "directions must contain at least one element")
    private String[] directions;
    @JsonIgnore
    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User author) {
        this.user = author;
    }
}
