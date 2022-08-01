

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
@Table(name="form_label_constant_translation")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class FormLabelConstantTranslation implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "form_label_constant_translation_id")
 @Schema(example = "1")
 private Integer formLabelConstantTranslationId;

 @Schema(example = "string")
 @Column(name = "value")
 private String value;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @ManyToOne
 @JoinColumn(name = "form_label_constant_id")
 private FormLabelConstant formLabelConstant;

 @ManyToOne
 @JoinColumn(name = "language_id")
 private Language language;

}
