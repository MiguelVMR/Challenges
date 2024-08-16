package com.agenda.commitment.controls;

import com.agenda.commitment.configuration.AbstractIntegrationTest;
import com.agenda.commitment.entities.CommitmentSchema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommitmentRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private CommitmentRepository commitmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private CommitmentSchema commitment;

    @BeforeEach
    public void setUp(){
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
        CommitmentSchema commitmentSaved = commitmentRepository.save(commitment);

        assertNotNull(commitmentSaved);
        assertTrue(Objects.nonNull(commitmentSaved.getId()));
    }

    @Test
    @DisplayName("Test Given Commitent Page When FindAll Then Retur Pageble Commitment")
    void testGivenCommitentPage_whenfindAll_thenReturPagebleCommitment() {

        CommitmentSchema commitment2 = new CommitmentSchema();
        commitment2.setDescription("Workshop Backend tutoring");
        commitment2.setCreatedAt(LocalDateTime.now());
        commitment2.setName("Workshop");
        commitment2.setDate(LocalDate.now());
        commitment2.setHour("20:00");

        commitmentRepository.save(commitment);
        commitmentRepository.save(commitment2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<CommitmentSchema> exp = commitmentRepository.findAll("EXP", pageable);

        assertNotNull(exp);
        assertEquals(1, exp.getTotalElements());
    }

    @DisplayName("Given Commitment object when FindById then return Commitent")
    @Test
    void testGivenCommitentObject_whenFindById_thenReturnCommitment() {
        CommitmentSchema commitmentSaved = commitmentRepository.save(commitment);

        CommitmentSchema commitmentDb = commitmentRepository.findById(commitmentSaved.getId()).get();

        assertNotNull(commitmentSaved);
        assertEquals(commitmentSaved.getId(), commitmentDb.getId());
    }

    @DisplayName("Given Commitment object when disabled then return Commitent updated")
    @Test
    void testGivenCommitentObject_whenDisabled_thenReturnCommitmentUpdated() {

        CommitmentSchema commitmentSchema = commitmentRepository.save(commitment);

        commitmentRepository.disable(commitmentSchema.getId());

        entityManager.flush();
        entityManager.clear();

        CommitmentSchema commitmentSaved = commitmentRepository.findById(commitmentSchema.getId()).get();

        assertNotNull(commitmentSaved.getDisabledAt());
        assertEquals(true,commitmentSaved.isDisabled());

    }
}