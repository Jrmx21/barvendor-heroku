package com.davidruiz.barvendor.Users;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepository userRepository;
    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }
    public Optional<UserModel> getUserById(Long id){
        return userRepository.findById(id);
    }
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    //Modificar un usuario
    public UserModel updateUser(Long id, UserModel user){
        UserModel userFound = userRepository.findById(id).get();
        userFound.setFirstName(user.getFirstName());
        userFound.setLastName(user.getLastName());
        userFound.setEmail(user.getEmail());
        userFound.setUsername(user.getUsername());
        userFound.setRole(user.getRole());
        userFound.setPassword(user.getPassword());
        return userRepository.save(userFound);
    }
    public Boolean deleteUserById(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
