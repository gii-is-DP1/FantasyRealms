/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

	private UserRepository userRepository;
	private AuthoritiesService authoritiesService;

	@Autowired
	public UserService(UserRepository userRepository, AuthoritiesService authoritiesService) {
		this.userRepository = userRepository;
		this.authoritiesService = authoritiesService;
		
	}

	@Transactional
	public User saveUser(UserUpdateProfileDTO userDto) {
		// Crear el usuario a partir del DTO
		User userToCreate = new User(userDto);
		
		// Inicializar las colecciones vacías
		userToCreate.setPlayers(new HashSet<>());
		userToCreate.setAchievements(new ArrayList<>());

		// Si se provee una autoridad, crearla y asociarla
		if (userDto.getAuthority() != null) {
			Authorities newAuthority = new Authorities();
			newAuthority.setAuthority(userDto.getAuthority());
			newAuthority.setUser(userToCreate);
			authoritiesService.saveAuthorities(newAuthority);
			userToCreate.setAuthority(newAuthority);
		}

		// Guardar el usuario en la base de datos

		User savedUser = userRepository.save(userToCreate);

		return savedUser;
	}

	// Método para el AuthService base del proyecto -> Es el funcional actualmente

	@Transactional
	public User saveUserBasic(User user) throws DataAccessException {
		userRepository.save(user);
		return user;
	}

	@Transactional(readOnly = true)
	public User findUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

	@Transactional(readOnly = true)
	public User findUser(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}	

	@Transactional(readOnly = true)
	public User findCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			throw new ResourceNotFoundException("Nobody authenticated!");
		else
			return userRepository.findByUsername(auth.getName())
					.orElseThrow(() -> new ResourceNotFoundException("User", "Username", auth.getName()));
	}

	public Boolean existsUser(String username) {
		return userRepository.existsByUsername(username);
	}

	@Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

	@Transactional(readOnly = true)
    public Page<User> findAllByAuthority(String authority, Pageable pageable) {
        return userRepository.findAllByAuthority(authority, pageable);
    }

	@Transactional
	public User updateUser(UserUpdateProfileDTO userDTO, Integer idToUpdate) {
		// Encontrar el usuario existente
		User toUpdate = findUser(idToUpdate);
		
		// Actualizar sólo los campos necesarios
		if (userDTO.getUsername() != null) {
			toUpdate.setUsername(userDTO.getUsername());
		}
		if (userDTO.getPassword() != null) {
			toUpdate.setPassword(userDTO.getPassword());
		}
		if (userDTO.getEmail() != null) {
			toUpdate.setEmail(userDTO.getEmail());
		}
		if (userDTO.getAvatar() != null) {
			toUpdate.setAvatar(userDTO.getAvatar());
		}
		if (userDTO.getAuthority() != null) {
			Authorities newAuthority = new Authorities();
			newAuthority.setAuthority(userDTO.getAuthority());
			toUpdate.setAuthority(newAuthority);
			authoritiesService.saveAuthorities(newAuthority);
		}

		// Guardar cambios
		return userRepository.save(toUpdate);
	}

	@Transactional
	public void deleteUser(Integer id) {
		// Buscar el usuario que se desea eliminar
		User toDelete = findUser(id);

		toDelete.getAchievements().clear();

		// Eliminar al usuario
		userRepository.delete(toDelete);
	}	

	/**
     * Encuentra todos los usuarios que tienen un logro específico.
     * 
     * @param achievementId ID del logro
     * @return Lista de usuarios que tienen el logro
     */
    @Transactional(readOnly = true)
    public List<User> findUsersWithAchievement(Integer achievementId) {
        return userRepository.findUsersWithAchievement(achievementId);
    }

}
