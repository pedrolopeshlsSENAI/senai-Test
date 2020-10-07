package com.senaitest.demo.Controllers;

import com.senaitest.demo.Repository.UserRepo;
import com.senaitest.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserRepo repository;

    @PostMapping
    @CrossOrigin
    public Object create(@RequestBody UserModel user){
        try {
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
    public Object update(@RequestBody UserModel newUser, @PathVariable int id){
        try{
            UserModel user = repository.findById(id).get();

            user.setName(newUser.getName());
            //user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());

            return repository.save(user);
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public Object updatePassword(@RequestBody UserModel newUser, @PathVariable int id){
        try{
            UserModel user = repository.findById(id).get();
            user.setPassword(newUser.getPassword());
            return repository.save(user);

        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public Object getById(@PathVariable int id){
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
    public Boolean delete(@PathVariable int id){
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
