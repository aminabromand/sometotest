<jsp:useBean id="sessionBean" class="com.test.felix.bookshelf.webapp.beans.SessionBean" scope="session">
    <% sessionBean.initialize(getServletContext()); %>
</jsp:useBean>