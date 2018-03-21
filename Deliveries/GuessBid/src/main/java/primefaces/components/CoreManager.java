/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Product;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.business.security.entity.Userbid;
import it.polimi.registration.business.security.entity.UserbidPK;
import it.polimi.registration.gui.security.UserBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import primefaces.components.chat.MessageBean;
import primefaces.components.chat.MessageManager;

@ManagedBean
@SessionScoped
public class CoreManager implements Serializable {

    @EJB
    private BidService bidService;

    @EJB
    private UserManager um;

    MessageManager mm = new MessageManager();

    @PersistenceContext
    EntityManager em;

    private Userbid userBid;
    private UserbidPK userBidPK;

    private List<Auction> auctions;
    private List<Product> products;
    private Auction selectedAuction;
    private UserBean userBean = new UserBean();

    private boolean auctionEnded;

    private String bidAmount;

    @PostConstruct
    public void init() {
        auctions = getAuctionsList();
//        if (selectedAuction == null) {
//            System.out.println("SELECTEDAUCTION IS NULL");
//        } else {
//            mm.newOnlineUser(selectedAuction.getAuctionId(), um.getLoggedUser().getUserEmail());
//        }

    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public List<Auction> getAuctionsList() {
        List<Auction> auctions = em.createNamedQuery("Auction.findAll").getResultList();
        List<Auction> temp = new ArrayList<Auction>();
        for (Auction ac : auctions) {
            //if (!finishedAuction(ac)) {
            temp.add(ac);
            //}
        }
        return temp;
    }

    public List<Auction> getAllAuctionsList() {
        List<Auction> auctions = em.createNamedQuery("Auction.findAll").getResultList();
        List<Auction> temp = new ArrayList<>();
        for (Auction ac : auctions) {
            temp.add(ac);
        }
        return temp;
    }

    public List<Auction> getAuctions() {
        return getAuctionsList();
    }

    public List<Auction> getmyAuctions(String email) {
        List<Auction> tempAuctions = new ArrayList<Auction>();
        List<Auction> allAuctionList = getAllAuctionsList();
        for (Auction ac : allAuctionList) {
            if (email.equals(ac.getUserAuctionEmail().getUserEmail())) {
                tempAuctions.add(ac);
            }
        }
        return tempAuctions;
    }

    public boolean isAuctionEnded() {
        if (finishedAuction(selectedAuction)) {
            return true;
        } else {
            return false;
        }
    }

    public void setAuctionEnded(boolean auctionEnded) {
        this.auctionEnded = auctionEnded;
    }

    public Auction getSelectedAuction() {
        return selectedAuction;
    }

    public void setSelectedAuction(Auction selectedAuction) {
        this.selectedAuction = selectedAuction;
    }

    public String goToAuction() {
        return "auction?faces-redirect=true";
    }

    public String goToAuctionHistory(Auction auc) {
        this.selectedAuction = auc;
        return "auction?faces-redirect=true";
    }

    public String goToCreateAuction() {
        return "createAuction?faces-redirect=true";
    }

    public String goToCreateNewProduct() {
        return "createNewProduct?faces-redirect=true";
    }

    public String updateBid(String bid, Auction auction, User user) {
        if (auction.getUserAuctionEmail().getUserEmail().equals(user.getUserEmail())) {
            showMessage("You can't bid on your own auction!");
            return "auction?faces-redirect=true";
        }

        if (utilities.isInteger(bid)) {
            int bidInt = Integer.parseInt(bid);
            Auction tempAuc = new Auction();
            User tempUser = new User();
            userBidPK = new UserbidPK();
            userBid = new Userbid();

            userBid.setUserbidBid(bidInt);

            tempAuc = bidService.findAuction(auction.getAuctionId());
            tempUser = bidService.findUser(user.getUserEmail());
            int totalCredit = 2 + bidInt;
            if (tempUser.getUserCredit() >= totalCredit) {
                userBidPK.setUserBidEmail(tempUser.getUserEmail());
                userBidPK.setUserbidAuction(tempAuc.getAuctionId());
                userBid.setUserbidPK(userBidPK);
                userBid.setUser(tempUser);
                userBid.setAuction(tempAuc);
                if (bidService.findUserBid(userBidPK)) {
                    bidService.updateBid(userBid);
                } else {
                    bidService.createBid(userBid);
                }
                tempUser.setUserCredit(tempUser.getUserCredit() - 2);
                bidService.updateUser(tempUser);

                int tempPos = getBidPosition(user.getUserEmail());

                if (tempPos == -1) {
                    showMessage("Your bid is not unique! Try again...");
                } else {
                    showMessage("Bid added! Current position: " + tempPos);

                }
                //refreshes the page
                goToAuction();
            } else {
                System.out.println("Not enough credit");
                showMessage("Not enough credit!");
            }
        } else {
            showMessage("You have to enter a number as a bid!");
            System.out.println("You have to enter a number");
        }

        return "auction?faces-redirect=true";
    }

    private void showMessage(String msg) {
        GrowlView gv = new GrowlView();
        gv.setMessage(msg);
        gv.saveMessage();
    }

    //this function is used to print the details of bid current position
    public String getCurrentPosition(String email) {
        if (checkIfUserHasBidded(email)) {
            int pos = getBidPosition(email);
            if (pos == -1) {
                return "Your bid is not unique!";
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
        } else {
            return "User has not bidded yet!";
        }
    }

    //This function returns true if a user has bidded on an auction
    //and false otherwise
    private boolean checkIfUserHasBidded(String mail) {
        List<Userbid> allUserBids = getAllUserbidRows();
        List<Integer> bidsList = new ArrayList<>();
        //get the id of the auction we want to check.
        int auctionID = selectedAuction.getAuctionId();
        int currentAuction;
        for (int i = 0; i < allUserBids.size(); i++) {
            currentAuction = allUserBids.get(i).getUserbidPK().getUserbidAuction();
            if (currentAuction == auctionID) {
                //if users mail is in the row of bids, 
                //we know that the user has bidded
                if (mail.equals(allUserBids.get(i).getUserbidPK().getUserBidEmail().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    //this functions returns all the rows from the table userbid 
    private List<Userbid> getAllUserbidRows() {
        return em.createNamedQuery("Userbid.findAll").getResultList();
    }

    private int getBidPosition(String userEmail) {
        int auctionID = selectedAuction.getAuctionId();

        List<Userbid> allUserBids = getAllUserbidRows();
        List<Integer> bidsList = new ArrayList<>();
        int myBid = 0;

        //tempAuctionID holds the auction id of iterated rows from 
        //allUsersBids list.
        int tempAuctionID;
        for (int i = 0; i < allUserBids.size(); i++) {
            tempAuctionID = allUserBids.get(i).getUserbidPK().getUserbidAuction();
            //we check if we are in the same auction
            if (tempAuctionID == auctionID) {
                //here I get the bid that the current user 
                //has placed in the auction
                if (userEmail.equals(allUserBids.get(i).getUserbidPK().getUserBidEmail().toString())) {
                    myBid = allUserBids.get(i).getUserbidBid();
                } else {
                    //here I get the bids of all other users on the same auction
                    bidsList.add(allUserBids.get(i).getUserbidBid());
                }
            }
        }

        //bidPosition is initialized to 1 because if myBid is smaller than 
        //every other bid it will just print this value. 
        //if it is not smaller than any value it will get another position.
        int bidPosition = 1;

        if (checkDuplicate(myBid, bidsList)) {
            System.out.println("*Duplicate Branch*");
            //I decided to use -1 as a flag to check if a bid is duplicate 
            return -1;
        } else {
            System.out.println("*No Duplicate Branch*");
            //now I add myBid to the list of bids because I know it is not
            //a duplicate bid, and I check for its position
            bidsList.add(myBid);
            List<Integer> noDuplicates = removeDuplicates(bidsList);
            //Here I sort the bids starting from the least.
            Collections.sort(noDuplicates);
            //We assign the index of myBid + 1to the bidPosition
            //This works because our array is sorted
            bidPosition = noDuplicates.indexOf(myBid) + 1;
        }
        return bidPosition;
    }

    //This function checks if there is a duplicate bid.
    //If there is a duplicate bid we return true.
    private boolean checkDuplicate(int bid, List<Integer> bidsList) {
        for (int item : bidsList) {
            if (item == bid) {
                return true;
            }
        }
        return false;
    }

    //This function returns a list of bids with no duplicates
    //this makes possible to get the position because it discards the
    //duplicate entries. We want to discard duplicate entries because they
    //can't be the winner of an auction. 
    private List<Integer> removeDuplicates(List<Integer> bidsList) {
        List<Integer> noDuplicates = new ArrayList<>();
        List<Integer> tempList = new ArrayList<Integer>(bidsList);

        for (Integer item : bidsList) {
            //Here I remove the item I'm about to check because if I leave it
            //the checkDuplicate function will alway return true because it will
            //compare with itself
            tempList.remove(item);
            if (!checkDuplicate(item, tempList)) {
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

    public String getProductCategory(String productCat) {
        int cat = Integer.parseInt(productCat);
        String returnString = new String();
        switch (cat) {
            case 1:
                returnString = "Technology";
                break;
            case 2:
                returnString = "Real Estate";
                break;
            case 3:
                returnString = "Automotive";
                break;
            case 4:
                returnString = "Other goods";
                break;
        }
        return returnString;
    }
}
