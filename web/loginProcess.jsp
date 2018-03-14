<%-- 
    Document   : loginProcess
    Created on : 13-03-2018, 21:40:36
    Author     : Robert Sand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.jws.WebService" %>
<%@page import="javax.jws.WebMethod" %>
<%@page import="java.rmi.RemoteException" %>
<%@page import="java.rmi.Naming"%>
<%@page import="brugerautorisation.transport.rmi.Brugeradmin" %>
<%@page import="brugerautorisation.data.Bruger" %>
<%@page import="brugerautorisation.data.Diverse" %>
<%@page import="galgeleg.*" %>
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
            }
            else {
                response.sendRedirect("index.jsp?error=1");
            }
        %>
    </body>
    <%!
        public boolean loginSuccess(String userName, String password){
        boolean success;
        
        try {
            Brugeradmin ba = (Brugeradmin)Naming.lookup("rmi://javabog.dk/brugeradmin");
            Bruger b = ba.hentBruger(userName, password);
            success = true;
        }
        catch (Exception e) {
            success = false;
        }
        
        return success;
        }
    %>
</html>
