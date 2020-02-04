package com.test.felix.bookshelf.service.tui.api;

import com.test.felix.bookshelf.inventory.api.Book;
import com.test.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import com.test.felix.bookshelf.inventory.api.InvalidBookException;
import com.test.felix.bookshelf.service.api.InvalidCredentialsException;
import org.apache.felix.service.command.Descriptor;

import java.util.Set;

public interface BookshelfServiceProxy{
	@Descriptor("Search books by author, title, or category") Set<Book> search( @Descriptor("username") String username,
					@Descriptor("password") String password,
					@Descriptor("search on attribute: author, title, or category") String attribute,
					@Descriptor("match like (use % at the beginning or end of <like> for wild-card)") String filter )
					throws InvalidCredentialsException;

	@Descriptor("Search books by rating") Set<Book> search( @Descriptor("username") String username,
					@Descriptor("password") String password, @Descriptor("search on attribute: rating") String attribute,
					@Descriptor("lower rating limit (inclusive)") int lower, @Descriptor("upper rating limit (inclusive)") int upper )
					throws InvalidCredentialsException;

	String add( @Descriptor("username") String username, @Descriptor("password") String password, @Descriptor("ISBN") String isbn,
					@Descriptor("Title") String title, @Descriptor("Author") String author, @Descriptor("Category") String category,
					@Descriptor("Rating (0..10)") int rating )
									throws InvalidCredentialsException, BookAlreadyExistsException, InvalidBookException;
}
