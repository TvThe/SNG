<%-- 
    Document   : PopUpEditProduct
    Created on : Jun 13, 2022, 9:51:54 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Modal -->

        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" id="contentModalProduct">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">${sessionScope.account.displayname}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                       
                    <div class="modal-body">
                        
                        <label>Name Product: </label>
                        <input class="form-control" id="nameProduct" name="nameProduct" rows="3">
                        <label>Description: </label>
                        <input class="form-control" id="description" name="description" rows="3">
                        <label>Price: </label>
                        <input class="form-control" id="price" name="price" rows="3">

                        <label class="mt-3">Choose Image: </label>
                        <input type="file" name="photo"id="uploadImage"multiple="true" size="50" onchange="loadFile()"/>
                        <div id="images" class="mt-3">

                        </div>
                        Type of game: <div id="selectGame" ></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save</button>
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
                        <p>Are you want to delete this product ?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="confirmDelete">Delete</button>
                    </div>
                </div>
            </div>
        </div>                
    </body>
</html>
