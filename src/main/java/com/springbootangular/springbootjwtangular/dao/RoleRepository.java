package com.springbootangular.springbootjwtangular.dao;

import com.springbootangular.springbootjwtangular.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByRoleName(String roleName);


}
