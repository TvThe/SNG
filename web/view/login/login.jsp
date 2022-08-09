<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modal.Question"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>
        <link href="resource/css/login.css" rel="stylesheet" type="text/css"/>
        <script src="library/jquery.min.js" type="text/javascript"></script>
    </head>
    <body>

        <div class="Logo" id="logo">
            <img src="resource/img/logo-removebg-preview.png" alt="" style="width: 340px; height: 175px;">
        </div>

        <div class="login-box" id="login">
            <div id="content">
                <form action="Login" method="POST" class="row g-3 needs-validation" novalidate>
                    <h2 style="color: White; margin-bottom: 15px">Welcome!</h2>
                    <input type="hidden" name="typeOfAccount" value="1">
                    <div class="user-box">
                        <input type="text" name="username" id="validationCustom01" required="">
                        <label class="form-label" for="validationCustom01">Username</label>
                        <div class="invalid-feedback">
                            Cannot be blank!
                        </div>
                    </div>

                    <div class="user-box">
                        <input type="password" name="password" id="validationCustom01" required="">
                        <label class="form-label" for="validationCustom01">Password</label>
                        <p class="text-danger" >${message}</p>
                        <div class="invalid-feedback">
                            Cannot be blank!
                        </div>
                    </div>

                    <div class="remember-box">
                        <input type="checkbox" name="remember">
                        <label>Remember me!</label>
                    </div>

                    <div style="text-align: center">
                        <button>
                            <span></span>
                            <span></span>
                            <span></span>
                            <span></span>
                            Sign In
                        </button>
                    </div>
                </form>

                <div class="forget-password" style="text-align: center">
                    <a style="color: white; border: none; background: transparent" id="btn_forget" type="button">Forget Password?</a>
                </div>

                <div style="text-align: center">
                    <p style="color: white;">-------------------------------------------</p>
                </div>

                <button id="btn" style="color: white; margin-left: 40px; background: #3AAA23; margin-bottom: 5px; border-radius: 5px; border: none; width: 240px; height: 40px">Create Your Account</button>
                <div style="margin-left: 40px" onlogin="checkLoginState();" class="fb-login-button button_login_API" data-width="170" data-size="large" data-button-type="login_with" data-layout="default" data-auto-logout-link="false" data-use-continue-as="false"></div>


            </div>
        </div>
        <div class="register-box" style="display:<c:if test="${message2==null}">none</c:if><c:if test="${message2!=null}">block</c:if>" id="register" >

                <form action="Register" method="POST" class="row g-3 needs-validation" novalidate id="create-form">
                    <input type="hidden" name="typeOfAccount" value="1">
                    <div>
                        <div class="header row">
                            <div class="col-md-10">
                                <h2>Create Account</h2>
                            </div>
                            <div class="col-md-1">
                                <button type="button" id="btn1" style="background: transparent; color: white; border: none; font-size: 25px">&times;</button>
                            </div>
                        </div>
                        <div class="user-box">
                            <label for="validationCustom01" class="form-label" style="color: white">Username<b style="color: red">*</b></label><br>
                            <input  type="text" name="username" placeholder="Enter Your Username..." id="myuser" required>
                                <span class="red-text accent-4" style="color: #dc3545"><c:if test="${message2!=null}">${message2}</c:if></span>
                            <div class="invalid-feedback">
                                Cannot be blank!
                            </div>
                        </div>

                        <div class="user-box">
                            <label for="validationCustom02" class="form-label" style="color: white">Gender<b style="color: red">*</b></label><br>
                            <input  type="text" name="gender" placeholder="Enter Your Gender..." id="validationCustom02" required>
                            <div class="invalid-feedback">
                                Cannot be blank!
                            </div>
                        </div>

                        <div class="user-box">
                            <label for="validationCustom02" class="form-label" style="color: white">Password<b style="color: red">*</b></label><br>
                            <input  type="password" name="password" placeholder="Enter Your Password..." id="register_password" required >
                            <div class="invalid-feedback">
                                Cannot be blank!
                            </div>
                        </div>

                        <div class="user-box">
                            <label for="validationCustom02" class="form-label" style="color: white">Re-password<b style="color: red">*</b></label><br>
                            <input  type="password" name="repassword" placeholder="Enter Re-password..." id="register_confirm_password" required onkeyup="validate_password()">
                            <span id="alert-pass"></span>
                            <div class="invalid-feedback">
                                Cannot be blank!
                            </div>

                        </div>

                        <div class="user-box">
                            <label for="validationCustom02" class="form-label" style="color: white">Date Of Birth<b style="color: red">*</b></label><br>
                            <input  type="date" name="dateofbirth" style="color: white" id="" required>
                            <div class="invalid-feedback">
                                Cannot be blank!
                            </div>

                        </div>


                        <div class="user-box">
                            <select name="question" class="form-control" style="outline: none; border: none;border-bottom: 1px solid #fff; margin-bottom: 10px;">
                                <option selected>Choose Your Security Question</option>
                            <c:forEach items="${quest}" var="q">
                                <option value="${q.ID}">${q.content}</option> 
                            </c:forEach>
                        </select>
                    </div>

                    <div class="user-box">
                        <label for="validationCustom02" class="form-label" style="color: white">Answer<b style="color: red">*</b></label><br>
                        <input  type="text" name="answer" placeholder="Your Answer..." id="validationCustom02" required >
                        <div class="invalid-feedback">
                            Cannot be blank!
                        </div>
                    </div>
                </div>

                <div style="text-align: center; margin-top: 10px;">
                    <button class="btn" style="color: white; background: #3AAA23;" id="create-account1">Create</button>
                </div>
            </form>

        </div>
        <!--Register -->
        <script>
            $(document).ready(function () {
                $('#btn_forget').click(function () {
                    var html = "";
                    var option = "";
            <c:forEach items="${quest}" var="q">
                    option += "<option style=\"color: black\" value=\"" +${q.ID} + "\">" +${q.content} + "</option>";
            </c:forEach>
                    html += "<h2 style=\"color: White; margin-bottom: 30px;\">Reset Password</h2>\n"
                            + "<div style=\"text-align: center; margin-bottom: 15px\">\n"
                            + "<span id=\"message\" style=\"color: green; font-weight:bold\"></span>\n"
                            + "</div>\n"
                            + "            <div class=\"user-box\">\n"
                            + "                <input type=\"text\" name=\"username\" id=\"forgetuser\" style=\"height: 18px;\">\n"
                            + "                <label class=\"form-label\">Username</label><br>\n"
                            + "                <span id=\"alert_exist\" style=\"color: red\"></span>\n"
                            + "            </div>\n"
                            + "                 <span id=\"quest\"></span>\n"
                            + "            <div style=\"text-align: right\" id=\"btn_submit\">\n"
                            + "                <button id=\"check-user\" class=\"check_user\" style=\"border: 1px solid white; background: #3AAA23; padding: 5px 10px; color: white; border-radius: 7px;\">Check</button>\n"
                            + "            </div>";
                    document.getElementById("content").innerHTML = html;
                    $(document).on("input", "#forgetuser", function () {
                        var forgetuser = $('#forgetuser').val();
                        $.ajax({
                            type: 'POST',
                            data: {forgetuser: forgetuser},
                            url: 'Checkexist',
                            success: function (data) {
                                var html = "";
                                console.log(data);
                                if (forgetuser != "") {
                                    if (data.username != forgetuser) {
                                        $('#alert_exist').html(data);
                                    } else {
                                        html += " <div class=\"user-box\" style=\"margin-bottom: 20px\">\n"
                                                + "                <select id=\"forget_quest\" style=\"height: 30px; width: 100%; border-bottom: 1px solid white; background: transparent; color: white\">\n"
                                                + "                <option selected>Choose Your Security Question</option>\n"
                                                + option + "\n"
                                                + "                </select>\n"
                                                + "            </div>"
                                                + "            <div class=\"user-box\">\n"
                                                + "                <input type=\"text\" id=\"answer_forget\" style=\"height: 18px;\">\n"
                                                + "                <label class=\"form-label\">Answer</label><br>\n"
                                                + "                <span id=\"alert_exist_answer\" style=\"color: red\"></span>\n"
                                                + "            </div>\n"
                                                + "            <span id=\"password_form\"></span>\n";
                                        document.getElementById("quest").innerHTML = html;
                                        $('#alert_exist').html("");
                                    }
                                } else {
                                    $('#alert_exist').html("Cannot be blank!");
                                }
                            }
                        });
                    });
                    $(document).on("click", "#check-user", function () {
                        var forgetuser = $('#forgetuser').val();
                        var question = $('#forget_quest').val();
                        var answer = $('#answer_forget').val();
                        $.ajax({
                            type: 'GET',
                            data: {forgetuser: forgetuser,
                                question: question,
                                answer: answer},
                            url: 'Checkexist',
                            success: function (data) {
                                var html1 = "";
                                console.log(data);
                                console.log(answer);
                                if (answer != "") {
                                    if (data == null) {
                                        $('#alert_exist_answer').html("Wrong question or answer");
                                    } else {
                                        html1 += "              <div class=\"user-box\">\n"
                                                + "                <input type=\"password\" id=\"password_forget\">\n"
                                                + "                <label class=\"form-label\">New Password</label><br>\n"
                                                + "                <span id=\"alert-pass2\" style=\"color: red\"></span>\n"
                                                + "            </div>\n"
                                                + "            <div class=\"user-box\">\n"
                                                + "                <input type=\"password\" id=\"confirm_password_forget\">\n"
                                                + "                <label class=\"form-label\">Confirm Password</label><br>\n"
                                                + "                <span id=\"alert-pass1\" style=\"color: red\"></span>\n"
                                                + "            </div>";
                                        document.getElementById("password_form").innerHTML = html1;
                                        $('#alert_exist_answer').html("");
                                        $('#btn_submit').html("<button id=\"submit_form\" style=\"border: 1px solid white; background: #3AAA23; padding: 5px 10px; color: white; border-radius: 7px;\">Submit</button>")
                                    }
                                } else {
                                    $('#alert_exist_answer').html("Cannot be blank!");
                                }
                            }
                        });
                    });
                    $(document).on("click", "#submit_form", function () {
                        var forgetuser = $('#forgetuser').val();
                        var password = $('#password_forget').val();
                        var confirm = $('#confirm_password_forget').val();
                        if (confirm != "" && password != "") {
                            if (confirm == password) {
                                $.ajax({
                                    type: 'POST',
                                    data: {forgetuser: forgetuser,
                                        password: password},
                                    url: 'forgetpassword',
                                    success: function (data) {
                                        var html1 = "<span>Reset Successfully</span>";
                                        document.getElementById("message").innerHTML = html1;
                                    }
                                });
                                $('#alert-pass2').html("");
                                $('#alert-pass1').html("");
                            } else {
                                $('#alert-pass2').html("");
                                $('#alert-pass1').html("Not Matching!");
                            }
                        } else {
                            if (password == "" && confirm == "") {
                                $('#alert-pass1').html("Cannot be blank!");
                                $('#alert-pass2').html("Cannot be blank!");
                            } else if (password == "" && confirm != "") {
                                if (password != confirm) {
                                    $('#alert-pass1').html("Not Matching!");
                                    $('#alert-pass2').html("Cannot be blank!");
                                }
                            } else if (password != "" && confirm == "") {
                                if (password != confirm) {
                                    $('#alert-pass1').html("Cannot be blank!");
                                    $('#alert-pass2').html("Not Matching!");
                                }
                            }
                        }
                    });
                });
            });
        </script>

        <script src="resource/js/newjavascript.js" type="text/javascript"></script>
        <script>
            function statusChangeCallback(response) {  // Called with the results from FB.getLoginStatus().
                console.log('ham dc goi statusChangeCallback');
                console.log(response); // The current login status of the person.
                if (response.status === 'connected') {   // Logged into your webpage and Facebook.
                    FB.api(
                            '/me',
                            'GET',
                            {"fields": "id,name,picture,gender,birthday"},
                            function (response) {
                                //                                console.log(response);
                                //                                console.log("id: " + response.id);
                                //                                console.log("name: " + response.name);
                                //                                toDataUrl(response.picture.data.url, function (myBase64) {
                                //                                    var asdahjsd = myBase64;
                                //                                    console.log("base 64: " + asdahjsd); // myBase64 is the base64 string
                                //                                });
                                //                                console.log("gender: " + response.gender);
                                //                                console.log("birthday: " + response.birthday);
                                fb_loginWithFacbook(response.id, response.name, response.picture.data.url);
                            }
                    );
                    //                    window.location.href = 'Sign_in_Controller.jsp?access_token=' + response.authResponse.accessToken; // ??a ch? g?i token qua method GET
                } else {                                 // Not logged into your webpage or we are unable to tell.

                }
            }

            function checkLoginState() {               // Called when a person is finished with the Login Button.
                console.log("ham dc goi checkLoginState");
                FB.getLoginStatus(function (response) {   // See the onlogin handler
                    statusChangeCallback(response);
                });
            }


            window.fbAsyncInit = function () {
                console.log("ham dc goi fbAsyncInit");
                FB.init({
                    appId: '502943704922897',
                    cookie: true, // Enable cookies to allow the server to access the session.
                    xfbml: true, // Parse social plugins on this webpage.
                    version: 'v14.0'           // Use this Graph API version for this call.
                });
                console.log("trc khi goi FB.getLoginStatus");
                FB.getLoginStatus(function (response) {   // Called after the JS SDK has been initialized.
                    console.log("trc khi goi statusChangeCallback");
                    statusChangeCallback(response); // Returns the login status.
                });
            };
            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "https://connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
            //convert url to base 64
            function toDataUrl(url, callback) {
                var xhr = new XMLHttpRequest();
                xhr.onload = function () {
                    var reader = new FileReader();
                    reader.onloadend = function () {
                        callback(reader.result);
                    };
                    reader.readAsDataURL(xhr.response);
                };
                xhr.open('GET', url);
                xhr.responseType = 'blob';
                xhr.send();
            }
            ;
            function fb_loginWithFacbook(fb_id, fb_displayname, fb_avatar) {
                toDataUrl(fb_avatar, function (myBase64) {
                    var avatarBase64 = myBase64.substring(23);
                    $.ajax({
                        url: 'Login',
                        method: 'POST',
                        data: {
                            "typeOfAccount": "2",
                            "fb_id": fb_id,
                            "fb_displayname": fb_displayname,
                            "fb_avatar": avatarBase64
                        },
                        success: function () {
                            //                            window.location.href = '/viewNewsfeed';
                            console.log('sucess');
                            window.location.replace('http://localhost:8080/nhom-4-se1604ks-swp391-social-network-for-gamer/viewNewsfeed');
                        }
                    });
                });
            }
            ;
        </script>

        <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js"></script>
    </body>
</html>
