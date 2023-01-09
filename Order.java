/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orders.pkg;

/**
 *
 * @author Bozz
 */
//ΚΛΑΣΗ ΓΙΑ ΜΙΑ ΠΛΗΡΗ ΠΑΡΑΓΓΕΛΙΑ
public class Order {

    private String appId;
    private String orderId;
    private String orderDate;
    private String clientName;
    private String itemName;
    private String UnitsCount;
    private String netItemPrice;
    private String taxPercentage;

    public Order() {
    }

    //CONSTRUCTOR
    public Order(String appId, String orderId, String orderDate, String clientName, String itemName, String UnitsCount, String netItemPrice, String taxPercentage) {
        this.appId = appId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.itemName = itemName;
        this.UnitsCount = UnitsCount;
        this.netItemPrice = netItemPrice;
        this.taxPercentage = taxPercentage;
    }

    //SETTERS
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setUnitsCount(String UnitsCount) {
        this.UnitsCount = UnitsCount;
    }

    public void setNetItemPrice(String netItemPrice) {
        this.netItemPrice = netItemPrice;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    //GETTERS
    public String getAppId() {
        return appId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUnitsCount() {
        return UnitsCount;
    }

    public String getNetItemPrice() {
        return netItemPrice;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    //ΜΕΘΟΔΟΣ ΓΙΑ ΕΓΓΡΑΦΗ ΣΕ ΑΡΧΕΙΑ CSV
    @Override
    public String toString() {
        return appId + ";" + orderId + ";" + orderDate + ";"
                + clientName + ";" + itemName + ";"
                + UnitsCount + ";"
                + netItemPrice + ";" + taxPercentage;
    }

    //ΜΕΘΟΔΟΣ ΓΙΑ ΕΜΦΑΝΙΣΗ ΣΤΗΝ ΟΘΟΝΗ ΤΟΥ ΚΕΝΤΡΙΚΟΥ ΠΑΡΑΘΥΡΟΥ
    public String printScreen() {
        return orderId + "             " + orderDate + "             "
                + clientName + "             " + itemName + "             "
                + UnitsCount + "             "
                + netItemPrice + "$" + "             " + taxPercentage + "%";

    }

}
