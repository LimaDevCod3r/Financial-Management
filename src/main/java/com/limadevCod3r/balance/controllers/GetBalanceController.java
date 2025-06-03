package com.limadevCod3r.balance.controllers;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.GetBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance")
public class GetBalanceController {

    @Autowired
    private GetBalanceService service;

    @GetMapping("/{id}")
    public ResponseEntity<Balance> getBalanceById(@PathVariable String id) {
        var balance = service.getById(id);
        return ResponseEntity.ok().body(balance);
    }
}
