package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(em.find(Publisher.class, publisher));
	}
	
	@Override
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
		return publisher;
	}
	
	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
