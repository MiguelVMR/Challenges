package com.agenda.commitment.controls;

import com.agenda.commitment.entities.Commitment;
import com.agenda.commitment.entities.CommitmentMapper;
import com.agenda.commitment.entities.CommitmentSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommitmentGatewayTest {

    @Mock
    private CommitmentRepository commitmentRepository;

    @InjectMocks
    private CommitmentGateway commitmentGateway;

    private CommitmentSchema commitment;

    @BeforeEach
    public void setUp() {
        commitment = new CommitmentSchema();
        commitment.setDescription("EXP mentoring online class");
        commitment.setCreatedAt(LocalDateTime.now());
        commitment.setName("EXP");
        commitment.setDate(LocalDate.now());
        commitment.setHour("20:00");
    }

    @DisplayName("Given Commitment object when save then return Saved Commitent")
    @Test
    void testGivenCommitentObject_whenSave_thenReturnSavedCommitment() {
        Commitment commitmentDTO = CommitmentMapper.toDTO(commitment);
        CommitmentSchema commitmentEntity = CommitmentMapper.toEntity(commitmentDTO);

        commitmentGateway.save(commitmentDTO);

        ArgumentCaptor<CommitmentSchema> captor = ArgumentCaptor.forClass(CommitmentSchema.class);

        verify(commitmentRepository).save(captor.capture());

        CommitmentSchema captured = captor.getValue();

        assertEquals(commitmentEntity.getDescription(), captured.getDescription());
        assertEquals(commitmentEntity.getName(), captured.getName());
        assertEquals(commitmentEntity.getDate(), captured.getDate());
        assertEquals(commitmentEntity.getHour(), captured.getHour());

    }

    @DisplayName("Given valid ID when findById then return Commitment")
    @Test
    void testFindById_whenValidId_thenReturnCommitment() {
        UUID id = UUID.randomUUID();

        commitment.setId(id);

        when(commitmentRepository.findById(id)).thenReturn(Optional.of(commitment));

        Commitment result = commitmentGateway.findById(id);

        verify(commitmentRepository).findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @DisplayName("Given invalid ID when findById then throw NotFoundException")
    @Test
    void testFindById_whenInvalidId_thenThrowNotFoundException() {
        UUID id = UUID.randomUUID();

        when(commitmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommitmentException.NotFound.class, () -> {
            commitmentGateway.findById(id);
        });

        verify(commitmentRepository).findById(id);
    }


    @DisplayName("Given filter and pageable when findAll then return Page of Commitments")
    @Test
    void testFindAll_whenFilterAndPageable_thenReturnPageOfCommitments() {
        String filter = "EXP";
        Pageable pageable = PageRequest.of(0, 10);

        Page<CommitmentSchema> commitmentEntities = new PageImpl<>(List.of(commitment));

        when(commitmentRepository.findAll(filter, pageable)).thenReturn(commitmentEntities);

        Page<Commitment> result = commitmentGateway.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(commitmentRepository).findAll(filter, pageable);
    }



    @DisplayName("Given valid ID when disable then repository disable method is called")
    @Test
    void testDisable_whenValidId_thenRepositoryDisableMethodIsCalled() {
        UUID id = UUID.randomUUID();

        doNothing().when(commitmentRepository).disable(id);

        commitmentGateway.disable(id);

        verify(commitmentRepository).disable(id);
    }


    @DisplayName("Given valid ID and Commitment when update then return updated Commitment")
    @Test
    void testUpdate_whenValidIdAndCommitment_thenReturnUpdatedCommitment() {
        UUID id = UUID.randomUUID();

        Commitment commitmentDTO = new Commitment();
        commitmentDTO.setName("Updated Name");

        CommitmentSchema commitmentEntity = new CommitmentSchema();
        commitmentEntity.setId(id);
        commitmentEntity.setName("EXP");

        when(commitmentRepository.findById(id)).thenReturn(Optional.of(commitmentEntity));
        when(commitmentRepository.save(any(CommitmentSchema.class))).thenAnswer(invocation -> {
            CommitmentSchema savedEntity = invocation.getArgument(0);
            savedEntity.setName(commitmentDTO.getName());
            return savedEntity;
        });

        Commitment result = commitmentGateway.update(id, commitmentDTO);

        assertEquals(commitmentDTO.getName(), result.getName());
        verify(commitmentRepository).findById(id);
        verify(commitmentRepository).save(any(CommitmentSchema.class));
    }

    @DisplayName("Given invalid ID when update then throw NotFoundException")
    @Test
    void testUpdate_whenInvalidId_thenThrowNotFoundException() {
        UUID id = UUID.randomUUID();
        Commitment commitment = new Commitment();

        when(commitmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommitmentException.NotFound.class, () -> commitmentGateway.update(id, commitment));
        verify(commitmentRepository).findById(id);
    }

}