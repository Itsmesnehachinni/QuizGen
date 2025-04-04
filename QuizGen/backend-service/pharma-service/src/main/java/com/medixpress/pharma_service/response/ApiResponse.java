package com.medixpress.pharma_service.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {
    @NonNull
    private int status;

    @NonNull
    private String message;

    private Object data;
}
