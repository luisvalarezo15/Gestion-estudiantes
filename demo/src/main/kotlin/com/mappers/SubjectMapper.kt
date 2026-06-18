package com.mappers

import com.dto.SubjectResponse
import com.entities.Subject

fun Subject.toResponse() = SubjectResponse(
    id = this.id,
    name = this.name,
    code = this.code,
    professor = this.professor.toResponse()
)