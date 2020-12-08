package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Promotion;
import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.repository.UserRepository;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        if (!allUsers.isEmpty()) {
            return ResponseEntity.ok(allUsers);

        } else {
            return ResponseEntity.notFound().build();

        }
    }


    @PostMapping
    public ResponseEntity registerUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userRepository.save(user));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id,
                                   @RequestBody User user) {

        if(userRepository.existsById(id)) {
            user.setId(id);
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity editUserCpf(@PathVariable Integer id,
                                      @RequestBody User user) {

        if(userRepository.existsById(id)) {
            user.setId(id);
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){

        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);

            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity getUserByPhone(@PathVariable String phone) {
        List<User> user;

        if(userRepository.existsByPhone(phone)) {
            user = userRepository.findByPhone(phone);

            return ResponseEntity.ok(user);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity getUserByCpf(@PathVariable String cpf) {
        User user;

        if(userRepository.existsByCpf(cpf)) {
            user = userRepository.findByCpf(cpf);

            return ResponseEntity.ok(user);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        Optional<User> user;

        if(userRepository.existsById(id)) {
            user = userRepository.findById(id);

            return ResponseEntity.of(user);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}








