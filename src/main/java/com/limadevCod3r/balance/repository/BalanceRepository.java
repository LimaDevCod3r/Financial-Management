package com.limadevCod3r.balance.repository;

import com.limadevCod3r.balance.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, String> {
}
