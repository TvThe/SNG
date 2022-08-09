function transform_data_into_post(dataJson, userID) {
//    console.log(dataJson);
    $("#all_id_post").text($("#all_id_post").text() + "," + dataJson['ID']);
    var html = '';
    html += "                <div class = \"card\" id=\"card_post_id_" + dataJson['ID'] + "\">\n" +
            "                    <div class=\"card-body\">\n" +
            "                        <div class=\"row\">\n" +
            "                            <div class=\"col-lg-1 col-md-2\">\n" +
            "                                <img src=\"data:image/jpeg;base64," + dataJson['user']['avatar'] + "\" class=\"rounded-circle\" style=\"height: 75px; width: 75px;\">\n" +
            "                            </div>\n" +
            "                            <div class=\"col-lg-11 col-md-10\">\n" +
            "                                <h5 class=\"card-title\">" + dataJson['user']['displayname'] + "</h5>\n" +
            "                                <p class=\"card-text\"><small>" + dataJson['time'] + "</small></p>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"card-body row\">\n" +
            "                            <div class=\"col-3  post-game\" id=\"card_game_id_" + dataJson['ID'] + "\" data-game_id=\"" + dataJson['game']['ID'] + "\">\n" +
            "                                <img src=\"data:image/jpeg;base64," + dataJson['game']['image'] + "\" class=\"img-fluid rounded-start img-game\">\n" +
            "                            </div>\n" +
            "                            <div class=\"col-7\">\n" +
            "                                <div class=\"row\">\n" +
            "                                    <p class=\"card-text post-content col-8\" id=\"card_content_id_" + dataJson['ID'] + "\">" + dataJson['content'] + "</p>\n" +
            "                                    <div class=\"col-4\">\n";
    if (dataJson['image'] !== 'null') {
        html += "                                    <img src=\"data:image/jpeg;base64," + dataJson['image'] + "\" style=\"height: 130px; width: 130px;\" id=\"card_image_id_" + dataJson['ID'] + "\">\n";
    } else {
        html += "                                    <img src=\"\" style=\"height: 130px; width: 130px;\" id=\"card_image_id_" + dataJson['ID'] + "\" hidden>\n";
//                        html += "                                    <div id=\"card_image_id_" + dataJson['ID'] + "\"></div>\n";
    }
    html += "                                </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"btn-group\">\n" +
            "                                   <button class=\"btn btn-light\" id=\"card_like_amount_id_" + dataJson['ID'] + "\" disabled>" + parseInt(dataJson['like'] / 10) + "</button>\n";
    if (dataJson['like'] % 10 === 1) {
        html += "                                <button class=\"btn btn-card-like\" data-post-id=\"" + dataJson['ID'] + "\" id=\"card_like_id_" + dataJson['ID'] + "\">Liked</button>\n";
    }
    if (dataJson['like'] % 10 === 2) {
        html += "                                 <button class=\"btn btn-card-like\" data-post-id=\"" + dataJson['ID'] + "\" id=\"card_like_id_" + dataJson['ID'] + "\">Like</button>\n";
    }
    html += "                                </div>\n" +
            "                                <a href=\"#\" class=\"btn btn-card-like\">Comment</a>\n" +
            "                            </div>\n" +
            "                            <div class=\"col-2\">\n";
    if (userID == dataJson['user']['ID']) {
        html += "                                <div class=\"row\">\n" +
                "                                    <button class=\"btn btn-card btn_edit_post\" data-post-id=\"" + dataJson['ID'] + "\">Edit</button>\n" +
                "                                </div>\n" +
                "                                <div class=\"row\">\n" +
                "                                    <button class=\"btn btn-card btn_remove_post\" data-post-id=\"" + dataJson['ID'] + "\" data-bs-target=\"#modalConfirmDelete\" data-bs-toggle=\"modal\">Remove</button>\n" +
                "                                </div>\n" +
                "                                <div class=\"row\">\n" +
                "                                    <button class=\"btn btn-card btn_open_post\" data-post-id=\"" + dataJson['ID'] + "\">Open post</button>\n" +
                "                                </div>\n";
    } else {
        html += "                                <div class=\"row\">\n" +
                "                                    <button class=\"btn btn-card btn_repost_post\" data-post-id=\"" + dataJson['ID'] + "\">Report</button>\n" +
                "                                </div>\n" +
                "                                <div class=\"row\">\n" +
                "                                    <button class=\"btn btn-card btn_open_post\" data-post-id=\"" + dataJson['ID'] + "\">Open post</button>\n" +
                "                                </div>\n";
    }
    html += "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>";
    return html;
}