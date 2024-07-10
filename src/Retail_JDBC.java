import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import tables.*;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class Retail_JDBC implements Retail 
{
    Connection dbConnection;
    public Retail_JDBC(Connection dbconn){
    // JDBC driver name and database URL
    //  Database credentials
    dbConnection = dbconn;
    }

    public boolean checkQty(String productname,int qty){
        String sql;
        Statement stmt=null;
        int quantity=0;
        try {
			stmt = dbConnection.createStatement();
            sql="select qty from Product where productName='"+productname+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                quantity=quantity+rs.getInt(1);
            }
            if(qty<=quantity) return true;
            else return false;
            
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    
    
    public String checkProductName(String productName){
        String sql;
        Statement stmt=null;
        String name=null;
        try {
			stmt = dbConnection.createStatement();
            sql="select productName from Product where productName='"+productName+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            name=rs.getString(1);
            return name;
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("Product does not exist!");

        }
        return name;
    }

    public String checkReturnProductName(String productName){
        String sql;
        Statement stmt=null;
        String name=null;
        try {
			stmt = dbConnection.createStatement();
            sql="select productName from Product where productName='"+productName+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            name=rs.getString(1);
            return name;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("This product cannot be returned!");

        }
        return name;
    }

    public String checkCategoryName(String categoryName)
    {
        String sql;
        Statement stmt=null;
        String name=null;
        try 
        {
			stmt = dbConnection.createStatement();
            sql="select categoryName from Category where categoryName='"+categoryName+"'";
            //sql="select * from Product";
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            name=rs.getString(1);
            return name;
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
        return name;
    }
    public ArrayList<String> buyProduct(String productName, int quantity,String customerMob,String orderId){
        
        ArrayList<String> s=new ArrayList<>();
        String sql;
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            // System.out.println(productName.size());
            // int i=0;
            // int size=productName.size();
            // while(i<size){
            // for(int i=0;i<productName.size();i++){
                // System.out.println(i);
                int finalPrice=0;
                
                // ResultSet rs;
                // System.out.println(customerMob);
                sql = "select sum(qty), count(qty) from Product where productName='"+productName+"' and qty>0";
                
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                int totalQuantity = rs.getInt(1);
                // System.out.println(totalQuantity);
                if(quantity>totalQuantity){
                    return null;
                }
                
                sql = "select customerId from Customer where customerMobile='"+customerMob+"'";
                rs = stmt.executeQuery(sql);
                rs.next();
                String pattern = "MM/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                String customerId = rs.getString(1);
                int tempQuantity = quantity;
                while(tempQuantity>0){
                    // String tempSql = "select * from Product where productId = '"+productName+"'";
                    // rs = stmt.executeQuery(tempSql);
                    // rs.next();
                    String sql5 = "select * from Product where productName='"+productName+"' and qty>0";
                    rs = stmt.executeQuery(sql5);
                    rs.next();
                    int discount = rs.getInt(10);
                    String productId = rs.getString(1); 
                    String batchId = rs.getString(4);
                    finalPrice = quantity*rs.getInt(6);
                    if(discount!=0){
                        finalPrice = finalPrice - finalPrice*rs.getInt(10)/100;
                    }
                    int availableQty = rs.getInt(5);
                    if(availableQty>=tempQuantity){
                        int newQty = availableQty-tempQuantity;
                        // System.out.println(tempQuantity+"if");
                        String sql2 = "update Product set qty="+newQty+" where productId='"+productId+"' and batchId='"+batchId+"' and qty>0";
                        stmt.executeUpdate(sql2);
                        date = date.toString();
                        String sql3 = "select categoryId from Product where productId='"+productId+"' and qty>0";
                        ResultSet rs6 = stmt.executeQuery(sql3);
                        rs6.next();
                        String categoryId = rs6.getString(1);
                        // System.out.println(categoryId);
                        sql2 = "insert into Purchases VALUES ('"+productId+"','"+batchId+"','"+categoryId+"','"+orderId+"',"+quantity+",'"+customerId+"',"+finalPrice+",'"+date+"')";
                        // System.out.println("sdfsdf"+orderId);
                        stmt.execute(sql2);
                        s.add(productId+","+batchId+","+categoryId+","+orderId+","+quantity+","+customerId+","+finalPrice+","+date);
                        // purchasedList.add(productId+","+batchId+","+orderId+","+quantity+","+customerId+","+finalPrice+","+date);
                        // System.out.println(purchasedList.size());
                        tempQuantity = 0;
                        // break;
                    }
                    else{
                        int newQty = 0;
                        System.out.println(tempQuantity+"else");
                        String sql2 = "update Product set qty='"+newQty+"'"+"where productId='"+productId+"'and batchId='"+batchId+"' and qty>0";
                        stmt.executeUpdate(sql2);
                        date = date.toString();
                        String sql3 = "select categoryId from Product where productId='"+productId+"' and qty>0";
                        ResultSet rs6 = stmt.executeQuery(sql3);
                        rs6.next();
                        String categoryId = rs6.getString(1);
                        System.out.println(categoryId);
                        sql2 = "insert into Purchases VALUES ('"+productId+"','"+batchId+"','"+categoryId+"','"+orderId+"',"+quantity+",'"+customerId+"',"+finalPrice+",'"+date+"')";
                        // System.out.println("sdfsdf"+orderId);
                        stmt.execute(sql2);
                        s.add(productId+","+batchId+","+categoryId+","+orderId+","+quantity+","+customerId+","+finalPrice+","+date);
                        // purchasedList.add(productId+","+batchId+","+orderId+","+quantity+","+customerId+","+finalPrice+","+date);
                        // System.out.println(purchasedList.size());
                        tempQuantity = quantity - availableQty;
                    }
                    // String deleteQuery = "delete from Product where qty=0";
                    // stmt.executeUpdate(deleteQuery);
                }
            // System.out.println(s.size()+"sdf");
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return s;
    }
    public ArrayList<Product> getProductsByCategoryName(String categoryName, String sortBy)
    {
        String sql;
        Statement stmt=null;
        ArrayList<Product> productList=new ArrayList<Product>();

        try 
        {
			stmt = dbConnection.createStatement();
            sql="select * from Product p inner join Category c  on c.categoryId=p.categoryId and c.categoryName='"+categoryName+"' order by "+sortBy;
            //sql="select * from Produc;
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                Product p=new Product();
				String id  = rs.getString(1);
				String name = rs.getString(2);
                String categoryId=rs.getString(3);
                String batchId=rs.getString(4);
                int qty=rs.getInt(5);
                int sellingPrice=rs.getInt(6);
                int costPrice=rs.getInt(7);
                String expiryDate=rs.getString(8);
                String vendorId=rs.getString(9);
                int discount=rs.getInt(10);
                String dateOfPurchase=rs.getString(11);
                p.setBatchId(batchId);
                p.setProductId(id);
                p.setProductName(name);
                p.setCategoryId(categoryId);
                p.setCostPrice(costPrice);
                p.setDateOfPurchase(dateOfPurchase);
                p.setDiscount(discount);
                p.setVendorId(vendorId);
                p.setExpiryDate(expiryDate);
                p.setSellingPrice(sellingPrice);
                p.setQty(qty);
                productList.add(p);
			}
            return productList;
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return productList;
    }
    public ArrayList<Product> getProductsByExpiryDate(int months,String orderBy)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
	    Date date = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,months);
        Date newDate=c.getTime();
        String mm=formatter.format(newDate).substring(0,2);
        String yy=formatter.format(newDate).substring(3,5);
	    // System.out.println(mm+" "+yy); 
        String sql;
        Statement stmt=null;
        ArrayList<Product> productList=new ArrayList<Product>();

        try 
        {
			stmt = dbConnection.createStatement();
            // select * from Product where expiryDate<"06/25" AND expiryDate like "%25";
            // sql="select * from Product where expiryDate<"06/25" AND expiryDate like "%25""
            sql="select * from Product where (substring(expiryDate,1,2)<'"+mm+"' and substring(expiryDate,4,2)='"+yy+"') or substring(expiryDate,4,2)<'"+yy+"' order by "+orderBy;
            // sql="select * from Product p inner join Category c  on c.categoryId=p.categoryId and c.categoryName='"+categoryName+"' order by "+sortBy;
            //sql="select * from Product";
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                Product p=new Product();
				String id  = rs.getString(1);
				String name = rs.getString(2);
                String categoryId=rs.getString(3);
                String batchId=rs.getString(4);
                int qty=rs.getInt(5);
                int sellingPrice=rs.getInt(6);
                int costPrice=rs.getInt(7);
                String expiryDate=rs.getString(8);
                String vendorId=rs.getString(9);
                int discount=rs.getInt(10);
                String dateOfPurchase=rs.getString(11);
                p.setBatchId(batchId);
                p.setProductId(id);
                p.setProductName(name);
                p.setCategoryId(categoryId);
                p.setCostPrice(costPrice);
                p.setDateOfPurchase(dateOfPurchase);
                p.setDiscount(discount);
                p.setVendorId(vendorId);
                p.setExpiryDate(expiryDate);
                p.setSellingPrice(sellingPrice);
                p.setQty(qty);
                productList.add(p);
			}
            return productList;
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return productList;
    }
    public boolean checkCustomer(String customerMob)
    {
        String sql;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            sql="select customerName from Customer where customerMobile='"+customerMob+"'";
            //sql="select * from Product";
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(rs.getString(1).length()!=0)
                return true;
            else 
                return false;
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
            return false;
        }

    }
    public boolean checkVendor(String vendorMob)
    {
        String sql;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            // System.out.println(vendorMob);
            sql="select vendorName from Vendor where vendorMobile='"+vendorMob+"'";
            //sql="select * from Product";
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(rs.getString(1).length()!=0)
                return true;
            else 
                return false;
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
            return false;
        }

    }
    public void addCustomer(String customerName,String customerMob,String customerAddress)
    {
        String sql1,sql2="";
        Statement stmt=null;
        try 
        {
            
			stmt = dbConnection.createStatement();
            sql1="select max(customerId) from Customer";

			ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            String maxCustomerId = rs.getString(1);
            if(maxCustomerId.length()==0) // r.next mei doubt hai...check krlenge
            {
                sql2="insert into Customer values ('c00001','"+customerName+"','"+customerMob+"','"+customerAddress+"')";
                stmt.execute(sql2);
            }
            else
            {
                String customerID="c"+getNewId(rs.getString(1),"customer");
                System.out.println(customerID+" "+customerAddress+" "+customerName+" "+customerMob);
                sql2="insert into Customer values ('"+customerID+"','"+customerName+"','"+customerMob+"','"+customerAddress+"')";
                stmt.execute(sql2);
            }          
        }
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
    }
    public String getNewCatId(){
        String sql1;
        Statement stmt=null;
        try 
        {
            stmt = dbConnection.createStatement();
            sql1="select max(categoryId) from Category";
            // System.out.println("in add");

			ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            String maxCategoryId = rs.getString(1);
            // System.out.println(maxCategoryId);
            if(maxCategoryId.length()==0) // r.next mei doubt hai...check krlenge
            {
                return "cat00001";
            }
            else
            {
                // System.out.println("kasdjf");
                String categoryId="cat"+getNewId(maxCategoryId,"Category");
                // System.out.println(customerID+" "+customerAddress+" "+customerName+" "+customerMob);

                // sql2="insert into Category VALUES ('"+categoryId+"','"+categoryName+"')";
                // stmt.execute(sql2);
                // System.out.println("er");
                return categoryId;
            }          
        }
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
        return "";
    }
    public void addCategory(Category c){
        String sql2="";
        Statement stmt=null;
        try 
        {
            stmt = dbConnection.createStatement();
                sql2="insert into Category VALUES ('"+c.getId()+"','"+c.getName()+"')";
                stmt.execute(sql2);
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
    }
    public String getNewVendorId(){
        String sql1;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            sql1="select max(vendorId) from Vendor";
			ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            String maxVendorId = rs.getString(1);
            if(maxVendorId.length()==0) // r.next mei doubt hai...check krlenge
            {
                return "v00001";
            }
            else
            {
                // System.out.println("hhh");
                String vendorID="v"+getNewId(rs.getString(1),"vendor");
                return vendorID;
                // System.out.println("hh3h");
                // boolean res = stmt.execute(sql2);
                // System.out.println(res+" this is result");
            }
            // boolean check = stmt.execute(sql2);
            // System.out.println(check);
            
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
        return "";
    }
    public void addVendor(Vendor v)
    {
        String sql2="";
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
                sql2="insert into Vendor values ('"+v.getId()+"','"+v.getName()+"','"+v.getMob()+"','"+v.getAdd()+"')";
                stmt.execute(sql2);
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }

    }
    public String addOrder()
    {
        String sql1,sql2;
        Statement stmt=null;
        String orderID="";
        try 
        {
			stmt = dbConnection.createStatement();
            sql1="select * from Purchases";
			ResultSet rs1 = stmt.executeQuery(sql1);
            // System.out.println("sdfyyyy");
            // boolean notEmpty=rs1.next();
            // System.out.println(notEmpty);
            if(!rs1.next()) // rs.next mei doubt hai...check krlenge
                orderID="o1";
            else 
            {
                sql2="select max(orderId) from Purchases";
			    ResultSet rs2 = stmt.executeQuery(sql2);
                // System.out.println(rs2.getString(1));
                rs2.next();
                orderID="o"+getNewId(rs2.getString(1),"order");
            }
            // System.out.println(orderID);
            return orderID;
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
        return orderID;

    }
    public String getNewId(String prevId,String idType)
    {
        // System.out.println(prevId+" asdf");
        String newId="";
        int prev=0;
        if(idType.equals("Category")){
            prev=Integer.parseInt(prevId.substring(3));
            prev+=1;
        }
        else{
            prev=Integer.parseInt(prevId.substring(1));
            prev+=1;
        }
        // System.out.println(prev);
        int len=(String.valueOf(prev)).length();
        if(idType.equals("customer")||idType.equals("vendor")||idType.equals("Product")||idType.equals("Category")||idType.equals("exchange"))
        {
            for(int i=0;i<5-len;i++)
            {
                newId+="0";
            }
        }
        newId=newId+String.valueOf(prev);
        // System.out.println(newId+" wer");
        return newId;
    }
    public ArrayList<ArrayList<String>> getSaleByCategory(String orderBy){
        String sql;
        Statement stmt=null;
        ArrayList<ArrayList<String>> Sales=new ArrayList<ArrayList<String>>();
        // ArrayList<Product> productList=new ArrayList<Product>();

        try 
        {
			stmt = dbConnection.createStatement();
            sql="select c.categoryName as 'CategoryName', sum(p.finalPrice) as 'Sales', sum(p.qty) as Quantity from Category c inner join Purchases p on p.categoryId = c.categoryId group by c.categoryId order by sum(p."+orderBy+")";
            //sql="select * from Product";
            //sql="select * from Product p, Category c where c.categoryName='"+categoryName+"' and p.categoryId=c.categoryId order by "+sortBy;
			ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                String categoryName = rs.getString(1);
                int finalPrice = rs.getInt(2);
                int quantity = rs.getInt(3);
                ArrayList<String> tempString = new ArrayList<String>();
                tempString.add(categoryName);
                tempString.add(String.valueOf(finalPrice));
                tempString.add(String.valueOf(quantity));
                Sales.add(tempString);
			}
            return Sales;
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return Sales;
    }
    public String getVendorIdFromMobile(String mob){
        String sql,productId="",batchId="";
        Statement stmt=null;
        try 
        {
            stmt = dbConnection.createStatement();
            sql="select * from Vendor where vendorMobile='"+mob+"'";
            ResultSet rs1 = stmt.executeQuery(sql);
            rs1.next();
            String vendorId = rs1.getString(1);
            return vendorId;
        }
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "";
    }
    public ArrayList<String> getIdDetailsforName(String prodName,String catName){
        String sql,productId="",batchId="";
        Statement stmt=null;
        try 
        {
            ArrayList<String> Ids=new ArrayList<>();
			stmt = dbConnection.createStatement();
            sql = "select max(batchId),productId,productName from Product group by productId,productName having productName='"+prodName+"'";
            ResultSet rs2 = stmt.executeQuery(sql);
            boolean temp = rs2.next();
            if(temp)
            {
                productId = rs2.getString(2);
                batchId = rs2.getString(1);
            }
            if(productId.length()==0){
               //*******considering there is atleast one product in the table.****
                sql = "select max(productId) from Product";
                ResultSet rs3 = stmt.executeQuery(sql);
                rs3.next();
                productId="p"+getNewId(rs3.getString(1),"Product");
                batchId = "b1";
            }
            else{
                int newNumber = Integer.parseInt(batchId.substring(1))+1;
                batchId = "b"+newNumber;
            }
            sql = "select categoryId from Category where categoryName='"+catName+"'";
            ResultSet rs4 = stmt.executeQuery(sql);
            rs4.next();
            String categoryId = rs4.getString(1);
            Ids.add(productId);
            Ids.add(batchId);
            Ids.add(categoryId);
            return Ids;
        }
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public String updateTheInventory(Product p){
        String sql;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            // System.out.println(p.getProductId()+"','"+p.getProductName()+"','"+p.getCategoryId()+"','"+p.getBatchId()+"',"+p.getQty()+","+p.getSellingPrice()+","+p.getCostPrice()+",'"+p.getExpiryDate()+"','"+p.getVendorId()+"',"+p.getDiscount()+",'"+p.getDateOfPurchase());
            sql = "insert into Product VALUES ('"+p.getProductId()+"','"+p.getProductName()+"','"+p.getCategoryId()+"','"+p.getBatchId()+"',"+p.getQty()+","+p.getSellingPrice()+","+p.getCostPrice()+",'"+p.getExpiryDate()+"','"+p.getVendorId()+"',"+p.getDiscount()+",'"+p.getDateOfPurchase()+"')";
            // System.out.println(sql);
            stmt.executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "Sales";
    }    
    
    public void discount_category(String name,int discount)
    {
        String sql;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            sql="update Product inner join Category on Product.categoryId=Category.categoryId set discount="+discount+" where Category.categoryName='"+name+"'";
            stmt.executeUpdate(sql);
        }
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
    }
    public void discount_product(String pName,String batchId,int discount)
    {
        String sql;
        Statement stmt=null;
        try 
        {
			stmt = dbConnection.createStatement();
            sql="update Product set discount="+discount+" where productName='"+pName+"' and batchId='"+batchId+"'";
            stmt.executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
    }

    public void discount_expiry(int months,int discount)
    {
        String sql;
        Statement stmt=null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
	    Date date = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,months);
        Date newDate=c.getTime();
        String mm=formatter.format(newDate).substring(0,2);
        String yy=formatter.format(newDate).substring(3,5);
        try 
        {
			stmt = dbConnection.createStatement();
            sql="update Product set discount="+discount+" where (substring(expiryDate,1,2)<'"+mm+"' and substring(expiryDate,4,2)='"+yy+"') or substring(expiryDate,4,2)<'"+yy+"'";
            stmt.executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
            // System.out.println("Category does not exist!");
        }
    }

    public boolean checkOrderId(String orderId)
    {
        String sql;
        Statement stmt=null;
        try {
			stmt = dbConnection.createStatement();
            sql="select orderId from Purchases where orderId='"+orderId+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if(rs.getString(1).length()!=0)
            return true;
            else 
            return false;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("No such order exists. Please give valid Order ID");

        }
        return false;
    }

    public int checkNameAndBatch(String productName,String batchId){
        String sql1;
        Statement stmt=null;
        try {
			stmt = dbConnection.createStatement();
            sql1="select productId from Product where productName='"+productName+"' and batchId='"+batchId+"'";
            ResultSet rs = stmt.executeQuery(sql1);
            if(!rs.next()){
                System.out.println("No such product "+productName+ " exists in the batch "+batchId+". Please enter valid information.");
                return 0;
            }
            else
            {
                return 1;
            }
            

        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
        }
        return 0;
    }

    public void removeProduct(String productName,String batchId)
    {
        String sql1,sql2,sql3,today;
        String productId="";
        int qty,costPrice;
        Statement stmt=null;
        String pattern = "MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        try {
			stmt = dbConnection.createStatement();
            // dbConnection.setAutoCommit(true);
            sql1="select productId,qty,costPrice from Product where productName='"+productName+"' and batchId='"+batchId+"'";
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            productId=rs.getString(1);
            qty=rs.getInt(2);
            costPrice=rs.getInt(3);
            System.out.println(productName);
            System.out.println(batchId);
            sql2="delete from Product where productName='"+productName+"' and batchId='"+batchId+"'";
            stmt.executeUpdate(sql2);
            System.out.println(sql2);
            sql3="insert into Removed_products VALUES ('"+productId+"','"+batchId+"','"+productName+"',"+qty+","+costPrice+",'"+date+"')";
            stmt.executeUpdate(sql3);

        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("SQLException: " + e.getMessage());
		    // System.out.println("SQLState: " + e.getSQLState());
		    // System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public ArrayList<Integer> getLossSuffered (String month)
    {
        String sql;
        int loss=0;
        int qty=0;
        ArrayList<Integer> result=new ArrayList<>();
        Statement stmt=null;
        try {
			stmt = dbConnection.createStatement();
            sql="select sum(costPrice*qty),sum(qty) from Removed_products where substring(removeDate,1,5)='"+month+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next())
            System.out.println("No loss suffered in this month");
            else
            loss=rs.getInt(1); 
            qty=rs.getInt(2);
            result.add(loss);
            result.add(qty);
            return result;
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("No loss suffered in this month");
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
        }
        return result;
    }

    public ArrayList<Product> getAllProductList(){
        ArrayList<Product> listOfProducts = new ArrayList<>();
        String sql;
        Statement stmt=null;
        try {
            stmt = dbConnection.createStatement();
            sql="select productName, batchId, qty, sellingPrice, costPrice, expiryDate, discount, dateOfPurchase from Product";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Product p = new Product();
				String name = rs.getString(1);
                // String categoryId=rs.getString(3);
                String batchId=rs.getString(2);
                int qty=rs.getInt(3);
                int sellingPrice=rs.getInt(4);
                int costPrice=rs.getInt(5);
                String expiryDate=rs.getString(6);
                // String vendorId=rs.getString(9);
                int discount=rs.getInt(7);
                String dateOfPurchase=rs.getString(8);
                p.setBatchId(batchId);
                // p.setProductId(id);
                p.setProductName(name);
                // p.setCategoryId(categoryId);
                p.setCostPrice(costPrice);
                p.setDateOfPurchase(dateOfPurchase);
                p.setDiscount(discount);
                // p.setVendorId(vendorId);
                p.setExpiryDate(expiryDate);
                p.setSellingPrice(sellingPrice);
                p.setQty(qty);
                listOfProducts.add(p);
            }
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return listOfProducts;
    }

    public ArrayList<Category> getAllCategoryList(){
        ArrayList<Category> listOfCategories = new ArrayList<>();
        String sql;
        Statement stmt=null;
        try {
            
			stmt = dbConnection.createStatement();
            sql="select categoryName from Category order by categoryName";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Category c = new Category();
                String name = rs.getString(1);
                c.setName(name);
                listOfCategories.add(c);
            }
				
                
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return listOfCategories;
    }
    public boolean matchCategoryAndProduct(String categoryName, String productName){
        String sql;
        Statement stmt=null;
        try {
            Category c = new Category();
			stmt = dbConnection.createStatement();
            sql="select categoryName from Category where categoryId in (select categoryId from Product where productName = '"+productName+"')";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
			String catName = rs.getString(1);
            if(catName.equals(categoryName)){
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println("SQLException: " + ex.getMessage());
		    // System.out.println("SQLState: " + ex.getSQLState());
		    // System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return false;
    }

}
