package rest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import rest.domain.Room;
import rest.domain.RoomType;

@RepositoryRestResource(collectionResourceRel = "room",path = "room")
public interface RoomRepository extends CrudRepository<Room, Long> {

	List<Room> findByRoomtype(RoomType roomtype);
	
	@Query("select r.id from Room r where r.roomnumber=?1")
	Long checkNumber(String number);
	
	List<Room> findByRoomtypeAndStatus(RoomType roomtype,int status,Pageable pageable);
	
	List<Room> findByRoomtypeAndStatus(RoomType roomtype,int status);
	List<Room> findByRoomnumber(String roomnumber);
	
	@Query("select count(*) from Room r where r.roomtype=?1 and r.status=?2")
	List<Long> getNumByStatus(RoomType roomtype, int status);
	
	@Modifying
	@Transactional
	@Query("update Room r set r.status = 1, r.orderid=null where r.roomnumber = ?1")
	void quitRoom(String roomnumber);
	
	@Modifying
	@Transactional
	@Query("update Room r set r.status = ?1 where r.roomnumber = ?2")
	void setRoomStatus(int status,String roomnumber);
	
	@Query("select r.roomnumber from Room r where r.roomtype=?1 and( r.status= 1 or r.status =3 or r.status = 4)")
	List<String> getRoomList(RoomType roomtype);
}
