package com.vrp.system.paymentsystem.paymentservice.dao;

import com.vrp.system.paymentsystem.paymentservice.models.PaymentOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentOrderDao extends CrudRepository<PaymentOrder, UUID> {
}
