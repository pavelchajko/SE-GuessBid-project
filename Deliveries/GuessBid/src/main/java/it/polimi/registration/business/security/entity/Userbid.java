/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "userbid")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userbid.findAll", query = "SELECT u FROM Userbid u"),
    @NamedQuery(name = "Userbid.findByUserbidBid", query = "SELECT u FROM Userbid u WHERE u.userbidBid = :userbidBid"),
    @NamedQuery(name = "Userbid.findByUserbidAuction", query = "SELECT u FROM Userbid u WHERE u.userbidPK.userbidAuction = :userbidAuction"),
    @NamedQuery(name = "Userbid.findByUserBidEmail", query = "SELECT u FROM Userbid u WHERE u.userbidPK.userBidEmail = :userBidEmail")})
public class Userbid implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserbidPK userbidPK;
    @Column(name = "userbid_bid")
    private Integer userbidBid;
    @JoinColumn(name = "userbid_auction", referencedColumnName = "auction_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Auction auction;
    @JoinColumn(name = "user_bid_email", referencedColumnName = "user_email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Userbid() {
    }

    public Userbid(UserbidPK userbidPK) {
        this.userbidPK = userbidPK;
    }

    public Userbid(int userbidAuction, String userBidEmail) {
        this.userbidPK = new UserbidPK(userbidAuction, userBidEmail);
    }

    public UserbidPK getUserbidPK() {
        return userbidPK;
    }

    public void setUserbidPK(UserbidPK userbidPK) {
        this.userbidPK = userbidPK;
    }

    public Integer getUserbidBid() {
        return userbidBid;
    }

    public void setUserbidBid(Integer userbidBid) {
        this.userbidBid = userbidBid;
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
        hash += (userbidPK != null ? userbidPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userbid)) {
            return false;
        }
        Userbid other = (Userbid) object;
        if ((this.userbidPK == null && other.userbidPK != null) || (this.userbidPK != null && !this.userbidPK.equals(other.userbidPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.Userbid[ userbidPK=" + userbidPK + " ]";
    }
    
}
