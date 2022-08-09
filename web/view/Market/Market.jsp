<%-- 
    Document   : Purchase
    Created on : Jun 7, 2022, 3:04:23 PM
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
        <title>Purchase</title>
        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>
        <script src="library/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="<c:url value="resource/js/websocket.js"></c:url>"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="resource/css/common.css"></c:url>"/>
        </head>
        <body style="background-color: #212A37;">

            <div>
            <jsp:include page='../../resource/components/category.jsp'/>
        </div>

        <div class="purchase-box row" id="purchase" style="margin-top: 6%; ">
            <span id="reported" style="color: red; margin-top: auto"></span>
            <div class="col-md-2" style="text-align: center">
                <jsp:include page='../../resource/components/PopUpCreateProduct.jsp'/>
                <form action="viewpurchase" method="GET">
                    <div class="container mt-3" style="margin-top: 6%" id="slt">
                        <div style="text-align: left;">
                            <a style="color: Red; text-align: left; font-size: 16px">Search Product By:</a>
                        </div>

                        <select class="form-select" name="selectid" id="selectsortproduct">
                            <option value="${search}" hidden>${search}</option>
                            <option value="Price Decrease">Price Decrease</option>
                            <option value="Price Increase">Price Increase</option>
                            <option value="Oldest">Oldest </option>
                            <option value="Newest">Newest </option>
                        </select>

                        <div style="text-align: left; margin-top: 10px">
                            <a style="color: Red; text-align: left; font-size: 17px">Price:</a>
                        </div>  
                        <div >
                            <table>
                                <tr>

                                <div style="display: flex; width: 90%; margin-right: 10px;">
                                    <td>
                                        <div style="text-align: left;">
                                            <a style="color: Red; text-align: left; font-size: 16px">From:</a>
                                        </div>
                                    </td>
                                    <td>
                                        <select class="form-select" name="pricefrom" style="font-size: 14px">
                                            <option value="${pricefrom}" hidden>${pricefrom}$</option>
                                            <option value="0">0$</option>
                                            <option value="100">100$</option>
                                            <option value="250">250$</option>
                                            <option value="1000">1000$</option>
                                            <option value="5000">5000$</option>
                                        </select>
                                    </td>
                                </div>

                                </tr>
                                <tr>

                                <div style="display: flex; width: 90%">
                                    <td>
                                        <div style="text-align: left;">
                                            <a style="color: Red; text-align: left; font-size: 16px">To:</a>
                                        </div>
                                    </td>
                                    <td>
                                        <select class="form-select" name="priceto" style="font-size: 14px">
                                            <option value="${priceto}" hidden>${priceto}$</option>
                                            <option value="100">100$</option>
                                            <option value="250">250$</option>
                                            <option value="1000">1000$ </option>
                                            <option value="5000">5000$</option>
                                            <option value="More than 5000">More than 5000$</option>
                                        </select>
                                    </td>
                                </div>

                                </tr>
                            </table>>
                        </div>
                    </div>

                    <button id="searchbutton" style="background: #3AAA23; color: white; border: 1px solid white; width: 50%; padding: 5px 0">Search</button>
                </form>
            </div>

            <div  class="col-md-10 row" id="result1"> 
                <c:forEach items="${p}" var="p">
                    <div class="col-md-2 product" id="cardproduct_${p.purID}">
                        <div class="card" style="padding: 0px 0px">
                            <div class="card-header" >
                                <div class="card-1st-row" style="display: flex; justify-content: space-between">
                                    <div class="card-seller">
                                        <c:forEach items="${u}" var="listUser">
                                            <c:if test="${listUser.ID == p.userID}">
                                                <h6 class="card-title" id="seller">Seller: ${listUser.displayname} </h6>
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
                                <div class="card-img" id="imageGame${p.purID}">
                                    <c:forEach items="${g}" var="listGame">
                                        <c:if test="${listGame.ID == p.gameID}">
                                            <img src="data:image/jpeg;base64, ${listGame.image}" class="card-img-top" alt="..." style="width: 100%; height: 100%;">
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="card-body">
                                <p class="card-title productName" id="nameProduct${p.purID}">Product Name: ${p.name}</p>
                                <p hidden="true" class="card-title" id="purID">${p.purID}</p>
                                <p class="card-title" id="priceProduct${p.purID}">Price: ${p.price}$</p>
                                <p class="card-title">Release: ${p.time}</p>
                                <div class="card-button" style="justify-content: space-around; display: flex">
                                    <a href="chat?id=${p.userID}" class="btn btn-success" <c:if test="${sessionScope.account.ID == p.userID}">onclick="return false;" style="background: gray"</c:if>>Contact</a>
                                    <a class="btn btn-success" type="button" style="width: 80.94px; height:38.21px" id="detailProduct" href="product?id=${p.purID}">Detail</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
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
        <!--show detail product-->
        <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <span id="result"></span>
                </div>
            </div>
        </div>
        <div class="modal" id="reportDetail" tabindex="-1">
            <div class="modal-dialog" >
                <div class="modal-content" style="background: #3a3b3c;">
                    <span id="reportProblem"></span>

                </div>
            </div>
        </div>
        <!--pagination-->
        <div style="margin-left: 42%; margin-top: 10px;" id="page"> 
            <nav  style="background: transparent">
                <ul class="pagination" >
                    <li style="border:1px solid red"><a style="background: black; color: red; border: none" class="page-link" href="?page=1&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">First</a></li>
                        <c:if test="${page!=1}">
                            <c:if test="${page!=2}">
                            <li style="border:1px solid red; margin: 0 5px;"><a id="pagenext" style="background: black; color: red; border: none" class="page-link" href="?page=${page-2}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">${page-2}</a></li>
                            </c:if>
                        <li style="border:1px solid red; margin: 0 5px;"><a id="pagenext" style="background: black; color: red; border: none" class="page-link" href="?page=${page-1}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">${page-1}</a></li>
                        </c:if>
                    <li style="border:1px solid red; margin: 0 5px;"><a style="background:white ; color: red; border: none" class="page-link" href="?page=${page}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">${page}</a></li>
                        <c:if test="${page != totalpage}">
                        <li style="border:1px solid red; margin: 0 5px;"><a id="pagenext" style="background: black; color: red; border: none" class="page-link" href="?page=${page+1}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">${page+1}</a></li>
                        </c:if>
                        <c:if test="${page != (totalpage-1) && page != totalpage}">
                        <li style="border:1px solid red; margin: 0 5px;"><a id="pagenext" style="background: black; color: red; border: none" class="page-link" href="?page=${page+2}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">${page+2}</a></li>
                        </c:if>
                    <li style="border:1px solid red"><a style="background: black; color: red; border: none" class="page-link" href="?page=${totalpage}&selectid=${search}&pricefrom=${pricefrom}&priceto=${priceto}">Last</a></li>
                </ul>
            </nav>
        </div>

        <script>
            $('#header').mouseenter(function () {
                $("#navigation").slideDown(250);
            });
            $("#header").mouseleave(function () {
                $("#navigation").slideUp(250);
            });
            function reportProduct(id) {
                console.log(id);
                $.ajax({
                    type: 'POST',
                    url: 'viewpurchase',
                    data: {"productID": id
                    },
                    success: function (data) {
                        var html = '';
                        html += "                    <div class=\"modal-content\" style=\"background: #3a3b3c;\">\n"
                                + "                                <div class=\"modal-header\" style=\"border: none;\">\n"
                                + "                                    <div>\n"
                                + "                        <input type=\"hidden\" id=\"productID\" name=\"productID\" value=\"" + data.PurID + "\"/>\n"
                                + "                                        <h5 class=\"modal-title\" style=\" color: white\">Report</h5>\n"
                                + "                                    </div>\n"
                                + "                                    <div aria-label=\"Close\" role=\"button\" tabindex=\"0\" style=\"padding: 5px 5px\">\n"
                                + "                                        <i data-visualcompletion=\"css-img\" data-bs-dismiss=\"modal\" class=\"close\" style=\"background-image: url(&quot;https://static.xx.fbcdn.net/rsrc.php/v3/y1/r/Y8MxSAfaTlS.png&quot;); background-position: 3px -436px; background-size: 26px 908px; width: 25px; height: 25px; background-repeat: no-repeat; display: inline-block;\"></i>\n"
                                + "                                        <div class=\"\" data-visualcompletion=\"ignore\">\n"
                                + "                                        </div>\n"
                                + "                                    </div>\n"
                                + "                                </div>\n"
                                + "                                    <div class=\"modal-body\" style=\"justify-content: flex-start;\">\n"
                                + "                                        <h1 style=\"font-size: 20px; color: white; font-weight: bold\">Select report a problem below if you think the product has a problem</h1>\n"
                                + "                                        <p style=\"color: gray\">We will check your report and issue punishment if it is true</p>\n"
                                + "                                        <input type=\"radio\" id=\"reportProd\" name=\"report\" value=\"The product is not as described\">\n"
                                + "                                        <label class=\"a\" for=\"report\"> The product is not as described</label><br>\n"
                                + "                                        <input type=\"radio\" id=\"reportProd\" name=\"report\" value=\"Product does not exist\">\n"
                                + "                                        <label class=\"a\" for=\"report\"> Product does not exist</label><br>\n"
                                + "                                        <input type=\"radio\" id=\"reportProd\" name=\"report\" value=\"Inappropriate words\">\n"
                                + "                                        <label class=\"a\" for=\"report\"> Inappropriate words</label><br>\n"
                                + "                                    <div style=\"text-align:right\">\n"
                                + "                                        <button id=\"submit-report-button\" data-bs-dismiss=\"modal\" style=\"border: none; background: #3AAA23; padding:5px 10px; color:white\">Submit</button>"
                                + "                                    </div>\n"
                                + "                                    </div>\n"
                                + "                            </div>\n";
                        document.getElementById('reportProblem').innerHTML = html;
                    }
                });
                $(document).ready(function () {
                    $(document).on('click', '#submit-report-button', function () {

                        $.ajax({
                            type: 'POST',
                            url: 'Report',
                            data: {
                                "productID": $('#productID').val(),
                                "report": $("input[name=report]:checked").val()
                            },
                            success: function (data) {
                                var html = "";
                                html += "<div class=\"alert alert-dark alert-dismissible\" style=\"width: 500px; margin: auto; margin-bottom:10px;\">\n"
                                        + "    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\"></button>\n"
                                        + "    <strong>Product has been reported</strong>\n"
                                        + "  </div>"
                                document.getElementById('reported').innerHTML = html;
                            }
                        });
                    });
                });
            }
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
                                "<span id=\"checkName\" style=\"color:red;\"></span>" + "<br>" +
                                "                            <label>Description: </label>\n" +
                                "                            <input  class=\"form-control\" id=\"descriptionProduct\" name=\"description\"value=\"" + data.description + "\" rows=\"3\">\n" +
                                "<span id=\"checkDes\" style=\"color:red;\"></span>" + "<br>" +
                                "                            <label>Price: </label>\n" +
                                "                            <input class=\"form-control\" id=\"priceProduct\" name=\"price\"value=\"" + data.price + "\" rows=\"3\">\n" +
                                "<span id=\"checkPrice\" style=\"color:red;\"></span>" + "<br>" +
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
                                "                        <button class=\"btn btn-primary\" id=\"btnUpdateProduct\">Save</button>\n" +
                                "                    </div>\n";
                        document.getElementById('contentModalProduct').innerHTML = html;
                        console.log(data);
                    }
                });
                $(document).ready(function () {
                    $(document).on("click", "#btnUpdateProduct", function () {
                        if($('#priceProduct').val()!="" && !isNumeric($('#priceProduct').val())){
                            $('#checkPrice').html("Price is number!"); 
                            return 
                        }
                        if ($('#nameproduct').val() != "" && $('#descriptionProduct').val() != "" && $('#priceProduct').val() != "") {
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
                                    console.log("Update successfuly");
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
                        } else if ($('#nameproduct').val() != "" && $('#descriptionProduct').val() != "") {
                            $('#checkPrice').html("Price is blank!");
                        } else if ($('#descriptionProduct').val() != "" && $('#priceProduct').val() != "") {
                            $('#checkName').html("Name Product is blank!");
                        } else if ($('#nameproduct').val() != "" && $('#priceProduct').val() != "") {
                            $('#checkDes').html("Description is blank!");
                        } else if ($('#nameproduct').val() != "") {
                            $('#checkDes').html("Description is blank!");
                            $('#checkPrice').html("Price is blank!");
                        } else if ($('#descriptionProduct').val() != "") {
                            $('#checkName').html("Name Product is blank!");
                            $('#checkPrice').html("Price is blank!");
                        } else if ($('#priceProduct').val() != "") {
                            $('#checkName').html("Name Product is blank!");
                            $('#checkDes').html("Description is blank!");
                        } else {
                            $('#checkName').html("Name Product is blank!");
                            $('#checkDes').html("Description is blank!");
                            $('#checkPrice').html("Price is blank!");
                        }
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
            $(document).ready(function () {
                $(document).on('input', '#nameproduct', function () {
                    $('#checkName').html("");
                });
                $(document).on('input', '#descriptionProduct', function () {
                    $('#checkDes').html("");
                });
                $(document).on('input', '#priceProduct', function () {
                    $('#checkPrice').html("");
                });
            });
            function isNumeric(str) {
                if (typeof str != "string")
                    return false 
                return !isNaN(str) && 
                        !isNaN(parseFloat(str)) 
            }
        </script>
    </script>
    <style>
        html {
            height: 100%;
        }
        body {
            margin:0;
            padding:0;
            font-family: sans-serif;
            /*                overflow-x: hidden;*/
        }
        .body-content{
            box-sizing: border-box;
        }
        .product{
            margin-right: 1px; 
        }
        img {
            max-width: 100%; }
        .card {
            margin-top: 15px;
            background: #eee;
            line-height: 1.5em; }

        .release-date{
            color: gray;
            font-style: italic;
            font-size: 12px;
        }
        .a{
            color: white;
            border: none;
            font-size: 14px;
            padding: 10px 10px;
            border-radius: 10px;
        }

        .close{
            border-radius: 50%;
            background: gray;
        }
        .close:hover{
            background: silver;
            color: white;
        }
        .card-title{
            font-size: 14px;
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
        #seller{
            font-size: 16px;    
        }
    </style>
    <%@include file="../../resource/components/ShowListFriend.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
