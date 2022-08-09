
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>
        <script src="library/jquery.min.js" type="text/javascript"></script>

        <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
        <script>
            $(document).on('click', '#btnPopUpMoDal', function () {
                var listOptionGame = "";
                listOptionGame += "<select name=\"gID\" id=\"gID\">";
            <c:forEach items="${g}" var="g">
                listOptionGame += "<option value=${g.ID}>${g.name}</option>";
            </c:forEach>
                listOptionGame += "</select>";

                $('#selectGame').html(listOptionGame);
            });

        </script>
    </head>
    <body>
        <div class="container mt-3" style="margin-top: 6%">
            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#myModal" id="btnPopUpMoDal"style=" border: 1px solid red; color: red; background: black">
                Create Product
            </button>
        </div>
        
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->

                    <!-- Modal body -->
                    <form id="createPostForm" action="CreateProduct" method="POST" enctype="multipart/form-data">
                        <div class="modal-header">

                            <input type="hidden" name="uID" value="${sessionScope.account.ID}"/>
                            <h4 class="modal-title">${sessionScope.account.displayname}</h4>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body" style="text-align: left; font-size: 16px">
                            
                            <label style="font-weight: bold">Name Product: </label>
                            <input class="form-control" id="nameProduct" name="nameProduct" rows="3" required="">
                            <label style="font-weight: bold">Description: </label>
                            <input class="form-control" id="description" name="description" rows="3"required="">
                            <label style="font-weight: bold">Price: </label>
                            <input oninput="checkNumber(event)" class="form-control" id="price" name="price" rows="3"required="">
                            <span id="checkNumber" style="color:red;"></span><br>
                            <label class="mt-3" style="font-weight: bold">Choose Image: </label>
                            <input type="file" name="photo" id="uploadImage" multiple="true" size="50" onchange="loadFile()"/>
                            <div id="images" class="mt-3"></div>
                            <div style="display: flex">
                                <label style="font-weight: bold">Type of game:</label> 
                                <div id="selectGame" ></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button id="creatPostbutton"class="btn btn" style="background: #3AAA23; color: white">Create</button>
                        </div>
                    </form>
                    <!-- Modal footer -->


                </div>
            </div>             
        </div>
        <script>
            function loadFile() {
                var fileEle = document.getElementById("uploadImage");
                var imgagesBox = document.getElementById("images");
                imgagesBox.innerHTML = ""
                if (fileEle) {
                    for (let i = 0; i < fileEle.files.length; i++) {
                        var reader = new FileReader();

                        reader.onload = function (e) {


                            imgagesBox.innerHTML += '<img style="width:50px"src=' + e.target.result + '>';
                        };

                        reader.readAsDataURL(fileEle.files[i]);
                    }

                }
            }
            function checkNumber(e){
                console.log(e.target.value);
                if(!isNumeric(e.target.value)){
                    $('#checkNumber').html("Price is number!")
                }else{
                    $('#checkNumber').html("")
                }
            }
            function isNumeric(str) {
                if (typeof str != "string")
                    return false 
                return !isNaN(str) && 
                        !isNaN(parseFloat(str)) 
            }
        </script>
    </body>
</html>
