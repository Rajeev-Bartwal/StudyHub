package com.rajeev.StudyHub.Services;

import com.rajeev.StudyHub.Payload.ResourceDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {

    ResourceDTO uploadResource(ResourceDTO resourceDTO, int id);

    List<ResourceDTO> getAllApprovedResources();

    List<ResourceDTO> getAllPendingResources();

    void approveResource(int id);  //for approving the resource.

    void deleteResource(Integer id , String userName);

    ResourceDTO updateResource(Integer id,ResourceDTO resourceDTO , String userName);

    List<ResourceDTO> searchResources(String keyword);
}
