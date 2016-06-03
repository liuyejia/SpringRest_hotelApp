package rest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rest.common.RNDTO;
import rest.common.TypeDateDTO;
import rest.domain.RoomType;
import rest.domain.TypeDate;

@Repository
public interface TypeDateRepository extends CrudRepository<TypeDate, Long>{

	/*
	 * 获取特定类型的房间在指定日期范围内预定量的最大值 
	 * 则用户可预定数量=remain - maxBooknumber
	 * 其中remain = total - 在住房间的数量
	 */
	  @Query("select max(t.number) from TypeDate t where t.indate between ?2 and ?3 and t.roomtype = ?1")
	  Integer findMaxNumber(RoomType roomtype,Long pretime,Long posttime);
	  
	  List<TypeDate> findByRoomtypeAndIndate(RoomType roomtype,Long indate);
	  
	  TypeDate findByIndate(Long indate);
	  /*
	   * 获取所有类型房间指定时间范围的最大预定订单数
	   * <订单数  ，房间类型>
	   */
	  @Query("select distinct new rest.common.TypeDateDTO(t1.number,t1.roomtype) from TypeDate t1 where (t1.roomtype, t1.number) in "
     +"(select t.roomtype, max(t.number) from TypeDate t where t.indate between ?1 and ?2 group by t.roomtype) order by t1.roomtype ASC")
	  List<TypeDateDTO> findMaxNumberByType(Long pretime,Long posttime);
	  /*
	   * 获取已经使用的日期数值（系统限制最多居住30天，则查询的日期数量上限为30）
	   * 如 date={1,2,3,4,6,7,8}
	   * 用户预定 3号到9号
	   * 则对date={3,4,6,7,8}的记录做批量更新：number+1
	   *  对 date={5,9}的记录做批量插入
	   * 
	   */
	  @Query("select t.indate from TypeDate t where t.indate >= ?2 and t.roomtype = ?1 ORDER BY t.indate ASC")
	  List<Long> findUseDate(RoomType roomtype,Long pretime,Pageable pageable);
	  
	  /*
	   * 批量更新指定日期预定的数量
	   */
	  @Transactional
	  @Modifying
	  @Query("update TypeDate t set t.number = ?1 + t.number , t.total = ?2 + t.total where t.roomtype = ?3 and t.indate in ?4")
	  int setBookNumber(int quantity,double total,RoomType roomtype, Iterable<Long> datelist);
	  
	  @Transactional
	  @Modifying
	  @Query("update TypeDate t set t.number=t.number+1 where t.indate=?1 and t.roomtype=?2")
	  void setIndate(Long indate,RoomType roomtype);
	  
//	  @Transactional
//	  @Modifying
//	  @Query("insert into TypeDate values(null,1,?1,?2)")
//	  void insertIndate(Long indate,RoomType roomtype);
	  
	  @Query("select sum(t.total) from TypeDate t where t.indate>=?1 and t.indate<?2")
	  Double findIncomeByTime(long pretime,long posttime);
	  
	  @Query("select sum(t.number) from TypeDate t where t.indate>=?1 and t.indate<?2")
	  Long findOrderByTime(long pretime,long posttime);
	  
	  @Query("select new rest.common.RNDTO(t.roomtype,sum(t.number)) from TypeDate t where t.indate>=?1 and t.indate<?2 group by t.roomtype")
	  List<RNDTO> getOrderByTypeTime(long pretime,long posttime);
	  
}
