package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.qa.domain.Author;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
public class AuthorDBRepository implements AuthorRepository{
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Inject
	private JSONUtil util;
	
	public String getAll() {
		TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a ORDER BY a.id", Author.class);
		return util.getJSONForObject(query.getResultList());
	}

	public String getOne(Long id) {
		return util.getJSONForObject(em.find(Author.class, id));
	}

	@Transactional(REQUIRED)
	public String create(String authorJson) {
		Author author = util.getObjectForJSON(authorJson, Author.class);
		em.persist(author);
		return "{\"message\": \"Author successfully created\"}";
	}
	
	@Transactional(REQUIRED)
	public String delete(Long id) {
		Author authorToDelete = em.find(Author.class, id);
		if(authorToDelete.equals(null)) {
			return "{\"message\": \"ERROR: Author not found\"}";
		} else {
			em.remove(authorToDelete);
			return "{\"message\": \"Author successfully deleted\"}";
		}
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}
	
}
