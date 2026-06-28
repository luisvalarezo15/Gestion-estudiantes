package com.pucetec2.demo.services

import com.dto.SubjectRequest
import com.entities.Professor
import com.entities.Subject
import com.exceptions.BlankNameException
import com.exceptions.ProfessorNotFound
import com.exceptions.SubjectNotFoundException
import com.repositories.ProfessorRepository
import com.repositories.SubjectRepository
import com.services.SubjectService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class SubjectServiceTest {

    private lateinit var professorRepository: ProfessorRepository
    private lateinit var subjectRepository: SubjectRepository
    private lateinit var subjectService: SubjectService

    @BeforeEach
    fun setUp() {
        professorRepository = mock()
        subjectRepository = mock()
        subjectService = SubjectService(professorRepository, subjectRepository)
    }

    private fun professor() = Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com")

    @Test
    fun `createSubject should save and return the subject`() {
        val request = SubjectRequest(name = "Arquitectura", code = "AE-101", professorId = 1L)
        val prof = professor()
        val saved = Subject(id = 1L, name = "Arquitectura", code = "AE-101", professor = prof)
        whenever(professorRepository.findById(1L)).thenReturn(Optional.of(prof))
        whenever(subjectRepository.save(any())).thenReturn(saved)

        val result = subjectService.createSubject(request)

        assertEquals(1L, result.id)
        assertEquals("Arquitectura", result.name)
        verify(subjectRepository).save(any())
    }

    @Test
    fun `createSubject should throw BlankNameException when name is blank`() {
        val request = SubjectRequest(name = "", code = "AE-101", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.createSubject(request)
        }
    }

    @Test
    fun `createSubject should throw BlankNameException when code is blank`() {
        val request = SubjectRequest(name = "Arquitectura", code = "", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.createSubject(request)
        }
    }

    @Test
    fun `createSubject should throw ProfessorNotFound when professor does not exist`() {
        val request = SubjectRequest(name = "Arquitectura", code = "AE-101", professorId = 99L)
        whenever(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            subjectService.createSubject(request)
        }
    }

    @Test
    fun `getAllSubjects should return list of subjects`() {
        val subjects = listOf(
            Subject(id = 1L, name = "Arquitectura", code = "AE-101", professor = professor())
        )
        whenever(subjectRepository.findAll()).thenReturn(subjects)

        val result = subjectService.getAllSubjects()

        assertEquals(1, result.size)
    }

    @Test
    fun `getSubjectById should return the subject when it exists`() {
        val subject = Subject(id = 1L, name = "Arquitectura", code = "AE-101", professor = professor())
        whenever(subjectRepository.findById(1L)).thenReturn(Optional.of(subject))

        val result = subjectService.getSubjectById(1L)

        assertEquals(1L, result.id)
    }

    @Test
    fun `getSubjectById should throw SubjectNotFoundException when it does not exist`() {
        whenever(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFoundException::class.java) {
            subjectService.getSubjectById(99L)
        }
    }

    @Test
    fun `updateSubject should update and return the subject`() {
        val request = SubjectRequest(name = "Arquitectura Avanzada", code = "AE-201", professorId = 1L)
        val prof = professor()
        val existing = Subject(id = 1L, name = "Arquitectura", code = "AE-101", professor = prof)
        val updated = Subject(id = 1L, name = "Arquitectura Avanzada", code = "AE-201", professor = prof)
        whenever(subjectRepository.findById(1L)).thenReturn(Optional.of(existing))
        whenever(professorRepository.findById(1L)).thenReturn(Optional.of(prof))
        whenever(subjectRepository.save(any())).thenReturn(updated)

        val result = subjectService.updateSubject(1L, request)

        assertEquals("Arquitectura Avanzada", result.name)
    }

    @Test
    fun `updateSubject should throw BlankNameException when name is blank`() {
        val request = SubjectRequest(name = "", code = "AE-101", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun `updateSubject should throw BlankNameException when code is blank`() {
        val request = SubjectRequest(name = "Arquitectura", code = "", professorId = 1L)
        assertThrows(BlankNameException::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun `updateSubject should throw SubjectNotFoundException when subject does not exist`() {
        val request = SubjectRequest(name = "Arquitectura", code = "AE-101", professorId = 1L)
        whenever(subjectRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(SubjectNotFoundException::class.java) {
            subjectService.updateSubject(99L, request)
        }
    }

    @Test
    fun `updateSubject should throw ProfessorNotFound when professor does not exist`() {
        val request = SubjectRequest(name = "Arquitectura", code = "AE-101", professorId = 99L)
        val existing = Subject(id = 1L, name = "Arquitectura", code = "AE-101", professor = professor())
        whenever(subjectRepository.findById(1L)).thenReturn(Optional.of(existing))
        whenever(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            subjectService.updateSubject(1L, request)
        }
    }

    @Test
    fun `deleteSubject should delete when it exists`() {
        whenever(subjectRepository.existsById(1L)).thenReturn(true)
        subjectService.deleteSubject(1L)
        verify(subjectRepository).deleteById(1L)
    }

    @Test
    fun `deleteSubject should throw SubjectNotFoundException when it does not exist`() {
        whenever(subjectRepository.existsById(99L)).thenReturn(false)
        assertThrows(SubjectNotFoundException::class.java) {
            subjectService.deleteSubject(99L)
        }
    }
}