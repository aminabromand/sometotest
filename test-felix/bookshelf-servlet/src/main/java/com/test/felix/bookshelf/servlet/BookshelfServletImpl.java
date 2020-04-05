package com.test.felix.bookshelf.servlet;

import com.test.felix.bookshelf.log.api.BookshelfLogHelper;
import com.test.felix.bookshelf.service.api.BookshelfService;
import com.test.felix.bookshelf.service.api.InvalidCredentialsException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BookshelfServletImpl extends HttpServlet{
	private String alias;
	private BookshelfService service;
	private BookshelfLogHelper logger;
	private String sessionId;

	public void init( ServletConfig config ){
	}


	private static final String PARAM_OP = "op";

	private static final String OP_CATEGORIES = "categories";

	private static final String OP_BYCATEGORY = "byCategory";
	private static final String OP_BYAUTHOR = "byAuthor";
	private static final String PARAM_CATEGORY = "category";
	private static final String PARAM_AUTHOR = "author";

	private static final String OP_ADDBOOKFORM = "addBookForm";
	private static final String PARAM_ISBN = "isbn";
	private static final String PARAM_TITLE = "title";
	private static final String PARAM_RATING = "rating";

	private static final String OP_ADDBOOK = "addBook";
	private static final String OP_LOGINFORM = "loginForm";
	private static final String OP_LOGIN = "login";
	private static final String PARAM_USER = "user";
	private static final String PARAM_PASS = "pass";

	protected void doGet( HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
		String op = req.getParameter(PARAM_OP);
		resp.setContentType("text/html");
		this.logger.debug( "op = " + op + ", session = " + this.sessionId);

		if (OP_LOGIN.equals(op))
		{
			String user = req.getParameter(PARAM_USER);
			String pass = req.getParameter(PARAM_PASS);
			try {
				doLogin(user, pass);
				htmlMainPage(resp.getWriter());
			}
			catch (InvalidCredentialsException e)
			{
				htmlLoginForm(resp.getWriter(), e.getMessage());
			}
			return;
		} else if (OP_LOGINFORM.equals(op) || !sessionIsValid())
		{
			htmlLoginForm(resp.getWriter(), "please login");
			return;
		}
		try {
			if (op == null)
			{
				htmlMainPage(resp.getWriter());
			}
			else if (OP_CATEGORIES.equals(op))
			{
				htmlCategories(resp.getWriter());
			}
			else if (OP_BYCATEGORY.equals(op))
			{
				String category = req.getParameter(PARAM_CATEGORY);
				htmlByCategory(resp.getWriter(), category);
			}
			else if (OP_BYAUTHOR.equals(op))
			{
				String author = req.getParameter(PARAM_AUTHOR);
				htmlByAuthor(resp.getWriter(), author);
			}
			else if (OP_ADDBOOKFORM.equals(op))
			{
				htmlAddBookForm(resp.getWriter());
			}
			else if (OP_ADDBOOK.equals(op))
			{
				htmlTop(resp.getWriter());
				doAddBook(req, resp);
				htmlBottom(resp.getWriter());
			}
			else {
				htmlMainPage(resp.getWriter());
			}
		}
		catch (InvalidCredentialsException e)
		{
			resp.getWriter().write(e.getMessage());
		}
	}


	private void htmlCategories( PrintWriter printWriter) throws InvalidCredentialsException
	{
		htmlTop(printWriter);
		printWriter.println("<h3>Categories:</h3>");
		printWriter.println("<ul>");
		for (String category : this.service.getCategories())
		{
			printWriter.println(
							"<li><a href=\"" + browseByCategoryUrl(category)
											+ "\">" + category + "</li>");
		}
		printWriter.println("</ul>");
		htmlBottom(printWriter);
	}

	private void htmlTop(PrintWriter printWriter) {
		printWriter.println("<!doctype html>");
		printWriter.println("<html>");
		printWriter.println("<head></head>");
		printWriter.println("<body>");

		printWriter.println("<table>");
		printWriter.println("<tr>");
		printWriter.println("<td><a href=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + "main" + "\">Home</a></td>");
		printWriter.println("<td><a href=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + OP_CATEGORIES + "\">by category</a></td>");
		printWriter.println("<td><a href=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + OP_BYAUTHOR + "\">by author</a></td>");
		printWriter.println("<td><a href=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + OP_ADDBOOKFORM + "\">add book</a></td>");
		printWriter.println("</tr>");
		printWriter.println("</table>");

	}

	private void htmlBottom(PrintWriter printWriter) {
		printWriter.println("</body>");
		printWriter.println("</html>");
	}

	private String browseByCategoryUrl(String category) {
		return "?" + PARAM_OP +  "=" + OP_BYCATEGORY + "=" + category;
	}

	private String addBookUrl() {
		return "?" + PARAM_OP +  "=" + OP_ADDBOOK;
	}

	private void doAddBook(
					HttpServletRequest req, HttpServletResponse resp)
					throws IOException
	{
		String isbn = req.getParameter(PARAM_ISBN);
		String category = req.getParameter(PARAM_CATEGORY);
		String author = req.getParameter(PARAM_AUTHOR);
		String title = req.getParameter(PARAM_TITLE);
		String ratingStr = req.getParameter(PARAM_RATING);
		int rating = 0;
		try
		{
			rating = Integer.parseInt(ratingStr);
		}
		catch (NumberFormatException e)
		{
			resp.getWriter().println(e.getMessage());
			return;
		}
		try {
			resp.getWriter().println("Adding book: " + isbn + ", " + title + ", " + author + ", " + category + ", " + rating);
			this.service.addBook(sessionId, isbn, title, author, category, rating);
		}
		catch (Exception e)
		{
			resp.getWriter().println(e.getMessage());
			return;
		}
		resp.getWriter().println("Added!");
	}

	private void htmlMainPage(PrintWriter printWriter){
		htmlTop(printWriter);
		printWriter.println("<h1>Main</h1>");
		htmlBottom(printWriter);
	}

	private void doLogin(String user, String pass) throws InvalidCredentialsException{
		sessionId = this.service.login( user, pass.toCharArray() );
	}

	private void htmlLoginForm(PrintWriter printWriter, String errorMsg) {
		htmlTop(printWriter);

		printWriter.println("<form action=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + OP_LOGIN + "\">");
		printWriter.println("user name:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_USER + "\" value=\"Mickey\" />");
		printWriter.println("<br>");
		printWriter.println("password:<br>");
		printWriter.println("<input type=\"password\" name=\"" + PARAM_PASS + "\" value=\"Mouse\" />");
		printWriter.println("<br><br>");
		printWriter.println("<input type=\"submit\" value=\"Submit\" />");

		printWriter.println("<input type=\"hidden\" name=\"" + PARAM_OP + "\" value=\"" + OP_LOGIN + "\" />");

		printWriter.println("</form>");

		printWriter.println("<p>" + errorMsg + "</p>");

		htmlBottom(printWriter);
	}

	private boolean sessionIsValid() {
		return this.service.sessionIsValid( sessionId );
	}


	private void htmlByCategory(PrintWriter printWriter, String category) {
		htmlTop(printWriter);
		printWriter.println("<h3>Category: "+ category +"</h3>");
		printWriter.println("<ul>");
		for (String book : this.service.searchBooksByCategory( sessionId, category ))
		{
			printWriter.println(
							"<li><a href=\"" + browseByCategoryUrl(book)
											+ "\">" + book + "</li>");
		}
		printWriter.println("</ul>");
		htmlBottom(printWriter);
	}

	private void htmlByAuthor(PrintWriter printWriter, String author) {
		htmlTop(printWriter);
		printWriter.println("<h3>author: " + author + "</h3>");
		printWriter.println("<ul>");
		for (String book : this.service.getCategories())
		{
			printWriter.println(
							"<li><a href=\"" + browseByCategoryUrl(book)
											+ "\">" + book + "</li>");
		}
		printWriter.println("</ul>");
		htmlBottom(printWriter);
	}

	private void htmlAddBookForm(PrintWriter printWriter) {
		htmlTop(printWriter);

		printWriter.println("<form action=\"http://localhost:8080/bookshelf?" + PARAM_OP + "=" + OP_ADDBOOK + "\">");
		printWriter.println("isbn:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_ISBN + "\" value=\"Mickey\" />");
		printWriter.println("<br>");
		printWriter.println("title:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_TITLE + "\" value=\"Mouse\" />");
		printWriter.println("<br>");
		printWriter.println("title:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_AUTHOR + "\" value=\"Mouse\" />");
		printWriter.println("<br>");
		printWriter.println("rating:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_RATING + "\" value=\"Mouse\" />");
		printWriter.println("<br>");
		printWriter.println("rating:<br>");
		printWriter.println("<input type=\"text\" name=\"" + PARAM_CATEGORY + "\" value=\"Mouse\" />");
		printWriter.println("<br><br>");
		printWriter.println("<input type=\"submit\" value=\"Submit\" />");

		printWriter.println("<input type=\"hidden\" name=\"" + PARAM_OP + "\" value=\"" + OP_ADDBOOK + "\" />");

		printWriter.println("</form>");

		htmlBottom(printWriter);
	}
}