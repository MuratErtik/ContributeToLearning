package org.example.contributetolearning.controllers;

import lombok.RequiredArgsConstructor;
import org.example.contributetolearning.dtos.requests.CreateRepositoryRequest;
import org.example.contributetolearning.dtos.response.CreateRepositoryResponse;
import org.example.contributetolearning.dtos.response.GetAllRepositoryResponse;
import org.example.contributetolearning.services.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.contributetolearning.configs.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(REPOSITORIES)
public class RepositoryController {

    private final RepositoryService repositoryService;




    @PostMapping(CREATE) // its less data transfer because using void
    @ResponseStatus(HttpStatus.CREATED)
    public void createRepository(@RequestBody CreateRepositoryRequest request) {

        repositoryService.createRepository(request);

    }

    @GetMapping(GET_ALL)
    public List<GetAllRepositoryResponse> getAllRepository(){
        return repositoryService.getAllRepository();
    }
}
