package rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import rest.domain.Customer;

@RepositoryRestResource(collectionResourceRel = "customer",path = "customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{

	Customer findByIdentity(String identity);
	
	Long countById(Long id);
}
