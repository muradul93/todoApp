package com.murad.todoApp.repositories;

import com.murad.todoApp.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, Integer> {
	 UsersModel findByUsername(String username);
	 List<UsersModel> findAll();
}
