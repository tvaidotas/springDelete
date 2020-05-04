package com.qa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "The notebook you're looking for isn't here")
public class NoteBookNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = -2591687720244290021L;

}
