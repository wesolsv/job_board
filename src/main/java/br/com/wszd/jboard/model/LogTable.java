package br.com.wszd.jboard.model;


import br.com.wszd.jboard.util.LogStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs", schema = "job_board")
@NoArgsConstructor
@AllArgsConstructor
public class LogTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payload")
    private String payload;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LogStatus status;

    @Column(name = "data_inclusion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape= JsonFormat.Shape.STRING)
    private LocalDateTime dataInclusao;

    @Column(name = "method")
    private String method;

    public static class Builder{

        private String payload;
        private String endpoint;
        private Long userId;
        private LogStatus status;
        private String method;

        private LocalDateTime dataInclusao;
        public LogTable.Builder payload(String payload){
            this.payload = payload;
            return this;
        }public LogTable.Builder endpoint(String endpoint){
            this.endpoint = endpoint;
            return this;
        }public LogTable.Builder userId(Long userId){
            this.userId = userId;
            return this;
        }public LogTable.Builder status(LogStatus status){
            this.status = status;
            return this;
        }public LogTable.Builder dataInclusao(LocalDateTime dataInclusao){
            this.dataInclusao = dataInclusao;
            return this;
        }public LogTable.Builder method(String method){
            this.method = method;
            return this;
        }

        public LogTable build(){
            return new LogTable(this);
        }
    }

    private LogTable(LogTable.Builder builder){
        payload = builder.payload;
        endpoint = builder.endpoint;
        userId = builder.userId;
        status = builder.status;
        dataInclusao = builder.dataInclusao;
        method = builder.method;
    }
}
