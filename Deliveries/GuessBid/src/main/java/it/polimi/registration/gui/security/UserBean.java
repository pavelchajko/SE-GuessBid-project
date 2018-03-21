/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.Product;
import it.polimi.registration.business.security.entity.User;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import primefaces.components.AuctionService;

/**
 *
 * @author ErvinK
 */
@Named
@RequestScoped
public class UserBean{

    @EJB
    UserManager um;
    
    @EJB
    AuctionService ac;
    
    public UserBean() {
    }
    
    public String getName() {
        return um.getLoggedUser().getUserName();
    }
    
    public String getEmail() {
        return um.getLoggedUser().getUserEmail();
    }
    
    public String getCredit(){
        return um.getLoggedUser().getUserCredit().toString();
    }
    
    public User getUser(){
        return um.getLoggedUser();
    }
    
    public String notificationCount(String userEmail){
        return um.countNotificatins(userEmail);
    }
    
    public List <Notification> getNotificationsOfUser(String userEmail){
        return um.getNotificationsOfUser(userEmail);
    }
    
    public String markAsRead(String userEmail){
        List <Notification> notif = um.getNotificationsOfUser(userEmail);
        for(Notification n : notif){
            n.setSeen(Boolean.TRUE);
            ac.updateeNotification(n);
        }
        return "notifications.xhtml?faces-redirect=true";
    }
}
