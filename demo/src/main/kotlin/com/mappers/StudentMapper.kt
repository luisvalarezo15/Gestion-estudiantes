package com.mappers

import com.dto.StudentResponse
import com.entities.Student

fun Student.toResponse() = StudentResponse(
    id = this.id,
    name = this.name,
    email = this.email
)