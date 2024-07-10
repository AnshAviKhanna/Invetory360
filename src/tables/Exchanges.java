package tables;
public class Exchanges
{
	String exchangeId,orderId,returnedProductId,purchasedProductId,returnedBatchid,purchasedBatchId,purchasedQty,dateOfExchange;

    public Exchanges(String exchangeId, String orderId, String returnedProductId, String purchasedProductId, String returnedBatchid, String purchasedBatchId, String purchasedQty, String dateOfExchange) {
        this.exchangeId = exchangeId;
        this.orderId = orderId;
        this.returnedProductId = returnedProductId;
        this.purchasedProductId = purchasedProductId;
        this.returnedBatchid = returnedBatchid;
        this.purchasedBatchId = purchasedBatchId;
        this.purchasedQty = purchasedQty;
        this.dateOfExchange = dateOfExchange;
    }

    public String getexchangeId() {
        return this.exchangeId;
    }

    public void setexchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getorderId() {
        return this.orderId;
    }

    public void setorderId(String orderId) {
        this.orderId = orderId;
    }

    public String getreturnedProductId() {
        return this.returnedProductId;
    }

    public void setreturnedProductId(String returnedProductId) {
        this.returnedProductId = returnedProductId;
    }

    public String getpurchasedProductId() {
        return this.purchasedProductId;
    }

    public void setpurchasedProductId(String purchasedProductId) {
        this.purchasedProductId = purchasedProductId;
    }

    public String getreturnedBatchid() {
        return this.returnedBatchid;
    }

    public void setreturnedBatchid(String returnedBatchid) {
        this.returnedBatchid = returnedBatchid;
    }

    public String getpurchasedBatchId() {
        return this.purchasedBatchId;
    }

    public void setpurchasedBatchId(String purchasedBatchId) {
        this.purchasedBatchId = purchasedBatchId;
    }

    public String getpurchasedQty() {
        return this.purchasedQty;
    }

    public void setpurchasedQty(String purchasedQty) {
        this.purchasedQty = purchasedQty;
    }

    public String getDateOfExchange() {
        return this.dateOfExchange;
    }

    public void setDateOfExchange(String dateOfExchange) {
        this.dateOfExchange = dateOfExchange;
    }

};

