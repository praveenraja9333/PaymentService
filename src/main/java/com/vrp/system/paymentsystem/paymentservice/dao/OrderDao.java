package com.vrp.system.paymentsystem.paymentservice.dao;

import com.vrp.system.paymentsystem.paymentservice.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderDao extends CrudRepository<Order, UUID> {
}
