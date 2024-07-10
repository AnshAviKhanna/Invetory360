create table Customer
(
    customerId varchar(20) ,
    customerName varchar(20) NOT NULL,
    customerMobile varchar(20) NOT NULL,
    customerAddress varchar(20),
    primary key (customerId) 
);

create table Vendor
(
    vendorId varchar(20) ,
    vendorName varchar(20) NOT NULL,
    vendorMobile varchar(20),
    vendorAddress varchar(20),
    primary key (vendorId)
);

create table Category
(
    categoryId varchar(20) ,
    categoryName varchar(20) NOT NULL UNIQUE,
    primary key (categoryId)
);

create table Product
(
    productId varchar(20) ,
    productName varchar(20) NOT NULL,
    categoryId varchar(20) NOT NULL,
    batchId varchar(20) NOT NULL ,
    qty integer CHECK(qty>=0),
    sellingPrice integer CHECK(sellingPrice>=0) NOT NULL,
    costPrice integer CHECK(costPrice>=0) NOT NULL,
    expiryDate varchar(20) NOT NULL,
    vendorId varchar(20) NOT NULL,
    discount integer CHECK(discount>=0 AND discount<=100),
    dateOfPurchase varchar(20) NOT NULL,
    primary key (productId,batchId)   
);

create table Purchases
(
    productId varchar(20) NOT NULL ,
    batchId varchar(20) NOT NULL ,
    categoryId varchar(20) NOT NULL,
    orderId varchar(20) NOT NULL ,
    qty integer CHECK(qty>=0) NOT NULL,
    customerId varchar(20) NOT NULL,
    finalPrice integer NOT NULL,
    dateOfPurchase varchar(20) NOT NULL,
    primary key (productId,batchId,orderId)
);

create table Removed_products
(
    productId varchar(20) NOT NULL ,
    batchId varchar(20) NOT NULL ,
    productName varchar(20) NOT NULL,
    qty integer NOT NULL,
    costPrice integer NOT NULL,
    removeDate varchar(20) NOT NULL,
    primary key (productId,batchId)
);

