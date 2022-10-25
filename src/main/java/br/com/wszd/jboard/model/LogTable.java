package br.com.wszd.jboard.model;


import br.com.wszd.jboard.util.LogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "logs", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class LogTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String payload;
    private String endpoint;
    private Long userId;
    private LogStatus status;
}
