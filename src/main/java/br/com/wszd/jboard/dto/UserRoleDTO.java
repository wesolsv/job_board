package br.com.wszd.jboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO {

  private Long idUser;

  private List<Long> idsRoles;

  public UserRoleDTO(Long idUser, List<Long> idsRoles) {
    this.idUser = idUser;
    this.idsRoles = idsRoles;
  }
}
