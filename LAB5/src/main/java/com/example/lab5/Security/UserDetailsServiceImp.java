package com.example.lab5.Security;

import com.example.lab5.Entity.Usuario;
import com.example.lab5.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
        return new User(usuario.getEmail(), usuario.getPwd(), authorities);
    }
}

