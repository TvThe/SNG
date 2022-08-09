//    $(document).on('click', '#dropdownNotification', function () {
function loadNotification(userID) {
    $.ajax({
        url: 'viewNotification',
        method: 'POST',
        data: {
            "userID": userID
        },
        dataType: 'json',
        success: function (dataJson) {
            let html = '';
            $.each(dataJson, function (key, item) {
                let messeage = (item.post.user.ID === userID) ? 'your post' : 'the post you\'re following';
                let style = (item.seen === false) ? 'style="width: 350px; background: #d7d7d7;"' : 'style="width: 350px;"'
                html += '<li ' + style + '><div class="dropdown-item" onClick="window.location.href = \'post?id=' + item.post.ID + '\'" style=\"cursor: pointer;\">' + item.userS.displayname + ' just liked ' + messeage + '</div></li>';
                console.log(item);
            });
            console.log(html);
            $('#dropdownNotificationBoard').html(html);
            updateSeenNotification(userID);
        }
    });
}

function updateSeenNotification(userID) {
    $.ajax({
        url: 'notificationStatus',
        method: 'GET',
        data: {
            "userID": userID
        },
        dataType: 'json'
    });
}

function checkIfHaveNewNotification(userID) {
    $.ajax({
        url: 'notificationStatus',
        method: 'POST',
        data: {
            "userID": userID
        },
        dataType: 'json',
        success: function (dataJson) {
            console.log(dataJson);
            if(dataJson===true){
                $("#numberOfNewNotification").css("background-color", "red");
            }
        }
    });
}