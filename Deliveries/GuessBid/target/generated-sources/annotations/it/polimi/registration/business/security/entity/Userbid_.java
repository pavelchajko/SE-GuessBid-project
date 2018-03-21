package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.business.security.entity.UserbidPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-19T03:37:36")
@StaticMetamodel(Userbid.class)
public class Userbid_ { 

    public static volatile SingularAttribute<Userbid, Integer> userbidBid;
    public static volatile SingularAttribute<Userbid, UserbidPK> userbidPK;
    public static volatile SingularAttribute<Userbid, User> user;
    public static volatile SingularAttribute<Userbid, Auction> auction;

}