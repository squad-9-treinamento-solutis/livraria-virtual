package br.com.solutis.livraria.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EBookDTO extends BookDTO {
    @NotNull(message = "Size is required")
    private Float size;
}
