package com.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
open class GlobalExceptionHandler {

    @ExceptionHandler(value = [BlankNameException::class])
    open fun handleBlankNameException(
        e: BlankNameException
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "Nombre en blanco - ERROR",
            source = "StudentService"
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    @ExceptionHandler(value = [StudentNotFoundException::class])
    open fun handleStudentNotFoundException(
        e: StudentNotFoundException
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "Estudiante no encontrado - ERROR",
            source = "StudentService"
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }

    @ExceptionHandler(value = [SubjectNotFoundException::class])
    open fun handleSubjectNotFoundException(
        e: SubjectNotFoundException
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "Materia no encontrada - ERROR",
            source = "SubjectService"
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }

    @ExceptionHandler(value = [ProfessorNotFound::class])
    open fun handleProfessorNotFound(
        e: ProfessorNotFound
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "Profesor no encontrado - ERROR",
            source = "SubjectService"
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }
    @ExceptionHandler(value = [EnrollmentNotFound::class])
    open fun handleEnrollmentNotFound(
        e: EnrollmentNotFound
    ): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            message = e.message ?: "Inscripción no encontrada - ERROR",
            source = "EnrollmentService"
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }
}

data class ExceptionResponse(
    val message: String,
    val source: String,

)