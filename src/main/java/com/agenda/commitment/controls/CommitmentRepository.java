package com.agenda.commitment.controls;

import com.agenda.commitment.entities.Commitment;
import com.agenda.commitment.entities.CommitmentSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The Interface CommitmentRepository
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
@Transactional
@Repository
public interface CommitmentRepository extends JpaRepository<CommitmentSchema, UUID> {

    @Query("""
    SELECT c FROM CommitmentSchema c
    WHERE c.disabled = false
            AND (
                    UPPER(c.name) LIKE UPPER(CONCAT('%', :filter, '%')) OR
                    UPPER(c.description) LIKE UPPER(CONCAT('%', :filter, '%'))
                )
    """)
    Page<CommitmentSchema> findAll(@Param("filter") String filter, Pageable pageable);

    @Modifying
    @Query("""
            UPDATE CommitmentSchema c
            SET c.disabled = TRUE, c.disabledAt = CURRENT_TIMESTAMP, c.updatedAt = CURRENT_TIMESTAMP
            """)
    void disable(UUID id);
}
