/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components.chat;


 
import it.polimi.registration.business.security.entity.Auction;
import java.util.Date;
import javax.ejb.Local;

 
/**
 * Local interface for chat lagic EJB
 * @author Danon
 */
@Local
public interface MessageManagerLocal {
 
    void sendMessage(ChatMessage msg);
 
    ChatMessage getFirstAfter(Date after, Auction auc);
 
}