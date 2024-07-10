import java.util.*;
import tables.*;

public class Retail_Demo {
	public static Retail_Factory retailFactory;
    
    public static void main(String[] args) {
		try{
			
			retailFactory = new Retail_Factory();  // step 1
			System.out.println("WELCOME!");
			while(true){
				System.out.println("Enter the index of the usecase you want to perform from the list below:");
				System.out.println("1. List products by expiry date");
				System.out.println("2. List products by Category");
				System.out.println("3. Select items and process order");
				System.out.println("4. List Sale by Category");
				System.out.println("5. Add items to inventory");
				System.out.println("6. Apply discount");
				System.out.println("7. Remove products");
				System.out.println("8. Monthly loss");
				System.out.println("9. Exit");
				Scanner sc = new Scanner(System.in);
				int option = sc.nextInt();
				switch (option) {
					case 1:
						System.out.println("Enter number of months to see the products that will expire in the given months time:");
						int months=sc.nextInt();
						System.out.println("Choose ordering parameter from below options:");
						System.out.println("1. Quantity");
						System.out.println("2. Expiry Date");
						int opt1 = sc.nextInt();
						String orderBy = "";
						while(true){
							if(opt1==1){
								orderBy = "qty";
								break;
							}
							else if(opt1==2){
								orderBy ="expiryDate";
								break;
							}
							else{
								System.out.println("Enter correct choice: ");
								opt1 = sc.nextInt();
							}
						}
						usecase_listByExpiryDate(months,orderBy);
						break;
					case 2:
						System.out.println("Enter Category of Product: ");
						String category = sc.next();
						System.out.println("Select sorting parameter: ");
						System.out.println("1. Product Name");
						System.out.println("2. Quantity");
						System.out.println("3. Selling Price");
						int opt = sc.nextInt();
						String order = "";
						while(true){
							if(opt==1){
							order = "productName";
							break;
							}
							else if(opt==2){
								order ="qty";
								break;
							}
							else if(opt==3){
								order ="sellingPrice";
								break;
							}
							else{
								System.out.println("Enter correct choice: ");
								opt = sc.nextInt();
							}
						}
						usecase_listByCategory(category,order);
						break;
					case 3:
						System.out.println("Enter customer mobile number:");
						String mob=sc.next();
						System.out.println("Enter products and quantity to be bought(Enter 0 when done):");
						ArrayList<String> productName=new ArrayList<>();
						ArrayList<Integer> quantity=new ArrayList<>();
						while(true){
							String name=sc.next();
							if(name.equals("0")) break;
							productName.add(name);
							int qty=sc.nextInt();
							quantity.add(qty);
						}
						
						if(usecase_processOrder(mob,productName,quantity)==0){
							System.out.println("Enter customer name:");
							String custName=sc.next();
							System.out.println("Enter customer address:");
							sc.nextLine();
							String custAdd=sc.nextLine();
							usecase_addCustomer(custName, mob, custAdd);
							usecase_processOrder(mob, productName, quantity);
						}
						break;

					case 4:
					System.out.println("Select sorting parameter: ");
					System.out.println("1. Total Sale");
					System.out.println("2. Quantity");
					int opt2 = sc.nextInt();
					String order2 = "";
					while(true){
						if(opt2==1){
						order2 = "finalPrice";
						break;
						}
						else if(opt2==2){
							order2 ="qty";
							break;
						}
						else{
							System.out.println("Enter correct choice: ");
							opt2 = sc.nextInt();
						}
					}
						usecase_listSaleByCategory(order2);
						break;
					case 5:
					System.out.println("Enter vendor mobile number:");
					String vendorMob=sc.next();
					System.out.println("Enter Product, Quantity, Purchase Date, Expiry Date, Category Name, Selling Price, Cost Price, Discount of Product (Enter 0 when done):");
					ArrayList<String> pName=new ArrayList<>();
					ArrayList<Integer> qty=new ArrayList<>();
					ArrayList<String> dateOfPurchase=new ArrayList<>();
					ArrayList<String> expiryDate=new ArrayList<>();
					ArrayList<String> categoryList=new ArrayList<>();
					ArrayList<Integer> sellingPrice=new ArrayList<>();
					ArrayList<Integer> costPrice=new ArrayList<>();
					ArrayList<Integer> discount=new ArrayList<>();
					while(true){
						String name=sc.next();
						if(name.equals("0")) break;
						pName.add(name);
						int qty2=sc.nextInt();
						qty.add(qty2);
						String purchaseDate = sc.next();
						dateOfPurchase.add(purchaseDate);
						String expiry = sc.next();
						expiryDate.add(expiry);
						String cat5 = sc.next();
						categoryList.add(cat5);
						int sp = sc.nextInt();
						sellingPrice.add(sp);
						int cp = sc.nextInt();
						costPrice.add(cp);
						int dis = sc.nextInt();
						discount.add(dis);
					}
						int status = usecase_addItems(vendorMob, pName, qty, dateOfPurchase, expiryDate, categoryList, sellingPrice, costPrice, discount);
						if(status==1){
							System.out.println("Enter vendor name:"); 
							String vendorName=sc.next();
							System.out.println("Enter vendor address:");
							sc.nextLine();
							String vendorAddress=sc.nextLine();
							usecase_addVendor(vendorName, vendorMob, vendorAddress);
							status = usecase_addItems(vendorMob, pName, qty, dateOfPurchase, expiryDate, categoryList, sellingPrice, costPrice, discount);
							if(status ==2){
								continue;
							}
						}
						else if(status==2){
							continue;
						}
						break;
					case 6:
					System.out.println("Select from the following options: ");
					System.out.println("1. Apply discount on a Product with a batch Id");
					System.out.println("2. Apply discount by Expiry Date");
					System.out.println("3. Apply discount by Category Name");
					int opt3 = sc.nextInt();
					String disOn="", batch="", cat="", product="";
					int mon=0,dis_percent=0;
					while(true){
						if(opt3==1){
							disOn = "product&batchId";
							usecase_getListOfProducts();
							System.out.println("Enter Product Name: ");
							product = sc.next();
							System.out.println("Enter Batch Id of Product: ");
							batch = sc.next();
							System.out.println("Enter discount percent: ");
							dis_percent = sc.nextInt();
							usecase_applyDiscount(disOn, batch, dis_percent, cat, product, mon);
						break;
						}
						else if(opt3==2){
							disOn = "byExpiryDate";
							usecase_listByExpiryDate(Integer.MAX_VALUE,"expiryDate");
							System.out.println("Enter number of months to apply discount: ");
							mon = sc.nextInt();
							System.out.println("Enter discount percent: ");
							dis_percent = sc.nextInt();
							usecase_applyDiscount(disOn, batch, dis_percent, cat, product, mon);
						break;
						}
						else if(opt3 == 3){
							disOn = "category";
							usecase_getListOfCategories();
							System.out.println("Enter Category Name: ");
							cat = sc.next();
							System.out.println("Enter discount percent: ");
							dis_percent = sc.nextInt();
							usecase_applyDiscount(disOn, batch, dis_percent, cat, product, mon);
						break;
						}
						else{
							System.out.println("Enter correct choice: ");
							opt3 = sc.nextInt();
						}
					}
						usecase_applyDiscount(disOn, batch, dis_percent, cat, product, mon);
						break;
					case 7:
						System.out.println("Enter products and batchId to be removed(Enter 0 when done):");
						ArrayList<String> productName1=new ArrayList<>();
						ArrayList<String> batchId=new ArrayList<>();
						while(true){
							String name=sc.next();
							if(name.equals("0")) break;
							productName1.add(name);
							String batch1=sc.next();
							batchId.add(batch1);
						}
						usecase_removeProduct(productName1, batchId);
						break;
					case 8:
						System.out.println("Enter month in MM/YY format to find losses:");
						String month=sc.next();
						usecase_lossSufferedByMonth(month);
						break;
					case 9:
						System.exit(0);
						break;
					default:
						System.out.println("Enter valid option number!");
						continue;
				}
			}


			// System.out.println("Running usecase1");
			// usecase_listByCategory();

			// System.out.println("Running usecase2");
			// usecase_listByExpiryDate();

			// System.out.println("Running usecase3");
			// usecase_processOrder();

			// System.out.println("Running usecase4");
			// usecase_listSaleByCaregory();

			// System.out.println("Running usecase5");
			// usecase_addItems();

			// System.out.println("Running usecase6");
			// usecase_applyDiscount();

			// System.out.println("Running usecase7");
			// usecase_removeProduct();

			// System.out.println("Running usecase8");
			// usecase_lossSufferedByMonth();

			
		}catch(Exception e){
				//Handle errors for Class.forName
                e.printStackTrace();
		}
	}
	public static void usecase_listByExpiryDate(int months,String orderBy){
		try {
			// int months;
			// if(!retailFactory.activateConnection())
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			// months = 24;

			ArrayList<Product> productList = retail.getProductsByExpiryDate(months,orderBy);
			if(productList.size()==0){
				System.out.println("No products!");
			}
			else{
				System.out.println("ProductId   ProductName   CategoryId   Quantity   Discount   ExpiryDate");
				for(Product element: productList){
					System.out.println(element.getProductId() +"\t    "+ element.getProductName()+"\t   "+element.getCategoryId()+"\t"+element.getQty()+"\t  "+element.getDiscount()+"\t\t"+element.getExpiryDate());
				}
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
			}

		} catch (Exception e) {
			// TODO: handle exception
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}
    public static void usecase_listByCategory(String Category, String order)
	{
		try{
			String categoryName=Category,orderBy=order;

			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			// categoryName="catName2";
            // orderBy="qty";
			
			if(retail.checkCategoryName(categoryName)!=null)
			{
				ArrayList<Product>productList =  retail.getProductsByCategoryName(categoryName,orderBy);
				if(productList.size()==0)	
					System.out.println("No products in this category!");
				else
				{
					System.out.println("ProductId  ProductName  BatchId  Quantity  Selling Price  Discount%   BatchId");
					for (Product element : productList)
						System.out.println(element.getProductId() + "        "+element.getProductName()+"         "+element.getBatchId()+"        "+element.getQty()+"        "+element.getSellingPrice()+"        "+element.getDiscount()+"          "+element.getBatchId());
				}
			}

			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
	}
	public static int usecase_processOrder(String customerMob,ArrayList<String> productName,ArrayList<Integer>quantity){
		try {
			
			ArrayList<String> bills = new ArrayList<>();
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();
			if(!retail.checkCustomer(customerMob))	
			{
				return 0;
			}
			int flag=1;
			for (String element: productName){
				if(retail.checkProductName(element)==null){
					System.out.println("Product name: "+ element +" does not exist!");
					flag=0;
				}
			}
			if(flag==1){
				for(int i=0;i<productName.size();i++){
					if(retail.checkQty(productName.get(i), quantity.get(i))==false){
						System.out.println("Enough quantity not available for "+productName.get(i));
						flag=0;
					}
				}
			}
			if(flag==1){
				//Call buyProduct function with given productName and quantity
				String orderId=retail.addOrder();
				for(int i=0;i<productName.size();i++)
				{
					ArrayList<String> s = retail.buyProduct(productName.get(i),quantity.get(i),customerMob,orderId);
					for(String p: s){
						bills.add(p);
					}
				}
				for(String s:bills){
					System.out.println(s);
				}
			}
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
			return 1;
		} catch (Exception e) {
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
			// TODO: handle exception
		}
		return 1;
	}
	public static void usecase_addCustomer(String customerName,String customerMob,String customerAddress){
		try{
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();
			retail.addCustomer(customerName, customerMob, customerAddress);
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
		catch(Exception e){
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}
	public static void usecase_addVendor(String vendorName,String vendorMob,String vendorAddress){
		try{
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();
			String vendorId=retail.getNewVendorId();
			Vendor v=new Vendor(vendorName,vendorId,vendorMob,vendorAddress);
			retail.addVendor(v);
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
		catch(Exception e){
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
		}
	}
    public static void usecase_listSaleByCategory(String sortBy)
	{
		try{
			String orderBy=sortBy;
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			
			ArrayList<ArrayList<String>> Sales = new ArrayList<ArrayList<String>>();
			
			//handle the case of the giving the parameter for sorting the list.
			// orderBy="finalPrice";

			Sales = retail.getSaleByCategory(orderBy);
			if(Sales.size()!=0){
				System.out.println("Category Name\t"+"Sales\t"+"Quantity");
			}
			for(ArrayList<String> i : Sales){
				for(String j: i){
					System.out.print(j+" \t");
				}
				System.out.println();
			}
			System.out.println();
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
	}
	public static int usecase_addItems(String mob, ArrayList<String> pName,ArrayList<Integer> qty,ArrayList<String> dop,ArrayList<String> expDate,ArrayList<String> catList,ArrayList<Integer> sp,ArrayList<Integer> cp,ArrayList<Integer> dis){
		try{
			String vendorMobile = mob;
			ArrayList<String> productName = pName;
			ArrayList<Integer> quantity = qty;
			ArrayList<String> dateOfPurchase = dop;
			ArrayList<String> expiryDate = expDate;
			ArrayList<String> categoryList = catList;
			ArrayList<Integer> sellingPrice = sp;
			ArrayList<Integer> costPrice = cp;
			ArrayList<Integer> discount = dis;
			// ArrayList<Product> products=new ArrayList<>();
			int flag = 0;
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			// productName.add("pname21");productName.add("pname22");
			// quantity.add(230);quantity.add(234);
			// dateOfPurchase.add("02/23");dateOfPurchase.add("03/23");
			// expiryDate.add("06/27");expiryDate.add("08/28");
			// categoryList.add("catName13");
			// categoryList.add("catName6");
			// sellingPrice.add(99);
			// sellingPrice.add(98);
			// costPrice.add(49);
			// costPrice.add(48);
			// discount.add(7);
			// discount.add(9);
			if(!retail.checkVendor(vendorMobile))	
			{
				return 1;
				// System.out.println("cus here also");
			}
				// System.out.println("customer here");
				// String vendorName="vendor7";
				// String vendorAddress="home";
				// String vendorId=retail.getNewVendorId();
				// Vendor v=new Vendor(vendorName,vendorId,vendorMobile,vendorAddress);
				// retail.addVendor(v);
			for(int i=0;i<categoryList.size();i++){
				if(retail.checkCategoryName(categoryList.get(i)) !=null && retail.checkProductName(productName.get(i)) !=null )
				{
					if(retail.matchCategoryAndProduct(categoryList.get(i), productName.get(i))==false){
						flag = 1;
						System.out.println("Category: "+categoryList.get(i)+" and Product: "+productName.get(i)+" mismatch.");
					}
				}
			}
			if(flag==1){
				return 2;
			}
			for(String categoryName:categoryList){
				if(retail.checkCategoryName(categoryName)==null)
				{
					// System.out.println("hrere");
					String categoryId=retail.getNewCatId();
					Category c=new Category(categoryName,categoryId);
					retail.addCategory(c);
				}
			}
			
			for(int i=0;i<productName.size();i++){
				String vendId=retail.getVendorIdFromMobile(vendorMobile);
				String prodId,batchId,catId;
				prodId=retail.getIdDetailsforName(productName.get(i), categoryList.get(i)).get(0);
				batchId=retail.getIdDetailsforName(productName.get(i), categoryList.get(i)).get(1);
				catId=retail.getIdDetailsforName(productName.get(i), categoryList.get(i)).get(2);
				// System.out.println(prodId+" "+batchId+" "+catId);
				Product p=new Product(prodId,productName.get(i),catId,batchId,quantity.get(i),sellingPrice.get(i),costPrice.get(i),expiryDate.get(i),vendId,discount.get(i),dateOfPurchase.get(i));
				retail.updateTheInventory(p);
			}
			
			// pname24 100 05/23 05/25 catName94 90 70 5
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
		return 0;
	}
	public static void usecase_applyDiscount(String disOn,String batch,int dis_percent,String category, String product, int mon)
	{
		try{
			String discountOn=disOn;
			String batchId=batch;
			int discount_percent=dis_percent;
			String CatName = category;
			String pName = product;
			int months=mon;

			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			if(discountOn.equals("product&batchId"))
				retail.discount_product(pName,batchId,discount_percent);
			else if(discountOn.equals("category"))
				retail.discount_category(CatName,discount_percent);
			else if(discountOn.equals("byExpiryDate"))
				retail.discount_expiry(months,discount_percent);
			else 
				System.out.println("Invalid input");

			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}catch(Exception e){
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
		}
	}


	public static void usecase_getListOfProducts()
	{
		try {
			// String customerMob="8907623424";
			// String orderId="o00001";
			// String oldProduct="pname2";
			// String newProduct="pname3";
			// int newQty=5;
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			ArrayList<Product> listOfProducts = new ArrayList<>();
			listOfProducts = retail.getAllProductList();
			// System.out.println(listOfProducts.size());
			if(listOfProducts.size()>0){
				for(Product e: listOfProducts){
					System.out.println(e.getProductName()+" "+e.getBatchId()+" "+e.getQty()+" "+e.getSellingPrice()+" "+e.getCostPrice()+" "+e.getExpiryDate()+" "+e.getDiscount()+" "+e.getDateOfPurchase());
				}
			}
			else{
				System.out.println("No product in the inventory :(");
			}
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
			catch (Exception e) {
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
				// TODO: handle exception
			}
		}

	public static void usecase_getListOfCategories()
	{
		try {
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();

			ArrayList<Category> listOfCategories = new ArrayList<>();
			listOfCategories = retail.getAllCategoryList();
			if(listOfCategories.size()>0){
				for(Category e: listOfCategories){
					System.out.println(e.getName());
				}
			}
			else{
				System.out.println("No Category in the inventory :(");
			}
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
			catch (Exception e) {
				retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
				e.printStackTrace();
				// TODO: handle exception
			}
		}

	public static void usecase_removeProduct(ArrayList<String>name,ArrayList<String>batch)
	{
		int flag=1;
		try {
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();
			for(int i=0;i<name.size();i++){
				if(retail.checkNameAndBatch(name.get(i),batch.get(i))==0) flag=0; 
			}
			if(flag==1){
				for(int i=0;i<name.size();i++){
					retail.removeProduct(name.get(i),batch.get(i));
				}
			}
			
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
		catch (Exception e) {
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.ROLLBACK );
			e.printStackTrace();
				// TODO: handle exception
			}
		}

	public static void usecase_lossSufferedByMonth(String month)
	{
		try {
			ArrayList<Integer> result=new ArrayList<>();
			if(retailFactory.activeConnection==false){

				retailFactory.activateConnection();
			}
			Retail retail = retailFactory.getRetail();
			result=retail.getLossSuffered(month);
			System.out.println("Total loss suffered in the month: "+result.get(0)+" from a total of "+result.get(1)+" products.");
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
		}
		catch (Exception e) {
			retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
				// TODO: handle exception
			}
		}


	// public static void usecase_exchangeProducts()
	// {
	// 	try {
	// 		String customerMob="8907623424";
	// 		String orderId="o00001";
	// 		String oldProduct="pname2";
	// 		String newProduct="pname3";
	// 		int newQty=5;
	// 		retailFactory.activateConnection();
	// 		Retail retail = retailFactory.getRetail();

	// 		if(retail.checkOrderId(orderId)||retail.checkReturnProductName(oldProduct).length()!=0 || retail.checkProductName(newProduct).length()!=0 || (!retail.checkQty(newProduct, newQty)))	
	// 		{
	// 			retail.exchangeProducts(oldProduct,newProduct,newQty,orderId);
	// 		} 
	// 		retailFactory.deactivateConnection( Retail_Factory.TXN_STATUS.COMMIT );
	// 	}
	// 		catch (Exception e) {
	// 			// TODO: handle exception
	// 		}
	// 	}
	}
	
	
	// 	int flag=1;
	// 	for (String element: productName){
	// 		if(retail.checkProductName(element)==null){
	// 			System.out.println("Product name: "+ element +" does not exist!");
	// 			flag=0;
	// 		}
	// 	}
	// 	if(flag==1){
	// 		for(int i=0;i<productName.size();i++){
	// 			if(retail.checkQty(productName.get(i), quantity.get(i))==false){
	// 				System.out.println("Enough quantity not available for "+productName.get(i));
	// 				flag=0;
	// 			}
	// 		}
	// 	}
	// 	if(flag==1){
	// 		//Call buyProduct function with given productName and quantity
	// 		String orderId=retail.addOrder();
	// 		// System.out.println(orderId);
	// 		// System.out.println("check");
	// 		for(int i=0;i<productName.size();i++)
	// 		{
	// 			ArrayList<String> s = retail.buyProduct(productName.get(i),quantity.get(i),customerMob,orderId);
	// 			for(String p: s){
	// 				bills.add(p);
	// 			}
	// 		}
	// 		for(String s:bills){
	// 			System.out.println(s);
	// 		}

	// }