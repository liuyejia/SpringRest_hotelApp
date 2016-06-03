package rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import rest.domain.Manager;

@RepositoryRestResource(collectionResourceRel = "manager",path = "manager")
public interface ManagerRepository extends PagingAndSortingRepository<Manager, Long> {

	Manager findByAccount(String account);
}
