package com.marcin.jer.computerGames.repositories;

import com.marcin.jer.computerGames.entities.Role;
import com.marcin.jer.computerGames.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(RoleName roleName);
}
