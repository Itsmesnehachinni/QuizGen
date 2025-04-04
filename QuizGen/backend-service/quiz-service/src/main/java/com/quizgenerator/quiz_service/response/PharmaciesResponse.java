package com.quizgenerator.quiz_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmaciesResponse {

    private List<PharmacyDetails> pharmacies;

}
