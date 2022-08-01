

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
@Table(name="control_short_cut_key")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class ControlShortCutKey implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "control_short_cut_key_id")
 @Schema(example = "1")
 private Integer controlShortCutKeyId;

 @Schema(example = "1")
 @Column(name = "app_role_id")
 private Integer appRoleId;

 @Schema(example = "string")
 @Column(name = "action_type")
 private String actionType;

 @Schema(example = "string")
 @Column(name = "action_value")
 private String actionValue;

 @Schema(example = "string")
 @Column(name = "short_cut_key")
 private String shortCutKey;

 @Schema(example = "true")
 @Column(name = "shift_key")
 private Boolean shiftKey;

 @Schema(example = "true")
 @Column(name = "alt_key")
 private Boolean altKey;

 @Schema(example = "true")
 @Column(name = "ctrl_key")
 private Boolean ctrlKey;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

}
