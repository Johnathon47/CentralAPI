package exam.hei.school.central_api.components.repository;

import exam.hei.school.central_api.components.entity.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void upsertPlayer(Player player) {
        if (player.getPosition() == null) {
            System.err.println("Le joueur " + player.getId() + " n'a pas de position définie.");
            return; // Ne pas continuer si la position est manquante
        }

        String sql = """
        INSERT INTO player (id, name, number, position, nationality, age, championship, scored_goals, playing_time_value, duration_unit)
        VALUES (?, ?, ?, ?::player_position_enum, ?, ?, ?::championship_enum, ?, ?, ?::time_unit_enum)
        ON CONFLICT (id) DO UPDATE SET
            name = EXCLUDED.name,
            number = EXCLUDED.number,
            position = EXCLUDED.position,
            nationality = EXCLUDED.nationality,
            age = EXCLUDED.age,
            championship = EXCLUDED.championship,
            scored_goals = EXCLUDED.scored_goals,
            playing_time_value = EXCLUDED.playing_time_value,
            duration_unit = EXCLUDED.duration_unit;
    """;

        jdbcTemplate.update(sql,
                player.getId(),
                player.getName(),
                player.getNumber(),
                player.getPosition().name(),
                player.getNationality(),
                player.getAge(),
                player.getChampionship().name(),
                player.getScoredGoals(),
                player.getPlayingTime().getValue(),
                player.getPlayingTime().getDurationUnit().name()
        );
    }


}