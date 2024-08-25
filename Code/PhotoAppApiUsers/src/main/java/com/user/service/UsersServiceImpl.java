package com.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.dao.AlbumsServiceClient;
import com.user.dao.UserRepository;
import com.user.dto.UserDto;
import com.user.entity.AuthorityEntity;
import com.user.entity.RoleEntity;
import com.user.entity.UserEntity;
import com.user.model.AlbumResponseModel;

import feign.FeignException;


@Service
public class UsersServiceImpl implements UsersService {
	
	public UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	//RestTemplate restTemplate;
	Environment env;
	AlbumsServiceClient albumsServiceClient;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RestTemplate restTemplate,
			Environment environment,AlbumsServiceClient albumsServiceClient){
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		//this.restTemplate = restTemplate;
		this.env = environment;
		this.albumsServiceClient = albumsServiceClient;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		userRepository.save(userEntity);
		
		UserDto user = modelMapper.map(userEntity, UserDto.class);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);	
		
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Collection<RoleEntity> roles = userEntity.getRoles();

		roles.forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));

			Collection<AuthorityEntity> authorityEntities = role.getAuthorities();
			authorityEntities.forEach((authorityEntity) -> {
				authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
			});
		});
		 
		
		return new User(userEntity.getEmail(), 
				userEntity.getEncryptedPassword(), 
				true, true, true, true, 
				authorities);
	
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		
		return new ModelMapper().map(userEntity, UserDto.class);
	
	}
	
public UserDto getUserByUserId(String userId, String authorization) {
		
        UserEntity userEntity = userRepository.findByUserId(userId);     
        if(userEntity == null) throw new UsernameNotFoundException("User not found");
        
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        
        
		/*
		 * String albumsUrl = String.format(env.getProperty("albums.url"), userId);
		 * 
		 * ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
		 * restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
		 * 
		 * userDto.setAlbums(albumsList);
		 */
		
		 logger.info("Before calling albums Microservice"); 
        //List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId, authorization);
        try {
        	
        }catch (FeignException e) {
			logger.error(e.getLocalizedMessage());
		}
        List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId,authorization);
		 logger.info("After calling albums Microservice");
		 
		 
		
		
		return userDto;
	}


}
