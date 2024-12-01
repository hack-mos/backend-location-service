package com.binocla.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceRequestDto {
    private Integer placeId;
    @NotBlank(message = "Name can't be blank")
    private String name;
    @NotBlank(message = "Address can't be blank")
    private String address;
    @NotBlank(message = "River can't be blank")
    private String river;
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
}
