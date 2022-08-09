
<!------ Include the above in your HEAD tag ---------->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Chat</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="resource/css/chat.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="resource/css/common.css"></c:url>">
        <script type="text/javascript" src="<c:url value="resource/js/chat.js"></c:url>"></script>
        <script type="text/javascript" src="<c:url value="resource/js/websocket.js"></c:url>"></script>

<!--            <link href="<c:url value="library/bootstrap.min.css"></c:url>" rel="stylesheet" id="bootstrap-css">
        <script src="<c:url value="library/bootstrap.min.js"></c:url>"></script>-->

            <script src="<c:url value="library/jquery.min.js"></c:url>"></script>
         
        </head>
        <!--Coded With Love By Mutiullah Samim-->
        <body style="height:0">

        <jsp:include page="../../resource/components/category.jsp" />
        <c:if test="${receiveID != null}">
            <input type="text" id="receiveID" hidden="" value="${receiveID}">
        </c:if>
        <input type="text" hidden="" id="countNewMsg" value="${countNewMsg}">
        <input type="text" hidden="" id="idSenderStrs" value="${idSenderStrs}">
        <div class="container-fluid h-90" style="margin-top: 100px;">
            <div class="row justify-content-center h-100">
                <div class="col-md-4 col-xl-3 chat"><div class="card mb-sm-3 mb-md-0 contacts_card">
                        <div  class="card-header">
                            <div class="input-group">
                                <input id="inputSearch" oninput="searchFriend(this.value)"type="text" placeholder="Search..."  class="form-control search">
                                <div class="input-group-prepend">
                                    <span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
                                </div>
                            </div>
                            <ul style="list-style-type: none;padding:0" id="resultSearch" >


                            </ul>


                        </div>
                        <div class="card-body contacts_body">
                            <ui class="contacts" id="recent">
                                <c:forEach items="${msgLastList}" var="msg">

                                    <c:forEach items="${userList}" var="user">
                                        <c:if test="${msg.getSenderID() == user.getID()}">
                                            <li onclick="setRecieve('${user.getID()}', '${user.getDisplayname()}', '${user.getAvatar()}')" id="recent${user.getID()}">
                                                <div class="d-flex bd-highlight">
                                                    <div class="img_cont">
                                                        <img src="data:image/jpg;base64,${user.getAvatar()}" class="rounded-circle user_img">
                                                        <span id="status-onl${user.getID()}"></span>
                                                    </div>
                                                    <div class="user_info">
                                                        <span>${user.getDisplayname() }</span>
                                                        <p id="lastMessage${ user.getID()}">${msg.getContent()}</p>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                        <c:if test="${msg.getReceiveID() == user.getID()}">
                                            <li onclick="setRecieve('${user.getID()}', '${user.getDisplayname()}', '${user.getAvatar()}')" id="recent${user.getID()}">
                                                <div class="d-flex bd-highlight">
                                                    <div class="img_cont">
                                                        <img src="data:image/jpeg;base64,${user.getAvatar()}" class="rounded-circle user_img">
                                                        <span id="status-onl${user.getID()}"></span>
                                                    </div>
                                                    <div class="user_info">
                                                        <span>${user.getDisplayname() }</span>
                                                        <p id="lastMessage${ user.getID()}">You: ${msg.getContent()}</p>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </ui>
                        </div>
                        <div class="card-footer"></div>
                    </div></div>
                <div class="col-md-8 col-xl-6 chat">
                    <div id="chat-right-content" class="card">
                        <div class="card-header msg_head">
                            <div class="d-flex bd-highlight">
                                <div class="img_cont">
                                    <img id="chatWithAvatar"  src="https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg" class="rounded-circle user_img">
                                    <span id="status-onl"></span>
                                </div>
                                <div class="user_info">
                                    <span>Chat with <span id="chatWith"></span></span>
                                    <p ><span id="numberOfMessage"></span> Messages</p>
                                </div>

                            </div>
                            <span id="action_menu_btn"><i class="fas fa-ellipsis-v"></i></span>
                            <div class="action_menu">
                                <ul>
                                    <li><i class="fas fa-user-circle"></i> View profile</li>

                                    <li onclick="removeConversation()"><i class="fas fa-ban"></i> Remove conversation</li>
                                </ul>
                            </div>
                        </div>

                        <div id="log" class="card-body msg_card_body">

                        </div>

                        <div id="send_msg">
                            <img id="insert_img" />
                        </div>
                        <div class="card-footer">

                            <div class="input-group">

                                <label style="margin-bottom: 0" for="upload" class="input-group-append" >

                                    <span class="input-group-text attach_btn" aria-hidden="true"><i class="fas fa-paperclip" >
                                            <input id="upload" type="file" style="display: none;" onchange="handleFileSelect(event)"  size=30>
                                        </i></span>
                                </label>

                                <input type="text" id="content" class="form-control type_msg" onkeypress="return enter(event)" placeholder="Type your message..."></textarea>
                                <div  id="sendMessage" class="input-group-append" onclick="send()" >
                                    <span  class="input-group-text send_btn" ><i class="fas fa-location-arrow"></i></span>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../../resource/components/ShowListFriend.jsp" %>%>
    </body>
    <script>
        $('#header').mouseenter(function () {
            $("#navigation").slideDown(250);

        });
        $("#header").mouseleave(function () {
            $("#navigation").slideUp(250);
        });

    </script>
</html>
