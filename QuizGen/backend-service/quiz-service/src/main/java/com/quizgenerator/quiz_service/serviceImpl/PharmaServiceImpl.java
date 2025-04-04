package com.quizgenerator.quiz_service.serviceImpl;

import com.quizgenerator.quiz_service.constants.PharmacyStatusEnum;
import com.quizgenerator.quiz_service.entity.Pharmacy;
import com.quizgenerator.quiz_service.repository.PharmacyRepository;
import com.quizgenerator.quiz_service.response.PharmaciesResponse;
import com.quizgenerator.quiz_service.response.PharmacyDetails;
import com.quizgenerator.quiz_service.service.PharmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PharmaServiceImpl implements PharmaService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Override
    public PharmaciesResponse getAllPharmacies() {
        PharmaciesResponse pharmaciesResponse = new PharmaciesResponse();
        List<PharmacyDetails> pharmacyDetailsList = new ArrayList<>();
        List<Pharmacy> pharmacyEntitiesList = pharmacyRepository.findByStatus(PharmacyStatusEnum.ACTIVE.name());
        if(!pharmacyEntitiesList.isEmpty()){
            for (Pharmacy pharmacy: pharmacyEntitiesList){
                PharmacyDetails pharmacyDetails = PharmacyDetails.builder()
                        .pharmacyId(pharmacy.getPharmacyId())
                        .pharmaName(pharmacy.getPharmaName())
                        .licenseNumber(pharmacy.getLicenseNumber())
                        .pharmaStreetName(pharmacy.getPharmaStreetName())
                        .city(pharmacy.getCity())
                        .state(pharmacy.getState())
                        .country(pharmacy.getCountry())
                        .emailAddress(pharmacy.getEmailAddress())
                        .phoneNumber(pharmacy.getPhoneNumber())
                        .isAlwaysAvailable(pharmacy.getIsAlwaysAvailable())
                        .status(pharmacy.getStatus())
                        .build();
                pharmacyDetailsList.add(pharmacyDetails);
            }
            pharmaciesResponse.setPharmacies(pharmacyDetailsList);
        }
        return pharmaciesResponse;
    }
}
