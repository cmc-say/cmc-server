package cmc.domain.world.repository;

import cmc.domain.world.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findByHashtagNameIn(List<String> hashtagNames);
}
