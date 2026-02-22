package org.example.contributetolearning.configs;

import lombok.RequiredArgsConstructor;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GithubClientConf {

    private final GithubApplicationProperties properties;

//    @Bean
//    public GitHubClient gitHubClient() {
//
//        GitHubClient client = new GitHubClient();
//        client.setOAuth2Token(properties.getToken());
//        return client;
//    }
}
