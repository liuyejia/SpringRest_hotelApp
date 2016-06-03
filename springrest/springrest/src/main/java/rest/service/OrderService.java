package rest.service;

import rest.common.IndexDTO;
import rest.domain.Horder;

public interface OrderService {

	public Horder saveOrder(Horder order);
	
	public IndexDTO findIndexContent(Long time);
}
