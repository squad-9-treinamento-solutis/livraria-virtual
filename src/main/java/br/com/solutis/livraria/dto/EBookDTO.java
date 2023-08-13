package br.com.solutis.livraria.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EBookDTO {
    @Nullable
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    @NotNull(message = "Price is required")
    private Float price;

    @NotNull(message = "Size is required")
    private Float size;

    @NotNull(message = "Publisher id is required")
    private Long publisherId;
}
