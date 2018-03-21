/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import primefaces.components.GrowlView;

/**
 *
 * @author miglie
 */
@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    private UserManager um;

    private User user;

    public RegistrationBean() {
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String register() {
        List<User> tempUsers = um.getAllUsers();
        for(User us : tempUsers){
            if(user.getUserEmail().equals(us.getUserEmail())){
                showMessage("A user with the same email address already exists!");
                return "register?faces-redirect=true";
            }
        }
        um.save(user);
        return "user/index?faces-redirect=true";
    }
    
    private void showMessage(String msg) {
        GrowlView gv = new GrowlView();
        gv.setMessage(msg);
        gv.saveMessage();
    }

}
