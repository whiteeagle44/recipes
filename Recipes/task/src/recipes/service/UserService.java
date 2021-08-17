package recipes.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import javax.management.InstanceAlreadyExistsException;
import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, RecipeRepository recipeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void add(User user) throws InstanceAlreadyExistsException {
        if (userRepository.findUserByEmail(user.getEmail()).isEmpty()) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } else {
            throw new InstanceAlreadyExistsException();
        }
    }

    @Transactional
    public void delete(UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername()).get();
        recipeRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
