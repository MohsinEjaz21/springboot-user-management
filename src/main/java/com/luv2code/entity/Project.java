

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
@Table(name="project")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "project_id")
 @Schema(example = "1")
 private Integer projectId;

 @Schema(example = "string")
 @Column(name = "image")
 private String image;

 @Schema(example = "string")
 @Column(name = "owner")
 private String owner;

 @Schema(example = "Example Project")
 @Column(name = "short_title")
 private String shortTitle;

 @Schema(example = "2020-01-20")
 @Column(name = "start_date")
 private Date startDate;

 @Schema(example = "2020-01-20")
 @Column(name = "end_date")
 private Date endDate;

 @Schema(example = "Example Project")
 @Column(name = "title")
 private String title;

 @Schema(example = "string")
 @Column(name = "status")
 private String status;

 @Schema(example = "string")
 @Column(name = "code")
 private String code;

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
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "string")
 @Column(name = "logo")
 private String logo;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "old_project_timestamp")
 private Timestamp oldProjectTimestamp;

 @Schema(example = "1")
 @Column(name = "old_project_id")
 private Integer oldProjectId;

 @Schema(example = "string")
 @Column(name = "copy_status")
 private String copyStatus;

 @Schema(example = "1")
 @Column(name = "target_system_id")
 private Integer targetSystemId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "import_start_time")
 private Timestamp importStartTime;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "import_last_update")
 private Timestamp importLastUpdate;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "import_lease_time")
 private Timestamp importLeaseTime;

 @Schema(example = "1")
 @Column(name = "import_default_step_mins_allowed")
 private Integer importDefaultStepMinsAllowed;

 @Schema(example = "1")
 @Column(name = "import_final_step_mins_allowed")
 private Integer importFinalStepMinsAllowed;

 @Schema(example = "string")
 @Column(name = "import_status")
 private String importStatus;

 @Schema(example = "string")
 @Column(name = "business_role_id")
 private String businessRoleId;

 @Schema(example = "1")
 @Column(name = "parent_project_id")
 private Integer parentProjectId;

 @Schema(example = "Example description of Project")
 @Column(name = "description")
 private String description;

 @NotNull
 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private Project root;

 @ManyToOne
 @JoinColumn(name = "import_user_id")
 private Users importUser;

 @ManyToOne
 @JoinColumn(name = "client_id")
 private Client client;

 @ManyToOne
 @JoinColumn(name = "project_type_id")
 private ProjectType projectType;

}
