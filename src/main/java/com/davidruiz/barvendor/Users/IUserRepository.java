package com.davidruiz.barvendor.Users;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Long>{
    Optional<UserModel> findByUsername(String username);

    UserModel findByUsernameAndPassword(String username, String password);
}
