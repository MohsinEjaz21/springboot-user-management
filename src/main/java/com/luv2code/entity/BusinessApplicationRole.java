

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
@Table(name="business_application_role")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class BusinessApplicationRole implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "application_business_role_id")
 @Schema(example = "1")
 private Integer businessApplicationRoleId;

 @Schema(example = "1")
 @Column(name = "business_role_id")
 private Integer businessRoleId;

 @Schema(example = "1")
 @Column(name = "application_role_id")
 private Integer applicationRoleId;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "1")
 @Column(name = "root_id")
 private Integer rootId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamps")
 private Timestamp insertionTimestamps;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamps")
 private Timestamp closeTimestamps;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "1")
 @Column(name = "project_id")
 private Integer projectId;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

}
