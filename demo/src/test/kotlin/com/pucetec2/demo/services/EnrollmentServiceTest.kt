package com.pucetec2.demo.services

import com.dto.EnrollmentRequest
import com.dto.EnrollmentUpdateRequest
import com.entities.Enrollment
import com.entities.Professor
import com.entities.Student
import com.entities.Subject
import com.exceptions.EnrollmentNotFound
import com.exceptions.StudentNotFoundException
import com.exceptions.SubjectNotFoundException
import com.repositories.EnrollmentRepository
import com.repositories.StudentRepository
import com.repositories.SubjectRepository
import com.services.EnrollmentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class EnrollmentServiceTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var subjectRepository: SubjectRepository
    private lateinit var enrollmentRepository: EnrollmentRepository
    private lateinit var enrollmentService: EnrollmentService

    @BeforeEach
    fun setUp() {
        studentRepository = mock()
        subjectRepository = mock()
        enrollmentRepository = mock()
        enrollmentService = EnrollmentService(studentRepository, subjectRepository, enrollmentRepository)
    }

    private fun student() = Student(id = 1L, name = "Ana", email = "ana@test.com")
    private fun subject() = Subject(
        id = 1L, name = "Arquitectura", code = "AE-101",
        professor = Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com")
    )
    private fun enrollment() = Enrollment(id = 1L, status = "INSCRITO", student = student(), subject = subject())

    @Test
    fun `createEnrollment should save and return the enrollment`() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 1L)
        whenever(studentRepository.findById(1L)).thenReturn(Optional.of(student()))
        whenever(subjectRepository.findById(1L)).thenReturn(Optional.of(subject()))
        whenever(enrollmentRepository.save(any())).thenReturn(enrollment())

        val result = enrollmentService.createEnrollment(request)

        assertEquals(1L, result.id)
        assertEquals("INSCRITO", result.status)
        verify(enrollmentRepository).save(any())
    }

    @Test
    fun `createEnrollment should throw StudentNotFoundException when student does not exist`() {
        val request = EnrollmentRequest(studentId = 99L, subjectId = 1L)
        whenever(studentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(StudentNotFoundException::class.java) {
            enrollmentService.createEnrollment(request)
        }
    }

    @Test
    fun `createEnrollment should throw SubjectNotFoundException when subject does not exist`() {
        val request = EnrollmentRequest(studentId = 1L, subjectId = 99L)
        whenever(studentRepository.findById(1L)).thenReturn(Optional.of(student()))
        whenever(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFoundException::class.java) {
            enrollmentService.createEnrollment(request)
        }
    }

    @Test
    fun `getAllEnrollments should return list of enrollments`() {
        whenever(enrollmentRepository.findAll()).thenReturn(listOf(enrollment()))

        val result = enrollmentService.getAllEnrollments()

        assertEquals(1, result.size)
    }

    @Test
    fun `getEnrollmentById should return the enrollment when it exists`() {
        whenever(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment()))

        val result = enrollmentService.getEnrollmentById(1L)

        assertEquals(1L, result.id)
    }

    @Test
    fun `getEnrollmentById should throw EnrollmentNotFound when it does not exist`() {
        whenever(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.getEnrollmentById(99L)
        }
    }

    @Test
    fun `updateEnrollment should update status and return the enrollment`() {
        val request = EnrollmentUpdateRequest(status = "APROBADO")
        val updated = Enrollment(id = 1L, status = "APROBADO", student = student(), subject = subject())
        whenever(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment()))
        whenever(enrollmentRepository.save(any())).thenReturn(updated)

        val result = enrollmentService.updateEnrollment(1L, request)

        assertEquals("APROBADO", result.status)
    }

    @Test
    fun `updateEnrollment should throw EnrollmentNotFound when it does not exist`() {
        val request = EnrollmentUpdateRequest(status = "APROBADO")
        whenever(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.updateEnrollment(99L, request)
        }
    }

    @Test
    fun `deleteEnrollment should delete when it exists`() {
        whenever(enrollmentRepository.existsById(1L)).thenReturn(true)
        enrollmentService.deleteEnrollment(1L)
        verify(enrollmentRepository).deleteById(1L)
    }

    @Test
    fun `deleteEnrollment should throw EnrollmentNotFound when it does not exist`() {
        whenever(enrollmentRepository.existsById(99L)).thenReturn(false)
        assertThrows(EnrollmentNotFound::class.java) {
            enrollmentService.deleteEnrollment(99L)
        }
    }
}