package com.springbootangular.springbootjwtangular.controller;

import com.springbootangular.springbootjwtangular.entity.User;
import com.springbootangular.springbootjwtangular.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        List<User>user=userService.getAllUsers();
        System.out.println(user.size());
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{userName}")
    public User getUser(@PathVariable("userName")String userName){
        System.out.println(userName);

        return userService.getUser(userName);
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user){


    return userService.updateUser(user);
    }


    @DeleteMapping("/deleteUser/{userName}")
    public void deleteUser(@PathVariable("userName")String userName){
        userService.deleteUser(userName);


    }

}
