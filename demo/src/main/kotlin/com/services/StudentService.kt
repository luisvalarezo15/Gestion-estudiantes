package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

// es el que almacena la logica del negocio
@Service
open class StudentService(
    private val studentRepository: StudentRepository
) {

    open private val logger = LoggerFactory.getLogger(clazz = StudentService::class.java)

    open fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creating student ${request.name}")

        // validar
        // TODO: validar que el estudiante no exista previamente

        // crear entidad
        val studentEntity = Student(
            name = request.name,
            email = request.email,
        )

        // guardar entidad
        val savedStudent = studentRepository.save(studentEntity)

        // retornar response
        return StudentResponse(
            id = savedStudent.id,
            name = savedStudent.name,
            email = savedStudent.email,
        )
    }

    open fun getAllStudents(): List<StudentResponse> {
        logger.info("Getting all students")

        // consultar todos los estudiantes
        val savedStudents = studentRepository.findAll()

        // convertir al response adecuado
        return savedStudents.map {
            StudentResponse(
                id = it.id,
                name = it.name,
                email = it.email,
            )
        }
    }
}