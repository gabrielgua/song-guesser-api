package com.gabriel.hksongguesser.api.security;

import com.gabriel.hksongguesser.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(usuario.getTipo().name());
        return new User(usuario.getUsername(), usuario.getSenha(), List.of(simpleGrantedAuthority));
    }
}
