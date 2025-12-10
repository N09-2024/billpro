// src/main/java/com/example/billpro/security/CustomUserDetailsService.java
package com.example.billpro.security;

import com.example.billpro.model.Employe;
import com.example.billpro.model.Manager;
import com.example.billpro.model.SuperAdmin;
import com.example.billpro.repository.EmployeRepository;
import com.example.billpro.repository.ManagerRepository;
import com.example.billpro.repository.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private SuperAdminRepository superAdminRepo;
    @Autowired private ManagerRepository managerRepo;
    @Autowired private EmployeRepository employeRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. SuperAdmin
        SuperAdmin sa = superAdminRepo.findByEmail(email).orElse(null);
        if (sa != null) {
            return new User(sa.getEmail(), sa.getMdp(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_SUPERADMIN")));
        }

        // 2. Manager
        Manager m = managerRepo.findByEmail(email).orElse(null);
        if (m != null && Boolean.TRUE.equals(m.getActif())) {
            return new User(m.getEmail(), m.getMdp(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        }

        // 3. Employé
        Employe e = employeRepo.findByEmail(email).orElse(null);
        if (e != null && Boolean.TRUE.equals(e.getActif())) {
            return new User(e.getEmail(), e.getMdp(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYE")));
        }

        throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email);
    }
}