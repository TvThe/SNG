<%-- 
    Document   : popupForEditPost
    Created on : 31-05-2022, 09:08:41
    Author     : TuanAnh
--%>

<div class="modal fade" id="edit_modal">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->

            <!-- Modal body -->
            <form id="edit_modal_form">
                <div class="modal-header">

                    <input type="hidden" name="uID" value="${sessionScope.account.ID}"/>
                    <input type="hidden" name="edit_post_id" id="edit_post_id" value=""/>
                    <h4 class="modal-title">${sessionScope.account.displayname}</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <textarea class="form-control" id="edit_content" name="text" rows="3" placeholder="Content"></textarea>
                    <label class="mt-3">Choose Image: </label>
                    <input type="file" name="photo"id="editImage"multiple="true" size="50" onchange="editLoadFile(event)"/>
                    <div id="images" class="mt-3" style="position: relative;">
                        <img id="edit_output" width="30%" />
                        <!--                        <span><button type="button" class="btn-close" data-bs-dismiss="modal"></button></span>-->
                        <span id="edit_btn_remove_image" hidden="hidden" style="position: absolute;top: -14px;left: 220px;cursor: pointer;"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style="height: 20px;width: 20px;"><path d="M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8zm121.6 313.1c4.7 4.7 4.7 12.3 0 17L338 377.6c-4.7 4.7-12.3 4.7-17 0L256 312l-65.1 65.6c-4.7 4.7-12.3 4.7-17 0L134.4 338c-4.7-4.7-4.7-12.3 0-17l65.6-65-65.6-65.1c-4.7-4.7-4.7-12.3 0-17l39.6-39.6c4.7-4.7 12.3-4.7 17 0l65 65.7 65.1-65.6c4.7-4.7 12.3-4.7 17 0l39.6 39.6c4.7 4.7 4.7 12.3 0 17L312 256l65.6 65.1z"/></svg></span>
                    </div>
                </div>
                <div class="modal-body">
                    Type of game: <div id="edit_selectGame" ></div>
                </div>
            </form>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button id="edit_submit_button" data-edit_post_id="0" data-bs-target="#modal_notice" data-bs-toggle="modal" class="btn btn-card">Edit</button>
                <!--                <button class="btn btn-primary" data-bs-target="#modal_notice" data-bs-toggle="modal">Open second modal</button>-->
            </div>
        </div>
    </div>
</div>             
</div>

<div class="modal fade" id="modal_notice" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered ">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure to edit this post
            </div>
            <div class="modal-footer">
                <!--        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>-->
                <button type="button" id="confirm_edit" class="btn btn-card" data-bs-dismiss="modal">Confirm</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>