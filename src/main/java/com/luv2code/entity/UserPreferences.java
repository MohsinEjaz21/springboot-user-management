

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
@Table(name="user_preferences")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class UserPreferences implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "user_preference_id")
 @Schema(example = "1")
 private Integer userPreferencesId;

 @Schema(example = "string")
 @Column(name = "default_skin")
 private String defaultSkin;

 @Schema(example = "1")
 @Column(name = "idle_timeout")
 private Integer idleTimeout;

 @Schema(example = "1")
 @Column(name = "notification_interval")
 private Integer notificationInterval;

 @Schema(example = "1")
 @Column(name = "chat_interval")
 private Integer chatInterval;

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

 @Schema(example = "string")
 @Column(name = "version")
 private String version;

 @ManyToOne
 @JoinColumn(name = "default_language")
 private Language defaultLanguage;

 @ManyToOne
 @JoinColumn(name = "user_id")
 private Users user;

 @ManyToOne
 @JoinColumn(name = "default_project")
 private Project defaultProject;

 @ManyToOne
 @JoinColumn(name = "default_project_role")
 private BusinessRoles defaultProjectRole;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_preference_id")
 private UserPreferences rootPreference;

}
