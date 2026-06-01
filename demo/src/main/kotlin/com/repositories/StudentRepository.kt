package com.pucetec.students.repositories

import com.pucetec.students.entities.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long>