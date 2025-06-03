package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateBalanceService {

    private BalanceRepository repository;

    public UpdateBalanceService(BalanceRepository repository) {
        this.repository = repository;

    }

    public Balance update(String id, Balance updateBalance) {
        Balance balance = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Balance not found with ID: " + id));

        if (updateBalance.getDescription() != null) {
            balance.setDescription(updateBalance.getDescription());
        }

        if (updateBalance.getAmount() != null) {

            balance.setAmount(updateBalance.getAmount());
        }
        return repository.save(balance);
    }
}
