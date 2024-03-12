package telran.java51.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final ModelMapper modelMapper;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;
	
	@Transactional
	@Override
	public boolean addBook(BookDto bookDto) {
		if(bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
		
	}
	@Transactional(readOnly = true)
	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}
	@Transactional
	@Override
	public BookDto removeBook(String isbn) {
		BookDto bookDto = findBookByIsbn(isbn);
		bookRepository.deleteById(isbn);
		return bookDto;
	}
	@Transactional
	@Override
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}
	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		return bookRepository.findByAuthorsName( authorName)
					.map(b->modelMapper.map(b, BookDto.class)).toList();
	}
	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		return bookRepository.findByPublisherPublisherName(publisherName)
				.map(b->modelMapper.map(b, BookDto.class)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<AuthorDto> findBookAuthors(String isbn) {
		return bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new)
				.getAuthors().stream()
				.map(a->modelMapper.map(a, AuthorDto.class))
				.toList();

	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<String> findPublishersByAutyor(String authorName) {
		return bookRepository.findByAuthorsName( authorName)
				.map(b->b.getPublisher().getPublisherName())
				.toList();
	}

	@Transactional
	@Override
	public AuthorDto removeAuthor(String authorName) {
		bookRepository.findByAuthorsName(authorName)
					.forEach(b->b.getAuthors().removeIf(a->a.getName().equalsIgnoreCase(authorName)));
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
		authorRepository.deleteById(authorName);
		return modelMapper.map(author, AuthorDto.class);
	}

}
