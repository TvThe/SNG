<%-- 
    Document   : Post
    Created on : 04-06-2022, 23:30:43
    Author     : TuanAnh
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<!--        <link href="library/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="library/bootstrap.min.js" type="text/javascript"></script>-->
        <script src="library/jquery.min.js" type="text/javascript"></script>
        <script src="library/bs4pop.js" type="text/javascript"></script>
        <link href="library/bs4pop.css" rel="stylesheet" type="text/css"/>
        <script src="resource/js/Newsfeed/Interact_with_post.js" type="text/javascript"></script>
        <link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
        <script>
//            if ("WebSocket" in window) {
////                console.log("Websocket supported");
//                var host = document.location.host;
//                var pathname = "/nhom-4-se1604ks-swp391-social-network-for-gamer";
//                // connect to websocket
//                socket = new WebSocket("ws://" + host + pathname + "/postSeverEndpoint/" + new URL(document.location.href).searchParams.get("id") + "/" + ${sessionScope.account.ID});
//                console.log("connected");
//                socket.onmessage = onMessage;
//
//                function onMessage(event) {
//                    var jsondata = JSON.parse(event.data);
//                    console.log("nhan ve: " + jsondata);
//                    console.log(jsondata);
//                    if (jsondata.action === "Like") {
//                        $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) + 1);
//                        if (jsondata.userID === ${sessionScope.account.ID}) {
//                            $("#card_like_id_" + jsondata.postID).text("Liked");
//                        }
//                        if (jsondata.userID !== ${sessionScope.account.ID}) {
//                            bs4pop.notice('<div onClick="window.location.href = \'post?id=' + jsondata.postID + '\'" style=\"cursor: pointer;\">' + jsondata.displayname + ' just liked the post you\'re following</div>', {type: 'secondary', position: 'bottomleft', autoClose: 5000});
//                            $("#numberOfNewNotification").css("background-color", "red");
//                        }
//                    }
//                    if (jsondata.action === "Liked") {
//                        $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) - 1);
//                        if (jsondata.userID === ${sessionScope.account.ID}) {
//                            $("#card_like_id_" + jsondata.postID).text("Like");
//                        }
//                    }
//                    if (jsondata.action === "Comment") {
//                        $('#comment-list').html(transform_data_into_comment(jsondata.content) + $('#comment-list').html());
//                        bs4pop.notice('<div onClick="window.location.href = \'post?id=' + jsondata.postID + '\'" style=\"cursor: pointer;\">' + jsondata.displayname + ' just comment the post you\'re following</div>', {type: 'secondary', position: 'bottomleft', autoClose: 5000});
//                    }
//                    if (jsondata.action === "EditComment") {
////                        $('#comment_content_' + jsondata.cmtID).html(jsondata.text);
//                        $('#comment_content_' + jsondata.cmtID).hide("slow", function () {
//                            $(this).html(jsondata.text);
//                        }).show(3000);
//                        console.log(jsondata.cmtID);
//                    }
//                    if (jsondata.action === "RemoveComment") {
//                        $('.comment_id_' + jsondata.cmtID).hide("slow", function () {
//                            $(this).remove();
//                        });
//                    }
//                }
//
//            } else {
//                console.log("Websocket not support");
//            }

            $(document).ready(function () {
                $.each(${commentList}, function (key, item) {
                    $('#comment-list').append(transform_data_into_comment(item));
                });

                $('#header').mouseenter(function () {
                    $("#navigation").slideDown(250);

                });
                $("#header").mouseleave(function () {
                    $("#navigation").slideUp(250);
                });

                $("#post").html(transform_data_into_post(${post}, ${sessionScope.account.ID}));

                $(document).on('click', '#togglePostContent', function () {
                    $('#post_and_cmt_field').slideToggle();
                });

                $(document).on("click", ".btn-card-like", function () {
                    console.log("a");
                    var LikeAction = {
                        action: $(this).text(),
                        postID: $(this).data('post-id'),
                        userID: ${sessionScope.account.ID}
                    };
                    console.log(JSON.stringify(LikeAction));
                    socket.send(JSON.stringify(LikeAction));
                });

                $(document).on('click', '.btn-comment', function () {
                    var CommentAction = {
                        action: $(this).text(),
                        postID: $('.btn-card-like').data('post-id'),
                        userID: ${sessionScope.account.ID},
                        text: $('.input-comment').val()
                    };
//                    console.log(JSON.stringify(CommentAction));
                    socket.send(JSON.stringify(CommentAction));
                    $('.input-comment').val('');
                });

                $(document).on({
                    mouseenter: function () {
                        $('#comment_interact_' + $(this).attr('id')).removeAttr('hidden');
//                        console.log($(this).attr('id'));
                    },
                    mouseleave: function () {
                        $('#comment_interact_' + $(this).attr('id')).attr('hidden', 'hidden');
//                        console.log($(this).attr('id'));
                    }
                }, ".comment");

                $(document).on('click', '.btn_comment_edit', function () {
                    var cmt_id = $(this).attr('id');
                    open_overlay(cmt_id);
                    $('.comment_id_' + cmt_id).css('transform', 'scale(1.01)');
                    $('.comment_id_' + cmt_id).css('border-radius', '10px');
                    $('.comment_id_' + cmt_id).css('background-color', 'black');

                    var comment_content = $('#comment_content_' + cmt_id).html().trim();
                    var comment_interact = $('#comment_interact_' + cmt_id).html();
                    $('.overlay_comment_content').text($('#comment_content_' + cmt_id).html().trim());
                    $('.overlay_comment_interact').text($('#comment_interact_' + cmt_id).html());

                    $('#comment_content_' + cmt_id).html("<input class=\"input_edit_comment\" type=\"text\" value=\"" + $('#comment_content_' + cmt_id).text().trim() + "\" style=\"width: 100%;\"/>");
                    var html = '';
                    html += '<div class="row">\n' +
                            '   <button class="btn btn_comment_interact btn_comment_edit_confirm_' + cmt_id + '">Confirm</button>\n' +
                            '</div>\n' +
                            '<div class="row">\n' +
                            '   <button class="row btn btn_comment_interact btn_comment_edit_close">Close</button>\n' +
                            '</div>\n';
                    $('#comment_interact_' + cmt_id).html(html);


                    $(document).on('click', '.btn_comment_edit_confirm_' + cmt_id, function () {
                        var EditCommentAction = {
                            action: 'EditComment',
                            postID: ${post}['ID'],
                            commentID: cmt_id,
                            text: $('.input_edit_comment').val()
                        };
                        console.log("Send:" + EditCommentAction);
                        console.log(EditCommentAction);
                        socket.send(JSON.stringify(EditCommentAction));
//                        $('.overlay_comment_content').text($('.input_edit_comment').val().trim());
                        off();
                    });

                    $(document).on('click', '.btn_comment_edit_close', function () {
                        off();
                    });
                });

            });

            $(document).on('click', '.btn_comment_Remove', function () {
                var cmt_id = $(this).attr('id');
                open_overlay(cmt_id);
                $('.comment_id_' + cmt_id).css('transform', 'scale(1.01)');
                $('.comment_id_' + cmt_id).css('border-radius', '10px');
                $('.comment_id_' + cmt_id).css('background-color', 'black');

                $('.overlay_comment_content').text($('#comment_content_' + cmt_id).html().trim());
                $('.overlay_comment_interact').text($('#comment_interact_' + cmt_id).html());

                var html = '';
                html += '<div class="row">\n' +
                        '   <button class="btn btn_comment_interact btn_comment_remove_confirm">Confirm</button>\n' +
                        '</div>\n' +
                        '<div class="row">\n' +
                        '   <button class="row btn btn_comment_interact btn_comment_remove_close">Close</button>\n' +
                        '</div>\n';
                $('#comment_interact_' + cmt_id).html(html);

                $(document).on('click', '.btn_comment_remove_confirm', function () {
                    var RemoveCommentAction = {
                        action: 'RemoveComment',
                        postID: ${post}['ID'],
                        commentID: cmt_id
                    };
//                    console.log(RemoveCommentAction);
                    socket.send(JSON.stringify(RemoveCommentAction));
                    off();
                });

                $(document).on('click', '.btn_comment_remove_close', function () {
                    off();
                });
            });
//            $('.input-comment').addEventListener("keypress", function (event) {
//                if (event.key === 'Enter') {
//                    event.preventDefault();
//                    $('.btn-comment').click();
//                }
//            });

            function open_overlay(cmt_id) {
                $('.overlay_around_cmt').text(cmt_id);
                $('body').css('overflow-y', 'hidden');
                $('.comment_id_' + cmt_id).css('position', 'relative');
                $('.comment_id_' + cmt_id).css('z-index', 3000);
                document.getElementById("overlay").style.display = "block";
            }

            function off() {
                document.getElementById("overlay").style.display = "none";
                $('body').css('overflow-y', '');
                var cmt_id = $('.overlay_around_cmt').text();
                $('.comment_id_' + cmt_id).css('position', '');
                $('.comment_id_' + cmt_id).css('z-index', '');
                $('.comment_id_' + cmt_id).css('transform', '');
                $('.comment_id_' + cmt_id).css('border-radius', '');
                $('.comment_id_' + cmt_id).css('background-color', '');
                $('#comment_content_' + cmt_id).html($('.overlay_comment_content').text());
                $('#comment_interact_' + cmt_id).html($('.overlay_comment_interact').text());
            }

            function end_edit() {

            }

            function transform_data_into_comment(dataJson) {
//                console.log(dataJson);
                var html = '';
                html += "                    <div class=\"comment row comment_id_" + dataJson['id'] + "\" id=\"" + dataJson['id'] + "\">\n" +
                        "                        <div class=\"comment_infor col-lg-3 col-4 row\">\n" +
                        "                            <div class=\"comment_infor_user_avatar col-lg-4\">\n" +
                        "                                <img src=\"data:image/jpeg;base64," + dataJson['user']['avatar'] + "\" class=\"rounded-circle\" style=\"height: 75px; width: 75px;\">\n" +
                        "                            </div>\n" +
                        "                            <div class=\"col-lg-8\">\n" +
                        "                                <div class=\"comment_infor_user_displayname row\">" + dataJson['user']['displayname'] + "</div>\n" +
                        "                                <div class=\"comment_infor_time row\"><small>" + dataJson['time'] + "</small></div>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
//                        "                        <div class=\"comment_content col-lg-9 col-8\">" + dataJson['content'] + " <button class=\"btn_more_infor \" style=\"float: right; margin-top: 20px; background-color: black; color: white;\" hidden>i</button></div>\n" +
//                        "                        <div class=\"comment_content col-lg-9 col-8\">" + dataJson['content'] + " <button class=\"btn_more_infor dropdown-toggle dropdown-toggle-split\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\" style=\"float: right; margin-top: 20px; background-color: black; color: white;\">i</button></div>\n" +
//                        "                        <div class=\"comment_content col-lg-9 col-8\"></div>\n" +
                        "                        <div class=\"comment_content col-lg-9 col-8 row\">\n" +
                        "                            <div class=\"col-10\" id=\"comment_content_" + dataJson['id'] + "\">\n" +
                        "                                " + dataJson['content'] + "\n" +
                        "                            </div>\n";
                if (dataJson['user']['ID'] == ${sessionScope.account.ID}) {
                    html += "                            <div class=\"row col-2 comment_interact\" style=\"float: right; width=71px;\" hidden id=\"comment_interact_" + dataJson['id'] + "\">\n" +
                            "                                <div class=\"row\">\n" +
                            "                                    <button class=\"btn btn_comment_interact btn_comment_edit\" id=\"" + dataJson['id'] + "\">Edit</button>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"row\">\n" +
                            "                                    <button class=\"row btn btn_comment_interact btn_comment_Remove\" id=\"" + dataJson['id'] + "\">Remove</button>\n" +
                            "                                </div>\n" +
                            "                            </div>\n";
                }
                html += "                        </div>\n" +
                        "                    </div>";
                return html;
            }

        </script>
    </head>
    <body style="background-color: #212A37;">
        <jsp:include page='../../resource/components/category.jsp'/>
        <div class="container" style="margin-top: 100px;">
            <div class="card">
                <div style="position: sticky; top: 70px;">
                    <div>
                        <button id="togglePostContent" class="btn" style="width: 100%; background-color: #111720; color: white;">Show/Hide</button>
                    </div>
                    <div id="post_and_cmt_field" style="background-color: #111720;">
                        <div id="post"></div>
                        <div class="comment_field row" >
                            <div class="col-lg-1 col-md-2" style="">
                                <img src="data:image/jpeg;base64,${sessionScope.account.avatar}" class="rounded-circle" style="height: 60px; width: 60px; display: block; margin: auto auto auto auto;">
                            </div>
                            <div class="col-lg-11 col-md-10 row">
                                <div class="col-10" style="margin: auto auto auto auto;">
                                    <input class="form-control me-2 input-comment" type="search" placeholder="Comment" aria-label="Comment" >
                                </div>
                                <button class="btn btn-comment col-2" style="margin: auto auto auto auto;">Comment</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="comment-list">
                    <div class="row" id="">
                        <div class="comment_infor col-4 row">
                            <div class="comment_infor_user_avatar col-5">

                            </div>
                            <div class="col-7">
                                <div class="comment_infor_user_displayname row"></div>
                                <div class="comment_infor_time row"></div>
                            </div>
                        </div>
                        <div class="comment_content col-8"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="overlay" onclick="off()">
            <div class="overlay_around_cmt" hidden></div>
            <div class="overlay_comment_content" hidden></div>
            <div class="overlay_comment_interact" hidden></div>
        </div>
    </body>
    <style>
        .card {
            background-color: #111720; 
            color: #FFFFFF;
            margin: 0px 0px 5px 0px;
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
        .btn-card-like, .btn-comment {
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
        #post, #post_and_cmt_field{
            border-bottom: solid gray 1px;
            border-top: solid gray 1px;
            margin-bottom: 5px;
        }
        .comment{
            margin: 15px 0px 15px 0px;
            padding: 5px 0px 5px 0px;
        }
        .comment:hover{
            transform: scale(1.01);
            border-radius: 10px;
            background-color: black;
        }
        .btn_comment_interact{
            /*            width: 70px;*/
            background-color: #3AAA23;
            color: #FFFFFF;
            padding: 3px 6px 3px 6px;
            margin: 2px auto 2px auto;
            display: inline-block;
            width: 71px;
        }
        #overlay {
            position: absolute;
            display: none;
            width: 100%;
            height: 100000px;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(255,255,255,0.1);
            z-index: 10;
            cursor: pointer;
        }
    </style>
</html>
