<%-- 
    Document   : ShowListFriend
    Created on : Jun 20, 2022, 9:52:55 PM
    Author     : window
--%>
<ul id="new-box-message">

</ul>

<div id="box-list-friend">
    <span style="font-size: 36px;"  data-bs-toggle="offcanvas" data-bs-target="#demo"><i class="fas fa-meteor"></i></span>
</div>



<div class="offcanvas offcanvas-end" id="demo">
    <div class="offcanvas-header">
        <h1 class="offcanvas-title">Show list friends </h1>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas"></button>
    </div>
    <div class="offcanvas-body">
        <div class="input-group">
            <input id="inputSearch" oninput="searchFriend(this.value)"type="text" placeholder="Search..."  class="form-control search">
            <div class="input-group-prepend">
                <span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
            </div>
        </div>

        <ul id="list-friend">
            <c:forEach items="${friendList}" var="fr">
                <li class="item-friend"><a class="item-friend-link">
                        <div class="box-img">
                            <img class="friend-img" src="data:image/jpeg;base64,${fr.avatar}">
                            <span id="friend-status-onl${fr.ID}"></span>
                        </div>
                        <span>${fr.displayname}</span>
                    </a>
                </li>
            </c:forEach>

        </ul>

    </div>
</div>