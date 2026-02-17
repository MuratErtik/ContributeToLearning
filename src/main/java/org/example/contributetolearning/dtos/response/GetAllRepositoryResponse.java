package org.example.contributetolearning.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllRepositoryResponse {

    private String name;
    private String organization;
}
