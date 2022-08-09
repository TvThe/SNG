package controller.chat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import modal.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author window
 */
public class ChatService {

    // list socket 
    public static final Set<ChatWebSocket> chatWebsockets = new CopyOnWriteArraySet<>();

    private static ChatService chatService = null;

    private ChatService() {
    }

    public synchronized static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatService();
        }
        return chatService;
    }

    // adding one socket to list
    public boolean register(ChatWebSocket chatWebsocket) {
        return chatWebsockets.add(chatWebsocket);
    }

    // removing one socket to list
    public boolean close(ChatWebSocket chatWebsocket) {
        return chatWebsockets.remove(chatWebsocket);
    }

    // method send message for all user
    public void sendMessageToAllUsers(Message message) {
        message.setOnlineList(getListIds());
        chatWebsockets.stream().forEach(chatWebsocket -> {
            try {
                chatWebsocket.getSession().getBasicRemote().sendObject(message);
            } catch (EncodeException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // method send message for one user
    public void sendMessageToOneUser(Message message) {

        if (message.getReceiveID() != null) {
            chatWebsockets.stream()
                    .filter(chatWebsocket -> Objects.equals(chatWebsocket.getId(), message.getReceiveID()))
                    .forEach(chatWebsocket -> {
                        try {
                            System.out.println("go here");
                            chatWebsocket.getSession().getBasicRemote().sendObject(message);
                        } catch (IOException | EncodeException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    protected Set<Integer> getListIds() {
        Set<Integer> listIds = new HashSet<Integer>();
        chatWebsockets.forEach(chatWebsocket -> {
            listIds.add(chatWebsocket.getId());
        });
        return listIds;
    }
}
