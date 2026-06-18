package com.services

import com.dto.ProfessorRequest
import com.dto.ProfessorResponse
import com.entities.Professor
import com.exceptions.BlankNameException
import com.exceptions.ProfessorNotFound
import com.mappers.toResponse
import com.repositories.ProfessorRepository
import org.springframework.stereotype.Service

@Service
open class ProfessorService(
    private val professorRepository: ProfessorRepository
) {

    open fun createProfessor(request: ProfessorRequest): ProfessorResponse {
        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }

        val professorEntity = Professor(
            name = request.name,
            email = request.email
        )

        return professorRepository.save(professorEntity).toResponse()
    }

    open fun getAllProfessors(): List<ProfessorResponse> {
        return professorRepository.findAll().map { it.toResponse() }
    }

    open fun getProfessorById(id: Long): ProfessorResponse {
        val professor = professorRepository.findById(id).orElseThrow {
            ProfessorNotFound("Profesor no encontrado: $id")
        }
        return professor.toResponse()
    }

    open fun updateProfessor(id: Long, request: ProfessorRequest): ProfessorResponse {
        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }

        val professor = professorRepository.findById(id).orElseThrow {
            ProfessorNotFound("Profesor no encontrado: $id")
        }

        val updatedProfessor = Professor(
            id = professor.id,
            name = request.name,
            email = request.email
        )

        return professorRepository.save(updatedProfessor).toResponse()
    }

    open fun deleteProfessor(id: Long) {
        if (!professorRepository.existsById(id)) {
            throw ProfessorNotFound("Profesor no encontrado: $id")
        }
        professorRepository.deleteById(id)
    }
}