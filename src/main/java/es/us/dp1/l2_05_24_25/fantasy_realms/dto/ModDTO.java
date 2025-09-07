package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.ModType;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModDTO extends BaseEntity{
    
     @NotNull //puede ser nulo?
    private String description;

    private Integer primaryValue;

    private Integer secondaryValue;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "mod_main_type")
    private ModType modType;

    public ModDTO(Mod other) {
        this.id = other.getId();
        this.description = other.getDescription();
        this.primaryValue = other.getPrimaryValue();
        this.secondaryValue = other.getSecondaryValue();
        this.modType = other.getModType();
    }

}
