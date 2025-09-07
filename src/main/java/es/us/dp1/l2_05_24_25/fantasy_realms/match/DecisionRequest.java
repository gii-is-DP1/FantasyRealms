package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.DecisionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecisionRequest {

    private List<DecisionDTO> decisions;
    
}
