/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.utils;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import khangtl.dtos.BookingDTO;

/**
 *
 * @author Peter
 */
public class PaymentServices implements Serializable {

    private static final String CLIENT_ID = "AXqUx9F2xrSYLhptiK_reeijDnP_TN7rU1XCiN9HEH5d-zJ2PjP-RqW2cP95B9KOk31B3UWkzrFX-mL9";
    private static final String CLIENT_SECRET = "EN8lvPxUB_-9f2cGqArCx7oePVoOBv-XWYug31sJmZNc35OVa0VCnu39gd78BZncxJcAjGrKHJ5VccNR";
    private static final String MODE = "sandbox";

    public String authorizePayment(BookingDTO booking) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(booking);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Khang")
                .setLastName("Tran")
                .setEmail("haphongpk12@gmail.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private List<Transaction> getTransactionInformation(BookingDTO booking) {
        double sum = 0;
        Amount amount = new Amount();
        amount.setCurrency("USD");
        Transaction transaction = new Transaction();

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < booking.getDetails().size(); i++) {
            Item item = new Item();
            item.setCurrency("USD");
            item.setName(String.valueOf(booking.getDetails().get(i).getTourName()));
            item.setPrice(String.format("%.2f", Utility.convertVNDToUSD(booking.getDetails().get(i).getPrice())));
            item.setQuantity(String.valueOf(booking.getDetails().get(i).getAmount()));
            items.add(item);
            sum += new Double(item.getPrice()) * new Double(item.getQuantity());
        }
        if (booking.getDiscount() != null) {
            Item item = new Item();
            item.setCurrency("USD");
            item.setName("Discount: " + booking.getDiscount().getCode());
            item.setPrice(String.format("%.2f", Utility.convertVNDToUSD(booking.getTotalPrice() * booking.getDiscount().getPercent() / 100) * -1));
            item.setQuantity("1");
            items.add(item);
            sum += new Double(item.getPrice()) * new Double(item.getQuantity());
        }
        itemList.setItems(items);
        amount.setTotal(String.format("%.2f", sum));
        transaction.setAmount(amount);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8084/SE130380_J3LP0008/home.jsp");
        redirectUrls.setReturnUrl("http://localhost:8084/SE130380_J3LP0008/ReviewPayment");
        return redirectUrls;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }
}
