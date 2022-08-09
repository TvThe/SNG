package controller.chat;

import dao.MessageDao;
import modal.MessageEncoder;
import modal.MessageDecoder;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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
@ServerEndpoint(value = "/chat/{id}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatWebSocket {

    private Session session;
    private Integer id;

    private ChatService chatService = ChatService.getInstance();
    private MessageDao dBMessage = new MessageDao();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") int id) throws IOException, EncodeException {

        if (chatService.register(this)) {
            this.session = session;
            this.id = id;
            Message message = new Message(null, id, null, "[O]pen", null, null);

            chatService.sendMessageToAllUsers(message);
        }

    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException, Exception {
        Date date = new Date();
        message.setTime(date);

        Integer idm = dBMessage.saveMessage(message);

        message.setID(idm);
        chatService.sendMessageToOneUser(message);

    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        if (chatService.close(this)) {
            Message message = new Message(null, id, null, "[C]lose", null, null);
            chatService.sendMessageToAllUsers(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    public Integer getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

}
