package com.services

import com.dto.StudentRequest
import com.dto.StudentResponse
import com.entities.Student
import com.exceptions.BlankNameException
import com.exceptions.StudentNotFoundException
import com.mappers.toResponse
import com.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
open class StudentService(
    private val studentRepository: StudentRepository
) {

    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    open fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creating student")

        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }

        val studentEntity = Student(
            name = request.name,
            email = request.email
        )

        val savedStudent = studentRepository.save(studentEntity)

        return savedStudent.toResponse()
    }

    open fun getAllStudents(): List<StudentResponse> {
        logger.info("Getting all students")

        val savedStudents = studentRepository.findAll()

        return savedStudents.map { it.toResponse() }
    }

    open fun getStudentById(id: Long): StudentResponse {
        logger.info("Getting student by id")

        val student = studentRepository.findById(id).orElseThrow {
            StudentNotFoundException("Estudiante no encontrado - ERROR")
        }

        return student.toResponse()
    }

    open fun updateStudent(id: Long, request: StudentRequest): StudentResponse {
        logger.info("Updating student")

        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }

        val student = studentRepository.findById(id).orElseThrow {
            StudentNotFoundException("Estudiante no encontrado - ERROR")
        }

        val updatedStudent = Student(
            id = student.id,
            name = request.name,
            email = request.email
        )

        return studentRepository.save(updatedStudent).toResponse()
    }

    open fun deleteStudent(id: Long) {
        logger.info("Deleting student")

        if (!studentRepository.existsById(id)) {
            throw StudentNotFoundException("Estudiante no encontrado - ERROR")
        }

        studentRepository.deleteById(id)
    }
}