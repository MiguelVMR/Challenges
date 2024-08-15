package com.agenda.commitment.entities;

import com.agenda.generic.GenericSchema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The Class CommitmentSchema
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_commitments")
public class CommitmentSchema extends GenericSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 30, nullable = false)
    private LocalDate date;

    @Column(length = 20, nullable = false)
    private String hour;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;


    public static class Builder {

        private CommitmentSchema commitmentSchema;

        public Builder builder() {
            this.commitmentSchema = new CommitmentSchema();
            return this;
        }
        public Builder setId(UUID id) {
            this.commitmentSchema.id = id;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.commitmentSchema.setDate(date);
            return this;
        }

        public Builder setHour(String hour) {
            this.commitmentSchema.setHour(hour);
            return this;
        }

        public Builder setName(String name) {
            this.commitmentSchema.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            this.commitmentSchema.setDescription(description);
            return this;
        }

        public CommitmentSchema build() {
            return this.commitmentSchema;
        }

    }
}
