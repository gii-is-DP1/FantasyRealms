package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementCondition;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.AchievementType;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.TierType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchievementDTO extends BaseEntity{
    
    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AchievementCondition condition;

    @Min(0)
    private Integer requiredValue;

    private String extraData;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TierType tier;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AchievementType type;

    public AchievementDTO() {

    }

    public AchievementDTO(Achievement other) {
        this.id = other.getId();
        this.description = other.getDescription();
        this.condition = other.getCondition();
        this.requiredValue = other.getRequiredValue();
        this.extraData = other.getExtraData();
        this.tier = other.getTier();
        this.type = other.getType();
    }

}
