package com.rajeev.StudyHub.Repository;

import com.rajeev.StudyHub.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role , Integer> {
}
