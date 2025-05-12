package exam.hei.school.central_api.components.service;

import exam.hei.school.central_api.components.entity.Enum.Championship;
import exam.hei.school.central_api.components.entity.Player;
import exam.hei.school.central_api.components.entity.PlayerStatistics;
import exam.hei.school.central_api.components.repository.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SynchronizationService {
    private final RestTemplate restTemplate;
    private final PlayerRepository playerRepository;

    private static final Map<String, Championship> API_URL_TO_CHAMPIONSHIP = Map.of(
            "http://localhost:8080", Championship.CHAMPIONAT1
    );

    public SynchronizationService(RestTemplate restTemplate, PlayerRepository playerRepository) {
        this.restTemplate = restTemplate;
        this.playerRepository = playerRepository;
    }

    public void synchronize() {
        for (String baseUrl : API_URL_TO_CHAMPIONSHIP.keySet()) {
            Championship championship = API_URL_TO_CHAMPIONSHIP.get(baseUrl);

            try {
                ResponseEntity<Player[]> response = restTemplate.getForEntity(baseUrl + "/players", Player[].class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    int seasonYear = 2024; // Par exemple, ou tu peux l'utiliser dynamiquement.

                    for (Player p : response.getBody()) {
                        System.out.println("Joueur récupéré : " + p);
                        if (p == null) {
                            System.err.println("Un joueur est null dans la réponse.");
                            continue;  // Si un joueur est null, passe au suivant
                        }

                        if (p.getPosition() == null) {
                            System.err.println("Le joueur " + p.getId() + " n'a pas de position définie.");
                            continue;  // Ne pas traiter ce joueur si la position est null
                        }

                        p.setChampionship(championship);  // Assigner le championnat à chaque joueur

                        PlayerStatistics stats = fetchStatistics(baseUrl, p.getId(), seasonYear);
                        if (stats != null) {
                            p.setScoredGoals(stats.getScoredGoals());
                            p.setPlayingTime(stats.getPlayingTime());  // Assigner les statistiques
                        }

                        playerRepository.upsertPlayer(p);
                    }

                }
            } catch (Exception e) {
                System.err.println("Erreur pour API " + baseUrl + " : " + e.getMessage());
            }
        }
    }


    private PlayerStatistics fetchStatistics(String baseUrl, String playerId, int seasonYear) {
        try {
            String url = String.format("%s/players/%s/statistics/%d", baseUrl, playerId, seasonYear);
            return restTemplate.getForObject(url, PlayerStatistics.class);
        } catch (Exception e) {
            System.err.println("Statistiques non trouvées pour joueur " + playerId + " depuis " + baseUrl);
            return null;
        }
    }

}
