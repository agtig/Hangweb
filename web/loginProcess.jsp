<%-- 
    Document   : loginProcess
    Created on : 13-03-2018, 21:40:36
    Author     : Robert Sand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hangman</title>
    </head>
    <body>
        <%
            String uName = request.getParameter("name");
            String uPasw = request.getParameter("password");
            
            if(loginSuccess(uName, uPasw)){
                //loginStatus = 1;
                session.setAttribute("pName", uName);
                response.sendRedirect("game.jsp");
            } else {
                response.sendRedirect("index.jsp?error=1");
            }
        %>
    </body>
    <%!
        public boolean loginSuccess(String userName, String password){
        boolean success = true;

        return success;
        }
    %>
</html>
