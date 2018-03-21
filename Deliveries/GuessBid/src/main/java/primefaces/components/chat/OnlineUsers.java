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
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.Startup;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Egzon
 */
@ManagedBean
@ApplicationScoped
public class OnlineUsers implements Serializable {

    private List<Pair<Integer, List<String>>> onlineUsers;

    public OnlineUsers() {
        onlineUsers = new ArrayList<>();
    }

    public void getOnlineUsersWeb(Auction auc) {
        RequestContext ctxUsers = RequestContext.getCurrentInstance();

        List<String> tempUsers = new ArrayList();
        List<String> onlineUsersList = new ArrayList();
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            if (pair.getKey() == auc.getAuctionId()) {
                tempUsers = pair.getValue();
                break;
            }
        }

        for (String userName : tempUsers) {
            if (!userName.equals("none")) {
                onlineUsersList.add(userName);
            }

        }
        boolean userOnlineStatus = false;

        if (!(onlineUsersList.size() == 0)) {
            userOnlineStatus = true;
            String jsonUsers = new Gson().toJson(onlineUsersList);
            ctxUsers.addCallbackParam("onlineUsers", jsonUsers);
        }
        ctxUsers.addCallbackParam("userOnlineStatus", userOnlineStatus);

    }

    public List<Pair<Integer, List<String>>> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<Pair<Integer, List<String>>> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public void newOnlineUser(Integer auc, String user) {
        boolean aucExists = false;
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            if (pair.getKey() == auc) {
                aucExists = true;
            }
        }
        if (!aucExists) {
            List<String> tempList = new ArrayList();
            Pair<Integer, List<String>> temp = new Pair<>(auc, tempList);

            temp.getValue().add(user);
            onlineUsers.add(temp);
            System.out.println("Chat for auction: " + auc + " created, and user " + user + "entered the chat.");
        } else {
            for (Pair<Integer, List<String>> pair : onlineUsers) {
                if (pair.getKey() == auc) {
                    boolean userAlreadyIn = false;
                    for (String usr : pair.getValue()) {
                        if (usr == user) {
                            userAlreadyIn = true;
                        }
                    }
                    if (!userAlreadyIn) {
                        System.out.println("User " + user + " entered the chat in auction: " + auc);
                        pair.getValue().add(user);
                    }
                }
            }
        }
    }

    public void logInChat(Auction auc, User usr) {
        newOnlineUser(auc.getAuctionId(), usr.getUserEmail());
    }

    public void logOutChat(Auction auc, User usr) {

        String userEmail = usr.getUserEmail();
        System.out.println("Before: ");
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            if (pair.getKey() == auc.getAuctionId()) {
                System.out.println("Auction: " + pair.getKey());
                for (String us : pair.getValue()) {
                    System.out.println("User: " + us);
                }
            }
        }
        Integer indexofUser = 0;
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            if (pair.getKey() == auc.getAuctionId()) {

                    for(int i=0;i<pair.getValue().size();i++){
                        if(userEmail.equals(pair.getValue().get(i))){
                            //pair.getValue().set(i, "none");
                            pair.getValue().remove(i);
                        }
                    }
//                for (String us : pair.getValue()) {
//                    indexofUser++;
//                    System.out.println("UseRRr: " + us);
//                    if (userEmail.equals(us)) {
//                        us = "none";
//                    }
//                }
            }
        }

        System.out.println("After: ");
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            if (pair.getKey() == auc.getAuctionId()) {
                System.out.println("Auction: " + pair.getKey());
                for (String us : pair.getValue()) {
                    System.out.println("User: " + us);
                }
            }

        }
    }

    public void log(Auction auc) {
        System.out.println("OnlineUsers size: " + onlineUsers.size());
        //System.out.println("Online users in auction :" + auc.getAuctionId());
        for (Pair<Integer, List<String>> pair : onlineUsers) {
            System.out.println("Auction: " + pair.getKey());
            for (String user : pair.getValue()) {
                System.out.print(user + " ");
            }
        }

    }
}
