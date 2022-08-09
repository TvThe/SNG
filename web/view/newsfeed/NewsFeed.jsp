<%-- 
    Document   : NewsFeed
    Created on : 25-05-2022, 03:12:05
    Author     : TuanAnh
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SNG</title>
<!--        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>-->
        <script src="library/jquery.min.js" type="text/javascript"></script>
        <!--handle online or offline-->
        <script type="text/javascript" src="<c:url value="resource/js/websocket.js"></c:url>"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="resource/css/common.css"></c:url>">

            <script src="resource/js/Newsfeed/Interact_with_post.js" type="text/javascript"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
            <script>
                //            function likePost(id, userID) {
                //                console.log(id + ", " + userID);
                //            }
//                var socket;
//                console.log(document.location.pathname + document.location.search);
//                if ("WebSocket" in window) {
//                    console.log("Websocket supported");
//                    var host = document.location.host;
//                    var pathname = "/nhom-4-se1604ks-swp391-social-network-for-gamer";
//                    //                    console.log(host);
//                    //                    console.log(pathname);
//                    // connect to websocket
//                    socket = new WebSocket("ws://" + host + pathname + "/postSeverEndpoint/0/" + ${sessionScope.account.ID});
//                    console.log("connected");
//                    socket.onmessage = onMessage;
//
                    $(document).on("click", ".btn-card-like", function () {
                        var LikeAction = {
                            action: $(this).text(),
                            postID: $(this).data('post-id'),
                            userID: ${sessionScope.account.ID}
                        };
                        //                    console.log(JSON.stringify(LikeAction));
                        socket.send(JSON.stringify(LikeAction));
                    });
//
//                    function onMessage(event) {
//                        var jsondata = JSON.parse(event.data);
//                        //                    console.log(jsondata);
//                        if (jsondata.action === "Like") {
//                            $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) + 1);
//                            if (jsondata.userID === ${sessionScope.account.ID}) {
//                                $("#card_like_id_" + jsondata.postID).text("Liked");
//                            }
//                        }
//                        if (jsondata.action === "Liked") {
//                            $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) - 1);
//                            if (jsondata.userID === ${sessionScope.account.ID}) {
//                                $("#card_like_id_" + jsondata.postID).text("Like");
//                            }
//                        }
//                    }
//
//                } else {
//                    console.log("Websocket not support");
//                }
                $(document).ready(function () {

                    var a = 0;
                    //                var socket;

                    if ($(window).scrollTop() === 0) {
                        loadMorePost_newest();
                    }

                    //                window.onload = function () {
                    ////                loadMorePost_newest();
                    //
                    //                };

                    $(window).scroll(function () {
                        //                    console.log('window height: ' + $(window).height());
                        //                    console.log('window scroll: ' + $(window).scrollTop());
                        //                    console.log('document height: ' + $(document).height());

                        if ($(window).scrollTop() + $(window).height() + 100 >= $(document).height() && a == 0) {
                            loadMorePost_newest();
                        }
                    });

                    function loadMorePost_newest() {
                        a = 1;
                        $.ajax({
                            type: 'POST',
                            url: 'loadMorePost',
                            data: {
                                "type": "newest",
                                "all_id_post": $("#all_id_post").text()
                            },
                            dataType: "json",
                            success: function (jsondata) {
                                $.each(jsondata, function (key, item) {
                                    $('.post-list').html($('.post-list').html() + transform_data_into_post(item, ${sessionScope.account.ID}));
                                });
                                loadMorePost_friend();
                            }
                        });
                    }

                    function loadMorePost_friend() {
                        $.ajax({
                            type: 'POST',
                            url: 'loadMorePost',
                            data: {
                                "type": "friend",
                                "all_id_post": $("#all_id_post").text()
                            },
                            dataType: "json",
                            success: function (jsondata) {
                                $.each(jsondata, function (key, item) {
                                    $('.post-list').html($('.post-list').html() + transform_data_into_post(item, ${sessionScope.account.ID}));
                                });
                                loadMorePost_game();
                            }
                        });
                    }

                    function loadMorePost_game() {
                        $.ajax({
                            type: 'POST',
                            url: 'loadMorePost',
                            data: {
                                "type": "game",
                                "all_id_post": $("#all_id_post").text()
                            },
                            dataType: "json",
                            success: function (jsondata) {
                                $.each(jsondata, function (key, item) {
                                    $('.post-list').html($('.post-list').html() + transform_data_into_post(item,${sessionScope.account.ID}));
                                });
                                a = 0;
                            }
                        });
                    }

                    //                function loadMorePost_mostLike(){
                    //                    $.ajax({
                    //                        type: 'POST',
                    //                        url: 'viewMorePost_mostLike',
                    //                        data: {"all_id_post": $("#all_id_post").text()},
                    //                        dataType: "json",
                    //                        success: function (jsondata) {
                    //                            $.each(jsondata, function (key, item) {
                    //                                $('.post-list').html($('.post-list').html() + transform_data_into_post(item));
                    //                            });
                    //                            a = 0;
                    //                        }
                    //                    });
                    //                }


                    $(document).on('click', '.btn_open_post', function () {
                        //                    console.log($(this).data('post-id'));
                        //                    console.log($('#card_post_id_' + $(this).data('post-id')).attr('class'));

                    });

                    $('#header').mouseenter(function () {
                        $("#navigation").slideDown(250);

                    });
                    $("#header").mouseleave(function () {
                        $("#navigation").slideUp(250);
                    });

                    $(document).on('click', '.btn_edit_post', function () {
                        var post_id = $(this).data('post-id');
                        $('#edit_post_id').val(post_id);
                        $('#edit_content').val($('#card_content_id_' + post_id).text());
                        if ($('#card_image_id_' + post_id).attr('src').length === 0) {
                            //                        console.log($('#card_image_id_' + post_id).attr('src').length);
                            $('#edit_output').attr('src', '');
                            $('#edit_btn_remove_image').attr('hidden', 'hidden');
                        } else {
                            $('#edit_output').attr('src', $('#card_image_id_' + post_id).attr('src'));
                            $('#edit_btn_remove_image').removeAttr('hidden');
                        }
                        //                    console.log($('#card_image_id_' + $(this).data('post-id')).attr('src'));
                        var listOptionGame = "";
                        listOptionGame += "<select name=\"gID\" id=\"edit_gID\">";
            <c:forEach items="${listgame}" var="g">
                        if ($('#card_game_id_' + post_id).attr('data-game_id') == ${g.ID}) {
                            listOptionGame += "<option value=${g.ID} selected=\"selected\">${g.name}</option>";
                        } else {
                            listOptionGame += "<option value=${g.ID}>${g.name}</option>";
                        }
            </c:forEach>
                        listOptionGame += "</select>";

                        $('#edit_selectGame').html(listOptionGame);
                        $('#edit_submit_button').attr('data-edit_post_id', post_id);
                        //                    console.log($('#edit_submit_button').data('edit_post_id'));
                        $("#edit_modal").modal('toggle');
                    });

                    $(document).on('click', '#edit_btn_remove_image', function () {
                        $('#edit_output').attr('src', '');
                        $('#edit_btn_remove_image').attr('hidden', 'hidden');
                    });

                    $(document).on('click', '#confirm_edit', function () {
                        post_id = $('#edit_post_id').val();
                        //                    console.log(post_id);
                        //                    console.log($('#edit_content').val());
                        //                    console.log('edit_id:   '+$('#edit_gID').val());
                        //                    console.log($('#edit_output').attr('src'));
                        //                    console.log('dcm  ' + $('#card_game_id_' + post_id + ' > img').attr('src'));


                        $('#card_content_id_' + post_id).text($('#edit_content').val());
                        $('#card_image_id_' + post_id).attr('src', $('#edit_output').attr('src'));
                        $('#card_game_id_' + post_id).attr('data-game_id', $('#edit_gID').val());
                        $('#card_game_id_' + post_id + ' > img').attr('src');
            <c:forEach items="${listgame}" var="g">
                        //                    console.log('game id: ' +${g.ID});
                        //                    console.log('edit id: ' + $('#edit_gID').val());
                        if ($('#edit_gID').val() == ${g.ID}) {
                            //                        console.log('game ne:   ' + '${g.image}');
                            $('#card_game_id_' + post_id + ' > img').attr('src', 'data:image/jpeg;base64,' + '${g.image}');
                            //                        console.log($('#card_game_id_' + post_id + ' > img').attr('src'));
                        }
            </c:forEach>

                        $.ajax({
                            url: 'editPost',
                            method: 'POST',
                            //                        data: $('#createPostForm').serialize(),
                            data: {
                                "post_id": $('#edit_post_id').val(),
                                "text": $('#edit_content').val(),
                                "gID": $('#edit_gID').val(),
                                "photo": $('#edit_output').attr('src')
                            },
                            //                        dataType: 'json',
                            success: function () {
                                $('#card_content_id_' + post_id).text($('#edit_content').val());
                                if ($('#edit_output').attr('src').length > 0) {
                                    $('#card_game_id_' + post_id).attr('data-game_id', $('#edit_gID').val());
                                    $('#card_image_id_' + post_id).attr('src', $('#edit_output').attr('src'));
                                    $('#card_image_id_' + post_id).attr('style', 'height: 130px; width: 130px;');
                                    $('#card_image_id_' + post_id).removeAttr('hidden');
                                } else {
                                    $('#card_image_id_' + post_id).attr('style', 'height: 130px; width: 130px; display: none;');
                                    $('#card_image_id_' + post_id).attr('hidden');
                                }
                                //                            $('#card_image_id_' + post_id).html("<img src=\"" + $('#edit_output').attr('src') + "\" style=\"height: 130px; width: 130px;\" id=\"card_image_id_" + post_id + "\">\n");
                            }
                        });
                    });

                    $(document).on('click', '.btn_open_post', function () {
                        window.location.href = "post?id=" + $(this).data('post-id');
                    });

                    //  like post
                    //                $(document).on("click", ".btn-card-like", function () {
                    //                    var LikeAction = {
                    //                        action: $(this).text(),
                    //                        postID: $(this).data('post-id'),
                    //                        userID: ${sessionScope.account.ID}
                    //                    };
                    //                    console.log(JSON.stringify(LikeAction));
                    //                    socket.send(JSON.stringify(LikeAction));
                    //                });
                    //
                    //                function onMessage(event) {
                    //                    var device = JSON.parse(event.data);
                    //                    console.log(device);
                    //                    if (device.action === "Like") {
                    //                        $("#card_like_amount_id_" + device.postID).text(parseInt($("#card_like_amount_id_" + device.postID).text()) + 1);
                    //                        if (device.userID === ${sessionScope.account.ID}) {
                    //                            $("#card_like_id_" + device.postID).text("Liked");
                    //                        }
                    //                    }
                    //                    if (device.action === "Liked") {
                    //                        $("#card_like_amount_id_" + device.postID).text(parseInt($("#card_like_amount_id_" + device.postID).text()) - 1);
                    //                        if (device.userID === ${sessionScope.account.ID}) {
                    //                            $("#card_like_id_" + device.postID).text("Like");
                    //                        }
                    //                    }
                    //                }


                    // phan code cua viet

                    var listOptionGame = "";
                    listOptionGame += "<select name=\"gID\" id=\"gID\">";
            <c:forEach items="${listgame}" var="g">
                    listOptionGame += "<option value=${g.ID}>${g.name}</option>";
            </c:forEach>
                    listOptionGame += "</select>";

                    $('#selectGame').html(listOptionGame);
                    $(document).on('click', '#creatPostbutton', function () {
                        var d = $(".post-list").html();


                        if (document.getElementById("uploadImage").files.length != 0) {
                            var file = document.getElementById("uploadImage").files[0];
                            var dataFile = "";
                            if (file) {
                                var reader = new FileReader();

                                reader.onload = function (e) {
                                    dataFile += e.target.result;
                                    $.ajax({
                                        url: 'createPost',
                                        method: 'POST',
                                        //                        data: $('#createPostForm').serialize(),
                                        data: {
                                            "text": $('#note').val(),
                                            "gID": $('#gID').val(),
                                            "photo": dataFile
                                        },
                                        dataType: 'json',
                                        success: function (dataJson) {
                                            var html = transform_data_into_post(dataJson, ${sessionScope.account.ID});
                                            var newstring = html + d;
                                            $(".post-list").html(newstring);
                                            $("#myModal").modal('toggle');
                                            //                                    $(".modal-backdrop").remove();
                                            console.log(dataJson);

                                        }
                                    });
                                };

                                reader.readAsDataURL(file);
                            }

                        } else {
                            $.ajax({
                                url: 'createPost',
                                method: 'POST',
                                //                        data: $('#createPostForm').serialize(),
                                data: {
                                    "text": $('#note').val(),
                                    "gID": $('#gID').val()
                                },
                                dataType: 'json',
                                success: function (dataJson) {
                                    var html = transform_data_into_post(dataJson, ${sessionScope.account.ID});
                                    var newstring = html + d;
                                    $(".post-list").html(newstring);
                                    $("#myModal").modal('toggle');
                                    //                                    $(".modal-backdrop").remove();
                                    console.log(dataJson);

                                }
                            });
                        }

                    });
                    $(document).on('click', '.btn_remove_post', function () {

                        var postID = $(this).data('post-id');

                        if ($(document).on('click', '#confirmDelete', function () {
                            console.log("ajax");
                            $.ajax({
                                url: 'deletePost',
                                method: 'GET',
                                data: {"pID": postID},
                                success: function () {
                                    $("#card_post_id_" + postID).remove();

                                }
                            });
                        }))
                            ;

                    });


                });

        </script>
    </head>
    <body style="background-color: #212A37;">
        <jsp:include page="../../resource/components/category.jsp" />
        <div class="container" style="margin-top: 70px;">

            <!--            button create-->
            <jsp:include page='../../resource/components/PopupForCreate.jsp'/>
            <div>
            </div>
            <div id="test" ></div>


            <div class="post-list"></div>

            <div class="post-loading">
                <div class="card" aria-hidden="true" style="background-color: #111720; margin: 25px 0px 25px 0px;">
                    <div class="card-body placeholder-glow">
                        <div class="row">
                            <div class="col-1">
                                <div class="placeholder bg-light" style="width: 70px; height: 70px;"></div>
                            </div>
                            <div class="col-11">
                                <h5 class="card-title placeholder bg-light">PSG.LGD Ame</h5>
                                <p class="card-text"><small class="placeholder bg-light">Last updated 3 mins ago</small></p>
                            </div>
                        </div>
                        <div class="card-body row">
                            <div class="col-3 ">
                                <div class="img-fluid rounded-start placeholder bg-light"  style="width: 150px; height: 150px;"></div>
                            </div>
                            <div class="col-7">
                                <p class="card-text placeholder-glow ">
                                    <span class="placeholder col-7 bg-light"></span>
                                    <span class="placeholder col-4 bg-light"></span>
                                    <span class="placeholder col-4 bg-light"></span>
                                    <span class="placeholder col-6 bg-light"></span>
                                    <span class="placeholder col-8 bg-light"></span>
                                </p>
                                <a href="#" class="btn btn-card-like disabled placeholder" style="width: 55px;"></a>
                                <a href="#" class="btn btn-card-like disabled placeholder" style="width: 120px;"></a>
                            </div>
                            <div class="col-2">
                                <div class="row">
                                    <a href="#" tabindex="-1" class="btn btn-card disabled placeholder"></a>
                                </div>
                                <div class="row">
                                    <a href="#" tabindex="-1" class="btn btn-card disabled placeholder"></a>
                                </div>
                                <div class="row">
                                    <a href="#" tabindex="-1" class="btn btn-card disabled placeholder"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="all_id_post" style="display: none;">0</div>
        <jsp:include page='../../resource/components/popupForEditPost.jsp'/>
        <%@include file="../../resource/components/ShowListFriend.jsp" %>

    </body>
    <style>
        .card {
            background-color: #111720; 
            color: #FFFFFF;
            margin: 25px 0px 25px 0px;
        }
        .avatar{
            width: 70px;
            height: 70px;
            border-radius: 50%;
        }
        .btn-card{
            background-color: #3AAA23;
            color: #FFFFFF;
            margin: 5px 0px 5px 0px;
        }
        .btn-card-like {
            background-color: #3AAA23;
            color: #FFFFFF;
        }
        .post-content{
            height: 100px;
        }
        .img-game{
            height: 150px;
            width: 150px;
        }
    </style>
    <script>
        var loadFile = function (event) {

            var image = document.getElementById('output');

            image.src = URL.createObjectURL(event.target.files[0]);

        };
        var editLoadFile = function (event) {
            var image = document.getElementById('edit_output');
            var file = document.getElementById("editImage").files[0];
            var dataFile = "";
            if (file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    dataFile += e.target.result;
//                    console.log(dataFile);
                    $('#edit_output').attr('src', dataFile);
                    $('#edit_btn_remove_image').removeAttr('hidden');
                };
                reader.readAsDataURL(file);
            }
        };
    </script>
</html>
