package br.com.solutis.livraria.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SaleDTO {
    @Nullable
    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String clientName;

    @NotNull(message = "Value is required")
    private Float value;

    @NotNull(message = "Books id are required")
    @NotEmpty(message = "Books id are required")
    private List<Long> booksId;

}
