package com.qa.service.repository;

public interface AuthorRepository {

	String getAll();

	String getOne(Long id);

	String create(String bookJson);

	String delete(Long id);
	
}
