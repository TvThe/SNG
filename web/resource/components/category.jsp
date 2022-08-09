<%-- 
    Document   : category
    Created on : 25-05-2022, 02:52:56
    Author     : TuanAnh
--%>
<script src="library/bootstrap.min.js" type="text/javascript"></script>
<link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<div class="fixed-top" id="header">
    <nav class="navbar navbar-expand-lg navbar-dark" id="category" style="background-color: #5865F2;">
        <div class="container-fluid row">
            <a class="navbar-brand col-1" onClick="window.location.href = 'viewNewsfeed'"><img style="width: 50px;" src="resource/img/sng.png"/></a>
            <button class="navbar-toggler collapsed col-1" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation" style="margin-bottom: 8px;">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse collapse col-10 row" id="navbarColor02" style="">
                <form class="d-flex col-lg-6" action="Search" method="POST">
                    <input class="form-control me-2" type="search" id="keywordSearch"name="keyword" placeholder="Search" aria-label="Search"  value="${keyword}">
                    <button class="btn btn-outline-light" type="submit">Search</button>
                </form>
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 offset-lg-2 col-lg-4 row">

                    <li class="nav-item col-3" style="text-align: center">
                        <div class="box-icon-chat" style="  position: relative;">
                            <a class="nav-link" href="chat"><i class="fas fa-comment-alt"></i></a>
                            <span id="numberOfNewMsg" style="
                                  position: absolute;
                                  top: 1px;
                                  right: 22px;
                                  font-size: 12px;
                                  background-color: white;
                                  border-radius: 5px;
                                  width: 16px;">0</span>
                        </div>

                    </li>
                    <li class="nav-item col-3" style="text-align: center">
                        <div class="box-icon-notification" style="position: relative;">
                            <div class="dropdown">
                                <a class="btn nav-link" href="#" role="button" id="dropdownNotification" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-bell" style="cursor: pointer;"></i></a>
                                <ul class="dropdown-menu" id="dropdownNotificationBoard" aria-labelledby="dropdownNotification" style="left: -100px;">
                                    <li style="width: 300px;" ><a class="dropdown-item" href="#">Action</a></li>
                                    <li><a class="dropdown-item" href="#">Another action</a></li>
                                    <li><a class="dropdown-item" href="#">Something else here</a></li>
                                </ul>
                            </div>
                            <div id="numberOfNewNotification" style="position: absolute;top: 5px;right: 25px;font-size: 12px;background-color: #5865F2;border-radius: 10px;width: 10px;height: 10px;"></div>
                        </div>

                    </li>
                    <li class="nav-item col-3" style="display:flex">
                        <a class="nav-link" href="viewProfile" style="margin-right: 5px"><img style="width:30px;height:30px;border-radius: 50%;object-fit: contain;" id="meImage" src="data:image/jpg;base64,${sessionScope.account.avatar}"></a>
                        <input type="text" hidden id="senderID" value="${sessionScope.account.ID}">
                     
                    <c:if test="${sessionScope.account.isAdmin() == true}">
                        <a class="nav-link" href="admin"><i class="fas fa-user-lock"></i></a>
                    </c:if>
                    </li>
                    <li class="nav-item col-3">
                        <a class="nav-link" href="Logout">log out</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div id="navigation" class="row justify-content-between" style="margin-top: 5px; display: none;">
        <button class="btn col-lg-6" onClick="window.location.href = 'viewNewsfeed'" style="background-color: #5865F2; color:#FFFFFF; text-align: center; height: 50px; width: 49.8%; ">News Feed</button>   
        <button class="btn col-lg-6" onClick="window.location.href = 'viewpurchase'" style="background-color: #5865F2; color:#FFFFFF; text-align: center; height: 50px; width: 49.8%; ">View Market</button>   
    </div>
</div>
<jsp:include page="notification.jsp" />
<script src="resource/js/notification.js"></script>
<script src="library/bs4pop.js" type="text/javascript"></script>
<link href="library/bs4pop.css" rel="stylesheet" type="text/css"/>
<!--add this code to show navigation
<link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
$('#header').mouseenter(function () {
    $("#navigation").slideDown(250);

});
$("#header").mouseleave(function () {
    $("#navigation").slideUp(250);
});
-->