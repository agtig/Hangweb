<%-- 
    Document   : index
    Created on : 13-03-2018, 13:18:33
    Author     : Mikkel Boechman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hangman</title>
        <meta name="viewport" content="width=device-width, initial-scale=1", charset=UTF-8">
        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            form {border: 3px solid #f1f1f1;}

            input[type=text], input[type=password] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            button {
                background-color: #4CAF50;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
            }

            button:hover {
                opacity: 0.8;
            }

            .imgcontainer {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            img.avatar {
                width: 40%;
                border-radius: 50%;
            }

            .container {
                padding: 16px;
            }

            span.password {
                float: right;
                padding-top: 16px;
            }

            /* Change styles for span and cancel button on extra small screens */
            @media screen and (max-width: 300px) {
                span.password {
                   display: block;
                   float: none;
                }

            }
        </style>
    </head>
    <body>
        <%
            int errorCode = 2;
            try {
                String errorString = request.getParameter("error");
                errorCode = Integer.parseInt(errorString);
            }
            catch (Exception e) {
                errorCode = 0;
            }
        %>
        <h1>HANGMAN Login</h1>

        <form name="Login form" action="loginProcess.jsp" method="POST">
            
            <div class="container">
            <%
                if(errorCode==1) out.println("<font color=\"red\"><b><i>Login error: Wrong username og password<br>Try again!</i></b></font><p>");
            %>
            <label for="name"><b>Brugernavn</b></label>
            <input type="text" placeholder="Enter username here..." name="name" required>
            <label for="password"><b>Password</b></label>            
            <input type="password" placeholder="Enter password here..." name="password" required>
            <button type="submit" >Login</button>
            </div>

        </form>
    </body>
</html>