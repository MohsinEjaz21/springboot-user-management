

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
@Table(name="project_team")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class ProjectTeam implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "project_team_id")
 @Schema(example = "1")
 private Integer projectTeamId;

 @Schema(example = "Example ProjectTeam")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @Column(name = "team_member_id")
 private Integer teamMemberId;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "2020-01-20")
 @Column(name = "insertion_timestamp")
 private Date insertionTimestamp;

 @Schema(example = "2020-01-20")
 @Column(name = "close_timestamp")
 private Date closeTimestamp;

 @Schema(example = "1")
 @Column(name = "parent_project_team_id")
 private Integer parentProjectTeamId;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private ProjectTeam root;

 @ManyToOne
 @JoinColumn(name = "business_role_id")
 private BusinessRoles businessRole;

 @ManyToOne
 @JoinColumn(name = "department_id")
 private Department department;

 @ManyToOne
 @JoinColumn(name = "department_role_id")
 private DepartmentRoles departmentRole;

 @ManyToOne
 @JoinColumn(name = "user_type_lov_id")
 private UserTypeLov userTypeLov;

}
