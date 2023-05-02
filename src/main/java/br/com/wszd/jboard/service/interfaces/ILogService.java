package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.model.LogTable;
import br.com.wszd.jboard.util.LogStatus;

public interface ILogService {

    public void createLog(String payload, String endpoint, Long userId, LogStatus status, String method);
}
