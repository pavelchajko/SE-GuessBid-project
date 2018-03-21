/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egzon
 */
@Entity
@Table(name = "auction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "Auction.findByAuctionId", query = "SELECT a FROM Auction a WHERE a.auctionId = :auctionId"),
    @NamedQuery(name = "Auction.findByAuctionTitle", query = "SELECT a FROM Auction a WHERE a.auctionTitle = :auctionTitle"),
    @NamedQuery(name = "Auction.findByAuctionDesc", query = "SELECT a FROM Auction a WHERE a.auctionDesc = :auctionDesc"),
    @NamedQuery(name = "Auction.findByAuctionAmount", query = "SELECT a FROM Auction a WHERE a.auctionAmount = :auctionAmount"),
    @NamedQuery(name = "Auction.findByAuctionEndtime", query = "SELECT a FROM Auction a WHERE a.auctionEndtime = :auctionEndtime")})
public class Auction implements Serializable {
    @Column(name = "auction_done")
    private Boolean auctionDone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Collection<Notification> notificationCollection;
    @JoinColumn(name = "user_auction_winner", referencedColumnName = "user_email")
    @ManyToOne
    private User userAuctionWinner;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "auction_id")
    private Integer auctionId;
    @Size(max = 45)
    @Column(name = "auction_title")
    private String auctionTitle;
    @Size(max = 200)
    @Column(name = "auction_desc")
    private String auctionDesc;
    @Column(name = "auction_amount")
    private Integer auctionAmount;
    @Column(name = "auction_endtime")
    @Temporal(TemporalType.DATE)
    private Date auctionEndtime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Collection<Userbid> userbidCollection;
    @JoinColumn(name = "auction_product", referencedColumnName = "product_id")
    @ManyToOne
    private Product auctionProduct;
    @JoinColumn(name = "user_auction_email", referencedColumnName = "user_email")
    @ManyToOne(optional = false)
    private User userAuctionEmail;

    public Auction() {
    }

    public Auction(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public String getAuctionDesc() {
        return auctionDesc;
    }

    public void setAuctionDesc(String auctionDesc) {
        this.auctionDesc = auctionDesc;
    }

    public Integer getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(Integer auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    public Date getAuctionEndtime() {
        return auctionEndtime;
    }

    public void setAuctionEndtime(Date auctionEndtime) {
        this.auctionEndtime = auctionEndtime;
    }

    @XmlTransient
    public Collection<Userbid> getUserbidCollection() {
        return userbidCollection;
    }

    public void setUserbidCollection(Collection<Userbid> userbidCollection) {
        this.userbidCollection = userbidCollection;
    }

    public Product getAuctionProduct() {
        return auctionProduct;
    }

    public void setAuctionProduct(Product auctionProduct) {
        this.auctionProduct = auctionProduct;
    }

    public User getUserAuctionEmail() {
        return userAuctionEmail;
    }

    public void setUserAuctionEmail(User userAuctionEmail) {
        this.userAuctionEmail = userAuctionEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auctionId != null ? auctionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.auctionId == null && other.auctionId != null) || (this.auctionId != null && !this.auctionId.equals(other.auctionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.Auction[ auctionId=" + auctionId + " ]";
    }

    public User getUserAuctionWinner() {
        return userAuctionWinner;
    }

    public void setUserAuctionWinner(User userAuctionWinner) {
        this.userAuctionWinner = userAuctionWinner;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    public Boolean getAuctionDone() {
        return auctionDone;
    }

    public void setAuctionDone(Boolean auctionDone) {
        this.auctionDone = auctionDone;
    }
    
}
