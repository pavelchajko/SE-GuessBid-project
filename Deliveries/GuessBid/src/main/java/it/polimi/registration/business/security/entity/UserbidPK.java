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
 * @author Egzon
 */
@Embeddable
public class UserbidPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "userbid_auction")
    private int userbidAuction;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_bid_email")
    private String userBidEmail;

    public UserbidPK() {
    }

    public UserbidPK(int userbidAuction, String userBidEmail) {
        this.userbidAuction = userbidAuction;
        this.userBidEmail = userBidEmail;
    }

    public int getUserbidAuction() {
        return userbidAuction;
    }

    public void setUserbidAuction(int userbidAuction) {
        this.userbidAuction = userbidAuction;
    }

    public String getUserBidEmail() {
        return userBidEmail;
    }

    public void setUserBidEmail(String userBidEmail) {
        this.userBidEmail = userBidEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userbidAuction;
        hash += (userBidEmail != null ? userBidEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserbidPK)) {
            return false;
        }
        UserbidPK other = (UserbidPK) object;
        if (this.userbidAuction != other.userbidAuction) {
            return false;
        }
        if ((this.userBidEmail == null && other.userBidEmail != null) || (this.userBidEmail != null && !this.userBidEmail.equals(other.userBidEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.registration.business.security.entity.UserbidPK[ userbidAuction=" + userbidAuction + ", userBidEmail=" + userBidEmail + " ]";
    }
    
}
