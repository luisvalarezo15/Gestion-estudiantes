package com.controllers

import com.dto.StudentRequest
import com.dto.StudentResponse
import com.services.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RestController
open class StudentController(
    private val studentService: StudentService
) {

    @PostMapping("/api/students")
    @ResponseStatus(HttpStatus.CREATED)
    open fun createStudent(
        @RequestBody request: StudentRequest
    ): StudentResponse {
        return studentService.createStudent(request)
    }

    @GetMapping("/api/students")
    open fun getAllStudents(): List<StudentResponse> {
        return studentService.getAllStudents()
    }

    @GetMapping("/api/students/{id}")
    open fun getStudentById(
        @PathVariable id: Long
    ): StudentResponse {
        return studentService.getStudentById(id)
    }
    @PutMapping("/api/students/{id}")
    open fun updateStudent(
        @PathVariable id: Long,
        @RequestBody request: StudentRequest
    ): StudentResponse {
        return studentService.updateStudent(id, request)
    }

    @DeleteMapping("/api/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    open fun deleteStudent(
        @PathVariable id: Long
    ) {
        studentService.deleteStudent(id)
    }
}