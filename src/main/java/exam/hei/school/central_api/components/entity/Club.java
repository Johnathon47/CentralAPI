package exam.hei.school.central_api.components.entity;

import lombok.*;

@Data
public class Club {
    private String id;
    private String name;
    private String acronym;
    private int yearCreation;
    private String stadiunm;
    private Coach coach;
}
