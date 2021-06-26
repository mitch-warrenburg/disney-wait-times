package com.disney.disneywaittimes.times.entity;

import com.disney.disneywaittimes.times.model.AttractionStatus;
import com.disney.disneywaittimes.times.model.AttractionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author mitch-warrenburg
 */

@Entity
@Table(name = "wait_time_notification")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "psqlEnum", typeClass = PostgreSQLEnumType.class)
public class WaitTimeNotification implements Serializable {

  private static final long serialVersionUID = -8616227264979801643L;

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = SEQUENCE, generator = "wait_time_notification_id_seq")
  @SequenceGenerator(name = "wait_time_notification_id_seq", sequenceName = "wait_time_notification_id_seq", allocationSize = 1)
  private Long id;

  private String name;
  private int waitTime;
  private boolean active;
  private boolean fastPass;
  private boolean singleRider;
  private LocalDateTime lastUpdate;

  @Enumerated(STRING)
  @Type(type = "psqlEnum")
  private AttractionType type;

  @Enumerated(STRING)
  @Type(type = "psqlEnum")
  private AttractionStatus status;

  @CreatedDate
  @Column(name = "created_ts")
  private LocalDateTime createdTs;

  @LastModifiedDate
  @Column(name = "modified_ts")
  private LocalDateTime modifiedTs;
}
