package exam.hei.school.central_api.components.entity;
import lombok.*;

@Data
public class ClubRanking {
    private int rank;
    private Club club;
    private int rankingPoints;
    private int scoredGoals;
    private int concededGoals;
    private int differenceGoals;
    private int cleanSheetNumber;
}
