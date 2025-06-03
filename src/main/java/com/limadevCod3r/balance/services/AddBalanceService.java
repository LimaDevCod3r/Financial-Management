package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AddBalanceService {

    private final BalanceRepository repository;

    public AddBalanceService(BalanceRepository repository) {
        this.repository = repository;
    }

    public Balance Add(Balance entity) {
        return repository.save(entity);
    }
}
