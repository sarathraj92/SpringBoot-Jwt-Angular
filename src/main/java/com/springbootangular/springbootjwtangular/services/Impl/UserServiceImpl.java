package com.springbootangular.springbootjwtangular.services.Impl;

import com.springbootangular.springbootjwtangular.dao.RoleRepository;
import com.springbootangular.springbootjwtangular.dao.UserRepository;
import com.springbootangular.springbootjwtangular.entity.User;
import com.springbootangular.springbootjwtangular.entity.UserRole;
import com.springbootangular.springbootjwtangular.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles){

        User local=userRepository.findByUsername(user.getUsername());

        if(local !=null){
            System.out.println("user is already there");
            throw new DataIntegrityViolationException("User is already present in database");
        }else{
            for(UserRole userRole: userRoles){
                roleRepository.save(userRole.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            user.setImage("default.png");
            user.setEnabled(true);
            user.setDeleted(false);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            local=userRepository.save(user);
        }

        return local;
    }


    @Override
    public User getUser(String userName) {
        System.out.println(userName);
        User user= userRepository.findByUsername(userName);
        System.out.println(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllBy();
    }

    @Override
    public User getUser(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User updateUser(User user) {
        User user1=userRepository.findByUsername(user.getUsername());
        System.out.println("service" + user1.getUsername());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user1.setGender(user.getGender());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
        return user1;
    }

    @Override
    public void deleteUser(String userName) {
        User user=userRepository.findByUsername(userName);
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void updateImage(User user, String fileName) {
        User user1=userRepository.findByUsername(user.getUsername());
        user1.setImage(fileName);
        userRepository.save(user1);

    }


}
