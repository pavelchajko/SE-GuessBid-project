/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ErvinK
 */
@Embeddable
public class NotificationPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_user_email")
    private String userUserEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "auction_auction_id")
    private int auctionAuctionId;

    public NotificationPK() {
    }

    public NotificationPK(String userUserEmail, int auctionAuctionId) {
        this.userUserEmail = userUserEmail;
        this.auctionAuctionId = auctionAuctionId;
    }

    public String getUserUserEmail() {
        return userUserEmail;
    }

    public void setUserUserEmail(String userUserEmail) {
        this.userUserEmail = userUserEmail;
    }

    public int getAuctionAuctionId() {
        return auctionAuctionId;
    }

    public void setAuctionAuctionId(int auctionAuctionId) {
        this.auctionAuctionId = auctionAuctionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userUserEmail != null ? userUserEmail.hashCode() : 0);
        hash += (int) auctionAuctionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationPK)) {
            return false;
        }
        NotificationPK other = (NotificationPK) object;
        if ((this.userUserEmail == null && other.userUserEmail != null) || (this.userUserEmail != null && !this.userUserEmail.equals(other.userUserEmail))) {
            return false;
        }
        if (this.auctionAuctionId != other.auctionAuctionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.NotificationPK[ userUserEmail=" + userUserEmail + ", auctionAuctionId=" + auctionAuctionId + " ]";
    }
    
}
