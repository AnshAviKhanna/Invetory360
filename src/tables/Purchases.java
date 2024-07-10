package tables;
public class Purchases {
    String orderId;
    int qty;
    int finalPrice;
    String dateOfPurchase;
    public Purchases(){}
    public Purchases(String orderId, int qty, int finalPrice, String dateOfPurchase){
        this.orderId=orderId;
        this.qty=qty;
        this.finalPrice=finalPrice;
        this.dateOfPurchase=dateOfPurchase;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }
    public int getQty() {
        return qty;
    }
    public String getDateOfPurchase() {
        return dateOfPurchase;
    }
    public String getOrderId() {
        return orderId;
    }
    public int getFinalPrice() {
        return finalPrice;
    }
}
