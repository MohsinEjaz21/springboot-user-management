

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
@Table(name="version")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Version implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "version_id")
 @Schema(example = "1")
 private Integer versionId;

 @Schema(example = "Example Version")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @Column(name = "major_version")
 private Integer majorVersion;

 @Schema(example = "1.0")
 @Column(name = "minor_version")
 private Double minorVersion;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamp")
 private Timestamp insertionTimestamp;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamp")
 private Timestamp closeTimestamp;

 @Schema(example = "1")
 @Column(name = "old_version_id")
 private Integer oldVersionId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "version_timestamp")
 private Timestamp versionTimestamp;

 @Schema(example = "string")
 @Column(name = "copy_status")
 private String copyStatus;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @ManyToOne
 @JoinColumn(name = "user_id")
 private Users user;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_version_id")
 private Version rootVersion;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

}
