package com.controllers

import com.dto.EnrollmentRequest
import com.dto.EnrollmentResponse
import com.dto.EnrollmentUpdateRequest
import com.services.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
open class EnrollmentController(
    private val enrollmentService: EnrollmentService
) {

    @PostMapping("/api/enrollments")
    @ResponseStatus(HttpStatus.CREATED)
    open fun createEnrollment(
        @RequestBody request: EnrollmentRequest
    ): EnrollmentResponse {
        return enrollmentService.createEnrollment(request)
    }

    @GetMapping("/api/enrollments")
    open fun getAllEnrollments(): List<EnrollmentResponse> {
        return enrollmentService.getAllEnrollments()
    }

    @GetMapping("/api/enrollments/{id}")
    open fun getEnrollmentById(
        @PathVariable id: Long
    ): EnrollmentResponse {
        return enrollmentService.getEnrollmentById(id)
    }

    @PutMapping("/api/enrollments/{id}")
    open fun updateEnrollment(
        @PathVariable id: Long,
        @RequestBody request: EnrollmentUpdateRequest
    ): EnrollmentResponse {
        return enrollmentService.updateEnrollment(id, request)
    }

    @DeleteMapping("/api/enrollments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    open fun deleteEnrollment(
        @PathVariable id: Long
    ) {
        enrollmentService.deleteEnrollment(id)
    }
}