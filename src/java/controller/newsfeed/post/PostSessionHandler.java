/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.newsfeed.post;

import com.google.gson.Gson;
import dao.CommentDao;
import dao.LikeDao;
import dao.NotificationDao;
import dao.PostDao;
import dao.UserDao;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import modal.Notification;
import modal.User;

/**
 *
 * @author TuanAnh
 */
@ApplicationScoped
public class PostSessionHandler {

    //path
    private final SortedMap<Integer, Set<Session>> mapP = new TreeMap<>();
    //userID
    private final SortedMap<Integer, Set<Session>> mapI = new TreeMap<>();
    private final SortedMap<Integer, HashMap<Session, Integer>> sessions = new TreeMap<>();

    Gson gson = new Gson();

    public void addSession(Session session, int postID, int userID) {
        addToSortedMap(mapP, session, postID);
        addToSortedMap(mapI, session, userID);
//        System.out.println("open: " + sessions.size());
    }

    public void addToSortedMap(SortedMap<Integer, Set<Session>> map, Session session, int key) {
        if (map.containsKey(key)) {
            map.get(key).add(session);
        } else {
            Set<Session> s = new HashSet<>();
            s.add(session);
            map.put(key, s);
        }
    }

    public void removeSession(int postId, int userId, Session session) {
        removeSessionInMap(mapP, session, postId);
        removeSessionInMap(mapI, session, userId);
//        System.out.println("close: " + sessions.size());
    }

    public void removeSessionInMap(SortedMap<Integer, Set<Session>> map, Session session, int key) {
        map.get(key).remove(session);
        if (map.get(key).isEmpty()) {
            map.remove(key);
        }
    }

    public void likePost(int postID, int userID, String like) {
        LikeDao db = new LikeDao();
        db.likePost(postID, userID);
        
        UserDao udb = new UserDao();
        User u = udb.getUserById(userID);
        
        PostDao pdb = new PostDao();
        int posterID = pdb.getAPost(postID, userID).getUser().getID();
        
        if (mapP.get(0) != null) {
            send_To_All_Session_In_One_Set(mapP.get(0), "{\"action\":\"" + like + "\",\"postID\":" + postID + ",\"posterID\":" + posterID + ",\"userID\":" + userID + ",\"displayname\":\"" + u.getDisplayname() + "\"}");
        }
        if (mapP.get(postID) != null) {
            send_To_All_Session_In_One_Set(mapP.get(postID), "{\"action\":\"" + like + "\",\"postID\":" + postID + ",\"posterID\":" + posterID + ",\"userID\":" + userID + ",\"displayname\":\"" + u.getDisplayname() + "\"}");
        }
        if (like.equals("Like")) {
            NotificationDao ndb = new NotificationDao();
            ndb.addNotification(postID, userID, 0);
        }
    }

    public void commentPost(int postID, int userID, String text) {
        CommentDao db = new CommentDao();
        UserDao udb = new UserDao();
        User u = udb.getUserById(userID);
        int id = db.createComment(postID, userID, text);
        if (id != 0) {
            String jsonData = gson.toJson(db.getCommentById(id));
            send_To_All_Session_In_One_Set(mapP.get(postID), "{\"action\":\"Comment\", \"content\":" + jsonData + ",\"postID\":" + postID + ",\"displayname\":\"" + u.getDisplayname() + "\"}");
        }
    }

    public void editComment(int postID, int cmtID, String text) {
        CommentDao db = new CommentDao();
        db.updateComment(cmtID, text);
        send_To_All_Session_In_One_Set(mapP.get(postID), "{\"action\":\"EditComment\",\"cmtID\":" + cmtID + ",\"text\":\"" + text + "\"}");
    }

    public void removeComment(int postID, int cmtID) {
        CommentDao db = new CommentDao();
        db.deleteComment(cmtID);
        send_To_All_Session_In_One_Set(mapP.get(postID), "{\"action\":\"RemoveComment\",\"cmtID\":" + cmtID + "}");
    }

    private void send_To_All_Session_In_One_Set(Set<Session> set, String text) {
        for (Session session : set) {
            try {
                session.getBasicRemote().sendText(text);
            } catch (IOException ex) {
//                sessions.get(id).remove(session);
                Logger.getLogger(PostSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
