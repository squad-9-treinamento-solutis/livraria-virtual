package br.com.solutis.livraria.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DiscriminatorValue("ebook")
public class EBook extends Book{
    @NotNull(message = "Size is required")
    private Float size;
}
