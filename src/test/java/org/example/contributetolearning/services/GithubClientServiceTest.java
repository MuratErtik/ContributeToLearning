package org.example.contributetolearning.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.example.contributetolearning.configs.GithubApplicationProperties;
import org.example.contributetolearning.configs.RestTemplateConfig;
import org.example.contributetolearning.dtos.response.GithubIssueResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;


@ContextConfiguration(initializers = GithubClientServiceTest.initializer.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {GithubClientService.class, RestTemplateConfig.class})
@EnableConfigurationProperties(GithubApplicationProperties.class)
class GithubClientServiceTest {


    @Autowired
    private  GithubClientService githubClientService;

    @Autowired
    private  WireMockServer wireMockServer;



    @Test
    public void it_should_list_issues(){

        //given
        wireMockServer.stubFor(
                get(urlPathEqualTo("/repos/octocat/Hello-World/issues"))
                        .withHeader("Authorization",equalTo("Token murat"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("issues.json")
                                .withStatus(200))
        );


        //when
        GithubIssueResponse[] response= githubClientService.listIssues("octocat", "Hello-World");

        //then
        then(response).isNotNull();
        then(response.length).isEqualTo(30);
        GithubIssueResponse response1 = response[0];
        then(response1.getTitle()).isEqualTo("[AI-AGENT] Auto-fix 0 issues — mohit");




    }

    static class initializer implements ApplicationContextInitializer<@org.jetbrains.annotations.NotNull ConfigurableApplicationContext> {




        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
            wireMockServer.start();

            applicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

            applicationContext.addApplicationListener(applicationEvent -> {
                if (applicationEvent instanceof ContextClosedEvent){
                    wireMockServer.stop();
                }
            });

            TestPropertyValues.of("github.api-url="+wireMockServer.baseUrl(),
                    "github.token=murat")
                    .applyTo(applicationContext.getEnvironment());

        }
    }

}