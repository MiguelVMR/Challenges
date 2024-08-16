package com.agenda.commitment.boundary;

import com.agenda.commitment.controls.CommitmentGateway;
import com.agenda.commitment.entities.Commitment;
import com.agenda.utils.CustomPageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The Class CommitmentController
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
@RestController
@RequestMapping(path = "v1/commitment")
@Tag(name = "Commitment module")
public class CommitmentController {

    private final CommitmentGateway commitmentGateway;

    public CommitmentController(CommitmentGateway commitmentGateway) {
        this.commitmentGateway = commitmentGateway;
    }

    @Operation(summary = "Endpoint responsible for creating a new commitment")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid Commitment commitment) {

        commitmentGateway.save(commitment);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Endpoint responsible for obtaining a commitment")
    @GetMapping
    public ResponseEntity<Commitment> findById(@RequestParam(name = "id") UUID id) {

        return ResponseEntity.status(HttpStatus.OK).body(commitmentGateway.findById(id));
    }

    @Operation(summary = "Endpoint responsible for obtaining all commitment")
    @GetMapping("all")
    public ResponseEntity<Page<Commitment>> findAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sorting", required = false) String sorting,
            @RequestParam(name = "filter", required = false, defaultValue = "") String filter
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(commitmentGateway.findAll(filter, CustomPageable.getInstance(page, size, sorting)));
    }

    @Operation(summary = "Endpoint responsible for delete commitment")
    @DeleteMapping
    public ResponseEntity<Void> disable(@RequestParam(name = "id") UUID id) {

        commitmentGateway.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Endpoint responsável por editar um médico")
    @PatchMapping
    public ResponseEntity<Commitment> update(
            @RequestParam(name = "id") UUID id,
           @RequestBody @Valid Commitment commitment
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(commitmentGateway.update(id, commitment));
    }
}
