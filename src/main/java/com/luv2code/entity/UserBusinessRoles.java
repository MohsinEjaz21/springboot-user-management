

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
@Table(name="user_business_roles")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class UserBusinessRoles implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "user_business_roles_id")
 @Schema(example = "1")
 private Integer userBusinessRolesId;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamps")
 private Timestamp insertionTimestamps;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamps")
 private Timestamp closeTimestamps;

 @Schema(example = "1")
 @Column(name = "u_id")
 private Integer uId;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @ManyToOne
 @JoinColumn(name = "business_roles_id")
 private BusinessRoles businessRoles;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private UserBusinessRoles root;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

}
