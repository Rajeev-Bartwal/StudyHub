package com.rajeev.StudyHub.Services.ServiceImpl;

import com.rajeev.StudyHub.Exception.ResourceNotFoundException;
import com.rajeev.StudyHub.Models.Resource;
import com.rajeev.StudyHub.Models.User;
import com.rajeev.StudyHub.Payload.ResourceDTO;
import com.rajeev.StudyHub.Repository.ResourceRepo;
import com.rajeev.StudyHub.Repository.UserRepo;
import com.rajeev.StudyHub.Services.ResourceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepo resourceRepo;

    @Autowired
    private ModelMapper mod;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResourceDTO uploadResource(ResourceDTO resourceDTO, int id) {

        Resource resource = mod.map(resourceDTO , Resource.class);
        resource.setApproved(false);
        User user = userRepo.findById(id).orElseThrow((() -> new ResourceNotFoundException("user" , "id" , id)));
        resource.setUploader(user);
        resource.setUploadedAt(LocalDateTime.now());
        Resource savedResource = resourceRepo.save(resource);
        return mod.map(savedResource , ResourceDTO.class);

    }

    @Override
    public List<ResourceDTO> getAllApprovedResources() {
        List<Resource> resources = resourceRepo.findByApprovedTrue();
        return resources.stream().map(resource -> mod.map(resource , ResourceDTO.class)).toList();
    }

    @Override
    public List<ResourceDTO> getAllPendingResources() {

        List<Resource> resources = resourceRepo.findByApprovedFalse();

        return resources.stream().map(resource -> mod.map(resource , ResourceDTO.class)).toList();
    }

    @Override
    public void approveResource(int id) {
        Resource resource = resourceRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource" , "id" , id));
        resource.setApproved(true);
        resourceRepo.save(resource);
    }

    @Override
    public void deleteResource(Integer id, String userName) {
        Resource resource = resourceRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource" , "id" , id));

        String name = resource.getUploader().getEmail();
        if(name.equalsIgnoreCase(userName)){
            resourceRepo.delete(resource);
        }else {
            throw new AccessDeniedException("You are not authorized to delete this resource");
        }
    }

    public ResourceDTO updateResource(Integer id, ResourceDTO resourceDTO, String userName) {
        Resource resource = resourceRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource", "id", id));

        String email = resource.getUploader().getEmail();

        if (!email.equalsIgnoreCase(userName)) {
            throw new AccessDeniedException("You are not authorized to update this resource");
        }

        // Update only provided fields
        if (resourceDTO.getTitle() != null && !resourceDTO.getTitle().isBlank()) {
            resource.setTitle(resourceDTO.getTitle());
        }
        if (resourceDTO.getDescription() != null && !resourceDTO.getDescription().isBlank()) {
            resource.setDescription(resourceDTO.getDescription());
        }
        if (resourceDTO.getFileUrl() != null && !resourceDTO.getFileUrl().isBlank()) {
            resource.setFileUrl(resourceDTO.getFileUrl());
        }
        if (resourceDTO.getSubject() != null && !resourceDTO.getSubject().isBlank()) {
            resource.setSubject(resourceDTO.getSubject());
        }
        // If approved status can be updated by uploader
        if (resourceDTO.isApproved() != resource.isApproved()) {
            resource.setApproved(resourceDTO.isApproved());
        }

        Resource updatedResource = resourceRepo.save(resource);

        return mod.map(updatedResource, ResourceDTO.class);

    }

    @Override
    public List<ResourceDTO> searchResources(String keyword) {
        List<Resource> resources = resourceRepo.searchByKeyword("%" + keyword +"%");

        return resources.stream().map(resource -> mod.map(resource , ResourceDTO.class)).toList();
    }


}
