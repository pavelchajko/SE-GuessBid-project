/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components.chat;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;
import primefaces.components.chat.ChatMessage;

/**
 * Simple chat logic
 *
 * @author Danon
 */
@Singleton
@Startup
public class MessageManager implements MessageManagerLocal {

    private final List<ChatMessage> messages
            = Collections.synchronizedList(new LinkedList());
    
    public MessageManager(){
        
    }
    
    @Override
    public void sendMessage(ChatMessage msg) {
        messages.add(msg);
        msg.setDateSent(new Date());
    }

    @Override
    public ChatMessage getFirstAfter(Date after, Auction auc) {
        if (messages.isEmpty()) {
            return null;
        }
        if (after == null) {
            return messages.get(0);
        }

        for (ChatMessage m : messages) {
            if (m.getDateSent().after(after) && (m.getAuctionID() == auc.getAuctionId())) {
                return m;
            }
        }
        return null;
    }

    
}
