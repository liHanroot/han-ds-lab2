package com.han.payment.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private UUID paymentUid;
    private String status;
    private Integer price;
}
