package br.com.solutis.livraria.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "e_books")
public class EBook extends Book{
    @NotNull(message = "Size is required")
    private Float size;

    @Override
    public String toString() {
        return "EBook[" +
                "size=" + size +
                "] " + super.toString();
    }
}
