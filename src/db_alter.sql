alter table Product
add constraint fk_category
foreign key (categoryId) references Category(categoryId) on delete cascade,
add constraint fk_vendor
foreign key (vendorId) references Vendor(vendorId);

alter table Purchases
-- add constraint fk_product
-- foreign key (productId,batchId) references Product(productId,batchId),
add constraint fk_category2
foreign key (categoryId) references Category (categoryId),
add constraint fk_customer
foreign key (customerId) references Customer(customerId);

-- alter table Exchanges
-- add constraint fk_returnedProduct
-- foreign key (returnedProductId,returnedBatchId,orderId) references Purchases(productId,batchId,orderId),
-- add constraint fk_purchasedProduct
-- foreign key (purchasedProductId,purchasedBatchId) references Product(productId,batchId);
-- add constraint fk_returnedBatch
-- foreign key (returnedBatchId) references Purchases(batchId),
-- add constraint fk_purchasedBatch
-- foreign key (purchasedBatchId) references Product(batchId),
-- add constraint fk_order
-- foreign key (orderId) references Purchases(orderId);