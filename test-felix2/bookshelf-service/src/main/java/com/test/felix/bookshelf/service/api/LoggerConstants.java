package com.test.felix.bookshelf.service.api;

public interface LoggerConstants{

	String LOG_ADD_BOOK =
					"LOG_ADD_BOOK: Add book: [isbn={0}] [title={1}] "+
									"[author={2}] [category={3}] [rating={4}]";
	String LOG_CREATE_BOOK =
					"LOG_CREATE_BOOK: Create new book [isbn={0}]";
	String LOG_STORE_BOOK =
					"LOG_STORE_BOOK: Store book [isbn={0}]";

}
