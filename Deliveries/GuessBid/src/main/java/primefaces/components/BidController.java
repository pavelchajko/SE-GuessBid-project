/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Userbid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Egzon
 */
@ManagedBean(name = "bidController")
@SessionScoped
public class BidController {

    private List<Userbid> myBids;
    private String status;
    private String dateStatus;
    private String myAuctionStatus;

    @EJB
    UserManager um;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        myBids = getMyBids();
    }

    public List<Userbid> getAllBids() {
        return em.createNamedQuery("Userbid.findAll").getResultList();
    }

    public Auction getAuction(int pKey) {
        return (Auction) em.createNamedQuery("Auction.findByAuctionId").setParameter("auctionId", pKey).getSingleResult();
    }

    public List<Userbid> getMyBids() {
        String email = um.getLoggedUserEmail();
        List<Userbid> tempAll = getAllBids();
        List<Userbid> tempMy = new ArrayList<Userbid>();
        Auction tempAuction = new Auction();

        for (Userbid ub : tempAll) {
            if (email.equals(ub.getUserbidPK().getUserBidEmail())) {
                tempAuction = getAuction(ub.getUserbidPK().getUserbidAuction());
                ub.setAuction(tempAuction);
                tempMy.add(ub);
            }
        }
        return tempMy;
    }

    public void setMyBids(List<Userbid> myBids) {
        this.myBids = myBids;
    }

    public void log() {
        System.out.println("BIDHISTORY DEBUG:");
        for (Userbid ub : myBids) {
            System.out.println("Title: " + ub.getAuction().getAuctionTitle());
            System.out.println("Desc: " + ub.getAuction().getAuctionDesc());
            System.out.println("ProductName: " + ub.getAuction().getAuctionProduct().getProductName());
            System.out.println("Bid: " + ub.getUserbidBid());
        }
    }

    public String getDateStatus(Auction auctionDate) {
        if (finishedAuction(auctionDate)) {
            return "Auction ended";
        } else {
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            Date today = c.getTime();
            long diff = auctionDate.getAuctionEndtime().getTime() - today.getTime();
            long Days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
            if (Days == 1) {
                return "Ends in " + String.valueOf(Days) + " day.";
            } else {
                return "Ends in " + String.valueOf(Days) + " days.";
            }

        }
    }

    public void setDateStatus(String dateStatus) {
        this.dateStatus = dateStatus;
    }

    public String getStatus(Userbid ub) {
        Auction tempAuc = getAuction(ub.getUserbidPK().getUserbidAuction());
        if (tempAuc != null) {
            return getCurrentPosition(tempAuc);
        }
        return "NULL";

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentPosition(Auction auction) {
        int pos = getBidPosition(auction);

        if (finishedAuction(auction)) {
            if (pos == 1) {
                return "Won";
            } else {
                return "Lost";
            }
        } else {
            if (pos == -1) {
                return "Not a unique bid...";
            } else {
                if (pos == 1) {
                    return "First!";
                } else if (pos == 2) {
                    return "Second!";
                } else if (pos == 3) {
                    return "Third!";
                } else {
                    return String.valueOf(pos) + "th";
                }
            }
        }

    }

    private int getBidPosition(Auction auc) {
        List<Userbid> allbids = getAllBids();
        List<Integer> bids = new ArrayList<Integer>();
        Auction tempAuc = new Auction();

        Integer enteredBid = 0;

        for (Userbid ub : allbids) {
            tempAuc = getAuction(ub.getUserbidPK().getUserbidAuction());
            if (tempAuc.getAuctionId() == auc.getAuctionId()) {
                //get all the other bids except my bid
                //so i can check if it is unique or not
                if (!um.getLoggedUserEmail().equals(ub.getUserbidPK().getUserBidEmail())) {
                    bids.add(ub.getUserbidBid());
                } else {
                    enteredBid = ub.getUserbidBid();
                }
            }
        }

        if (checkifUnique(enteredBid, bids)) {
            //now I add myBid to the list of bids because I know it is not
            //a duplicate bid, and I check for its position
            bids.add(enteredBid);
            List<Integer> noDuplicates = removeDuplicates(bids);
            //Here I sort the bids starting from the least.
            Collections.sort(noDuplicates);

            //We assign the index of myBid + 1to the bidPosition
            //This works because our array is sorted
            //System.out.println("VALUE: "+String.valueOf(noDuplicates.indexOf(enteredBid) + 1));
            return noDuplicates.indexOf(enteredBid) + 1;
        } else {
            return -1;
        }

    }

    private List<Integer> removeDuplicates(List<Integer> bidsList) {
        List<Integer> noDuplicates = new ArrayList<>();
        List<Integer> tempList = new ArrayList<Integer>(bidsList);

        for (Integer item : bidsList) {
            //Here I remove the item I'm about to check because if I leave it
            //the checkDuplicate function will alway return true because it will
            //compare with itself
            tempList.remove(item);
            if (checkifUnique(item, tempList)) {
                //The bids that are not duplicate are added to the 
                //noDuplicates list.
                noDuplicates.add(item);
            }
            //I have to reinitialize the tempList to the bidList
            //if I don't there will be missing bids that were remove
            //in the previous cycle
            tempList = new ArrayList<Integer>(bidsList);
        }
        return noDuplicates;
    }

    private boolean checkifUnique(Integer myBid, List<Integer> otherBids) {
        for (Integer bid : otherBids) {
            if (bid == myBid) {
                return false;
            }
        }
        return true;
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

    public String getMyAuctionStatus(Auction auction) {
        if (finishedAuction(auction)) {
            List<Userbid> allbids = em.createNamedQuery("Userbid.findAll").getResultList();
            List<Userbid> allAuctionBids = new ArrayList<Userbid>();

            for (Userbid ub : allbids) {
                if (ub.getAuction().getAuctionId() == auction.getAuctionId()) {
                    allAuctionBids.add(ub);
                }
            }
            System.out.println("auction: "+auction.getAuctionId());
            List<Integer> allbidsInt = new ArrayList<Integer>();
            for (Userbid ub : allAuctionBids) {
                allbidsInt.add(ub.getUserbidBid());
                System.out.println("xx: "+ub.getUserbidBid());
            }

            if (soldStatus(allbidsInt)) {
                return "Sold";
            } else {
                return "Not sold";
            }
            
        }else {
            return "Not finished yet...";
        }
    }

    private boolean soldStatus(List<Integer> auctionBids) {
  
        if (auctionBids.isEmpty()) {
            return false;
        } else {

            for (int i = 0; i < auctionBids.size(); i++) {
                int sum = 0;
                for (int k = 0; k < auctionBids.size(); k++) {
                    if ((auctionBids.get(i) == auctionBids.get(k)) && i != k) {
                        sum++;
                    }
                }
                if (sum == 0) {
                    return true;
                }
            }
            return false;
        }

    }

    public void setMyAuctionStatus(String myAuctionStatus) {
        this.myAuctionStatus = myAuctionStatus;
    }

}
