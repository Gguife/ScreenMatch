package br.com.gguife.screenmatch.repository;

import br.com.gguife.screenmatch.model.Category;
import br.com.gguife.screenmatch.model.Episodes;
import br.com.gguife.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);
    List<Series> findByActorsContainingIgnoreCase(String actorsName);
    List<Series> findTop5ByOrderByRatingDesc();
    List<Series> findByGenre(Category genre);
//    List<Series> findByTotalSeasonsLessThanEqualAndRatingGreaterThan(int totalSeasons, double rating);

    @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :totalSeasons AND s.rating >= :rating")
    List<Series> seriesBySeasonsAndRating(int totalSeasons, double rating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:partOFTitle")
    List<Episodes> searchEpisodesByName(String partOfTitle);
}
