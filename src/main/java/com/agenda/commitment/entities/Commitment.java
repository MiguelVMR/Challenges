package com.agenda.commitment.entities;

import com.agenda.generic.Generic;
import com.agenda.utils.annotations.ValidTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The Class Commitment
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commitment extends Generic {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Commitment date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ValidTime
    @Schema(defaultValue = "08:00")
    private String hour;

    private String description;

    @NotNull(message = "Commitment name is required")
    private String name;

    public static class Builder {

        private Commitment commitment;

        public Builder builder() {
            this.commitment = new Commitment();
            return this;
        }

        public Builder setId(UUID id) {
            this.commitment.setId(id);
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.commitment.setDate(date);
            return this;
        }

        public Builder setHour(String hour) {
            this.commitment.setHour(hour);
            return this;
        }

        public Builder setName(String name) {
            this.commitment.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            this.commitment.setDescription(description);
            return this;
        }

        public Commitment build() {
            return this.commitment;
        }

    }

}
