<%-- 
    Document   : game
    Created on : 13-03-2018, 13:24:47
    Author     : sand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.jws.WebService" %>
<%@page import="javax.xml.ws.Service" %>
<%@page import="javax.xml.namespace.QName" %>
<%@page import="javax.jws.WebMethod" %>
<%@page import="java.rmi.RemoteException" %>
<%@page import="java.rmi.Naming"%>
<%@page import="java.net.URL" %>
<%@page import="galgeleg.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hangman</title>
    </head>

    <body>
        <%
            String userName = (String)session.getAttribute("pName");
            if (userName == null) response.sendRedirect("index.jsp");
            
            String guessChar ="";
            URL url = new URL("http://ubuntu4.saluton.dk:9796/GalgeServer?wsdl"); // soap - Forbinder til det navn serveren udgiver sig på "GalgeServer"
            QName qname = new QName("http://galgeleg/", "GalgelogikService");
        
            Service service = Service.create(url, qname);
            GalgeInterface spil = service.getPort(GalgeInterface.class);
            
            if (request.getParameter("reset") != null) spil.nulstil();

            boolean gameOn = spil.erSpilletSlut();
            
            String guess = request.getParameter("guess");
            if (guess != null) {
                spil.gætBogstav(guess);
            } 
            String secret = spil.getOrdet();
            int wLength = secret.length();
            
            int guessLeft = 7 - spil.getAntalForkerteBogstaver();
            
            /**
            while (gameOn){
                //System.out.println("Indtast ét bogstav");
                guess = input.next();
                spil.gætBogstav(guess);   
                //System.out.println(spil.outputTilKlient());
                spil.logStatus();       
                //System.out.println(spil.outputTilKlient());    
                if (spil.erSpilletSlut()) gameOn = false;
            }
            **/
        %>      
        
        <h1>Hello, <%= userName %>!</h1>
        <h2>Welcome to a game of Hangman</h2>
            
        <%
            if(guessLeft == 7) %> <img src="hangman7.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 6) %> <img src="hangman6.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 5) %> <img src="hangman5.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 4) %> <img src="hangman4.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 3) %> <img src="hangman3.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 2) %> <img src="hangman2.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 1) %> <img src="hangman1.png" width="100" height="80" alt="Hangman"/> <%;
            if(guessLeft == 0) %> <img src="hangman0.png" width="100" height="80" alt="Hangman"/> <%;
        %>
        
        <p>Can you quess the word?<br>
            Try to quess the word! If you type a wrong letter seven times, you're out..</p>
        <p>The word you need to quess, is <b><%= wLength %></b> characters long:</p> 
        <p> &nbsp;
            <%
                
                out.write("<h3>" + spil.getSynligtOrd() + "</h3>");
                if (spil.erSpilletVundet()) out.write("<h3>Congrats! You have won!</h3>");
                if (spil.erSpilletTabt()) out.write("<h3>Looser! Better luck next time...</h3>");
                if (request.getParameter("reset") == null) out.write(spil.outputTilKlient());
                out.write("<br><br>Guessed characters: " + spil.getBrugteBogstaver());
                out.write("<br><h4>You have " + guessLeft + " guesses left.</h4>");
            %>
        </p>
        <form name="guess" action="game.jsp" method="POST">
            Type in a letter:<input type="text" name="guess" size="2" autofocus>
            <button type="button" >Guess</button>
        </form>
        <form name="reset" action="game.jsp" method="POST">
            <button type="submit" value="reset" name="reset"/>Reset</button>
        </form>
        <!--<form name="logout" action="loginProcess.jsp" method="POST">
           <button type="submit" value="login" name="login"/>Logout</button>
        </form> -->
    </body>
</html>
