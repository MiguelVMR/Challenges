package com.agenda.commitment.entities;

/**
 * The Class CommitmentMapper
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
public class CommitmentMapper {


    public static CommitmentSchema toEntity(Commitment commitment) {
        return new CommitmentSchema.Builder().builder()
                .setId(commitment.getId())
                .setDate(commitment.getDate())
                .setDescription(commitment.getDescription())
                .setHour(commitment.getHour())
                .setName(commitment.getName())
                .build();
    }

    public static Commitment toDTO(CommitmentSchema commitmentSchema) {
        return new Commitment.Builder().builder()
                .setDate(commitmentSchema.getDate())
                .setId(commitmentSchema.getId())
                .setDescription(commitmentSchema.getDescription())
                .setHour(commitmentSchema.getHour())
                .setName(commitmentSchema.getName())
                .build();
    }
}
