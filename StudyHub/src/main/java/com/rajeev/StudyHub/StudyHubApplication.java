package com.rajeev.StudyHub;

import com.rajeev.StudyHub.Configuration.AppConstants;
import com.rajeev.StudyHub.Models.Role;
import com.rajeev.StudyHub.Repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudyHubApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(StudyHubApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		Role student = new Role();
		student.setRole_id(AppConstants.ROLE_STUDENT_ID);
		student.setRole_name(AppConstants.ROLE_STUDENT);

		Role teacher = new Role();
		teacher.setRole_id(AppConstants.ROLE_TEACHER_ID);
		teacher.setRole_name(AppConstants.ROLE_TEACHER);

		Role moderator = new Role();
		moderator.setRole_id(AppConstants.ROLE_MODERATOR_ID);
		moderator.setRole_name(AppConstants.ROLE_MODERATOR);

		Role admin = new Role();
		admin.setRole_id(AppConstants.ROLE_ADMIN_ID);
		admin.setRole_name(AppConstants.ROLE_ADMIN);

		List<Role> roles = List.of(student, teacher, moderator, admin);
		roleRepo.saveAll(roles);

		System.out.println("Inserted roles: STUDENT, TEACHER, MODERATOR, ADMIN");
	}


}
