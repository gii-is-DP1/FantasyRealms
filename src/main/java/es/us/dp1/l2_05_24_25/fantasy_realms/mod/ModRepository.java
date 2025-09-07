package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModRepository extends CrudRepository<Mod,Integer> {

    
}
