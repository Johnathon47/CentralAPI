package exam.hei.school.central_api.components.entity;

import exam.hei.school.central_api.components.entity.Enum.DurationUnit;
import lombok.*;

@Data
public class PlayingTime {
    private int value;
    private DurationUnit durationUnit;
}
