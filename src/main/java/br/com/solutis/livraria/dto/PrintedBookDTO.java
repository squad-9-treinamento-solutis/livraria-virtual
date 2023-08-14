package br.com.solutis.livraria.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintedBookDTO extends BookDTO {
    @NotNull(message = "Shipment is required")
    private Float shipment;

    @NotNull(message = "Stock is required")
    private Integer stock;
}
