package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.time.LocalDateTime;
import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.NamedEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDTO extends NamedEntity{

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @NotNull
    private String creator;

    @NotNull
    private Boolean inProgress;

    @NotNull
    private Integer deckId;

    @NotNull
    private DiscardDTO discard;

    private TurnDTO currentTurn;

    private boolean isInScoringPhase;

    @NotNull
    @Size(min = 3, max = 6)
    private List<PlayerDTO> players;

    // Constructor vacío
    public MatchDTO() {}

    // Constructor que convierte un Match a un MatchDTO
    public MatchDTO(Match match) {
        this.name = match.getName();
        this.id = match.getId();
        this.startDate = match.getStartDate();
        this.endDate = match.getEndDate();
        this.deckId = match.getDeck().getId();
        this.creator = match.getCreador().getUser().getUsername();
        this.discard = new DiscardDTO(match.getDiscard());
        this.currentTurn = new TurnDTO(match.getCurrentTurn());
        this.players = match.getPlayers().stream().map(PlayerDTO::new).toList();
        this.inProgress = match.isInProgress();
        this.isInScoringPhase = match.isInScoringPhase();
    }
}
