package com.test.felix.bookshelf.inventory.impl.mock;

import com.test.felix.bookshelf.inventory.api.MutableBook;

public class MutableBookImpl implements MutableBook{

	private String isbn;
	private String author;
	private String title;
	private String category;
	private int rating;

	public MutableBookImpl(String isbn) {
		setIsbn( isbn );
	}

	@Override public void setIsbn( String isbn ){
		this.isbn = isbn;
	}

	@Override public void setTitle( String title ){
		this.title = title;
	}

	@Override public void setAuthor( String author ){
		this.author = author;
	}

	@Override public void setCategory( String category ){
		this.category = category;
	}

	@Override public void setRating( int rating ){
		this.rating = rating;
	}

	@Override public String getIsbn(){
		return isbn;
	}

	@Override public String getTitle(){
		return title;
	}

	@Override public String getAuthor(){
		return author;
	}

	@Override public String getCategory(){
		return category;
	}

	@Override public int getRating(){
		return rating;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append( getCategory() ).append( ": " );
		buf.append( getTitle() ).append( " from " ).append( getAuthor() );
		buf.append( " [" ).append(getRating()).append( "]" );
		return buf.toString();
	}
}
