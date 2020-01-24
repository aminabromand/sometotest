package com.test.felix.bookshelf.service.impl;

import com.test.felix.bookshelf.inventory.api.*;
import com.test.felix.bookshelf.service.api.BookInventoryNotRegisteredRuntimeException;
import com.test.felix.bookshelf.service.api.BookshelfService;
import com.test.felix.bookshelf.service.api.InvalidCredentialsException;
import com.test.felix.bookshelf.service.api.SessionNotValidRuntimeException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BookshelfServiceImpl implements BookshelfService{

	private String sessionId;
	private BundleContext context;

	public BookshelfServiceImpl(BundleContext context) {
		this.context = context;
	}

	@Override public Set<String> getGroups( String sessionId ){
		return null;
	}

	@Override public void addBook( String session, String isbn, String title, String author, String category, int rating )
					throws BookAlreadyExistsException, InvalidBookException{
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		MutableBook book;

		book = inventory.createBook( isbn );
		book.setAuthor( author );
		book.setCategory( category );
		book.setRating( rating );
		book.setTitle( title );
		inventory.storeBook( book );

	}

	@Override public void modifyBookCategory( String session, String isbn, String category )
					throws BookNotFoundException, InvalidBookException{

	}

	@Override public void modifyBookRating( String session, String isbn, int rating ) throws BookNotFoundException, InvalidBookException{

	}

	@Override public void removeBook( String session, String isbn ) throws BookNotFoundException{

	}

	@Override public Book getBook( String session, String isbn ) throws BookNotFoundException{
		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();
		return inventory.loadBook(isbn);
	}

	private BookInventory lookupBookInventory() {
		String name = BookInventory.class.getName();
		ServiceReference ref = this.context.getServiceReference(name);
		if (ref == null)
		{
			throw new BookInventoryNotRegisteredRuntimeException(name);
		}
		return (BookInventory) this.context.getService(ref);
	}

	@Override public Set<String> searchBooksByCategory( String session, String categoryLike ){
		return null;
	}

	@Override public Set<String> searchBooksByAuthor( String session, String authorLike ){

		checkSession(sessionId);
		BookInventory inventory = lookupBookInventory();

		Map<BookInventory.SearchCriteria, String> criterias = new HashMap<BookInventory.SearchCriteria, String>(  );
		criterias.put( BookInventory.SearchCriteria.AUTHOR_LIKE, authorLike );

		return inventory.searchBooks( criterias );
	}

	@Override public Set<String> searchBooksByTitle( String session, String titleLike ){
		return null;
	}

	@Override public Set<String> searchBooksByRating( String session, int ratingLower, int ratingUpper ){
		return null;
	}

	@Override public String login( String username, char[] password ) throws InvalidCredentialsException{
			if ("admin".equals(username) &&
							Arrays.equals(password, "admin".toCharArray()))
			{
				this.sessionId =
								Long.toString(System.currentTimeMillis());
				return this.sessionId;
			}
			throw new InvalidCredentialsException(username);
	}

	@Override public void logout( String sessionId ){
			checkSession(sessionId);
			this.sessionId = null;
	}

	protected void checkSession(String sessionId) {
		if (!sessionIsValid(sessionId)) {
			throw new SessionNotValidRuntimeException(sessionId);
		}
	}

	@Override public boolean sessionIsValid( String sessionId ){
		return this.sessionId!=null && this.sessionId.equals(sessionId);
	}
}
