package com.vrp.system.paymentsystem.paymentservice.config;

import com.vrp.system.paymentsystem.paymentservice.workers.ExecuteThreadImpl;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public ExecuteThreadImpl getExecutorThread(){
        return new ExecuteThreadImpl();
    }
}
