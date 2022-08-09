<%-- 
    Document   : test
    Created on : Jul 20, 2022, 9:30:43 PM
    Author     : LENNOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="margin:0;padding:0;font-family: sans-serif;background: #212428;">
        <div class="Logo" id="logo">
            <img src="../../resource/img/logo-removebg-preview.png" alt="" style="width: 340px; height: 175px;">
        </div>
        <div class="login-box">
            <h2 style="color: White; margin-bottom: 30px;">Reset Password</h2>
            <div class="user-box">

                <input type="text" name="username" >
                <label class="form-label">Username</label><br>
            </div>
            <div class="user-box">
                <label class="label-form" style="color: white">Choose Your Security Question <a style="color: red">*</a></label>
                <select name="question" class="form-control" style="outline: none; border: none;border-bottom: 1px solid #fff; margin-bottom: 10px;">
                    <c:forEach items="${quest}" var="q">
                        <option value="${q.ID}">${q.content}</option> 
                    </c:forEach>
                </select>
            </div>
            <div class="user-box">
                <input type="text" id="answer_forget">
                <label class="form-label">Answer</label><br>
            </div>
            <div class="user-box">
                <input type="password" id="password">
                <label class="form-label">Password</label><br>
            </div>
            <div class="user-box">
                <input type="password" id="repassword">
                <label class="form-label">Re-Password</label><br>
            </div>
            <div style="text-align: right">
                <button style="border: 1px solid white; background: #3AAA23; padding: 10px 10px; color: white">Submit</button>
            </div>
        </div>
    </body>
    <style>
        .login-box {
            position: absolute;
            top: 40%;
            left: 50%;
            width: 400px;
            padding: 40px;
            transform: translate(-50%, -50%);
            background: rgba(0,0,0,.5);
            box-sizing: border-box;
            box-shadow: 0 15px 25px rgba(0,0,0,.6);
            border-radius: 20px;
        }
        .login-box h2 {
            margin: 0 0 0px;
            padding: 0;
            color: #fff;
            text-align: center;
        }
        .login-box .user-box {
            position: relative;
            margin: 10px 0;
        }

        .login-box .user-box input {
            width: 100%;
            padding: 10px 0;
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            border: none;
            border-bottom: 1px solid #fff;
            border-radius: 10px;
            outline: none;
            background: transparent;
        }
        .login-box .user-box label {
            position: absolute;
            top:0;
            left: 0;
            padding: 10px 0;
            font-size: 16px;
            color: #fff;
            pointer-events: none;
            transition: .5s;
        }
        .Logo{
            margin: auto;
            padding-left: 40%;
            margin-top: 1%;
        }

        .login-box .user-box input:focus ~ label,
        .login-box .user-box input:valid ~ label {
            top: -22px;
            left: 0;
            color: #3AAA23;
            font-size: 13px;
        }
    </style>
</html>
