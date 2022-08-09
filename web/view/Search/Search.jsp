<%-- 
    Document   : Search
    Created on : Jul 12, 2022, 1:00:10 PM
    Author     : Admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>
        <script src="library/jquery.min.js" type="text/javascript"></script>
        <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    </head>
    <body style="background-color: #212A37;">
        <div>
            <jsp:include page='../../resource/components/category.jsp'/>
        </div>

        <div class="row" style="margin-top:200px;height: 510px">
            <div class="col-md-3" style="border: solid">
                <button class="everyone" id="searchByEveryone">EveryOne</button><br>
                <button class="post" id="searchByPost">Post</button><br>
                <button class="product" id="searchByProduct">Product</button>
            </div>
            <div class="col-md-9 row" style="border: solid"id="result">
                <c:if test="${listuser.size() == 0}">
                    <p style="color:white;text-align:center;margin-top: 50px;">Không tìm thấy người dùng mà bạn tìm kiếm !</p>
                </c:if>
                <c:forEach items="${listuser}" var="u">
                    <div class="flex-container">
                        <div class="avatar">
                            <img src="data:image/jpeg;base64,${u.avatar}" alt="avatar"/>
                        </div>
                        <div class="nameUser" style="color: black">${u.displayname}</div>
                        <div class="chat">
                            <a href="chat?id=${u.ID}" style="text-decoration: none;padding: 10px;">Chat</a>
                        </div>
                    </div>
                </c:forEach>


            </div>
        </div>
        <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <span id="resultDetail"></span>
                </div>
            </div>
        </div>

        <script>

            $(document).ready(function () {
                $(document).on("click", "#searchByPost", function () {
                    $.ajax({
                        type: 'POST',
                        url: 'SearchPost',
                        data: {"keyword": $('#keywordSearch').val(),
                        },
                        success: function (dataJson) {
                            console.log(dataJson);
                            if (dataJson.length == 0) {
                                $('#result').html("<p style=\"color:white;text-align:center;margin-top: 50px;\">Không tìm thấy bài đăng mà bạn tìm kiếm !</p>");
                            } else {
                                var html = "";
                                $.each(dataJson, function (key, value) {
                                    var imagePost = value['image'] != "null" ?
                                            "<img src=\"data:image/jpeg;base64," + value['image'] + "\" alt=\"image content\"style=\"height: 130px; width: 130px;\"/>\n" : ""
                                    //$('#result').remove();
                                    html += "<div class = \"card\" id=\"card_post_id_\" style=\"width: 1128px;margin-bottom:10px;\">\n" +
                                            "  <div class=\"card-body\">\n" +
                                            "       <div class=\"row\">\n" +
                                            "           <div class=\"col-lg-1 col-md-2\">\n" +
                                            "               <img src=\"data:image/jpeg;base64," + value['user']['avatar'] + "\" class=\"rounded-circle\" alt=\"avatar\" style=\"height: 75px; width: 75px;\">\n" +
                                            "           </div>" +
                                            "           <div class=\"col-lg-11 col-md-10\">\n" +
                                            "               <h5 class=\"card-title\">" + value['user']['displayname'] + "</h5>\n" +
                                            "               <p class=\"card-text\"><small>" + value['time'] + "</small></p>\n" +
                                            "           </div>" +
                                            "       </div>" +
                                            "       <div class=\"card-body row\">\n" +
                                            "           <div class=\"col-3  post-game\">\n" +
                                            "           <img src=\"data:image/jpeg;base64," + value['game']['image'] + "\" alt=\"image game\" class=\"img-fluid rounded-start img-game\">\n" +
                                            "           </div>" +
                                            "           <div class=\"col-7\">\n" +
                                            "               <div class=\"row\">\n" +
                                            "                   <p class=\"card-text post-content col-8\">" + value['content'] + "</p>\n" +
                                            "                   <div class=\"col-4\">\n" +
                                            imagePost
                                            +
                                            "    </div>\n" +
                                            "</div>\n" +
                                            "<div class=\"like\">" +
                                            "<button class=\"btn btn-light\" id=\"card_like_amount_id_" + value['ID'] + "\" disabled>" + value['like'] + "</button>\n" +
                                            "<button class=\"btn btn-card-like\">Like</button>\n" +
                                            "<button class=\"btn btn-card-comment\">Comment</button>\n" +
                                            "</div>\n" +
                                            "</div>\n" +
                                            "</div>\n" +
                                            "</div>\n" +
                                            "</div>";
                                });
                                $('#result').html(html);
                            }
                        }
                    });
                });
                $(document).on("click", "#searchByProduct", function () {
                    $.ajax({
                        type: 'POST',
                        url: 'SearchProduct',
                        data: {"keyword": $('#keywordSearch').val(),
                        },
                        success: function (dataJson) {
                            console.log(dataJson);
                            if (dataJson.length == 0) {
                                $('#result').html("<p style=\"color:white;text-align:center;margin-top: 50px;\">Không tìm thấy sản phẩm mà bạn tìm kiếm !</p>");
                            } else {
                                var html = "";
                                $.each(dataJson, function (key, value) {
                                    html += //"           <div class=\"row\">\n" +
                                            "                <div class=\"col-md-3\" id=\"cardproduct_" + value['PurID'] + "\" style=\"margin-left: 70px;margin-top: 20px;border:solid\">\n" +
                                            "                    <div class=\"image\" style=\"\" id=\"imageGame" + value['PurID'] + "\">\n" +
                                            "                        <img src=\"data:image/jpeg;base64," + value['imageGame'] + "\"style=\"width: 100%; height: 100%; margin: 5px 0;\"/>\n" +
                                            "                    </div>\n" +
                                            "                    <div class=\"body-content\">\n" +
                                            "                        <div class=\"\" style=\"display: flex; justify-content: space-between\">\n" +
                                            "                            <div class=\"content\">\n" +
                                            "\n" +
                                            "\n" +
                                            "                                <p style =\"color:white\">Seller:" + value['displaynameUser'] + " </p>\n" +
                                            "\n" +
                                            "                            </div>\n" +
                                            "                            <div class=\"content\"id=\"priceProduct\" >\n" +
                                            "                                <p style =\"color:white\">Price:" + value['price'] + " $ <p>\n" +
                                            "\n" +
                                            "                            </div>\n" +
                                            "                        </div>\n" +
                                            "\n" +
                                            "                        <div class=\"\">\n" +
                                            "                            <div class=\"content\" style=\"text-align: left\">\n" +
                                            "                                <p style =\"color:white\">Release Day:" + value['time'] + " </p>\n" +
                                            "                            </div>\n" +
                                            "                        </div>\n" +
                                            "                        <div class=\"\">\n" +
                                            "                            <div class=\"nameProduct\" style=\"text-align: left\"id=\"nameProduct\">\n" +
                                            "                               <p style =\"color:white\"> Product Name:" + value['name'] + "</p> \n" +
                                            "                            </div>\n" +
                                            "                        </div>\n" +
                                            "                    </div>\n" +
                                            "                    <div class=\"purchase-button row\" style=\"text-align: center; margin-bottom: 10px; margin-top: 10px\">\n" +
                                            "                        <div style=\"display: flex\">\n" +
                                            "                            <a style=\"background: #3AAA23; color: white;text-decoration: none; border: 1px solid white; padding: 5px 5px 5px 5px; width: 90%; margin-right: 5px\" href=\"chat?id=${listP.userID}\">\n" +
                                            "                                Contact\n" +
                                            "                            </a>\n" +
                                            "                            <button onclick=\"detailProduct(" + value['PurID'] + ")\" id=\"detail\" data-bs-toggle=\"modal\" data-bs-target=\"#detailModal\" style=\"margin-left: 5px;background: #3AAA23; color: white; border: 1px solid white; padding: 5px 5px 5px 5px;width: 90%\">\n" +
                                            "                                Detail\n" +
                                            "                            </button>\n" +
                                            "                        </div>\n" +
                                            "\n" +
                                            "                    </div>\n" +
                                            "                </div>\n"
                                    //"            </div>"
                                });
                                $('#result').html(html);
                            }
                        }
                    });
                });
                $(document).on("click", "#searchByEveryone", function () {
                    $.ajax({
                        type: 'POST',
                        url: 'Search',
                        data: {"keyword": $('#keywordSearch').val(),
                        },
                        success: function () {
//                       
//                        console.log(listUser);
//                        
            <c:if test="${listuser.size() == 0}">
                            console.log("acbc");

                            $('#result').html("<p style=\"color:white;text-align:center;margin-top: 50px;\">Không tìm thấy người dùng mà bạn tìm kiếm !</p>");
            </c:if>
            <c:if test="${listuser.size() != 0}">
                            var html = "";
                            //html+="<div class=\"flex-container\">"
                <c:forEach items="${listuser}" var="u">
                            var avatar = '${u.avatar}'
                            var displayname = '${u.displayname}'
                            var ID = '${u.ID}'
                            html += "<div class=\"flex-container\">" +
                                    " <div class=\"avatar\">\n" +
                                    "                            <img src=\"data:image/jpeg;base64," + avatar + "\" alt=\"avatar\"/>\n" +
                                    "                        </div>\n" +
                                    "                        <div class=\"nameUser\" style=\"color: black\">" + displayname + "</div>\n" +
                                    "                        <div class=\"chat\" style=\"color: white\">\n" +
                                    "                           <a href=\"chat?id=" + ID + "\" style=\"text-decoration: none;padding: 10px;\">Chat</a>" +
                                    "                        </div>"
                                    + "</div>"
                </c:forEach>
                            $('#result').html(html);
            </c:if>
                        }
                    });
                });
            });
            function detailProduct(id) {

                $.ajax({
                    type: 'POST',
                    url: 'viewpurchase',
                    data: {productID: id},
                    success: function (data) {
                        //                        $('#result').html(result);
                        console.log(data);

                        var html = "";
                        html += "                    <div class=\"modal-header\">\n"
                                + "                        <h5 class=\"modal-title\" style=\"font-size:25px\" id=\"exampleModalLabel\">Detail Information Of Product</h5>\n"
                                + "                        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>\n"
                                + "                    </div>\n"
                                + "                    <div class=\"modal-body\">\n"
                                + "                        <div class=\"container\">\n"
                                + "                            <div class=\"card\">\n"
                                + "                                <div class=\"container-fliud\">\n"
                                + "                                    <div class=\"wrapper row\">\n"
                                + "                                        <div class=\"preview col-md-6\">\n"
                                + "<div id=\"carouselExampleControls\" class=\"carousel slide\" data-bs-ride=\"carousel\">\n"
                                + "  <div class=\"carousel-inner\">\n"
                                + "    <div class=\"carousel-item active\">\n"
                                + "<img class=\"d-block w-100\" alt=\"...\" style=\"width:100%;height:384px\"src=\"data:image/jpeg;base64," + data.images[0] + "\">\n"
                                + "    </div>\n";
                        for (let i = 1; i < data.images.length; i++) {
                            html += "<div class=\"carousel-item\">\n"
                                    + "<img class=\"d-block w-100\" alt=\"...\" style=\"width:100%;height:384px\"src=\"data:image/jpeg;base64," + data.images[i] + "\">\n"
                                    + "    </div>"
                        }
                        html +=
                                "  </div>\n"
                                + "  <button class=\"carousel-control-prev\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"prev\">\n"
                                + "    <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>\n"
                                + "    <span class=\"visually-hidden\">Previous</span>\n"
                                + "  </button>\n"
                                + "  <button class=\"carousel-control-next\" type=\"button\" data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"next\">\n"
                                + "    <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>\n"
                                + "    <span class=\"visually-hidden\">Next</span>\n"
                                + "  </button>\n"
                                + "</div>"
                                + "                                        </div>\n"
                                + "                                        <div class=\"details col-md-6\">\n"
                                + "                                            <h3 class=\"product-title\">" + data.name + "</h3>\n"
                                + "                                            <p class=\"release-date\">Release Date:" + data.time + "</p>\n"
                                + "                                            <p class=\"product-description\">" + data.description + "</p>\n"
                                + "                                            <h4 class=\"price\">Current price: <span>" + data.price + "$</span></h4>\n"
                                + "                                        </div>\n"
                                + "                                    </div>\n"
                                + "                                </div>\n"
                                + "                            </div>\n"
                                + "                        </div>\n"
                                + "                    </div>\n"
                                + "<div class=\"modal-footer action\">\n"
                                + "        <a class=\"contact-chat btn btn-default\" type=\"button\" href=\"chat?id=" + data.userID + "\">Contact</a>\n"
                                + "      </div>"
                        document.getElementById('resultDetail').innerHTML = html;
                    }
                });
            }
        </script>
        <style>
            html{
                height: 100%;
            }
            body{
                margin:0;
                padding:0;
                font-family: sans-serif;
            }
            .everyone{
                margin-top: 20px;
                margin-left: 10px;
                height: 50px;
                width: 350px;
                border-radius: 10px;
            }
            .post{
                margin-top: 20px;
                margin-left: 10px;
                height: 50px;
                width: 350px;
                border-radius: 10px;
            }
            .product{
                margin-top: 20px;
                margin-left: 10px;
                height: 50px;
                width: 350px;
                border-radius: 10px;
            }
            .flex-container{
                display: flex;
                margin-top: 20px;
            }
            .avatar{
                margin-left: 10px;
                width: 100px;
                height: 50px;
            }
            .avatar img{
                width: 70px;
                height: 50px;
            }
            .chat{
                margin-left: 250px;
            }
            .flex-container {
                display: flex;
                margin-top: 20px;
                height: 100px;
                background-color: white;
                padding: 12px;
                /* color: black; */
                border-radius: 10px;
                width: 700px;
                /* margin: auto; */
                /* text-align: center; */
                margin-left: 200px;
            }
            .chat a{
                margin-left: 100px;
                background-color: rgba(144,183,241,0.25);
            }
            .like button{
                margin-top: 130px;
            }
            .like .btn-light{
                background-color: rgb(166, 169, 173);
            }
            .like .btn-card-like{
                background-color: rgb(58, 170, 35);
            }
            .like .btn-card-comment{
                background-color: rgb(58, 170, 35);
            }
        </style>
    </body>
</html>
