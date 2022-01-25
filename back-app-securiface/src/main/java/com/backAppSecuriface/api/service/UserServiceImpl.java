package com.backAppSecuriface.api.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backAppSecuriface.api.dao.RoleRepository;
import com.backAppSecuriface.api.dao.UserRepository;
import com.backAppSecuriface.api.exception.BusinessResourceException;
import com.backAppSecuriface.api.model.Role;
import com.backAppSecuriface.api.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserRepository userRepository;
	private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserServiceImpl() {
		super();
	}
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public Collection<User> getAllUsers() {
		return IteratorUtils.toList(userRepository.findAll().iterator());
	}
	
	@Override
	public Optional<User> findByEmail(String email) throws BusinessResourceException {
		
		Optional<User> userFound = userRepository.findByEmail(email);
        if (Boolean.FALSE.equals(userFound.isPresent())) {
            throw new BusinessResourceException("User Not Found", "L'utilisateur avec cet email n'existe pas :" + email);
        }
		return userFound;
	}

	@Override
	public Optional<User> getUserById(Long id) throws  BusinessResourceException{
		
		Optional<User> userFound = userRepository.findById(id);
        if (Boolean.FALSE.equals(userFound.isPresent())){
            throw new BusinessResourceException("User Not Found", "Aucun utilisateur avec l'identifiant :" + id);
        }
		return userFound;
	}

	@Override
	@Transactional
	public User saveOrUpdateUser(User user) throws BusinessResourceException{
		try{
			if(null ==user.getUserId()) {//pas d'Id --> création d'un user
				addUserRole(user);//Ajout d'un rôle par défaut
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			} else {//sinon, mise à jour d'un user
				
				Optional<User> userFromDB = getUserById(user.getUserId());
				if(! bCryptPasswordEncoder.matches(user.getPassword(), userFromDB.get().getPassword())) {
					user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//MAJ du mot de passe s'il a été modifié
				} else {
					
					user.setPassword(userFromDB.get().getPassword());//Sinon, on remet le password déjà haché
				}
				updateUserRole(user);//On extrait le rôle en cas de mise à jour
			}
			User result = userRepository.save(user);
			return  result;
		} catch(DataIntegrityViolationException ex){
			logger.error("Utilisateur non existant", ex);
			throw new BusinessResourceException("DuplicateValueError", "Un utilisateur existe déjà avec le compte : "+user.getEmail(), HttpStatus.CONFLICT);
		} catch (BusinessResourceException e) {
			logger.error("Utilisateur non existant", e);
			throw new BusinessResourceException("UserNotFound", "Aucun utilisateur avec l'identifiant: "+user.getUserId(), HttpStatus.NOT_FOUND);
		} catch(Exception ex){
			logger.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
			throw new BusinessResourceException("SaveOrUpdateUserError", "Erreur technique de création ou de mise à jour de l'utilisateur: "+user.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public void deleteUser(Long id) throws BusinessResourceException {
		try{
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException ex){
			logger.error(String.format("Aucun utilisateur n'existe avec l'identifiant: "+id, ex));
			throw new BusinessResourceException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: "+id, HttpStatus.NOT_FOUND);
		}catch(Exception ex){
			throw new BusinessResourceException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@Override
	public Optional<User> findByEmailAndPassword(String email, String password) throws BusinessResourceException{
		try {
			Optional<User> userFound = this.findByEmail(email);
			if(bCryptPasswordEncoder.matches(password, userFound.get().getPassword())) {
				return userFound;
			} else {
				throw new BusinessResourceException("UserNotFound", "Mot de passe incorrect", HttpStatus.NOT_FOUND);
			}
		} catch (BusinessResourceException ex) {
			logger.error("Login ou mot de passe incorrect", ex);
			throw new BusinessResourceException("UserNotFound", "Login ou mot de passe incorrect", HttpStatus.NOT_FOUND);
		}catch (Exception ex) {
			logger.error("Une erreur technique est survenue", ex);
			throw new BusinessResourceException("TechnicalError", "Une erreur technique est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void addUserRole(User user) {
		Set<Role> roles= new HashSet<>();
		Role roleUser = new Role("roleUser");//initialisation du rôle roleUser
		roles.add(roleUser);
		user.setActive(0);

		Set<Role> roleFromDB = extractRole_Java8(roles, roleRepository.getAllRolesStream());
		user.setRoles(roleFromDB);
	}
	
	private void updateUserRole(User user) {
		Set<Role> roleFromDB = extractRole_Java8(user.getRoles(), roleRepository.getAllRolesStream());
		// Set<Role> roleFromDB = extractRole_Java8(user.getRoles(), roleRepository.getAllRolesStream());
		user.setRoles(roleFromDB);
	}
	
 	private Set<Role> extractRole_Java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) { 		
 	// Collect UI role names
 		Set<String> uiRoleNames = rolesSetFromUser.stream()
 		    .map(Role::getRoleName)
 		    .collect(Collectors.toCollection(HashSet::new));
 	// Filter DB roles
		return roleStreamFromDB
			.filter(role -> uiRoleNames.contains(role.getRoleName()))
		    .collect(Collectors.toSet());

	}
}
