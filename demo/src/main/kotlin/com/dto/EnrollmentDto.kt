package com.dto

data class EnrollmentRequest(
    val studentId: Long,
    val subjectId: Long,
)

data class EnrollmentResponse(
    val id: Long,
    val createdAt: String,
    val status: String,
    val subject: SubjectResponse,
    val student: StudentResponse,
)
data class EnrollmentUpdateRequest(
    val status: String,
)