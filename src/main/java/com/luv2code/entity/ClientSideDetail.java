

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
@Table(name="client_side_detail")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class ClientSideDetail implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "client_detail_id")
 @Schema(example = "1")
 private Integer clientSideDetailId;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "string")
 @Column(name = "browser")
 private String browser;

 @Schema(example = "string")
 @Column(name = "browser_version")
 private String browserVersion;

 @Schema(example = "string")
 @Column(name = "os")
 private String os;

 @Schema(example = "string")
 @Column(name = "os_version")
 private String osVersion;

 @Schema(example = "2020-01-20")
 @Column(name = "insertion_timestamps")
 private Date insertionTimestamps;

 @Schema(example = "string")
 @Column(name = "client_ip")
 private String clientIp;

 @Schema(example = "string")
 @Column(name = "device")
 private String device;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

}
