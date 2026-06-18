package com.controllers

import com.dto.SubjectRequest
import com.dto.SubjectResponse
import com.services.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
open class SubjectController(
    private val subjectService: SubjectService
) {

    @PostMapping("/api/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    open fun createSubject(
        @RequestBody request: SubjectRequest
    ): SubjectResponse {
        return subjectService.createSubject(request)
    }

    @GetMapping("/api/subjects")
    open fun getAllSubjects(): List<SubjectResponse> {
        return subjectService.getAllSubjects()
    }

    @GetMapping("/api/subjects/{id}")
    open fun getSubjectById(
        @PathVariable id: Long
    ): SubjectResponse {
        return subjectService.getSubjectById(id)
    }

    @PutMapping("/api/subjects/{id}")
    open fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: SubjectRequest
    ): SubjectResponse {
        return subjectService.updateSubject(id, request)
    }

    @DeleteMapping("/api/subjects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    open fun deleteSubject(
        @PathVariable id: Long
    ) {
        subjectService.deleteSubject(id)
    }
}