package com.services

import com.dto.SubjectRequest
import com.dto.SubjectResponse
import com.entities.Subject
import com.exceptions.BlankNameException
import com.exceptions.ProfessorNotFound
import com.exceptions.SubjectNotFoundException
import com.mappers.toResponse
import com.repositories.ProfessorRepository
import com.repositories.SubjectRepository
import org.springframework.stereotype.Service

@Service
open class SubjectService(
    private val professorRepository: ProfessorRepository,
    private val subjectRepository: SubjectRepository
) {

    open fun createSubject(request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }
        if (request.code.isBlank()) {
            throw BlankNameException("Código en blanco - ERROR")
        }

        val professor = professorRepository.findById(request.professorId).orElseThrow {
            ProfessorNotFound("Profesor no encontrado: ${request.professorId}")
        }

        val subjectEntity = Subject(
            name = request.name,
            code = request.code,
            professor = professor
        )

        return subjectRepository.save(subjectEntity).toResponse()
    }

    open fun getAllSubjects(): List<SubjectResponse> {
        return subjectRepository.findAll().map { it.toResponse() }
    }

    open fun getSubjectById(id: Long): SubjectResponse {
        val subject = subjectRepository.findById(id).orElseThrow {
            SubjectNotFoundException("Materia no encontrada: $id")
        }
        return subject.toResponse()
    }

    open fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) {
            throw BlankNameException("Nombre en blanco - ERROR")
        }
        if (request.code.isBlank()) {
            throw BlankNameException("Código en blanco - ERROR")
        }

        val subject = subjectRepository.findById(id).orElseThrow {
            SubjectNotFoundException("Materia no encontrada: $id")
        }

        val professor = professorRepository.findById(request.professorId).orElseThrow {
            ProfessorNotFound("Profesor no encontrado: ${request.professorId}")
        }

        val updatedSubject = Subject(
            id = subject.id,
            name = request.name,
            code = request.code,
            professor = professor
        )

        return subjectRepository.save(updatedSubject).toResponse()
    }

    open fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id)) {
            throw SubjectNotFoundException("Materia no encontrada: $id")
        }
        subjectRepository.deleteById(id)
    }
}