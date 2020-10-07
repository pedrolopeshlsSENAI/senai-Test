package br.com.prova.controller;

import br.com.prova.model.UserModel;
import br.com.prova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    @CrossOrigin
    public Object createUser(@RequestBody UserModel user){
        try{
            return repository.save(user);

        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    @CrossOrigin
    public List<UserModel> listuser(){
        try {
            List<UserModel> users = (List<UserModel>) repository.findAll();

            for(int i = 0; i < users.size(); i++){
                UserModel user = users.get(i);
                user.setPassword(null);
            }
            return users;

        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public Object updateUser(@RequestBody UserModel newUser, @PathVariable long id){
        try{
            System.out.println(id);
            UserModel user = repository.findById(id).get();

            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());

            return repository.save(user);
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public Object getById(@PathVariable long id){
        try {
            UserModel user = repository.findById(id).get();
            user.setPassword(null);
            return user;

        }catch (Exception e){
            System.out.println(e);
            return "User not found!";
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public Boolean delete(@PathVariable long id){
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
