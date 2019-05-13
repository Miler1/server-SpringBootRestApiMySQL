package com.miler.spring.restapi.mysql.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miler.spring.restapi.mysql.model.Pessoa;
import com.miler.spring.restapi.mysql.repo.PessoaRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class PessoaController {

	@Autowired
	PessoaRepository repository;

	@GetMapping("/pessoas")
	public ResponseEntity<List<Pessoa>> getAllPessoas() {
		List<Pessoa> pessoas = new ArrayList<>();
		try {
			repository.findAll().forEach(pessoas::add);
			
			if (pessoas.isEmpty()) {
				System.out.println("Vazio");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			System.out.println(pessoas);
			for (Pessoa pessoa : pessoas) {
				System.out.println(pessoa.getNome());
			}
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> getPessoasById(@PathVariable("id") Integer id) {
		Optional<Pessoa> pessoaData = repository.findById(id);

		if (pessoaData.isPresent()) {
			return new ResponseEntity<>(pessoaData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/pessoas")
	public ResponseEntity<Pessoa> postPessoas(@RequestBody Pessoa pessoa) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedDate;
		try {
			parsedDate = dateFormat.parse(pessoa.getDataNascimento().toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		try {
			Pessoa _pessoa = repository.save(new Pessoa(pessoa.getNome(), pessoa.getCpf(), 
					pessoa.getDataNascimento(), pessoa.getPeso(), pessoa.getUf()));
			return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") Integer id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/pessoas")
	public ResponseEntity<HttpStatus> deleteAllPessoa() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping(value = "pessoas/id/{age}")
	public ResponseEntity<List<Pessoa>> findById(@PathVariable int age) {
		try {
			List<Pessoa> pessoas = repository.findById(age);

			if (pessoas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(pessoas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") Integer id, @RequestBody Pessoa pessoa) {
		Optional<Pessoa> pessoaData = repository.findById(id);
	
		if (pessoaData.isPresent()) {
			Pessoa _pessoa = pessoaData.get();
			_pessoa.setNome(pessoa.getNome());
			_pessoa.setCpf(pessoa.getCpf());
			_pessoa.setDataNascimento(pessoa.getDataNascimento());
			_pessoa.setPeso(pessoa.getPeso());
			_pessoa.setUf(pessoa.getUf());
			return new ResponseEntity<>(repository.save(_pessoa), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
