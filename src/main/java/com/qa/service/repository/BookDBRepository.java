package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.domain.Author;
import com.qa.domain.Book;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class BookDBRepository implements BookRepository {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;

	public String getAll() {
		TypedQuery<Book> bookQuery = em.createQuery("SELECT b FROM Book b", Book.class);
		return util.getJSONForObject(bookQuery.getResultList());
	}

	public String getOne(Long id) {
		return util.getJSONForObject(em.find(Book.class, id));
	}

	@Transactional(REQUIRED)
	public String create(String bookJson) {
		Book book = util.getObjectForJSON(bookJson, Book.class);
		em.persist(book);
		return "{\"message\": \"Book successfully created\"}";
	}

	@Transactional(REQUIRED)
	public String delete(Long id) {
		Book bookToDelete = em.find(Book.class, id);
		if(bookToDelete.equals(null)) {
			return "{\"message\": \"ERROR: Book not found\"}";
		} else {
			bookToDelete.setAuthor(null);
			em.remove(bookToDelete);
			return "{\"message\": \"Book successfully deleted\"}";
		}
	}
	
	@Transactional(REQUIRED)
	public String createData() {
		
		Author joeBloggs = new Author("Joe Bloggs");
		
		Author joeBlaggs = new Author("Joe Blaggs");
		
		Book book1 = new Book("Book1", "Adventure", "2001", joeBloggs);
		em.persist(book1);
		
		Book book2 = new Book("Book2: The Sequel", "Fantasy", "2006", joeBloggs);
		em.persist(book2);
		
		Book book3 = new Book("Book3: The Return of the Book", "Sci-Fi", "2011", joeBlaggs);
		em.persist(book3);
		
		Book book4 = new Book("How to write a Book", "Non-Fiction", "2013", joeBlaggs);
		em.persist(book4);
		
		Book book5 = new Book("Book4: The Last Book", "Fantasy", "2015", joeBloggs);
		em.persist(book5);
		
		return "{\"message\": \"Sample data created\"}";
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
