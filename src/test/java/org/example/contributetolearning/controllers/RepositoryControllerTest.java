package org.example.contributetolearning.controllers;

import org.example.contributetolearning.dtos.requests.CreateRepositoryRequest;
import org.example.contributetolearning.dtos.response.GetAllRepositoryResponse;
import org.example.contributetolearning.models.Repository;
import org.example.contributetolearning.services.RepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.example.contributetolearning.configs.RestApis.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RepositoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //what does mean mocking in spring?

    @MockitoBean
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void it_should_list_all_repositories() throws Exception {

        GetAllRepositoryResponse repository= GetAllRepositoryResponse.builder()
                .name("testRepository")
                .organization("testOrganization")
                .build();


        given(repositoryService.getAllRepository()).willReturn(Collections.singletonList(repository));

        mockMvc.perform(get(REPOSITORIES+"/"+GET_ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(repository.getName()))
                .andExpect(jsonPath("$[0].organization").value(repository.getOrganization()));

    }

    @Test
    public void it_should_create_repository() throws Exception {

        //given
        CreateRepositoryRequest createRepositoryRequest = new CreateRepositoryRequest();
        createRepositoryRequest.setRepository("testRepository");
        createRepositoryRequest.setOrganization("github");
        doNothing().when(repositoryService).createRepository(createRepositoryRequest);

        //when
        mockMvc.perform(post(REPOSITORIES+"/"+CREATE).content(objectMapper.writeValueAsString(createRepositoryRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated());

        // able to add as file content of json !!!
        //then
    }

}