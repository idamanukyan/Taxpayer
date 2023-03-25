package com.example.taxpayer.service;

import com.example.taxpayer.entity.Taxpayer;
import com.example.taxpayer.repository.TaxpayerRepository;
import com.example.taxpayer.service.exceptions.ApplicationException;
import com.example.taxpayer.service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaxpayerService {

    private final TaxpayerRepository taxpayerRepository;

    public TaxpayerService(TaxpayerRepository taxpayerRepository) {
        this.taxpayerRepository = taxpayerRepository;
    }

    public Taxpayer addTaxpayer(Taxpayer tp) {
        if (taxpayerRepository.existsByEmail(tp.getEmail())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Taxpayer already exists.");
        }
        taxpayerRepository.save(tp);
        return tp;
    }

    public void deactivate(Integer id) {
        Taxpayer tp = taxpayerRepository.findById(id).get();
        if (!(tp.getDeactivated() == null)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Taxpayer is already deactivated.");
        }
        tp.setDeactivated(LocalDateTime.now());
        taxpayerRepository.save(tp);
    }

    public Taxpayer get(Integer id) {
        return taxpayerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Taxpayer update(Integer id, Taxpayer updatedTp) {
        Taxpayer tp = taxpayerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Taxpayer with id : {%s} not found", id.toString())));
        Optional.ofNullable(updatedTp.getName()).ifPresent(tp::setName);
        Optional.ofNullable(updatedTp.getSurname()).ifPresent(tp::setSurname);
        Optional.ofNullable(updatedTp.getEmail()).ifPresent(tp::setEmail);

        return taxpayerRepository.save(tp);
    }

    public long getSize(){
        return taxpayerRepository.count();
    }

}
