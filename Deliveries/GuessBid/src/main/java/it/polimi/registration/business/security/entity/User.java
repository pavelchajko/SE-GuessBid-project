/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.control.PasswordEncrypter;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserCity", query = "SELECT u FROM User u WHERE u.userCity = :userCity"),
    @NamedQuery(name = "User.findByUserCredit", query = "SELECT u FROM User u WHERE u.userCredit = :userCredit"),
    @NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "User.findByUserGroup", query = "SELECT u FROM User u WHERE u.userGroup = :userGroup"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "User.findByUserSurname", query = "SELECT u FROM User u WHERE u.userSurname = :userSurname"),
    @NamedQuery(name = "User.findByUserUsername", query = "SELECT u FROM User u WHERE u.userUsername = :userUsername")})
public class User implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Notification> notificationCollection;
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "user_city")
    private String userCity;
    @Column(name = "user_credit")
    private Integer userCredit;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_email")
    private String userEmail;
    @Size(max = 255)
    @Column(name = "user_group")
    private String userGroup;
    @Size(max = 255)
    @Column(name = "user_name")
    private String userName;
    @Size(max = 255)
    @Column(name = "user_password")
    private String userPassword;
    @Size(max = 255)
    @Column(name = "user_surname")
    private String userSurname;
    @Size(max = 255)
    @Column(name = "user_username")
    private String userUsername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userOwnerEmail")
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Userbid> userbidCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAuctionEmail")
    private Collection<Auction> auctionCollection;

    public User() {
    }

    public User(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public Integer getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(Integer userCredit) {
        this.userCredit = userCredit;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = PasswordEncrypter.encryptPassword(userPassword);
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<Userbid> getUserbidCollection() {
        return userbidCollection;
    }

    public void setUserbidCollection(Collection<Userbid> userbidCollection) {
        this.userbidCollection = userbidCollection;
    }

    @XmlTransient
    public Collection<Auction> getAuctionCollection() {
        return auctionCollection;
    }

    public void setAuctionCollection(Collection<Auction> auctionCollection) {
        this.auctionCollection = auctionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userEmail != null ? userEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userEmail == null && other.userEmail != null) || (this.userEmail != null && !this.userEmail.equals(other.userEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.User[ userEmail=" + userEmail + " ]";
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }
    
}
