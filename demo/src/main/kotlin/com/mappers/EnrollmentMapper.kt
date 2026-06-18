package com.mappers

import com.dto.EnrollmentResponse
import com.entities.Enrollment

fun Enrollment.toResponse() = EnrollmentResponse(
    id = this.id,
    createdAt = this.createdAt.toString(),
    status = this.status,
    subject = this.subject.toResponse(),
    student = this.student.toResponse()
)