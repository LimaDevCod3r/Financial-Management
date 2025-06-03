package com.limadevCod3r.balance.controllers;

import com.limadevCod3r.balance.services.DeleteBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance")
public class DeleteBalanceController {

    @Autowired
    private DeleteBalanceService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalance(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
