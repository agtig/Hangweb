<%-- 
    Document   : game
    Created on : 13-03-2018, 13:24:47
    Author     : sand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="galgeleg.*, brugerautorisation.transport.rmi.Brugeradmin;
"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hangman Game</title>
    </head>

    <body>
        <jsp:useBean id="executioner" scope="session" class="galgeleg.loginHandler" />
        <!--
        jsp:setProperty name="executioner" property="name" />
        jsp:setProperty name="executioner" property="password" />
        -->
        <%
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            
            private Brugeradmin BI;
          

        %>      
        
        <h1>Hello, <% out.print(name); %>!</h1>
        <h2>Welcome to a game of Hangman</h2>
        
        <img src="hangman.jpg" width="100" height="80" alt="Hangman"/>
        <p>Can you quess the word? Try to quess the word! If you type a wrong letter seven times, you're out..</p>
        <p>The letter you need to quess, is typed with _ under here:</p> 
        <p>_ _ _ _ _ _ _ _ _ _</p>
        <form>

            Type in a letter:<input type="text" name="firstname" size="6">
            <button type="button" >Guess</button>
        </form>
    </body>
</html>
