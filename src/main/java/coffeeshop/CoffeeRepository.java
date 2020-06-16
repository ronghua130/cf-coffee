package coffeeshop;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CoffeeRepository extends PagingAndSortingRepository<Coffee, Long>{


}