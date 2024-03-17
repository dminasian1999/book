package telran.java51.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java51.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher,String> {

//	@Query("select distinct p.publisherName from Book b join b.authors a join b.publisher p where a.name=?1")
//	List<String> findByPublishersAuthor(String authorName);
	
	Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);
}
