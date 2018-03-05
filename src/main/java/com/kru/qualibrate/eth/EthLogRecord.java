package com.kru.qualibrate.eth;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Data
@NoArgsConstructor
@Entity(name = "eth_log")
public class EthLogRecord {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue
    private long id;

    private String transaction;

    private String response;

    private Date timestamp = new Date();

    public EthLogRecord(EthDTO eth) {
        this.transaction = eth.getTransaction();
        this.response = eth.getResponse();
        this.timestamp = eth.getTimestamp();
    }
}
