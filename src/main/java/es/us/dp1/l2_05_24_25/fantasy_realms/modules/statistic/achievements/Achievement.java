package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements;

import java.time.LocalDateTime;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.AchievementDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement extends BaseEntity{

    @NotNull
    @NotBlank
    private String description;

    private String icon;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AchievementCondition condition;

    @Min(0)
    private Integer requiredValue;

    private String extraData;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TierType tier;

    private LocalDateTime dateAchieved;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AchievementType type;

    public Achievement() {

    }
    
    public Achievement(Integer id, Integer requiredValue, TierType tier, String description, 
        String extraData, String icon, AchievementCondition condition, AchievementType type) {

            this.id = id;
            this.requiredValue = requiredValue;
            this.tier = tier;
            this.description = description;
            this.extraData = extraData;
            this.icon = icon;
            this.condition = condition;
            this.type = type;

    }

    public Achievement(AchievementDTO other) {
        this.description = other.getDescription();
        this.condition = other.getCondition();
        this.requiredValue = other.getRequiredValue();
        this.extraData = other.getExtraData();
        this.tier = other.getTier();
        this.type = other.getType();
    }
}
