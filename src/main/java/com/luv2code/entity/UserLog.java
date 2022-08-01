

package com.luv2code.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user_log")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class UserLog implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "user_log_id")
 @Schema(example = "1")
 private Integer userLogId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "user_log_datetime")
 private Timestamp userLogDatetime;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "user_logout_datetime")
 private Timestamp userLogoutDatetime;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "last_checked_datetime")
 private Timestamp lastCheckedDatetime;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "token_expiry")
 private Timestamp tokenExpiry;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @ManyToOne
 @JoinColumn(name = "user_id")
 private Users user;

}
