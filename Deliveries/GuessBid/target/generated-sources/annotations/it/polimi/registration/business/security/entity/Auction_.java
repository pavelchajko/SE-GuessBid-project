package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.Product;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.business.security.entity.Userbid;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-19T03:37:36")
@StaticMetamodel(Auction.class)
public class Auction_ { 

    public static volatile SingularAttribute<Auction, Integer> auctionId;
    public static volatile CollectionAttribute<Auction, Userbid> userbidCollection;
    public static volatile CollectionAttribute<Auction, Notification> notificationCollection;
    public static volatile SingularAttribute<Auction, Date> auctionEndtime;
    public static volatile SingularAttribute<Auction, Integer> auctionAmount;
    public static volatile SingularAttribute<Auction, User> userAuctionEmail;
    public static volatile SingularAttribute<Auction, String> auctionTitle;
    public static volatile SingularAttribute<Auction, User> userAuctionWinner;
    public static volatile SingularAttribute<Auction, String> auctionDesc;
    public static volatile SingularAttribute<Auction, Boolean> auctionDone;
    public static volatile SingularAttribute<Auction, Product> auctionProduct;

}