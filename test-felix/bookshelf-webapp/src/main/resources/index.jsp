<jsp:useBean id="sessionBean" class="com.test.felix.bookshelf.webapp.beans.SessionBean" scope="session">
    <% sessionBean.initialize(getServletContext()); %>
</jsp:useBean>
<%--
<%@ page import="java.util.Set"%>
<%@ include file="init.inc.jsp" %>
<html>
<head>
    <title>Bookshelf - Browse Categories</title>
    <link rel="stylesheet" type="text/css" href="css/style.css" /> </head>
<body>
<%@ include file="menu.inc.jsp" %>
<h2>Bookshelf - Browse Categories</h2> <% Set<String> categories =
        sessionBean.getBookshelf().getCategories();
%><ul>
    <% for (String category : categories) { %>
    <li>
        <a href="byCategory.jsp?category=<%= category %>">
            <%=category%></a> </li>
    <% }
    %></ul>
</body>
</html>--%>
