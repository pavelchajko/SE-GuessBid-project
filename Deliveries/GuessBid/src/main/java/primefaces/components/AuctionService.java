/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Egzon
 */
@Stateless
public class AuctionService {
    private Auction auction;
    private Product product;
    
    @PersistenceContext
    private EntityManager em;
    
    //Creates a new auction in the database
    public void createAuction(Auction auction) {
        em.persist(auction);
    }
    
    public void updateAuction(Auction auction) {
        em.merge(auction);
    }
    
    //Creates a new product in the database
    public void createProduct(Product product) {
        em.persist(product);
    }
    
    //Check if an auction exists in the database
    public boolean checkIfAuctionExists(Auction auction){
        return em.find(Auction.class, auction.getAuctionId()) != null;
    }
    
    //Check if an auction exists in the database
    public boolean checkIfProductExists(Product product){
        return em.find(Product.class, product.getProductId()) != null;
    }
    
    //persist Notification
    public void createNotification(Notification n){
        em.persist(n);
    }
    
        //persist Notification
    public void updateeNotification(Notification n){
        em.merge(n);
    }
}
