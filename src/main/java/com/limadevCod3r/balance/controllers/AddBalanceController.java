package com.limadevCod3r.balance.controllers;

import com.limadevCod3r.balance.dtos.CreateBalanceRequest;
import com.limadevCod3r.balance.mapper.BalanceMapper;
import com.limadevCod3r.balance.services.AddBalanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/balance")
public class AddBalanceController {

    @Autowired
    private AddBalanceService service;

    @Autowired
    private BalanceMapper mapper;


    @PostMapping
    public ResponseEntity<Void> AddBalance(@Valid @RequestBody CreateBalanceRequest request) {
        var entity = mapper.ConvertToEntity(request);
        var savedBalance = service.Add(entity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBalance.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
