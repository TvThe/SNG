var socket;
//var host = document.location.host;
//var pathname = document.location.pathname;
// connect to websocket
//socket = new WebSocket("ws://" + host + pathname + "/like");
//socket.onmessage = onMessage;


//function likePost(id, userID) {
////    console.log(id + ", " + userID);
//    var LikeAction = {
//        PostID: id,
//        UserID: userID
//    };
////    socket.send(JSON.stringify(LikeAction));
//}

//if ("WebSocket" in window) {
//    console.log("ws://" + host + pathname + "/like");
//} else {
//    console.log("Websockets not supported");
//}

window.onload = function () {
    console.log('aaa');
    if ("WebSocket" in window) {

        var host = document.location.host;
        var pathname = document.location.pathname;
        // connect to websocket
        socket = new WebSocket("ws://" + host + pathname + "/like");
        console.log("ws://" + host + pathname + "/like");
        // recieve message
//        ws.onmessage = function (event) {
//            var message = JSON.parse(event.data);
//            newMessage = message
//            if (message.content != "Open" && message.content != "Close" && document.getElementById("recent" + message.senderID) == null) {
//                setNewRecieveForReciever(message.senderID)
//
//            }
//            if (message.content != "Open" && message.content != "Close" && document.getElementById("recent" + message.senderID) != null) {
//                document.getElementById("recent" + message.senderID).classList.add("newMessage");
//                document.getElementById("lastMessage" + message.senderID).innerHTML = message.content
//                document.getElementById("numberOfMessage").innerHTML = parseInt(document.getElementById("numberOfMessage").innerHTML) + 1
//
//
//            }
//
//            setMessage(message)
//
//        };

    } else {
        console.log("Websockets not supported");
    }

};