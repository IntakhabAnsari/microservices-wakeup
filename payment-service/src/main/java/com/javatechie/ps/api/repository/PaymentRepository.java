package com.javatechie.ps.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.javatechie.ps.api.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

	Payment findByOrderId(int orderId);

}
