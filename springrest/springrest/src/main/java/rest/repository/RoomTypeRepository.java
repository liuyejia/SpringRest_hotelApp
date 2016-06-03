package rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import rest.domain.RoomType;

@RepositoryRestResource(collectionResourceRel = "roomtype",path = "roomtype")
public interface RoomTypeRepository extends CrudRepository<RoomType, Long>{

}
