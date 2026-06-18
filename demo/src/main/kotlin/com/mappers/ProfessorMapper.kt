package com.mappers

import com.dto.ProfessorResponse
import com.entities.Professor

fun Professor.toResponse() = ProfessorResponse(
    id = this.id,
    name = this.name,
    email = this.email
)