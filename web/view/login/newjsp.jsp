<%-- 
    Document   : newjsp
    Created on : Jul 20, 2022, 9:19:26 PM
    Author     : LENNOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
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
                    <label class="label-form" style="color: white">Choose Your Security Question <a style="color: red">*</a></label>
                    <select name="question" class="form-control" style="outline: none; border: none;border-bottom: 1px solid #fff; margin-bottom: 10px;">
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
    <!--forget password -->
    <div class="forget-box" id="forget-form">
        <form action="ForgetPassword" method="POST" class="row g-3 needs-validation" novalidate id="forgetform">
            <div class="header row" style="margin-top: 10px;">
                <div class="col-md-11">
                    <h2>Forget Password</h2>
                </div>
                <div class="col-md-1">
                    <button type="button" id="btn2" style="background: transparent; color: white; border: none; font-size: 25px">&times;</button>
                </div>
            </div>
            <div class="tab">
                <div class="user-box" style="margin-bottom: 15px;">
                    <label for="forgetuser" class="form-label" style="color: white">Username<b style="color: red">*</b></label><br>
                    <input  type="text" name="username" placeholder="Enter Your Username..." id="forgetuser" required>
                    <span class="red-text accent-4" style="color: #dc3545" id="result1"></span>
                    <div class="invalid-feedback">
                        Cannot be blank!
                    </div>
                </div>

                <div class="user-box">
                    <label class="label-form" style="color: white">Choose Your Security Question <a style="color: red">*</a></label>
                    <select name="question" class="form-control" style="outline: none; border: none;border-bottom: 1px solid #fff; margin-bottom: 10px;" id="forgetquest">
                        <c:forEach items="${quest}" var="q">
                            <option value="${q.ID}">${q.content}</option> 
                        </c:forEach>
                    </select>
                </div>

                <div class="user-box">
                    <label for="validationCustom02" class="form-label" style="color: white">Answer<b style="color: red">*</b></label><br>
                    <input  type="text" name="answer" placeholder="Your Answer..." id="validationCustom02" required id="forgetanswer">
                    <span class="red-text accent-4" style="color: #dc3545" id="result2"></span>
                    <div class="invalid-feedback">
                        Cannot be blank!
                    </div>
                </div>
            </div>

            <div class="tab">
                <div class="user-box">
                    <label for="validationCustom02" class="form-label" style="color: white">Password<b style="color: red">*</b></label><br>
                    <input  type="password" name="password" placeholder="Enter Your Password..." id="forget_password" required >
                    <div class="invalid-feedback">
                        Cannot be blank!
                    </div>
                </div>

                <div class="user-box">
                    <label for="validationCustom02" class="form-label" style="color: white">Re-password<b style="color: red">*</b></label><br>
                    <input  type="password" name="repassword" placeholder="Enter Re-password..." id="forget_confirm_password" required onkeyup="validate_password1()">
                    <span id="alert-pass1"></span>
                    <div class="invalid-feedback">
                        Cannot be blank!
                    </div>

                </div>
            </div>

            <div style="text-align: center">
                <div class="row">
                    <div class="col-md-6">
                        <button type="submit" id="prevBtn" onclick="nextPrev(-1)" style="background: #3AAA23; color: white; border: none; padding: 5px 25px; border-radius: 8px;">Prev</button>
                    </div>
                    <div class="col-md-6">
                        <button type="submit" id="nextBtn" onclick="nextPrev(1)" style="background: #3AAA23; color: white; border: none; padding: 5px 25px; border-radius: 8px;">Next</button>
                    </div>
                </div>
            </div>
            <!-- Circles which indicates the steps of the form: -->
            <div style="text-align:center;margin-top:20px;">
                <span class="step"></span>
                <span class="step"></span>
            </div>

        </form>

    </div>
</body>
</html>
