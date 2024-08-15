package com.agenda.commitment.controls;

import com.agenda.commitment.entities.Commitment;
import com.agenda.commitment.entities.CommitmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import java.util.UUID;

/**
 * The Class CommitmentGateway
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
@Component
public class CommitmentGateway {

    private final CommitmentRepository commitmentRepository;

    public CommitmentGateway(CommitmentRepository commitmentRepository) {
        this.commitmentRepository = commitmentRepository;
    }


    public void save(Commitment commitment) {
        commitmentRepository.save(CommitmentMapper.toEntity(commitment));
    }


    public Commitment findById(UUID id) {
        return commitmentRepository.findById(id).map(CommitmentMapper::toDTO)
                .orElseThrow(CommitmentException.NotFound::new);
    }

    public Page<Commitment> findAll(String filter, Pageable pageable) {
        return commitmentRepository.findAll(filter, pageable).map(CommitmentMapper::toDTO);
    }

    public void disable(UUID id) {
        commitmentRepository.disable(id);
    }

    public Commitment update(UUID id, Commitment commitment) {
        Commitment commitmentDb = commitmentRepository.findById(id).map(CommitmentMapper::toDTO)
                .orElseThrow(CommitmentException.NotFound::new);

        BeanUtils.copyProperties(commitment, commitmentDb, "id");

        return CommitmentMapper.toDTO(commitmentRepository.save(CommitmentMapper.toEntity(commitmentDb)));
    }
}
