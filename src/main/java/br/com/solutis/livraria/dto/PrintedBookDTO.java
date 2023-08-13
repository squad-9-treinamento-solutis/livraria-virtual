package br.com.solutis.livraria.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PrintedBookPutRequestBody {
    @NotNull(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    @NotNull(message = "Price is required")
    private Float price;

    @NotNull(message = "Shipment is required")
    private Float shipment;

    @NotNull(message = "Stock is required")
    private Integer stock;

    @NotNull(message = "Publisher id is required")
    private Long publisherId;
}
