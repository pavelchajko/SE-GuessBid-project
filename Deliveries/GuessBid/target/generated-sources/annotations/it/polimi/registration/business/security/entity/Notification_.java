package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.NotificationPK;
import it.polimi.registration.business.security.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-19T03:37:36")
@StaticMetamodel(Notification.class)
public class Notification_ { 

    public static volatile SingularAttribute<Notification, NotificationPK> notificationPK;
    public static volatile SingularAttribute<Notification, User> user;
    public static volatile SingularAttribute<Notification, Boolean> seen;
    public static volatile SingularAttribute<Notification, String> content;
    public static volatile SingularAttribute<Notification, Auction> auction;

}