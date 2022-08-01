

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
@Table(name="department_roles")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class DepartmentRoles implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "department_role_id")
 @Schema(example = "1")
 private Integer departmentRolesId;

 @Schema(example = "1")
 @Column(name = "parent_department_role_id")
 private Integer parentDepartmentRoleId;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "2020-01-20")
 @Column(name = "close_timestamp")
 private Date closeTimestamp;

 @Schema(example = "2020-01-20")
 @Column(name = "insertion_timestamp")
 private Date insertionTimestamp;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @ManyToOne
 @JoinColumn(name = "department_id")
 private Department department;

 @ManyToOne
 @JoinColumn(name = "business_role_id")
 private BusinessRoles businessRole;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_department_role_id")
 private DepartmentRoles rootDepartmentRole;

}
