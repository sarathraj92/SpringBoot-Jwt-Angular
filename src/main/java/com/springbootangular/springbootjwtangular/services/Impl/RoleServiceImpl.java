package com.springbootangular.springbootjwtangular.services.Impl;

import com.springbootangular.springbootjwtangular.dao.RoleRepository;
import com.springbootangular.springbootjwtangular.entity.Role;
import com.springbootangular.springbootjwtangular.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    public Role getRole() {
        return roleRepository.findByRoleName("USER");
    }
}
