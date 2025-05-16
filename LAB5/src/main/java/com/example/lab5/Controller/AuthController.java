package com.example.lab5.Controller;

import com.example.lab5.Entity.Usuario;
import com.example.lab5.Entity.Rol;
import com.example.lab5.Repository.UsuarioRepository;
import com.example.lab5.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Página de registro
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    // Procesamiento del registro
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPwd(passwordEncoder.encode(usuario.getPwd()));
        usuario.setActivo(true);

        Rol rolUser = rolRepository.findByNombre("USER");
        usuario.setRol(rolUser);

        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    // Redirección por rol luego del login
    @GetMapping("/redirectByRole")
    public String redirigirPorRol(Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";

        String rol = usuario.getRol().getNombre();
        if (rol.equals("ADMIN")) {
            return "redirect:/admin/usuarios";
        } else if (rol.equals("USER")) {
            return "redirect:/user/juego";
        }
        return "redirect:/login";
    }
}

