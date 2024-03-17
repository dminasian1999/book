package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}
	
	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}
	
	@Override
	public Stream<Book> findByAuthorsName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Book> findByPublisherPublisherName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	@Override
	public Optional<Book> findById(String isbn) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteById(String isbn) {
		// TODO Auto-generated method stub

	}

}
