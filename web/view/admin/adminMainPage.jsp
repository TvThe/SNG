<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />

        <title>Admin</title>
        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
        <!--     Fonts and icons     -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link href="<c:url value="library/bootstrap.min.css"></c:url>" rel="stylesheet" id="bootstrap-css">
        <script src="<c:url value="library/bootstrap.min.js"></c:url>"></script>
        <script src="<c:url value="library/jquery.min.js"></c:url>"></script> 
        <script src="<c:url value = "resource/js/admin.js"/>" type="text/javascript"></script>
        <link href="<c:url value = "resource/css/admin.css"/>" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>

        <script>
            $('#header').mouseenter(function () {
                $("#navigation").slideDown(250);
            });
            $("#header").mouseleave(function () {
                $("#navigation").slideUp(250);
            });
        </script>
        <style>
            .box-icon-chat {
                position: relative;
            }
            #numberOfNewMsg{
                position: absolute;
                top: 1px;
                right: 28px;
                font-size: 12px;
                background-color: white;
                border-radius: 5px;
                width: 16px;
            }
        </style> 

    </head>

    <body style="background-color: #212A37;">
        <jsp:include page ="../../resource/components/category.jsp" />
        <div class="container-fluid h-90" style="margin-top: 100px;">
            <div class="row justify-content-center h-100">
                <div class="col-sm-3 col-md-3 col-lg-3">
                    <div class="navbar_admin_left">
                        <ul class="list-group manage_box">
                            <li class="list-group-item manage_item" ><a style="text-decoration: none;color:black;" href="admin-statistic">Statistic</a></li>
                            <li class="list-group-item manage_item" onclick="selectManage(2)"><a >Manage type of game</a></li>
                            <li class="list-group-item manage_item" onclick="manageReport()" id="manageReport"><a>Manage report</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-9 col-md-8 col-lg-9">
                    <div id="content" class="content_admin_right" style="overflow: scroll;">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="card bg-primary">
                                    <div class="card-header">Number user online</div>
                                    <div class="card-body" id="numberOfUserOnline"></div> 

                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="card bg-success">
                                    <div class="card-header">Number new post</div>
                                    <div class="card-body">${numberOfPost}</div> 

                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="card bg-warning">
                                    <div class="card-header">Number new product</div>
                                    <div class="card-body">${numberOfProduct}</div> 

                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="card bg-dark">
                                    <div class="card-header">Number comments</div>
                                    <div class="card-body">${numberOfComment}</div> 

                                </div>
                            </div>
                        </div>
                        <br/>
                        <p>User active</p>
                        <div class="row" style="height: 400px;overflow: scroll;">
                            <div class="col-md-12" >
                                <table class="table table-dark table-hover" >
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Name</th>
                                            <th>Avatar</th>
                                            <th>See More</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listUser}" var="user" varStatus="loop">
                                            <tr>
                                                <td>${loop.index+1}</td>
                                                <td>${user.displayname}</td>
                                                <td><img src="data:image/jpg;base64,${user.getAvatar()}" style="width: 50px;height: 60px;" /></td>
                                                <td><a href="viewProfile?id=${user.getID()}">See more</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <canvas id="myChart" ></canvas>
                            </div>
                        </div>                    



                    </div>
                </div>
            </div>
        </div>  
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 id="titlePopup" class="modal-title">Modal Heading</h4>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <!-- Modal body -->
                    <div  id="formContent"class="modal-body">

                    </div>

                    <!-- Modal footer -->
                    <div id="modalFooter"class="modal-footer">
                        <button type="button" class="btn btn-danger" id="closeBtn" data-bs-dismiss="modal">Close</button>
                    </div>

                </div>
            </div>
        </div>
    </body>
    <script>
        function manageReport() {
            $.ajax({
                url: 'Report',
                type: 'GET',
                data: {},
                success: function (data) {
                    var html = "";
                    html += "<table class=\"table table-dark table-striped\" id=\"table\">\n"
                            + "            <thead>\n"
                            + "                <tr style=\"text-align:center\">\n"
                            + "                    <th scope=\"col\"><button style=\"border:1px solid white; background: transparent; color:white\" id=\"manageReportProduct\">Manage report Product</button></th>\n"
                            + "                    <th scope=\"col\"><button style=\"border:1px solid white; background: transparent; color:white\" id=\"manageReportPost\">Manage report Post</button></th>\n"
                            + "                </tr>\n"
                            + "            </thead>\n"
                            + "    </table>"
                            + "    <a id=\"view\"></a>";
                    document.getElementById("content").innerHTML = html;
                    console.log(data);
                    let textPurID = "";
                    let textMessage = "";
                    let textUser = "";
                    let textView = "";
                    for (let i = 0; i < data.length; i++) {
                        textPurID += "<span id=\"purID\">" + data[i].purID + "</span><br>";
                        textMessage += "<span>" + data[i].reportMessage + "</span><br>";
                        textUser += "<span>" + data[i].userID + "</span><br>";
                        textView += "<a id=\"viewProduct\" href=\"product?id=" + data[i].purID + "\" style=\"border-bottom: 1px solid white; text-decoration:none ; color:white\">View</a><br>";
                    }
                    var html = "";
                    html += "<table class=\"table table-dark table-striped\" id=\"table\" style=\"text-align:left\">\n"
                            + "            <thead>\n"
                            + "                <tr style=\"text-align:center\">\n"
                            + "                    <th scope=\"col\" style=\"border: 1px solid white\">Product ID</th>\n"
                            + "                    <th scope=\"col\" style=\"border: 1px solid white; width: 400px\">Report Message</th>\n"
                            + "                    <th scope=\"col\" style=\"border: 1px solid white\">User Report</th>\n"
                            + "                    <th scope=\"col\" style=\"border: 1px solid white\"></th>\n"
                            + "                </tr>\n"
                            + "            </thead>\n"
                            + "            <tbody style=\"overflow: scroll\">\n"
                            + "                <tr>\n"
                            + "                    <td style=\"border: 1px solid white;\">" + textPurID + "</td>\n"
                            + "                    <td style=\"border: 1px solid white\">" + textMessage + "</td>\n"
                            + "                    <td style=\"border: 1px solid white\">" + textUser + "</td>\n"
                            + "                    <td style=\"border: 1px solid white; text-align:center; cursor: pointer\">" + textView + "</td>\n"
                            + "                </tr>\n"
                            + "            </tbody>\n"
                            + "    </table>";
                    document.getElementById("view").innerHTML = html;
                    console.log(data);
                }
            });
            $(document).ready(function () {
                $(document).on('click', '#manageReportProduct', function () {

                    $.ajax({
                        type: 'GET',
                        url: 'Report',
                        data: {
                        },
                        success: function (data) {
                            let textPurID = "";
                            let textMessage = "";
                            let textUser = "";
                            let textView = "";
                            for (let i = 0; i < data.length; i++) {
                                textPurID += "<span id=\"purID\" value=\"" + data[i].purID + "\">" + data[i].purID + "</span><br>";
                                textMessage += "<span>" + data[i].reportMessage + "</span><br>";
                                textUser += "<span>" + data[i].userID + "</span><br>";
                                textView += "<a id=\"viewProduct1\" href=\"product?id=" + data[i].purID + "\" style=\"border-bottom: 1px solid white;text-decoration:none; color:white\">View</a><br>";
                            }
                            var html = "";
                            html += "<table class=\"table table-dark table-striped\" id=\"table\" style=\"text-align:left\">\n"
                                    + "            <thead>\n"
                                    + "                <tr style=\"text-align:center\">\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\">Product ID</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white; width: 400px\">Report Message</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\">User Report</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\"></th>\n"
                                    + "                </tr>\n"
                                    + "            </thead>\n"
                                    + "            <tbody>\n"
                                    + "                <tr>\n"
                                    + "                    <td style=\"border: 1px solid white;\">" + textPurID + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white\">" + textMessage + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white\">" + textUser + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white; text-align:center; cursor: pointer;\">" + textView + "</td>\n"
                                    + "                </tr>\n"
                                    + "            </tbody>\n"
                                    + "    </table>";
                            document.getElementById("view").innerHTML = html;
                        }
                    });
                });
            });
            $(document).ready(function () {
                $(document).on('click', '#manageReportPost', function () {

                    $.ajax({
                        type: 'GET',
                        url: 'Reportpost',
                        data: {
                        },
                        success: function (data) {
                            let textPostID = "";
                            let textMessage = "";
                            let textUser = "";
                            let textView = "";
                            for (let i = 0; i < data.length; i++) {
                                textPostID += "<span id=\"postID\">" + data[i].postID + "</span><br>";
                                textMessage += "<span>" + data[i].reportMessage + "</span><br>";
                                textUser += "<span>" + data[i].userID + "</span><br>";
                                textView += "<a id=\"viewPost\" href=\"post?id=" + data[i].postID + "\" style=\"border-bottom: 1px solid white\">View</a><br>";
                            }
                            var html = "";
                            html += "<table class=\"table table-dark table-striped\" id=\"table\" style=\"text-align:left\">\n"
                                    + "            <thead>\n"
                                    + "                <tr style=\"text-align:center\">\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\">Post ID</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white; width: 400px\">Report Message</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\">User Report</th>\n"
                                    + "                    <th scope=\"col\" style=\"border: 1px solid white\"></th>\n"
                                    + "                </tr>\n"
                                    + "            </thead>\n"
                                    + "            <tbody>\n"
                                    + "                    <td style=\"border: 1px solid white;\">" + textPostID + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white\">" + textMessage + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white\">" + textUser + "</td>\n"
                                    + "                    <td style=\"border: 1px solid white; text-align:center; cursor: pointer;\">" + textView + "</td>\n"
                                    + "            </tbody>\n"
                                    + "    </table>";
                            document.getElementById("view").innerHTML = html;
                        }
                    });
                });
            });
        }
        var listData = []
        var data1 = []
        var data2 = []
        <c:forEach items="${valueActives1}" var="item">
        data1.push('${item}')
        </c:forEach>
        <c:forEach items="${valueActives2}" var="item">
        data2.push('${item}')
        </c:forEach>
        listData.push(data1)
        listData.push(data2)
        var xValues = listData[0].map((item) => item.split("-")[0])
        Chart.defaults.global.defaultFontColor = '#000000';
        Chart.defaults.global.defaultFontFamily = 'Arial';
        new Chart("myChart", {
            type: "line",
            data: {
                labels: xValues,
                datasets: [
                    {
                        label: 'Post',
                        data: listData[0].map((item) => item.split("-")[1]),
                        backgroundColor: 'rgba(0, 128, 128, 0.3)',
                        borderColor: 'rgba(0, 128, 128, 0.7)',
                        borderWidth: 1
                    },
                    {
                        label: 'Product',
                        data: listData[1].map((item) => item.split("-")[1]),
                        backgroundColor: 'rgba(0, 128, 128, 0.7)',
                        borderColor: 'rgba(0, 128, 128, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: {title: {
                    display: true,
                    text: "The number of posts,products"
                },
                scales: {
                    yAxes: [{
                            ticks: {
                                label: "Date",
                                beginAtZero: true
                            }
                        }]
                },

            }
        });

    </script>
    <!--===============================================================================================-->
</html>
