package com.pucetec2.demo.services

import com.dto.ProfessorRequest
import com.entities.Professor
import com.exceptions.BlankNameException
import com.exceptions.ProfessorNotFound
import com.repositories.ProfessorRepository
import com.services.ProfessorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class ProfessorServiceTest {

    private lateinit var professorRepository: ProfessorRepository
    private lateinit var professorService: ProfessorService

    @BeforeEach
    fun setUp() {
        professorRepository = mock()
        professorService = ProfessorService(professorRepository)
    }

    @Test
    fun `createProfessor should save and return the professor`() {
        val request = ProfessorRequest(name = "Dr. Garcia", email = "garcia@test.com")
        val saved = Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com")
        whenever(professorRepository.save(any())).thenReturn(saved)

        val result = professorService.createProfessor(request)

        assertEquals(1L, result.id)
        assertEquals("Dr. Garcia", result.name)
        verify(professorRepository).save(any())
    }

    @Test
    fun `createProfessor should throw BlankNameException when name is blank`() {
        val request = ProfessorRequest(name = "", email = "test@test.com")
        assertThrows(BlankNameException::class.java) {
            professorService.createProfessor(request)
        }
    }

    @Test
    fun `getAllProfessors should return list of professors`() {
        val professors = listOf(
            Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com"),
            Professor(id = 2L, name = "Dr. Lopez", email = "lopez@test.com")
        )
        whenever(professorRepository.findAll()).thenReturn(professors)

        val result = professorService.getAllProfessors()

        assertEquals(2, result.size)
    }

    @Test
    fun `getProfessorById should return the professor when it exists`() {
        val professor = Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com")
        whenever(professorRepository.findById(1L)).thenReturn(Optional.of(professor))

        val result = professorService.getProfessorById(1L)

        assertEquals(1L, result.id)
    }

    @Test
    fun `getProfessorById should throw ProfessorNotFound when it does not exist`() {
        whenever(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            professorService.getProfessorById(99L)
        }
    }

    @Test
    fun `updateProfessor should update and return the professor`() {
        val request = ProfessorRequest(name = "Dr. Garcia Lopez", email = "new@test.com")
        val existing = Professor(id = 1L, name = "Dr. Garcia", email = "garcia@test.com")
        val updated = Professor(id = 1L, name = "Dr. Garcia Lopez", email = "new@test.com")
        whenever(professorRepository.findById(1L)).thenReturn(Optional.of(existing))
        whenever(professorRepository.save(any())).thenReturn(updated)

        val result = professorService.updateProfessor(1L, request)

        assertEquals("Dr. Garcia Lopez", result.name)
    }

    @Test
    fun `updateProfessor should throw BlankNameException when name is blank`() {
        val request = ProfessorRequest(name = "", email = "test@test.com")
        assertThrows(BlankNameException::class.java) {
            professorService.updateProfessor(1L, request)
        }
    }

    @Test
    fun `updateProfessor should throw ProfessorNotFound when it does not exist`() {
        val request = ProfessorRequest(name = "Dr. Garcia", email = "garcia@test.com")
        whenever(professorRepository.findById(99L)).thenReturn(Optional.empty())
        assertThrows(ProfessorNotFound::class.java) {
            professorService.updateProfessor(99L, request)
        }
    }

    @Test
    fun `deleteProfessor should delete when it exists`() {
        whenever(professorRepository.existsById(1L)).thenReturn(true)
        professorService.deleteProfessor(1L)
        verify(professorRepository).deleteById(1L)
    }

    @Test
    fun `deleteProfessor should throw ProfessorNotFound when it does not exist`() {
        whenever(professorRepository.existsById(99L)).thenReturn(false)
        assertThrows(ProfessorNotFound::class.java) {
            professorService.deleteProfessor(99L)
        }
    }
}