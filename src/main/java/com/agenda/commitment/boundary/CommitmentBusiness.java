package com.agenda.commitment.boundary;

import com.agenda.commitment.controls.CommitmentGateway;
import com.agenda.commitment.entities.Commitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * The Class CommitmentBusiness
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
@Component
public class CommitmentBusiness {

    private final CommitmentGateway commitmentGateway;

    public CommitmentBusiness(CommitmentGateway commitmentGateway) {
        this.commitmentGateway = commitmentGateway;
    }

    public void save(Commitment commitment) {
        commitmentGateway.save(commitment);
    }

    public Commitment findById(UUID id) {
        return commitmentGateway.findById(id);
    }

    public Page<Commitment> findAll(String filter, Pageable pageable) {

        return commitmentGateway.findAll(filter,pageable);
    }

    public void disable(UUID id) {
        commitmentGateway.disable(id);
    }

    public Commitment update(UUID id, Commitment commitment) {
        return commitmentGateway.update(id, commitment);
    }
}
