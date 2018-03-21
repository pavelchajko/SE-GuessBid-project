/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Group;
import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.User;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miglie
 */
@Stateless
public class UserManager {

    @PersistenceContext
    EntityManager em;
    List<Auction> auctionsList;
    @Inject
    Principal principal;
    
    public void save(User user) {
        user.setUserGroup(Group.USERS);
        user.setUserCredit(100);
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        //System.out.print("PRINCIPAL = " + principal.getName());
        return em.find(User.class, principal.getName());
    }    
    
    public String getLoggedUserEmail(){
        return getLoggedUser().getUserEmail();
    }
    
    public List<User> getAllUsers(){
        List<User> temp = new ArrayList<User>();
        temp = em.createNamedQuery("User.findAll").getResultList(); 
        return temp;
    }
    
    //get user notifications
    public String countNotificatins(String userEmail){
        List <Notification> notif = em.createNamedQuery("Notification.findAll").getResultList();
        int cnt = 0;
        for(Notification n : notif){
            if(n.getNotificationPK().getUserUserEmail().equals(userEmail) && n.getSeen() == false){
                ++cnt;
            }
        }
        return String.valueOf(cnt);
    }

    public List<Notification> getNotificationsOfUser(String userEmail) {
        List <Notification> notif = em.createNamedQuery("Notification.findAll").getResultList();
        List <Notification> ofUser = new ArrayList<>();
        
        for(Notification n : notif){
            if(n.getNotificationPK().getUserUserEmail().equals(userEmail) && n.getSeen() == false){
                ofUser.add(n);
                //
            }
        }
        return ofUser;
    }
    
}
