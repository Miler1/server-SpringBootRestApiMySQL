package com.miler.spring.restapi.mysql.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.miler.spring.restapi.mysql.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Integer>{
	List<Pessoa> findById(int age);
}
