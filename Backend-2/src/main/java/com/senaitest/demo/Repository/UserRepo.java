package com.senaitest.demo.Repository;

import org.springframework.data.repository.CrudRepository;
import com.senaitest.demo.model.UserModel;

public interface UserRepo extends CrudRepository<UserModel, Integer> {

}
