package com.test.felix.bookshelf.service.api;

import com.test.felix.bookshelf.inventory.api.Book;
import com.test.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import com.test.felix.bookshelf.inventory.api.BookNotFoundException;
import com.test.felix.bookshelf.inventory.api.InvalidBookException;

import java.util.Set;

public interface BookshelfService extends Authentication{
	Set<String> getGroups( String sessionId );
	void addBook( String session, String isbn, String title, String author, String category, int rating ) throws BookAlreadyExistsException,
					InvalidBookException;
	void modifyBookCategory( String session, String isbn, String category )
					throws BookNotFoundException, InvalidBookException;
	void modifyBookRating( String session, String isbn, int rating )
					throws BookNotFoundException, InvalidBookException;
	void removeBook( String session, String isbn )
					throws BookNotFoundException;
	Book getBook( String session, String isbn )
					throws BookNotFoundException;
	Set<String> searchBooksByCategory( String session, String categoryLike );
	Set<String> searchBooksByAuthor( String session, String authorLike );
	Set<String> searchBooksByTitle( String session, String titleLike );
	Set<String> searchBooksByRating( String session, int ratingLower, int ratingUpper );
}
