/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.business.security.entity.Userbid;
import it.polimi.registration.business.security.entity.UserbidPK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BidService {
    
    @PersistenceContext
    private EntityManager em;

    //Creates a new bid for a user
    public void createBid(Userbid userBid) {
        em.persist(userBid);
    }

    //Updates the bid from a user
    public void updateBid(Userbid ubid) {
        em.merge(ubid);
    }

    public void updateBidPK(UserbidPK ubidPK) {
        em.persist(ubidPK);
    }

    public void updateAuction(Auction auc) {
        em.merge(auc);
        System.out.println("Auction: " + auc.getAuctionDesc() + " id: " + auc.getAuctionId());
    }

    public void updateUser(User user) {
        em.merge(user);
        System.out.println("User updated");
    }

    //returns the auction
    public Auction findAuction(Integer id) {
        return em.find(Auction.class, id);
    }

    //Returns the user
    public User findUser(String id) {
        return em.find(User.class, id);
    }

    //Checks whether a bid from the same user in the same auction exists
    public boolean findUserBid(UserbidPK id) {
        return em.find(Userbid.class, id) != null;
    }
    
    

}
