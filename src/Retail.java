import java.util.*;
import tables.*;

public interface Retail 
{
    public ArrayList<Product> getProductsByCategoryName(String categoryName,String sortBy);
    public ArrayList<Product> getAllProductList();
    public ArrayList<Category> getAllCategoryList();
    public String checkCategoryName(String categoryName);
    public String checkProductName(String productName);
    public boolean checkQty(String productname,int qty);
    public boolean checkCustomer(String customerMob);
    public boolean checkVendor(String vendorMob);
    public boolean checkOrderId(String orderId);
    public void addCustomer(String customerName,String customerMob,String customerAddress);
    public String getNewCatId();
    public void addCategory(Category c);
    public String getNewVendorId();
    public void addVendor(Vendor v);
    public String addOrder();
    public String getNewId(String prevId, String idType);
    public ArrayList<Product> getProductsByExpiryDate(int months,String orderBy); 
    public ArrayList<String> buyProduct(String productName, int quantity, String custormerMob, String orderId);
    public ArrayList<ArrayList<String>> getSaleByCategory(String orderBy);
    public String getVendorIdFromMobile(String mob);
    public ArrayList<String> getIdDetailsforName(String prodName,String catName);
    public String updateTheInventory(Product p);
    public void discount_product(String name, String batchId, int discount);
    public void discount_expiry(int months, int discount_period);
    public boolean matchCategoryAndProduct(String categoryName, String productName);
    public void discount_category(String name,int discount);
    public int checkNameAndBatch(String productName,String batchId);
    public void removeProduct(String productName,String batchId);
    public ArrayList<Integer> getLossSuffered (String month);
}
