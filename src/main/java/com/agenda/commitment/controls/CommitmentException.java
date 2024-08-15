package com.agenda.commitment.controls;

import com.agenda.application.controls.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * The Class CommitmentException
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
public class CommitmentException {

    public static class NotFound extends ApplicationException{
        public NotFound() {
            super("Commitment not found", HttpStatus.NOT_FOUND);
        }
    }
}
