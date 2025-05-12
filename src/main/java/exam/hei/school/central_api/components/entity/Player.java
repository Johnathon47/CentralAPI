package exam.hei.school.central_api.components.entity;

import exam.hei.school.central_api.components.entity.Enum.Championship;
import exam.hei.school.central_api.components.entity.Enum.PlayerPosition;
import lombok.*;

@Data
public class Player {
    private String id;
    private String name;
    private int number;
    private PlayerPosition position;
    private String nationality;
    private int age;
    private Championship championship;
    private int scoredGoals;
    private PlayingTime playingTime;
}
