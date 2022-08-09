<%-- 
    Document   : UpdatePassword
    Created on : Jul 19, 2022, 10:50:09 PM
    Author     : duynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.js"
                integrity="sha512-n/4gHW3atM3QqRcbCn6ewmpxcLAHGaDjpEBu4xZd47N0W2oQ+6q7oc3PXstrJYXcbNU1OHdQ1T7pAP+gi5Yu8g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <style>
            html,body,.change-color{
                background-color: #212A37;
            }
            .container-fluid{
                width: 98%;
            }
            .boxcontainer{
                background-color: #111720;
                border-radius: 25px;
                display: block;
                position: relative;        
                margin-bottom: 20px;
                width: 35%;
            }

        </style>

        <script>
            function returntoUser() {
                window.location = "viewProfile";
            }

        </script>


        <title>JSP Page</title>
    </head>
    <body>
        <div class ="header">
            <script>

                $('#category').mouseenter(function () {
                    $("#navigation").slideDown();
                });
                $("#navigation").mouseleave(function () {
                    ("#navigation").slideUp();
                });

            </script>
            <jsp:include page='../../resource/components/category.jsp'/>
        </div>
        <div class="container-fluid change-color" style="margin-top: 100px;">
            <div class="row boxcontainer mx-auto">
                <div class="col-md-12">
                    <span id = "message" style="color:red"> </span> <br><br>  
                    <form class="row g-2" id="update-form" action="updateProfile" method="post" onsubmit ="return verifyPassword()">
                        <div class="col-auto">
                            <label for="currentdisplayname" class="visually-hidden" style="color:white;">new Password</label>
                            <input type="text" disabled="true" class="form-control-plaintext" value="Current Password:" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputCurrentPassword" class="visually-hidden">Enter Current Password</label>
                            <input type="password" class="form-control" id="current" placeholder=" Enter current password" name="currentpass" required>
                        </div>
                        <!-- -->
                        <div class="col-auto">
                            <label for="newPassword" class="visually-hidden" style="color:white;">new Password</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="gender" value="Enter new Password" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputnewPassword" class="visually-hidden">new Password</label>
                            <input type="password" class="form-control" id="newp" placeholder="Enter new Password" name="newpassword" required>
                        </div>
                        <!-- -->
                        <div class="col-auto">
                            <label for="repassword" class="visually-hidden" style="color:white;">Re-enter current password</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="dob" value="Re-enter current password" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputrepassword" class="visually-hidden">Re-enter current password</label>
                            <input type="password" class="form-control" id="rpas" placeholder="Re-enter current password" name="repass" required>
                        </div><div></div>
                        <div class="col-auto mx-auto">
                            <button type="button" onclick="returntoUser()" class="btn btn-secondary mb-3">Cancel</button>
                            <button type="submit" class="btn btn-primary mb-3" id="saves">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
            <script>
                function verifyPassword() {
                    var current = document.getElementById("current").value;
                    var newpass = document.getElementById("newp").value;
                    var renew = document.getElementById("rpas").value;
                    
                    
                    if (newpass !== renew) {
                        document.getElementById("message").innerHTML = "new password is not match";
                        return false;
                    }
                    
                }
            </script>
    </body>
</html>