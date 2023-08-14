package br.com.solutis.livraria.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    @Nullable
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    @NotNull(message = "Price is required")
    private Float price;

    @NotNull(message = "Publisher id is required")
    private Long publisherId;

    @NotNull(message = "Authors id are required")
    @NotEmpty(message = "Authors id are required")
    private List<Long> authorsId;
}
