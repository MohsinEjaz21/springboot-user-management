

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
@Table(name="working_days")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class WorkingDays implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "working_days_id")
 @Schema(example = "1")
 private Integer workingDaysId;

 @Schema(example = "2020-01-20")
 @Column(name = "start_time")
 private Date startTime;

 @Schema(example = "2020-01-20")
 @Column(name = "end_time")
 private Date endTime;

 @Schema(example = "2020-01-20")
 @Column(name = "break_start_time")
 private Date breakStartTime;

 @Schema(example = "2020-01-20")
 @Column(name = "break_end_time")
 private Date breakEndTime;

 @Schema(example = "5")
 @Column(name = "days")
 private String days;

 @Schema(example = "string")
 @Column(name = "status")
 private String status;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

 @ManyToOne
 @JoinColumn(name = "client_id")
 private Client client;

}
