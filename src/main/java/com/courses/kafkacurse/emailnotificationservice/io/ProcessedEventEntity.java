package com.courses.kafkacurse.emailnotificationservice.io;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/*
Entity to store information in the DB
 */
@Entity
@Table(name="processed-events")
public class ProcessedEventEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2009904700698328843L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String messageId;

    @Column(nullable = false)
    private String productId;

    //Constructors
    public ProcessedEventEntity() {
    }

    public ProcessedEventEntity(String messageId, String productId) {
        this.messageId = messageId;
        this.productId = productId;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
