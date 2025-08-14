package com.rajeev.StudyHub.Controllers;


import com.rajeev.StudyHub.Payload.ApiResponse;
import com.rajeev.StudyHub.Payload.ResourceDTO;
import com.rajeev.StudyHub.Services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/study_hub/resource/")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    //create resource
    @PostMapping("/{id}")
    public ResponseEntity<ResourceDTO> createResource(@RequestBody ResourceDTO resourceDTO,
                                                     @PathVariable Integer id){
        ResourceDTO resource  = resourceService.uploadResource(resourceDTO , id);

        return new ResponseEntity<>(resource , HttpStatus.CREATED);
    }

    //get all resource
    @GetMapping("/")
    public ResponseEntity<List<ResourceDTO>> getAllApprovedResources(){
        List<ResourceDTO> resources = resourceService.getAllApprovedResources();

        return  new ResponseEntity<>(resources , HttpStatus.FOUND);
    }

    //get all pending resource
    @GetMapping("/pending")
    public ResponseEntity<List<ResourceDTO>> getAllPendingResources(){
        List<ResourceDTO> resources = resourceService.getAllPendingResources();

        return new ResponseEntity<>(resources , HttpStatus.FOUND);
    }

    //approve resource
    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse> approveResource(@PathVariable Integer id){
        resourceService.approveResource(id);

        return new ResponseEntity<>(new ApiResponse(
                "Resource Approved Successfully.." , true)
                , HttpStatus.OK);
    }

    //Delete Resource
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteResource(@PathVariable Integer id,
                                                      Principal principal){
        String userName = principal.getName();
        resourceService.deleteResource(id , userName);

        return new ResponseEntity<>(
                new ApiResponse("Resource Deleted Successfully.." ,true)
                , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateResource(@PathVariable Integer id,
                                                 @RequestBody ResourceDTO resourceDTO,
                                                 Principal principal){
        String userName = principal.getName();
        resourceService.updateResource(id,resourceDTO,userName);

        return new ResponseEntity<>(new ApiResponse("Resource Updated successfully." , true) , HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
//    @Operation(summary = "for searching a post")
    public ResponseEntity<List<ResourceDTO>> searchResource(@PathVariable String keywords){

        return new ResponseEntity<>(resourceService.searchResources(keywords) , HttpStatus.FOUND);
    }
}
