package org.example.contributetolearning.managers;

import lombok.RequiredArgsConstructor;
import org.example.contributetolearning.configs.ApplicationProperties;
import org.example.contributetolearning.dtos.requests.CreateRepositoryRequest;
import org.example.contributetolearning.dtos.response.GithubIssueResponse;
import org.example.contributetolearning.models.Issue;
import org.example.contributetolearning.models.Repository;
import org.example.contributetolearning.services.GithubClientService;
import org.example.contributetolearning.services.IssueService;
import org.example.contributetolearning.services.RepositoryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepositoryManager {

    private final RepositoryService repositoryService;

    private final GithubClientService githubClientService;

    private final IssueService issueService;

    private final ApplicationProperties applicationProperties;

    public void importRepository(CreateRepositoryRequest request) {

        repositoryService.createRepository(request);
    }


    //If dont implementation manager class we would make hikari busier because transactional method would be run more!!!

    @Async
    public void importIssue(Repository repository) {

        int schedulerFrequencyMinutes = applicationProperties.getImportFrequency()/6000;

        // it went to one day ago
        LocalDate since = LocalDate.ofInstant(Instant.now().minus(schedulerFrequencyMinutes,ChronoUnit.MINUTES), ZoneOffset.UTC);


        GithubIssueResponse[] githubIssueResponses = githubClientService
                .listIssues(repository.getOrganization(),repository.getRepository(),since);

        List<Issue> issues = Arrays.stream(githubIssueResponses).map(githubIssue -> new Issue()).toList();

        issueService.importAll(issues);



    }
}
