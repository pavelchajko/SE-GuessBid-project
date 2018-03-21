/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ErvinK
 */
@Entity
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByUserUserEmail", query = "SELECT n FROM Notification n WHERE n.notificationPK.userUserEmail = :userUserEmail"),
    @NamedQuery(name = "Notification.findByAuctionAuctionId", query = "SELECT n FROM Notification n WHERE n.notificationPK.auctionAuctionId = :auctionAuctionId"),
    @NamedQuery(name = "Notification.findByContent", query = "SELECT n FROM Notification n WHERE n.content = :content")})
public class Notification implements Serializable {
    @Column(name = "seen")
    private Boolean seen;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificationPK notificationPK;
    @Size(max = 255)
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "auction_auction_id", referencedColumnName = "auction_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Auction auction;
    @JoinColumn(name = "user_user_email", referencedColumnName = "user_email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Notification() {
    }

    public Notification(NotificationPK notificationPK) {
        this.notificationPK = notificationPK;
    }

    public Notification(String userUserEmail, int auctionAuctionId) {
        this.notificationPK = new NotificationPK(userUserEmail, auctionAuctionId);
    }

    public NotificationPK getNotificationPK() {
        return notificationPK;
    }

    public void setNotificationPK(NotificationPK notificationPK) {
        this.notificationPK = notificationPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationPK != null ? notificationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationPK == null && other.notificationPK != null) || (this.notificationPK != null && !this.notificationPK.equals(other.notificationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.Notification[ notificationPK=" + notificationPK + " ]";
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
    
}
