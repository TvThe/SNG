var dataS = null;
var isCheckVaildateNameInput = true;
var imgInput = null
var alertHtml = null

function selectManage(type) {
    var contentEle = document.getElementById("content");

    if (type == 2) {
        contentEle.innerHTML = '<div id="msgRespond"></div><button type="button" class="btn btn-primary" data-bs-toggle = "modal" data-bs-target = "#myModal" onclick = "createGame()">Create type of game </button>\n\
<table class="table table-bordered table-list-game mt-3" style="color:white">\n\
<thead>\n\
<tr><th style="width: 25%">Name of game</th><th style="width: 25%;">Image</th> <th style="width: 25%">Action</th></tr>\n\
</thead> <tbody id="container"></tbody></table><div id="pagination"></div>'
        loadGames()
    } else {
    }
}
function loadGames() {
    var url = "http://" + window.location.host + "/nhom-4-se1604ks-swp391-social-network-for-gamer/get-all-games"
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (dataJ) {
            dataS = dataJ
            hanldePagination()
        },
        error: function () {

        }
    });
}
function hanldePagination() {
    let container = $('#pagination');
    container.pagination({
        dataSource: dataS,
        pageSize: 3,
        callback: function (data, pagination) {
            var dataHtml = ''
            $.each(data, function (index, g) {
                var img = g.image.includes("data:image/") == true ? g.image : "data:image/jpeg;base64," + g.image
                var pa = "'" + g.ID + "'" + ",'" + g.name + "'," + "'" + img + "'"
                dataHtml += '<tr >\n\
                            <td >' + g.name + '</td> \n\
                            <td><img style="width:150px;height:80px;object-fit: contain;" src="' + img + '"/></td>\n\
                             <td><button type="button" class="btn btn-warning"onclick="editGame(' + pa + ')" data-bs-toggle="modal" data-bs-target="#myModal">Edit</button>\n\
                                <button type="button" class="btn btn-danger" onclick="confirmDelete(' + g.ID + ')" data-bs-toggle="modal" data-bs-target="#myModal">Delete</button></td>\n\
                             </tr>'
            });


            $("#container").html(dataHtml);
        }
    })


}
function submitFrom(e) {

    e.preventDefault();
    var nameOfGame = document.getElementById("inputGame").value
    imgInput = document.getElementById("insert_img").src
    var msgEle = document.getElementById("msgRespond")
    if (nameOfGame == "" && !imgInput.includes("data:image/")) {
        document.getElementById("invalid-feedback-image").innerHTML = "Image is not empty"
        document.getElementById("invalid-feedback").innerHTML = "Name is not empty"
        return
    }
    if (!imgInput.includes("data:image/")) {
        document.getElementById("invalid-feedback-image").innerHTML = "Image is not empty"
        return
    }
    if (!isCheckVaildateNameInput)
        return
    if (nameOfGame != "") {
        var url = "http://" + window.location.host + "/nhom-4-se1604ks-swp391-social-network-for-gamer/create-game"
        var dataInp = {
            "name": nameOfGame,
            "image": imgInput
        }
        if (document.getElementById("idGame") != null) {
            url = "http://" + window.location.host + "/nhom-4-se1604ks-swp391-social-network-for-gamer/update-game"
            dataInp = {
                "id": document.getElementById("idGame").value,
                "name": nameOfGame,
                "image": imgInput
            }
        }
        $.ajax({
            url: url,
            method: 'POST',
            data: dataInp,
            dataType: 'json',
            success: function (data) {
                var game = {
                    "ID": data.id,
                    "name": nameOfGame,
                    "image": imgInput}
                var isCheck = isCheckExitEle(data.id)

                if (!isCheck) {
                    alertHtml = '<div class="alert alert-success alert-dismissible">\n\
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>\n\
                            <strong>Create success!</strong></div>'
                    dataS.unshift(game)
                } else {
                    dataS = dataS.map(obj => {
                        if (obj.ID == data.id) {
                            return game;
                        }
                        return obj;
                    });
                    alertHtml = '<div class="alert alert-success alert-dismissible">\n\
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>\n\
                            <strong>Update success!</strong></div>'
                }
                msgEle.innerHTML = alertHtml
                hanldePagination()
                document.getElementById("closeBtn").click()
            },
            error: function () {
                alertHtml = '<div class="alert alert-danger alert-dismissible">\n\
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>\n\
                            <strong>Failure!</strong></div>'
                msgEle.innerHTML = alertHtml
                document.getElementById("closeBtn").click()
            }
        });
    } else {
        document.getElementById("invalid-feedback").innerHTML = "Name is not empty"
    }
}
function isCheckExitEle(id) {
    return dataS.some(element => {
        if (element.ID == id) {
            return true;
        }

        return false;
    });
}
function handleFile(e) {
    var file = document.getElementById("inputImage").files[0];

    if (file) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#insert_img').attr('src', e.target.result)
            imgInput = e.target.result
        };

        reader.readAsDataURL(file);
    }
}
function handleInputName(value) {
    var fdEle = document.getElementById("invalid-feedback")
    if (value == "")
        fdEle.innerHTML = "Name is not empty"
    else {
        var flag = true
        isCheckVaildateNameInput = true
        dataS.forEach(g => {
            if (g.name.toLowerCase() == value.toLowerCase()) {
                fdEle.innerHTML = "Game exists"
                flag = false
                isCheckVaildateNameInput = false
            }
        })
        if (flag) {
            fdEle.innerHTML = ""
        }
    }

}
function createGame() {
    var eleTitle = document.getElementById("titlePopup");
    var formContent = document.getElementById("formContent")

    eleTitle.innerHTML = "Create type of game"
    formContent.innerHTML = '<form onsubmit="submitFrom(event)">\n\
        <div class="form-group">\n\
        <label for="inputGame">Name of game</label>\n\
        <input type="text" class="form-control" oninput="handleInputName(this.value)" id="inputGame" placeholder="typing something...">\n\
        <div id="invalid-feedback"></div>\n\
        </div>\n\
        <div class="form-group"> \n\
        <label for="inputImage" class="form-label">Choose image</label>\n\
         <input class="form-control" type="file" id="inputImage" onchange="handleFile(event)"> <img style="width: 90%;" id="insert_img" src="" />\n\
         <div id="invalid-feedback-image"></div>\n\
    </div>\n\
        <div class="form-group mt-3">\n\
        <button type="submit"  class="btn btn-primary">Create</button>\n\
        </div> </form>'

}
function confirmDelete(id) {
    var eleTitle = document.getElementById("titlePopup");
    var formContent = document.getElementById("formContent")
    var modalFooter = document.getElementById("modalFooter")
    eleTitle.innerHTML = "Confirm delete type of game"
    formContent.innerHTML = '<div>Are you sure when delete this type of game \n\
                             and then everything that relate to this will delete as well</div>'
    modalFooter.innerHTML = '<button class="btn btn-danger" onclick="removeGame(' + id + ')" data-bs-dismiss="modal">Yes</button>\n\
                                <button class="btn btn-primary" id="closeBtn" data-bs-dismiss="modal">No</button>'
}
function removeGame(id) {

    dataS = dataS.filter(function (item) {
        return item.ID != id
    })
    var url = "http://" + window.location.host + "/nhom-4-se1604ks-swp391-social-network-for-gamer/remove-game"
    $.ajax({
        url: url,
        method: 'POST',
        data: {
            "id": id
        },
        dataType: 'json',
        success: function (data) {
            alertHtml = '<div class="alert alert-success alert-dismissible">\n\
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>\n\
                            <strong>Delete success!</strong></div>'
            document.getElementById("msgRespond").innerHTML = alertHtml
            hanldePagination();
        },
        error: function () {
            alertHtml = '<div class="alert alert-danger alert-dismissible">\n\
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>\n\
                            <strong>Failure!</strong></div>'
            msgEle.innerHTML = alertHtml
            document.getElementById("closeBtn").click()
        }
    });

}
function editGame(id, name, img) {
    var eleTitle = document.getElementById("titlePopup");
    var formContent = document.getElementById("formContent")
    eleTitle.innerHTML = "Edit type of game"
    formContent.innerHTML = '<form onsubmit="submitFrom(event)"><input id="idGame"type="text"hidden value="' + id + '">\n\
        <div class="form-group">\n\
        <label for="inputGame">Name of game</label>\n\
        <input type="text" class="form-control" value="' + name + '" oninput="handleInputName(this.value)" id="inputGame" placeholder="typing something...">\n\
        <div id="invalid-feedback"></div>\n\
        </div>\n\
        <div class="form-group"> \n\
        <label for="inputImage" class="form-label">Choose image</label>\n\
         <input class="form-control" type="file" id="inputImage" onchange="handleFile(event)"> <img style="width: 90%;" id="insert_img" src="' + img + '" />\n\
         <div id="invalid-feedback-image"></div>\n\
        </div>\n\
        <div class="form-group mt-3">\n\
        <button type="submit"  class="btn btn-primary">Update</button>\n\
        </div> </form>'
}


window.onload = function () {
    if ("WebSocket" in window) {

        senderID = document.getElementById("senderID").value

        var host = document.location.host;
        var pathname = "/nhom-4-se1604ks-swp391-social-network-for-gamer/chat"

        // connect to websocket
        ws = new WebSocket("ws://" + host + pathname + "/" + senderID);
        // recieve message
        ws.onmessage = function (event) {
            var message = JSON.parse(event.data);
            document.getElementById("numberOfUserOnline").innerHTML = message.onlineList.length

        };


    } else {
        console.log("Websockets not supported");
    }

}