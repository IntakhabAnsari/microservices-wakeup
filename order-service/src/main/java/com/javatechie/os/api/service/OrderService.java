package com.javatechie.os.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javatechie.os.api.common.Payment;
import com.javatechie.os.api.common.TransactionRequest;
import com.javatechie.os.api.common.TransactionResponse;
import com.javatechie.os.api.entity.Order;
import com.javatechie.os.api.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private RestTemplate template;
	
	public TransactionResponse saveOrder(TransactionRequest request) {
		String response = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		//rest call
		Payment paymentResponse = template.postForObject("http://localhost:8082/myapp/payment/doPayment", payment, Payment.class);
		
		response = paymentResponse.getPaymentStatus().equals("success")?"payment processing successful and ordered placed":"there is a failure in payment api, order added to cart";
		repository.save(order);
		return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
	}

	public List<Order> getbookOrders() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}
