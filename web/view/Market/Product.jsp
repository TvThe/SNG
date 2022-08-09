<%-- 
    Document   : newjsp
    Created on : Jun 10, 2022, 5:24:18 PM
    Author     : LENNOVO
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
        <script src="library/jquery.min.js" type="text/javascript"></script>
    </head>
    <body style="background-color: #212A37;">
        <jsp:include page='../../resource/components/category.jsp'/>
        <div class="container" style="margin-top: 12%; display: flex; justify-content: space-between">
            <div class="card" style="width: 18rem;">
                <div class="card-header" >
                    <div class="card-1st-row" style="display: flex; justify-content: space-between">
                        <div class="card-seller">
                            <c:forEach items="${u}" var="listUser">
                                <c:if test="${listUser.ID == p.userID}">
                                    <h6 class="card-title">Seller: ${listUser.displayname} </h6>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="dropdown" style="text-align: right">
                            <i type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="true" class="fa fa-ellipsis-h"></i>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <c:if test="${sessionScope.account.ID == p.userID || sessionScope.account.ID == 1}">
                                    <a onclick="UpdateProduct(${p.purID})" data-bs-toggle="modal" data-bs-target="#exampleModal" class="dropdown-item" href="">Update/Edit</a>
                                    <a onclick="DeleteProduct(${p.purID})" data-bs-toggle="modal" data-bs-target="#modalConfirmDelete" class="dropdown-item" href="">Remove</a>
                                </c:if>
                                <c:if test="${sessionScope.account.ID!=p.userID}">
                                    <button class="dropdown-item" id="report" data-bs-toggle="modal" data-bs-target="#reportDetail" onclick="reportProduct(${p.purID})">Report</button>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <div class="card-img">
                        <c:forEach items="${g}" var="listGame">
                            <c:if test="${listGame.ID == p.gameID}">
                                <img src="data:image/jpeg;base64, ${listGame.image}" class="card-img-top" alt="..." style="width: 100%; height: 100%; margin: 5px 0; bo">
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="card-body">
                    <p class="card-title productName">Product Name: ${p.name}</p>
                    <p hidden="true" class="card-title" id="purID">${p.purID}</p>
                    <p class="card-title">Price: ${p.price}$</p>
                    <p class="card-title">Release: ${p.time}</p>
                    <div class="card-button" style="justify-content: space-around; display: flex">
                        <a href="chat?id=${p.userID}" class="btn btn-success" <c:if test="${sessionScope.account.ID == p.userID}">onclick="return false;" style="background: gray"</c:if>>Contact</a>
                            <button class="btn btn-success" style="width: 80.94px; height:38.21px" id="detailProduct">Detail</button>
                        </div>
                    </div>
                </div>
                <div1 class="detailProduct" id="detailProductShow">
                    <span id="result"></span>
                </div1>
            </div>
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" id="contentModalProduct">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">${sessionScope.account.displayname}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <label>Name Product: </label>
                        <input class="form-control" id="nameProduct" name="nameProduct" rows="3">
                        <label>Description: </label>
                        <input class="form-control" id="description" name="description" rows="3">
                        <label>Price: </label>
                        <input class="form-control" id="price" name="price" rows="3">

                        <label class="mt-3">Choose Image: </label>
                        <input type="file" name="photo"id="uploadImage"multiple="true" size="50" onchange="loadFile()"/>
                        <div id="images" class="mt-3">

                        </div>
                        Type of game: <div id="selectGame" ></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Close</button>
                        <button id="btnUpdateProduct" class="btn btn-primary" >Save</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" tabindex="-1" id="modalConfirmDelete">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Confirm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you want to delete this product ?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="confirmDelete">Delete</button>
                    </div>
                </div>
            </div>
        </div> 
    </body>
    <script>
        $('#header').mouseenter(function () {
            $("#navigation").slideDown(250);
        });
        $("#header").mouseleave(function () {
            $("#navigation").slideUp(250);
        });
        $(document).ready(function () {
            $('#detailProduct').click(function () {
                $.ajax({
                    url: 'viewpurchase',
                    type: 'POST',
                    data: {productID: $('#purID').text()
                    },
                    success: function (data) {
                        var html = "";
                        html += "              <div id=\"detailproductform\" class=\"card mb-3\" style=\"max-width: 700px;\">\n"
                                + "                    <div class=\"form-inline\" style=\"justify-content: space-between; display:flex \">\n"
                                + "                            <h4>Detail Product</h4>\n"
                                + "                   </div>\n"
                                + "                    <div class=\"row g-0\">\n"
                                + "                        <div class=\"col-md-7\">\n"
                                + "                            <div id=\"carouselExampleControls\" class=\"carousel slide\" data-bs-ride=\"carousel\">\n"
                                + "                                <div class=\"carousel-inner\">\n"
                                + "                                    <div class=\"carousel-item active\">\n"
                                + "                                        <img src=\"data:image/jpeg;base64," + data.images[0] + "\" style=\"width: 100%; height: 350px;\" alt=\"...\">\n"
                                + "                                    </div>\n";
                        for (let i = 1; i < data.images.length; i++) {
                            html += "<div class=\"carousel-item\">\n"
                                    + "<img style=\"width: 100%; height: 350px;\" alt=\"...\" src=\"data:image/jpeg;base64," + data.images[i] + "\">\n"
                                    + "    </div>";
                        }
                        html += "                                </div>\n"
                                + "                                <button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"prev\">\n"
                                + "                                    <span class=\"carousel-control-prev-icon btn-secondary\" aria-hidden=\"true\"></span>\n"
                                + "                                    <span class=\"visually-hidden\" style=\"color: red\">Previous</span>\n"
                                + "                                </button>\n"
                                + "                                <button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"next\">\n"
                                + "                                    <span class=\"carousel-control-next-icon btn-secondary\" aria-hidden=\"true\"></span>\n"
                                + "                                    <span class=\"visually-hidden\">Next</span>\n"
                                + "                                </button>\n"
                                + "                            </div>\n"
                                + "                        </div>\n"
                                + "                        <div class=\"col-md-5\">\n"
                                + "                            <div class=\"card-body\">\n"
                                + "                                <h5 class=\"card-title\">" + data.name + "</h5>\n"
                                + "                                <p class=\"card-text\"><small class=\"text-muted\" style=\"font-style:italic \">" + data.time + "</small></p>\n"
                                + "                                <p class=\"card-text\">Price: " + data.price + "$</p>\n"
                                + "                                <p class=\"card-text\">Description:</p>\n"
                                + "                                <p class=\"card-text\">" + data.description + "</p>\n"
                                + "                            </div>\n"
                                + "                        </div>\n"
                                + "                        <div class=\"card-footer\" style=\"text-align: right\">\n"
                                + "                            <a href=\"chat?id=" + data.userID + "\" type=\"button\" class=\"btn-success\" style=\"text-decoration:none; padding: 5px 5px;\">Contact</a>\n"
                                + "                        </div>\n"
                                + "                    </div>\n"
                                + "                </div>";
                        document.getElementById("result").innerHTML = html;
                    }
                });
            });
        });
        function UpdateProduct(id) {
            $.ajax({
                type: 'GET',
                url: "UpdateProduct",
                data: {productID: id},
                success: function (data) {
                    let text = "";
                    for (let i = 0; i < data.images.length; i++) {
                        text += "<img style=\"width:50px\"src=\"data:image/jpeg;base64," + data.images[i] + "\">\n";
                    }
                    var listOptionGame = "";
                    listOptionGame += "<select name=\"gID\" id=\"gID\">";
        <c:forEach items="${g}" var="ga">
                    var gId = '${ga.getID()}';
                    var gName = '${ga.name}';
                    var selected = gId == data.gameID ? "selected='selected'" : "";
                    listOptionGame += "<option " + selected + "  value=" + gId + ">" + gName + "</option>";
        </c:forEach>
                    listOptionGame += "</select>";
                    var html = "";
                    html +=
                            "                    <div class=\"modal-header\">\n" +
                            "                            <input type=\"hidden\" name=\"uID\" value=\"" + data.UserID + "\"/>\n" +
                            "                        <h5 class=\"modal-title\" id=\"exampleModalLabel\">${sessionScope.account.displayname}</h5>\n" +
                            "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n" +
                            "                    </div>\n" +
                            "                    <div class=\"modal-body\">\n" +
                            "                        <input type=\"hidden\" id=\"productID\" name=\"productID\" value=\"" + data.PurID + "\"/>\n" +
                            "                        <label>Name Product: </label>\n" +
                            "                            <input class=\"form-control\" id=\"nameproduct\" name=\"nameProduct\"value=\"" + data.name + "\" rows=\"3\">\n" +
                            "                            <label>Description: </label>\n" +
                            "                            <input class=\"form-control\" id=\"descriptionProduct\" name=\"description\"value=\"" + data.description + "\" rows=\"3\">\n" +
                            "                            <label>Price: </label>\n" +
                            "                            <input class=\"form-control\" id=\"priceProduct\" name=\"price\"value=\"" + data.price + "\" rows=\"3\">\n" +
                            "\n" +
                            "                            <label class=\"mt-3\">Choose Image: </label>\n" +
                            "                            <input type=\"file\" name=\"photo\"id=\"uploadImage\"multiple=\"true\" size=\"50\" onchange=\"loadFile()\"/>\n" +
                            "                            <div id=\"images\" class=\"mt-3\">\n" +
                            "\n" + text +
                            "                            </div>\n" +
                            "                             Type of game: <div id=\"selectGame\" ></div>\n" + listOptionGame +
                            "                    </div>\n" +
                            "                    <div class=\"modal-footer\">\n" +
                            "                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\" id=\"closeModal\">Close</button>\n" +
                            "                        <button class=\"btn btn-primary\" data-bs-dismiss=\"modal\" id=\"btnUpdateProduct\">Save</button>\n" +
                            "                    </div>\n";
                    document.getElementById('contentModalProduct').innerHTML = html;
                    console.log(data);
                }
            });
            $(document).ready(function () {
                $(document).on("click", "#btnUpdateProduct", function () {

                    $.ajax({
                        type: 'POST',
                        url: 'UpdateProduct',
                        data: {
                            "productID": $('#productID').val(),
                            "nameProduct": $('#nameproduct').val(),
                            "description": $('#descriptionProduct').val(),
                            "price": $('#priceProduct').val(),
                            "gID": $('#gID').val()
                        },
                        success: function () {
                            console.log("update successfuly");
                            var nameProduct = $('#nameproduct').val();
                            document.getElementById("nameProduct" + id).innerHTML = "Product Name: " + nameProduct;
                            var priceProduct = $('#priceProduct').val();
                            document.getElementById("priceProduct" + id).innerHTML = "Price: " + priceProduct;
                            var GameID = $('#gID').val();
                            var imageGame = "";
        <c:forEach items="${g}" var="listG">
                            var gID = '${listG.ID}';
                            if (gID == GameID) {
                                imageGame += "<img src=\"data:image/jpeg;base64, ${listG.image}\" style=\"width: 100%; height: 100%; margin: 5px 0\">";
                            }
        </c:forEach>
                            document.getElementById("imageGame" + id).innerHTML = imageGame;
                            document.getElementById("closeModal").click();
                        }
                    });
                });
            });
        }
        function DeleteProduct(id) {

            if ($(document).on('click', '#confirmDelete', function () {
                $.ajax({
                    type: 'GET',
                    url: 'DeleteProduct',
                    data: {productID: id},
                    success: function () {
                        console.log("delete successfuly");
                        $("#cardproduct_" + id).remove();
                    }
                });
            }))
                ;
        }
    </script>
    <style>
        .card-title{
            font-size: 16px;
        }
        .productName{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 200px;
        }
        .productName:hover { 
            width: 270px;
        }
    </style>
</html>
