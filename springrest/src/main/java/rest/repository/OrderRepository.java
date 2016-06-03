package rest.repository;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import rest.common.BQDTO;
import rest.common.GQDTO;
import rest.domain.Horder;

@RepositoryRestResource(collectionResourceRel = "order",path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Horder, Long>,
                                                QueryDslPredicateExecutor<Horder>{

	Page<Horder> findByPhone(String phone, Pageable pageable);
	
	Page<Horder> findByIndentity(String indentity, Pageable pageable);
	
	List<Horder> findByOrdertimeBetween(Long pretime,Long posttime);
	
	public List<Horder> findByPhoneOrIndentity(String phone,String indentity);
	/*
	 * 管理员对订单进行条件查询
	 * 
	 */
	//Page<Horder> findAll(Predicate predicate, Pageable pageable);
    public List<Horder> findById(Long id);
	
	@Modifying
	@Transactional
	@Query("update Horder o set o.status= ?1 where o.id= ?2")
	public void setStatus(int status,Long orderid);
	
	
	@Modifying
	@Transactional
	@Query("select new rest.common.GQDTO(c.gender,sum(o.quantity)) from Horder o,rest.domain.Customer c where c.identity = o.indentity and o.pretime>=?1 and o.posttime <?2 group by c.gender")
	public List<GQDTO> getGenderOrder(long pretime,long posttime);
	
	@Modifying
	@Transactional
	@Query("select new rest.common.BQDTO(c.birthyear,sum(o.quantity)) from Horder o,rest.domain.Customer c where c.identity = o.indentity and o.pretime>=?1 and o.posttime <?2 group by c.birthyear")
	public List<BQDTO> getBirthOrder(long pretime,long posttime);
}
