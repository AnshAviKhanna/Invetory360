package tables;
public class Product{
    String productId;
    String productName;
    String categoryId;
    String batchId;
    int qty;
    int sellingPrice;int costPrice; String expiryDate; String vendorId; int discount; String dateOfPurchase;

    public Product(){}
    public Product(String productId, String productName, String categoryId, String batchId, int qty, int sellingPrice, int costPrice, String expiryDate, String vendorId, int discount, String dateOfPurchase) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.batchId = batchId;
        this.qty = qty;
        this.sellingPrice = sellingPrice;
        this.costPrice = costPrice;
        this.expiryDate = expiryDate;
        this.vendorId = vendorId;
        this.discount = discount;
        this.dateOfPurchase = dateOfPurchase;
    }


    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDateOfPurchase() {
        return this.dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

}