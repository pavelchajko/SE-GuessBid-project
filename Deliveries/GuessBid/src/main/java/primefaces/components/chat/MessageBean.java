/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components.chat;

import com.google.gson.Gson;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import primefaces.components.chat.MessageManagerLocal;

/**
 *
 * @author Anton Danshin
 */
@ManagedBean
@ViewScoped
public class MessageBean implements Serializable {

    @EJB
    MessageManagerLocal mm;

    private final List<ChatMessage> messages;
    private Date lastUpdate;
    private ChatMessage message;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
        messages = Collections.synchronizedList(new LinkedList());
        lastUpdate = new Date(0);
        message = new ChatMessage();

    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public void sendMessage(Auction auc, User usr) {
        if (message.getMessage() != null) {
            message.setAuctionID(auc.getAuctionId());
            message.setUser(usr.getUserEmail());
            System.out.println("MESSAGE: " + message.getMessage() + " USER: " + usr.getUserEmail());
            mm.sendMessage(message);
        } else {
            System.out.println("MESSAGE IS EMPTY!");
        }

    }

    public void firstUnreadMessage(Auction auc) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        ChatMessage m = mm.getFirstAfter(lastUpdate, auc);

        ctx.addCallbackParam("messageStatus", m != null);
        if (m == null) {
            return;
        }

        lastUpdate = m.getDateSent();
        ctx.addCallbackParam("user", m.getUser());
        ctx.addCallbackParam("dateSent", m.getDateSent().toString());
        ctx.addCallbackParam("text", m.getMessage());

    }
    
    public void updateChatAndUsers(Auction auc){
        firstUnreadMessage(auc);
    }
    
}
