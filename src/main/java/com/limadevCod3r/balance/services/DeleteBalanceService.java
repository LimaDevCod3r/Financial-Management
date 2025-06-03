package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteBalanceService {

    private BalanceRepository repository;

    public DeleteBalanceService(BalanceRepository repository) {
        this.repository = repository;
    }

    public void delete(String id) {
        Balance entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Balance not found with ID: " + id));
        repository.delete(entity);
    }
}
