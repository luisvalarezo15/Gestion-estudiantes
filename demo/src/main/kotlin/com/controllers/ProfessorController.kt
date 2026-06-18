package com.controllers

import com.dto.ProfessorRequest
import com.dto.ProfessorResponse
import com.services.ProfessorService
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
open class ProfessorController(
    private val professorService: ProfessorService
) {

    @PostMapping("/api/professors")
    @ResponseStatus(HttpStatus.CREATED)
    open fun createProfessor(
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {
        return professorService.createProfessor(request)
    }

    @GetMapping("/api/professors")
    open fun getAllProfessors(): List<ProfessorResponse> {
        return professorService.getAllProfessors()
    }

    @GetMapping("/api/professors/{id}")
    open fun getProfessorById(
        @PathVariable id: Long
    ): ProfessorResponse {
        return professorService.getProfessorById(id)
    }

    @PutMapping("/api/professors/{id}")
    open fun updateProfessor(
        @PathVariable id: Long,
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {
        return professorService.updateProfessor(id, request)
    }

    @DeleteMapping("/api/professors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    open fun deleteProfessor(
        @PathVariable id: Long
    ) {
        professorService.deleteProfessor(id)
    }
}