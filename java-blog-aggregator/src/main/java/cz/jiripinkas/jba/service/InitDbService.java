package cz.jiripinkas.jba.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Blog;
import cz.jiripinkas.jba.entity.Role;
import cz.jiripinkas.jba.entity.User;
import cz.jiripinkas.jba.repository.BlogRepository;
import cz.jiripinkas.jba.repository.ItemRepository;
import cz.jiripinkas.jba.repository.RoleRepository;
import cz.jiripinkas.jba.repository.UserRepository;

@Transactional
@Service
public class InitDbService {
		
		@Autowired
		private RoleRepository roleRepository; 
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private ItemRepository itemRepository;
		
		@Autowired
		private BlogRepository blogRepository;
		
		@PostConstruct
		public void init(){
			if(roleRepository.findByName("ROLE_ADMIN")==null){
	
				Role roleUser = new Role();
				roleUser.setName("ROLE_USER");
				roleRepository.save(roleUser);
				
				Role roleAdmin = new Role();
				roleAdmin.setName("ROLE_ADMIN");
				roleRepository.save(roleAdmin);
				
				User userAdmin = new User();
				userAdmin.setEnabled(true);
				userAdmin.setName("admin");
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				userAdmin.setPassword(encoder.encode("admin"));
				List<Role> roles = new ArrayList<Role>();
				roles.add(roleAdmin);
				roles.add(roleUser);
				userAdmin.setRoles(roles);
				userRepository.save(userAdmin);
				
				Blog blogJavaVids = new Blog();
				blogJavaVids.setName("JavaVids");
				blogJavaVids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
				blogJavaVids.setUser(userAdmin);
				blogRepository.save(blogJavaVids);
				
			/*	Item item1 = new Item();
				item1.setBlog(blogJavaVids);
				item1.setTitle("first");
				item1.setLink("http://www.javavids.com");
				item1.setPublishedDate(new Date());
				itemRepository.save(item1);
				
				Item item2 = new Item();
				item2.setBlog(blogJavaVids);
				item2.setTitle("second");
				item2.setLink("http://www.javavids.com");
				item2.setPublishedDate(new Date());
				itemRepository.save(item2);*/
			}
		}
}
