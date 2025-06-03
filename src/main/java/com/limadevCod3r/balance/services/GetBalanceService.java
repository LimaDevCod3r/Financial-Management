package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetBalanceService {
    private final BalanceRepository repository;

    public GetBalanceService(BalanceRepository repository) {
        this.repository = repository;
    }

    public Balance getById(String id) {

        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Balance not found with ID: " + id));
    }
}
