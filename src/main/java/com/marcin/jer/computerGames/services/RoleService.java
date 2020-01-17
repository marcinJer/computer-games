package com.marcin.jer.computerGames.services;

import com.marcin.jer.computerGames.entities.Role;
import com.marcin.jer.computerGames.enums.RoleName;
import com.marcin.jer.computerGames.exceptions.NotFoundException;
import com.marcin.jer.computerGames.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role getWantedRole(RoleName roleName){
    switch (roleName){
      case ROLE_ADMIN:
        return roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new NotFoundException("Role not found"));
      case ROLE_USER:
        return roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new NotFoundException("Role not found"));
      case ROLE_MANUFACTURER:
        return roleRepository.findByName(RoleName.ROLE_MANUFACTURER).orElseThrow(() -> new NotFoundException("Role not found"));
      default:
        throw new IllegalStateException("Unexpected value: " + roleName);
    }
  }
}
