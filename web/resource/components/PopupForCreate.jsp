<%-- 
    Document   : PopupForCreate
    Created on : May 27, 2022, 12:54:13 AM
    Author     : Admin
--%>

<div class="container mt-3">
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" id="btnPopUpMoDal" style="margin-top: 60px;">
        Create Post
    </button>
</div>
<div class="modal" id="myModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->

            <!-- Modal body -->
            <form id="createPostForm">
                <div class="modal-header">

                    <input type="hidden" name="uID" value="${sessionScope.account.ID}"/>
                    <h4 class="modal-title">${sessionScope.account.displayname}</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <textarea class="form-control" id="note" name="text" rows="3" placeholder="Content"></textarea>
                    <label class="mt-3">Choose Image: </label>
                    <input type="file" name="photo"id="uploadImage"multiple="true" size="50" onchange="loadFile(event)"/>
                    <div id="images" class="mt-3">
                        <img id="output" width="30%" />
                    </div>
                </div>
                <div class="modal-body">
                    Type of game: <div id="selectGame" ></div>

                </div>
            </form>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button id="creatPostbutton"class="btn">Post</button>
            </div>

        </div>
    </div>             
</div>

                    <div class="modal" tabindex="-1" id="modalConfirmDelete">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
            </div>
            <div class="modal-body">
                <p>Are you want to delete this post ?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="confirmDelete">Delete</button>
            </div>
        </div>
    </div>
</div>


