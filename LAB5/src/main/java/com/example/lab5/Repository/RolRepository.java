package com.example.lab5.Repository;
import com.example.lab5.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}

