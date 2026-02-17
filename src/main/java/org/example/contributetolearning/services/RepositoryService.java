package org.example.contributetolearning.services;

import lombok.RequiredArgsConstructor;
import org.example.contributetolearning.dtos.requests.CreateRepositoryRequest;
import org.example.contributetolearning.dtos.response.GetAllRepositoryResponse;
import org.example.contributetolearning.models.Repository;
import org.example.contributetolearning.repositories.RepositoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;

    @Transactional
    public void createRepository(CreateRepositoryRequest request) {

        Repository r = Repository.builder()
                .organization(request.getOrganization())
                .repository(request.getRepository())
                .build();

        repositoryRepository.save(r);
    }

    public List<GetAllRepositoryResponse> getAllRepository() {

        return repositoryRepository.findAll().stream().map(this::getAllRepositoryResponse).collect(Collectors.toList());

    }

    private GetAllRepositoryResponse getAllRepositoryResponse(Repository repository) {

        return GetAllRepositoryResponse.builder()
                .name(repository.getRepository())
                .organization(repository.getOrganization())
                .build();
    }
}
