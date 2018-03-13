<%-- 
    Document   : game
    Created on : 13-03-2018, 13:24:47
    Author     : sand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.jws.WebService" %>
<%@page import="javax.jws.WebMethod" %>
<%@page import="java.rmi.RemoteException" %>
<%@page import="java.rmi.Naming"%>
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
            GalgeInterface spil = (GalgeInterface) Naming.lookup("rmi://ubuntu4.saluton.dk:9919/GalgeServer?wsdl");
            
            //Service service = Service.create(url, qname);
            //GalgeInterface spil = service.getPort(GalgeInterface.class);
            
            boolean gameOn = true;
            String guess;
            //Scanner input = new Scanner(System.in);
            
            int wLength = 5;
            
            while (gameOn){
                //System.out.println("Indtast ét bogstav");
                guess = input.next();
                spil.gætBogstav(guess);   
                //System.out.println(spil.outputTilKlient());
                spil.logStatus();       
                //System.out.println(spil.outputTilKlient());    
                if (spil.erSpilletSlut()) gameOn = false;
            }
            
        %>      
        
        <h1>Hello, <%= userName %>!</h1>
        <h2>Welcome to a game of Hangman</h2>
        
        <img src="hangman.jpg" width="100" height="80" alt="Hangman"/>
        <p>Can you quess the word?<br>
            Try to quess the word! If you type a wrong letter seven times, you're out..</p>
        <p>The word you need to quess, is <b><%= wLength %></b> characters long:</p> 
        <p> &nbsp;
            <%
                for (int i = 0; i < wLength; i++){
                    out.write("_&nbsp; &nbsp; ");
                }
            %>
        </p>
        <form>

            Type in a letter:<input type="text" name="firstname" size="6">
            <button type="button" >Guess</button>
        </form>
    </body>
</html>
