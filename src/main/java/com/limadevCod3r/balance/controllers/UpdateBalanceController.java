package com.limadevCod3r.balance.controllers;

import com.limadevCod3r.balance.dtos.UpdateBalanceRequest;
import com.limadevCod3r.balance.mapper.BalanceMapper;
import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.UpdateBalanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
public class UpdateBalanceController {

    @Autowired
    private UpdateBalanceService service;

    @Autowired
    private BalanceMapper mapper;

    @PatchMapping("/{id}")
    public ResponseEntity<Balance> UpdatePartialBalance(
            @PathVariable String id,
            @Valid @RequestBody UpdateBalanceRequest request) {

        var entity = mapper.ConvertToEntity(request);
        var updatedBalance = service.update(id, entity);
        return ResponseEntity.ok(updatedBalance);
    }
}
