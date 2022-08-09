<%-- 
   Document   : UpdateUser
   Created on : 29/05/2022, 8:38:23 PM
   Author     : duynh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">-->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <!--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                    integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.js"
                integrity="sha512-n/4gHW3atM3QqRcbCn6ewmpxcLAHGaDjpEBu4xZd47N0W2oQ+6q7oc3PXstrJYXcbNU1OHdQ1T7pAP+gi5Yu8g=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="resource/css/profile.css">
        <style>
            body,.change-color{
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
                width: 50%;
            }
        </style>

        <script>
            $(function () {
                $("#update-form").submit(function (e) {
                    e.preventDefault();

                })
            });

            function returntoUser() {
                window.location = "viewProfile";
            }
            function toProfile() {
                alert("user click!");

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
                    <form class="row g-3" id="update-form" action="updateProfile" method="post">
                        <div class="col-auto">
                            <label for="currentdisplayname" class="visually-hidden" style="color:white;">new display name</label>
                            <input type="text" disabled="true" class="form-control-plaintext" value="Current Display name:" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="displayname" class="visually-hidden" style="color:white;"> Current Display name:</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="olddisplayname" name ="olddisplayname" value="${user.getDisplayname()}" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputDisplayname" class="visually-hidden">new display name</label>
                            <input type="text" class="form-control" id="newdisplay" placeholder=" New display name" name="newDisplayname" value="${user.getDisplayname()}">
                        </div>

                        <div class="col-auto">
                            <label for="currentdisplayname" class="visually-hidden" style="color:white;">new gender</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="gender" value="Current gender:" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="displayname" class="visually-hidden" style="color:white;"> Current Gender:</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="oldgender"  name="oldgender" value="${user.getGender()}" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputeGender" class="visually-hidden">new Gender</label>
                            <input type="text" class="form-control" id="newgender" placeholder="Enter new Gender" name="newGender" value="${user.getGender()}">
                        </div>
                        <div class="col-auto">
                            <label for="currentdisplayname" class="visually-hidden" style="color:white;">Date of Birth</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="dob" value="Current date of birth:" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="displayname" class="visually-hidden" style="color:white;"> Current Display name:</label>
                            <input type="text" disabled="true" class="form-control-plaintext" id="olddob" name="olddob" value="${user.getDob()}" style="color:white;">
                        </div>
                        <div class="col-auto">
                            <label for="inputDateOFbirth" class="visually-hidden">new Date birth</label>
                            <input type="date" class="form-control" id="newDob" placeholder="Enter date of birth" name="newDob" value="${user.getDob()}">
                        </div><div></div>
                        <div class="col-auto mx-auto">
                            <button type="button" onclick="returntoUser()" class="btn btn-secondary mb-3">Cancel</button>
                            <button type="submit" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#confirmmodal" id="saves">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="modal fade" id="confirmmodal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Confirm submission</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="container">
                                <div class="row" id="getUsr">

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="getData()" id="save">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>

                $("#saves").on("click", function (e) {
                    e.preventDefault();
                    var displayname = $("#newdisplay").val();
                    var gender = $("#newgender").val();
                    var dob = $("#newDob").val();

                    if (displayname === " " || displayname === null) {
                        displayname = $("#olddisplayname").val();
                    }


                    if (gender === " " || gender === null) {
                        gender = document.getElementById("oldgender").value;
                        gender = $("#oldgender").val();
                    }
                    if (dob === " " || dob === null) {
                        dob = $("#olddob").val();
                    }
                    console.log(displayname);
                    console.log(gender);
                    console.log(dob);
                    var div = document.createElement("div");
                    div.className += "col-md-12";
                    div.innerHTML = "New User Name: " + displayname;
                    var div2 = document.createElement("div");
                    div2.className += "col-md-12";
                    div2.innerHTML = "New Gender: " + gender;
                    var div3 = document.createElement("div");
                    div3.className += "col-md-12";
                    div3.innerHTML = "New DoB: " + dob;

                    document.getElementById("getUsr").appendChild(div);
                    document.getElementById("getUsr").appendChild(div2);
                    document.getElementById("getUsr").appendChild(div3);

                });
                $("#save").on("click", function (e) {
                    document.getElementById("update-form").submit();
                });



                //



                //create element in modal

            </script>
    </body>
</html>
