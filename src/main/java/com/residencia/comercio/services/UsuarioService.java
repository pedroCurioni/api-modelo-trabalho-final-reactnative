package com.residencia.comercio.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.comercio.dtos.UsuarioRecuperacaoSenhaDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.entities.Usuario;
import com.residencia.comercio.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	ArquivoService arquivoService;
	
	public Usuario save(Usuario usuario) {
		String encodedPass = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(encodedPass);
        
        return usuarioRepository.save(usuario); 
	}
	
	public Usuario alteraSenha(UsuarioRecuperacaoSenhaDTO usuarioSenhaDTO) {
		Usuario usuario = usuarioRepository.findById(usuarioSenhaDTO.getIdUsuario()).get();
		usuario.setSenha(usuarioSenhaDTO.getSenha());
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	private Usuario convertUsuarioFromStringJson(String usuarioJson) {
		Usuario usuario = new Usuario();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			usuario = objectMapper.readValue(usuarioJson, Usuario.class);
		} catch (IOException err) {
			System.out.printf("Ocorreu um erro ao tentar converter a string json para um instância da entidade Usuario", err.toString());
		}
		
		return usuario;
	}
	
}
