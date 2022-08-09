/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.newsfeed.post;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author TuanAnh
 */
@ApplicationScoped
@ServerEndpoint(value = "/postSeverEndpoint/{postId}/{userId}")
public class PostWebSocketServer {

    @Inject
    private PostSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session, @PathParam("postId") int postId, @PathParam("userId") int userId) {
        System.out.println("postId: " + postId + ", call on open " + session);
        System.out.println("userId: " + userId + ", call on open " + session);
        sessionHandler.addSession(session, postId, userId);
    }

    @OnClose
    public void close(Session session, @PathParam("postId") int postId, @PathParam("userId") int userId) {
        System.out.println("call on close");
        sessionHandler.removeSession(postId, userId, session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(PostWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {

            JsonObject jsonMessage = reader.readObject();

            String action = jsonMessage.getString("action");
            
            int postID, userID;
            System.out.println(jsonMessage);
            switch (action) {
                case "Like":
                case "Liked":
                    System.out.println("calll");
                    postID = (int) jsonMessage.getInt("postID");
                    userID = (int) jsonMessage.getInt("userID");
                    sessionHandler.likePost(postID, userID, jsonMessage.getString("action"));
                    break;
                    
                case "Comment":
                    postID = (int) jsonMessage.getInt("postID");
                    userID = (int) jsonMessage.getInt("userID");
                    sessionHandler.commentPost(postID, userID, jsonMessage.getString("text"));
                    break;
                case "EditComment":
                    sessionHandler.editComment(jsonMessage.getInt("postID"), Integer.parseInt(jsonMessage.getString("commentID")), jsonMessage.getString("text"));
                    break;
                case "RemoveComment":
                    sessionHandler.removeComment(jsonMessage.getInt("postID"), Integer.parseInt(jsonMessage.getString("commentID")));
                    break;
            }
//            if ("Like".equals(action) || "Liked".equals(action)) {
////            System.out.println(action);
//                System.out.println("calll");
//                int postID = (int) jsonMessage.getInt("postID");
//                int userID = (int) jsonMessage.getInt("userID");
//                sessionHandler.likePost(postID, userID, jsonMessage.getString("action"));
//            }
//            if ("Comment".equals(action)) {
//                
//            }
        }
    }
}