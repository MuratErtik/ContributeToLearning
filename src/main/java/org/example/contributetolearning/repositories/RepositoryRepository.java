package org.example.contributetolearning.repositories;


import org.example.contributetolearning.models.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {

    List<Repository> findAll();

    Optional<Repository> findByOrganizationAndRepository(String organization, String repository);

}
