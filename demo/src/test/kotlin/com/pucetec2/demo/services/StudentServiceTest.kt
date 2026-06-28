package com.pucetec2.demo.services

import com.dto.StudentRequest
import com.entities.Student
import com.exceptions.BlankNameException
import com.exceptions.StudentNotFoundException
import com.repositories.StudentRepository
import com.services.StudentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class StudentServiceTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var studentService: StudentService

    @BeforeEach
    fun setUp() {
        studentRepository = mock()
        studentService = StudentService(studentRepository)
    }

    @Test
    fun `createStudent should save and return the student`() {
        // arrange
        val request = StudentRequest(name = "Ana", email = "ana@test.com")
        val savedStudent = Student(id = 1L, name = "Ana", email = "ana@test.com")
        whenever(studentRepository.save(any())).thenReturn(savedStudent)

        // act
        val result = studentService.createStudent(request)

        // assert
        assertEquals(1L, result.id)
        assertEquals("Ana", result.name)
        assertEquals("ana@test.com", result.email)
        verify(studentRepository).save(any())
    }

    @Test
    fun `createStudent should throw BlankNameException when name is blank`() {
        // arrange
        val request = StudentRequest(name = "", email = "test@test.com")

        // act & assert
        assertThrows(BlankNameException::class.java) {
            studentService.createStudent(request)
        }
    }

    @Test
    fun `getAllStudents should return list of students`() {
        // arrange
        val students = listOf(
            Student(id = 1L, name = "Ana", email = "ana@test.com"),
            Student(id = 2L, name = "Luis", email = "luis@test.com")
        )
        whenever(studentRepository.findAll()).thenReturn(students)

        // act
        val result = studentService.getAllStudents()

        // assert
        assertEquals(2, result.size)
        assertEquals("Ana", result[0].name)
    }

    @Test
    fun `getStudentById should return the student when it exists`() {
        // arrange
        val student = Student(id = 1L, name = "Ana", email = "ana@test.com")
        whenever(studentRepository.findById(1L)).thenReturn(Optional.of(student))

        // act
        val result = studentService.getStudentById(1L)

        // assert
        assertEquals(1L, result.id)
        assertEquals("Ana", result.name)
    }

    @Test
    fun `getStudentById should throw StudentNotFoundException when it does not exist`() {
        // arrange
        whenever(studentRepository.findById(99L)).thenReturn(Optional.empty())

        // act & assert
        assertThrows(StudentNotFoundException::class.java) {
            studentService.getStudentById(99L)
        }
    }

    @Test
    fun `updateStudent should update and return the student`() {
        // arrange
        val request = StudentRequest(name = "Ana Updated", email = "ana.new@test.com")
        val existing = Student(id = 1L, name = "Ana", email = "ana@test.com")
        val updated = Student(id = 1L, name = "Ana Updated", email = "ana.new@test.com")
        whenever(studentRepository.findById(1L)).thenReturn(Optional.of(existing))
        whenever(studentRepository.save(any())).thenReturn(updated)

        // act
        val result = studentService.updateStudent(1L, request)

        // assert
        assertEquals("Ana Updated", result.name)
        assertEquals("ana.new@test.com", result.email)
    }

    @Test
    fun `updateStudent should throw BlankNameException when name is blank`() {
        // arrange
        val request = StudentRequest(name = "", email = "test@test.com")

        // act & assert
        assertThrows(BlankNameException::class.java) {
            studentService.updateStudent(1L, request)
        }
    }

    @Test
    fun `updateStudent should throw StudentNotFoundException when it does not exist`() {
        // arrange
        val request = StudentRequest(name = "Ana", email = "ana@test.com")
        whenever(studentRepository.findById(99L)).thenReturn(Optional.empty())

        // act & assert
        assertThrows(StudentNotFoundException::class.java) {
            studentService.updateStudent(99L, request)
        }
    }

    @Test
    fun `deleteStudent should delete when it exists`() {
        // arrange
        whenever(studentRepository.existsById(1L)).thenReturn(true)

        // act
        studentService.deleteStudent(1L)

        // assert
        verify(studentRepository).deleteById(1L)
    }

    @Test
    fun `deleteStudent should throw StudentNotFoundException when it does not exist`() {
        // arrange
        whenever(studentRepository.existsById(99L)).thenReturn(false)

        // act & assert
        assertThrows(StudentNotFoundException::class.java) {
            studentService.deleteStudent(99L)
        }
    }
}