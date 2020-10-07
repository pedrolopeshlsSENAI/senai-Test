package br.com.prova.repository;


import br.com.prova.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserModel, Long>{
}
