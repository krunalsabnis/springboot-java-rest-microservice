package com.kru.qualibrate.eth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Service
public class EthLogService {

    private EthLogRepository ethLogRepository;

    private EthConverter ethConverter;

    public EthLogService(EthLogRepository ethLogRepository, EthConverter ethConverter) {
        this.ethLogRepository = ethLogRepository;
        this.ethConverter = ethConverter;
    }

    public void save(EthDTO ethLog) {
        ethLogRepository.save(new EthLogRecord(ethLog));
    }

    public Page<EthDTO> getLogs(Pageable page) {
        return ethLogRepository.findAll(page).map(ethConverter);
    }
}
