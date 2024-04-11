package com.davidruiz.barvendor.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidruiz.barvendor.Models.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Long>{
    // ArrayList<UserModel>
}
