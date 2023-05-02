package br.com.wszd.jboard.service;

import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.LogRepository;
import br.com.wszd.jboard.service.interfaces.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements ILogService {

  @Autowired
  LogRepository repository;

  public void createLog(LogTable log) {
    repository.save(log);
  }
}
