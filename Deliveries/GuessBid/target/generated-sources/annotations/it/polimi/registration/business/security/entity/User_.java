package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Notification;
import it.polimi.registration.business.security.entity.Product;
import it.polimi.registration.business.security.entity.Userbid;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-19T03:37:36")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> userCity;
    public static volatile CollectionAttribute<User, Userbid> userbidCollection;
    public static volatile CollectionAttribute<User, Auction> auctionCollection;
    public static volatile CollectionAttribute<User, Notification> notificationCollection;
    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, String> userUsername;
    public static volatile SingularAttribute<User, Integer> userCredit;
    public static volatile CollectionAttribute<User, Product> productCollection;
    public static volatile SingularAttribute<User, String> userEmail;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, String> userGroup;
    public static volatile SingularAttribute<User, String> userSurname;

}