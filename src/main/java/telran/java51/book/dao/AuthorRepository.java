package telran.java51.book.dao;

import java.util.Optional;

import telran.java51.book.model.Author;

public interface AuthorRepository  {

	Optional<Author> findById(String publisher);
	
	Author save(Author author);
	void deleteById(String authorname);

}
