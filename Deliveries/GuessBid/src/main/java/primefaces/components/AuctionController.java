/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefaces.components;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "auctionController")
@RequestScoped
public class AuctionController {

    @EJB
    UserManager um;

    @EJB
    private AuctionService auctionService;

    @PersistenceContext
    EntityManager em;

    private Date endDate;
    private List<Product> userProducts;
    private List<Product> allProductsList;
    private List<String> categories;
    private String selectedCategory;
    private String selectedProductName;
    private String selectedProductID;
    private Auction selectedAuction;
    
    @PostConstruct
    public void init() {
        allProductsList = getAllProductsList();
        selectedAuction = new Auction();
        selectedAuction.setAuctionProduct(new Product());

        userProducts = new ArrayList<Product>();
        userProducts = getProducts();

        categories = new ArrayList<String>();
        categories.add("Technology");
        categories.add("Real Estate");
        categories.add("Automotive");
        categories.add("Other goods");

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    public String getSelectedProductName() {
        return selectedProductName;
    }

    public void setSelectedProductName(String selectedProductName) {
        this.selectedProductName = selectedProductName;
    }

    public String getSelectedProductID() {
        return selectedProductID;
    }

    public void setSelectedProductID(String selectedProductID) {
        this.selectedProductID = selectedProductID;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Auction getSelectedAuction() {
        return selectedAuction;
    }

    public void setSelectedAuction(Auction selectedAuction) {
        this.selectedAuction = selectedAuction;
    }

    public List<Product> getProducts() {
        List<Product> tempProducts = new ArrayList<Product>();
        String theEmail = um.getLoggedUserEmail();

        for (Product p : allProductsList) {
            if (theEmail.equals(p.getUserOwnerEmail().getUserEmail())) {
                tempProducts.add(p);
            }
        }
        return tempProducts;
    }

    public void setProducts(List<Product> products) {
        this.userProducts = products;
    }

    public List<Product> getAllProductsList() {
        List<Product> products = em.createNamedQuery("Product.findAll").getResultList();
        return products;
    }
    
    public List<Auction> getAllAuctionsList() {
        List<Auction> auctions = em.createNamedQuery("Auction.findAll").getResultList();
        return auctions;
    }

    private Product getProductFromID(String productID) {
        Product tempProduct = new Product();
        for (Product pr : allProductsList) {
            if (String.valueOf(pr.getProductId()).equals(productID)) {
                tempProduct = pr;
            }
        }
        return tempProduct;
    }

    public String saveAuctionToDB() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();

        if (checkEmptyFields()) {
            showMessage("Enter all the fields");
            return "createAuction?faces-redirect=true";
        } else {
            if (endDate.after(today)) {
                //selected Date is in the future
                Product tempProduct = getProductFromID(selectedProductID);
                selectedAuction.setAuctionProduct(tempProduct);
                selectedAuction.setAuctionEndtime(endDate);
                selectedAuction.setAuctionAmount(0);
                selectedAuction.setAuctionDone(Boolean.FALSE);
                selectedAuction.setUserAuctionEmail(um.getLoggedUser());
                auctionService.createAuction(selectedAuction);
                if (auctionService.checkIfAuctionExists(selectedAuction)) {
                    showMessage("Auction is added successfully!");
                } else {
                    showMessage("An error occurred, try again!");
                }
            } else {
                //Selected Date is today or before today
                showMessage("You have to select a day in the future!");
                return "createAuction?faces-redirect=true";
            }
            return "index?faces-redirect=true";
        }

    }

    private boolean checkEmptyFields() {
        return selectedAuction.getAuctionTitle() == null
                || selectedAuction.getAuctionTitle() == null
                || selectedProductID == null
                || endDate == null;
    }

    public String saveProductToDB() {
        if (selectedProductName != null && selectedCategory != null) {
            int tempCatNumber = 1;
            for (int i = 0; i < categories.size(); i++) {
                if (selectedCategory.equals(categories.get(i))) {
                    tempCatNumber = i+1;
                }
            }

            Product tempProduct = new Product();
            tempProduct.setProductCategory(tempCatNumber);
            tempProduct.setProductName(selectedProductName);
            tempProduct.setUserOwnerEmail(um.getLoggedUser());

            auctionService.createProduct(tempProduct);
            if (auctionService.checkIfProductExists(tempProduct)) {
                showMessage("Your product is added successfully!");
            } else {
                showMessage("An error occurred, try again!");
            }
            return "createAuction?faces-redirect=true";
        } else {
            showMessage("Enter both fields!");
            return "createAuction?faces-redirect=true";
        }

    }
    
    private void showMessage(String msg) {
        GrowlView gv = new GrowlView();
        gv.setMessage(msg);
        gv.saveMessage();
    }
    
    

}
