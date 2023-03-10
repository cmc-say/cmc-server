package cmc.service;

import cmc.domain.RecommendedAlarm;
import cmc.repository.RecommendedAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final RecommendedAlarmRepository recommendedAlarmRepository;

    public List<RecommendedAlarm> getRecommendedAlarm() {
        return recommendedAlarmRepository.findAll();
    }
}
