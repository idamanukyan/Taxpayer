package com.example.taxpayer.controller;

import com.example.taxpayer.entity.Taxpayer;
import com.example.taxpayer.service.TaxpayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxpayer")
public class TaxpayerController {

    private final TaxpayerService taxpayerService;

    public TaxpayerController(TaxpayerService taxpayerService) {
        this.taxpayerService = taxpayerService;
    }

    @PostMapping
    public Taxpayer add(@RequestBody Taxpayer tp) {
        return taxpayerService.addTaxpayer(tp);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable("id") Integer id) {
        taxpayerService.deactivate(id);
    }

    @GetMapping("/{id}")
    public Taxpayer get(@PathVariable("id") Integer id) {
        return taxpayerService.get(id);
    }

    @PatchMapping("/{id}")
    public Taxpayer update(@PathVariable(name = "id") Integer id, @RequestBody Taxpayer tp) {
        return taxpayerService.update(id, tp);
    }
}
