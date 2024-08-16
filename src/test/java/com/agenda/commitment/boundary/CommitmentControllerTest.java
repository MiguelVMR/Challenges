package com.agenda.commitment.boundary;

import com.agenda.commitment.controls.CommitmentException;
import com.agenda.commitment.controls.CommitmentGateway;
import com.agenda.commitment.entities.Commitment;
import com.agenda.commitment.entities.CommitmentSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
public class CommitmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommitmentGateway commitmentGateway;

    private Commitment commitment;

    @BeforeEach
    public void setUp() {
        commitment = new Commitment();
        commitment.setDescription("EXP mentoring online class");
        commitment.setCreatedAt(LocalDateTime.now());
        commitment.setName("EXP");
        commitment.setDate(LocalDate.now());
        commitment.setHour("20:00");
    }

    @Test
    @DisplayName("Given Commitment When Create Commitment Then Return Commitment saved")
    void givenCommitment_WhenCreateCommitment_ThenReturnCommitmentsave() throws Exception {

        doNothing().when(commitmentGateway).save(any(Commitment.class));

        ResultActions response = mockMvc.perform(post("/v1/commitment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));

        response.andDo(print())
                .andExpect(status().isNoContent());

        verify(commitmentGateway, times(1)).save(any(Commitment.class));
    }

    @Test
    @DisplayName("Given CommitmentId When Find By Id Then Return Commitment")
    void givenCommitmentId_WhenFindById_ThenReturnCommitment() throws Exception {

        UUID id = UUID.randomUUID();

        given(commitmentGateway.findById(id)).willReturn(commitment);


        ResultActions response = mockMvc.perform(get("/v1/commitment")
                .param("id", id.toString()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(commitment.getName()))
                .andExpect(jsonPath("$.description").value(commitment.getDescription()));

        verify(commitmentGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Given Invalid CommitmentId When Find By Id Then Return NotFound")
    void givenInvalidCommitmentId_WhenFindById_ThenReturnNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        given(commitmentGateway.findById(id)).willThrow(new CommitmentException.NotFound());


        ResultActions response = mockMvc.perform(get("/v1/commitment")
                .param("id", id.toString()));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("Given Commitments When Find All Then Return Commitments")
    void givenCommitments_WhenFindAll_ThenReturnCommitments() throws Exception {

        List<Commitment> commitments = List.of(commitment);

        Page<Commitment> page = new PageImpl<>(commitments);

        given(commitmentGateway.findAll(anyString(), any(Pageable.class))).willReturn(page);


        mockMvc.perform(get("/v1/commitment/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sorting", "")
                        .param("filter", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(commitments.size()));

        verify(commitmentGateway, times(1)).findAll(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Given Commitment ID When Delete Commitment Then Return No Content")
    void givenCommitmentId_WhenDeleteCommitment_ThenReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(commitmentGateway).disable(id);

        mockMvc.perform(delete("/v1/commitment")
                        .param("id", id.toString()))
                .andExpect(status().isNoContent());

        verify(commitmentGateway, times(1)).disable(id);
    }

    @Test
    @DisplayName("Given Commitment ID and Commitment When Update Commitment Then Return No Content")
    void givenCommitmentIdAndCommitment_WhenUpdateCommitment_ThenReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        commitment.setName("Mentoring");

        given(commitmentGateway.update(any(UUID.class), any(Commitment.class))).willReturn(commitment);

        ResultActions response = mockMvc.perform(patch("/v1/commitment")
                .param("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(commitment.getName()))
                .andExpect(jsonPath("$.description").value(commitment.getDescription()));

        verify(commitmentGateway, times(1)).update(any(UUID.class), any(Commitment.class));
    }

    @Test
    @DisplayName("Given Invalid Commitment ID and Commitment When Update Commitment Then Return NotFound")
    void givenInvalidCommitmentIdAndCommitment_WhenUpdateCommitment_ThenReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        commitment.setName("Mentoring");

        given(commitmentGateway.update(any(UUID.class), any(Commitment.class))).willThrow(new CommitmentException.NotFound());

        ResultActions response = mockMvc.perform(patch("/v1/commitment")
                .param("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}