package org.example.contributetolearning.services;

import lombok.RequiredArgsConstructor;
import org.example.contributetolearning.configs.GithubApplicationProperties;
import org.example.contributetolearning.dtos.response.GithubIssueResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GithubClientService {

    private final GithubApplicationProperties properties;

    private final RestTemplate restTemplate;



    public GithubIssueResponse[] listIssues(String owner, String repository) {

        String issuesUrl = String.format("%s/repos/%s/%s/issues",properties.getApiUrl(), owner, repository);

        ResponseEntity<GithubIssueResponse[]> response = restTemplate.exchange(issuesUrl,HttpMethod.GET,null, GithubIssueResponse[].class);

        return response.getBody();


    }


}
