package br.com.solutis.livraria.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "printed_books")
@DiscriminatorValue("printed")
public class PrintedBook extends Book{
    @NotNull(message = "Shipment is required")
    private Float shipment;

    @NotNull(message = "Stock is required")
    private Integer stock;
}
