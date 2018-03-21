/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.NotificationPK;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.business.security.entity.Userbid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ErvinK
 */
@Startup
@Singleton
public class Scheduler {
    
    @EJB
    private AuctionService auctionService;
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private TimerService timerService;
    
    //contains the winning bid of auctions
    int winning_bid = 0;
    
    @PostConstruct
    private void init() {
        ScheduleExpression everyDay = new ScheduleExpression().second("0").minute("57").hour("16");
        timerService.createCalendarTimer(everyDay, new TimerConfig("", false));
    }    
    
    //Every midnight we check for auction that are finished 
    //And assign winners
    @Timeout
    public void checkIfFinshedAuctions(){
        
       //decide who is the winner and assign it to the auction
       List <Auction> auctions =  em.createNamedQuery("Auction.findAll").getResultList();
       
       for(Auction a : auctions){
           if(!finishedAuction(a)) continue;
           User winner = getWinnerAuction(a);
           if(winner != null){
               a.setUserAuctionWinner(winner);
               a.setAuctionAmount(winning_bid);
               auctionService.updateAuction(a);
           }
       }
       
       for( Auction a : auctions){
           //we already showed notification for this
           if(a.getAuctionDone() == true) continue;
           if(finishedAuction(a)){
               a.setAuctionDone(Boolean.TRUE);
               auctionService.updateAuction(a);
               
               //is there a winner?
               if(a.getUserAuctionWinner() != null){
                   //we notify him
                   Notification n = new Notification();
                   NotificationPK npk = new NotificationPK();
                   npk.setAuctionAuctionId(a.getAuctionId());
                   npk.setUserUserEmail(a.getUserAuctionWinner().getUserEmail());
                   n.setNotificationPK(npk);
                   n.setAuction(a);
                   n.setUser(a.getUserAuctionWinner());
                   n.setSeen(Boolean.FALSE);
                   n.setContent("Congratulations! You won " + a.getAuctionTitle() + " auction. ");
                   auctionService.createNotification(n);
                   
                   //notify owner who is the winner
                   Notification n1 = new Notification();
                   NotificationPK npk1 = new NotificationPK();
                   npk1.setAuctionAuctionId(a.getAuctionId());
                   npk1.setUserUserEmail(a.getUserAuctionEmail().getUserEmail());
                   n1.setNotificationPK(npk1);
                   n1.setAuction(a);
                   n1.setUser(a.getUserAuctionEmail());
                   n1.setSeen(Boolean.FALSE);
                   n1.setContent("The auction " + a.getAuctionTitle() + " was won by " + a.getUserAuctionWinner().getUserEmail() + ".");
                   auctionService.createNotification(n1);
                  
                   //WE NEED TO fix credit of winner and owner
                   User winner =  a.getUserAuctionWinner();
                   winner.setUserCredit(winner.getUserCredit() - a.getAuctionAmount());
                   
                   User owner = a.getUserAuctionEmail();
                   owner.setUserCredit(owner.getUserCredit() + a.getAuctionAmount());
                   //DO WE NEED TO assign won product ??
               }else{
                   //notify owner that no one bidded to the auction
                   //or no unique bid was provided
                   Notification n1 = new Notification();
                   NotificationPK npk1 = new NotificationPK();
                   npk1.setAuctionAuctionId(a.getAuctionId());
                   npk1.setUserUserEmail(a.getUserAuctionEmail().getUserEmail());
                   n1.setNotificationPK(npk1);
                   n1.setAuction(a);
                   n1.setUser(a.getUserAuctionEmail());
                   n1.setSeen(Boolean.FALSE);
                   n1.setContent("The auction " + a.getAuctionTitle() + " is not won by anyone. Either no bids or no unique bids.");
                   auctionService.createNotification(n1);
                   //PERSIST
               }
           }
       }
        System.out.print("CHECKING FOR NOTIFICATIONS DONE.");
    }
    
    private boolean finishedAuction(Auction auction) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();

        if (auction.getAuctionEndtime().after(today)) {
            return false;
        }
        return true;
    }

    private User getWinnerAuction(Auction a) {
        List <Userbid> bids = em.createNamedQuery("Userbid.findAll").getResultList();
        List <Userbid> auction_bids = new ArrayList<>();
        List <Userbid> unique_bids = new ArrayList<>();
        User WINNER = null;
        
        //take bids of auction a
        for(Userbid u : bids){
            if(u.getUserbidPK().getUserbidAuction()== a.getAuctionId()){
                auction_bids.add(u);
            }
        }
        
        //take only unique bids
        for(int i = 0; i < auction_bids.size(); i++){
            boolean unique = true;
            for(int j = 0; j < auction_bids.size(); j++){
                if(i == j) continue;
                if(Objects.equals(auction_bids.get(i).getUserbidBid(), auction_bids.get(j).getUserbidBid())) unique = false;
            }
            if(unique == true){
                unique_bids.add(auction_bids.get(i));
            }
        }
        
        //take the user with smalles unique bid
        int min = 1234567890;
        for(int i = 0; i < unique_bids.size(); i++){
            if(unique_bids.get(i).getUserbidBid() < min){
                min = unique_bids.get(i).getUserbidBid();
                WINNER = unique_bids.get(i).getUser();
            }
        }
        winning_bid = min;
        return WINNER;
    }

}
