package com.services

import com.dto.EnrollmentRequest
import com.dto.EnrollmentResponse
import com.dto.EnrollmentUpdateRequest
import com.entities.Enrollment
import com.exceptions.EnrollmentNotFound
import com.exceptions.StudentNotFoundException
import com.exceptions.SubjectNotFoundException
import com.mappers.toResponse
import com.repositories.EnrollmentRepository
import com.repositories.StudentRepository
import com.repositories.SubjectRepository
import org.springframework.stereotype.Service

@Service
open class EnrollmentService(
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val enrollmentRepository: EnrollmentRepository
) {

    open fun createEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        val student = studentRepository.findById(request.studentId).orElseThrow {
            StudentNotFoundException("Estudiante no encontrado: ${request.studentId}")
        }

        val subject = subjectRepository.findById(request.subjectId).orElseThrow {
            SubjectNotFoundException("Materia no encontrada: ${request.subjectId}")
        }

        val enrollment = Enrollment(
            student = student,
            subject = subject,
            status = "INSCRITO"
        )

        return enrollmentRepository.save(enrollment).toResponse()
    }

    open fun getAllEnrollments(): List<EnrollmentResponse> {
        return enrollmentRepository.findAll().map { it.toResponse() }
    }

    open fun getEnrollmentById(id: Long): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id).orElseThrow {
            EnrollmentNotFound("Inscripción no encontrada: $id")
        }
        return enrollment.toResponse()
    }

    open fun updateEnrollment(id: Long, request: EnrollmentUpdateRequest): EnrollmentResponse {
        val enrollment = enrollmentRepository.findById(id).orElseThrow {
            EnrollmentNotFound("Inscripción no encontrada: $id")
        }

        val updatedEnrollment = Enrollment(
            id = enrollment.id,
            createdAt = enrollment.createdAt,
            status = request.status,
            student = enrollment.student,
            subject = enrollment.subject
        )

        return enrollmentRepository.save(updatedEnrollment).toResponse()
    }

    open fun deleteEnrollment(id: Long) {
        if (!enrollmentRepository.existsById(id)) {
            throw EnrollmentNotFound("Inscripción no encontrada: $id")
        }
        enrollmentRepository.deleteById(id)
    }
}