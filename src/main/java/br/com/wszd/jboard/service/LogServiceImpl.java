package br.com.wszd.jboard.service;

import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.LogRepository;
import br.com.wszd.jboard.service.interfaces.ILogService;
import br.com.wszd.jboard.util.LogStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LogServiceImpl implements ILogService {

  @Autowired
  LogRepository repository;

  public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method) {
    log.info("Criando log");
    LogTable log = new LogTable.Builder()
            .payload(payload)
            .endpoint(endpoint)
            .userId(userId)
            .status(status)
            .dataInclusao(LocalDateTime.now())
            .method(method)
            .build();
    repository.save(log);
  }
}
