package com.limadevCod3r.balance.repository;

import com.limadevCod3r.balance.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, String> {
    Optional<Balance> findById(String id);
}
