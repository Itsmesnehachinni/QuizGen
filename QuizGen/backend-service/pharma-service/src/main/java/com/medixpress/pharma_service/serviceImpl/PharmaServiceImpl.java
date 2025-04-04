package com.medixpress.pharma_service.serviceImpl;

import com.medixpress.pharma_service.entity.PharmacyMedicines;
import com.medixpress.pharma_service.exception.MediXpressException;
import com.medixpress.pharma_service.feignclient.UserClient;
import com.medixpress.pharma_service.repository.PharmacyMedicinesRepository;
import com.medixpress.pharma_service.response.Medicine;
import com.medixpress.pharma_service.response.PharmaciesResponse;
import com.medixpress.pharma_service.response.PharmacyDetails;
import com.medixpress.pharma_service.service.PharmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PharmaServiceImpl implements PharmaService {

    @Autowired
    private PharmacyMedicinesRepository pharmacyMedicinesRepository;

    @Autowired
    private UserClient userClient;

    /**
     * feign call to user-service to get all active pharmacies
     * @return
     */
    @Override
    public PharmaciesResponse getAllPharmacies() {
        Map<Long, Set<String>> availablePharmacyMedicines = mapAvailableMedicinesToPharmacy();
        System.out.println("map::" +availablePharmacyMedicines.toString());
        PharmaciesResponse pharmaciesResponse = userClient.getAllPharmacies();
        List<PharmacyDetails> pharmacies = pharmaciesResponse.getPharmacies();

        if (!pharmacies.isEmpty()) {
            System.out.println("pharmacies:" + pharmacies);
            pharmacies.forEach(pharmacyDetails -> {
                if (availablePharmacyMedicines.containsKey(pharmacyDetails.getPharmacyId()))
                    pharmacyDetails.setAvailableMedicines(new HashSet<>(availablePharmacyMedicines.get(pharmacyDetails.getPharmacyId())));
                else
                    pharmacyDetails.setAvailableMedicines(new HashSet<>());
            });
            pharmaciesResponse.setPharmacies(pharmacies);
        }
        return pharmaciesResponse;
    }

    /**
     * Retrieving pharmacy medicine for Order service feign
     * @param pharmacyMedicineId
     * @return
     */
    @Override
    public Medicine getPharmacyMedicineById(Long pharmacyMedicineId) {
        try {
            Optional<PharmacyMedicines> pharmacyMedicinesOptional =
                    pharmacyMedicinesRepository.findByPharmaMedicineId(pharmacyMedicineId);
            return pharmacyMedicinesOptional.map(pharmacyMedicines -> Medicine.builder()
                    .pharmacyId(pharmacyMedicines.getPharmacyId())
                    .price(pharmacyMedicines.getPrice())
                    .pharmaMedicineId(pharmacyMedicines.getPharmaMedicineId())
                    .stock(pharmacyMedicines.getStock())
                    .expiryDate(new SimpleDateFormat("dd/MM/yyyy").format(pharmacyMedicines.getExpiryDate()))
                    .medicineDescription(pharmacyMedicines.getMedicineDescription())
                    .medicineName(pharmacyMedicines.getMedicineName())
                    .manfacturingCompany(pharmacyMedicines.getManfacturingCompany())
                    .manfacturingDate(new SimpleDateFormat("dd/MM/yyyy").format(pharmacyMedicines.getManfacturingDate()))
                    .build()).orElse(null);
        }catch(Exception e){
            throw new MediXpressException("Error occurred while retrieving pharmacy medicine : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private Map<Long,Set<String>> mapAvailableMedicinesToPharmacy(){
        Map<Long,Set<String>> pharmacyMedicinesMap = new HashMap<>();
        List<PharmacyMedicines> availableMedcines = pharmacyMedicinesRepository.findAllAvailableMedicines();
        for(PharmacyMedicines medicine : availableMedcines){
            Set<String> pharmaMedicines =  new HashSet<>();
            if(pharmacyMedicinesMap.containsKey(medicine.getPharmacyId())){
               pharmaMedicines = pharmacyMedicinesMap.get(medicine.getPharmacyId());
            }
             pharmaMedicines.add(medicine.getMedicineName());
            pharmacyMedicinesMap.put(medicine.getPharmacyId(),pharmaMedicines);
        }
        return pharmacyMedicinesMap;
    }
}
