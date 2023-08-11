package com.vrp.system.paymentsystem.paymentservice.dao;

import com.vrp.system.paymentsystem.paymentservice.models.RegistrationEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RegistrationEventDao extends CrudRepository<RegistrationEvent, UUID> {
}
