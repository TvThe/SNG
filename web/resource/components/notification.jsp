<script>
    if ("WebSocket" in window) {
        console.log("Websocket supported");
        var host = document.location.host;
        var pathname = "/nhom-4-se1604ks-swp391-social-network-for-gamer";
        // connect to websocket
        var url_postID = new URL(document.location.href).searchParams.get("id");
        if (url_postID === null) {
            url_postID = 0;
        }
        socket = new WebSocket("ws://" + host + pathname + "/postSeverEndpoint/" + url_postID + "/" + ${sessionScope.account.ID});
        console.log("connected to: " + "ws://" + host + pathname + "/postSeverEndpoint/" + url_postID + "/" + ${sessionScope.account.ID});
        socket.onmessage = onMessage;

        function onMessage(event) {
            var jsondata = JSON.parse(event.data);
            console.log("nhan ve: " + jsondata);
            console.log(jsondata);
            if (jsondata.action === "Like") {
                $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) + 1);
                if (jsondata.userID === ${sessionScope.account.ID}) {
                    $("#card_like_id_" + jsondata.postID).text("Liked");
                }
                if (jsondata.userID !== ${sessionScope.account.ID}) {
                    let messeage = (jsondata.posterID === ${sessionScope.account.ID}) ? 'your post' : 'the post you\'re following';
                    bs4pop.notice('<div onClick="window.location.href = \'post?id=' + jsondata.postID + '\'" style=\"cursor: pointer;\">' + jsondata.displayname + ' just liked ' + messeage + '</div>', {type: 'secondary', position: 'bottomleft', autoClose: 5000});
                    $("#numberOfNewNotification").css("background-color", "red");
                }
            }
            if (jsondata.action === "Liked") {
                $("#card_like_amount_id_" + jsondata.postID).text(parseInt($("#card_like_amount_id_" + jsondata.postID).text()) - 1);
                if (jsondata.userID === ${sessionScope.account.ID}) {
                    $("#card_like_id_" + jsondata.postID).text("Like");
                }
            }
            if (jsondata.action === "Comment") {
                $('#comment-list').html(transform_data_into_comment(jsondata.content) + $('#comment-list').html());
                bs4pop.notice('<div onClick="window.location.href = \'post?id=' + jsondata.postID + '\'" style=\"cursor: pointer;\">' + jsondata.displayname + ' just comment the post you\'re following</div>', {type: 'secondary', position: 'bottomleft', autoClose: 5000});
            }
            if (jsondata.action === "EditComment") {
//                        $('#comment_content_' + jsondata.cmtID).html(jsondata.text);
                $('#comment_content_' + jsondata.cmtID).hide("slow", function () {
                    $(this).html(jsondata.text);
                }).show(3000);
                console.log(jsondata.cmtID);
            }
            if (jsondata.action === "RemoveComment") {
                $('.comment_id_' + jsondata.cmtID).hide("slow", function () {
                    $(this).remove();
                });
            }
        }

    } else {
        console.log("Websocket not support");
    }
    $(document).ready(function () {
        checkIfHaveNewNotification(${sessionScope.account.ID});
        $(document).on('click', '#dropdownNotification', function () {
            loadNotification(${sessionScope.account.ID});
            $("#numberOfNewNotification").css("background-color", "#5865F2");
        });
    });
</script>