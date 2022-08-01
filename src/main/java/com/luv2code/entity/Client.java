

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
@Table(name="client")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "client_id")
 @Schema(example = "1")
 private Integer clientId;

 @Schema(example = "USA")
 @Column(name = "country")
 private String country;

 @Schema(example = "Los Angeles")
 @Column(name = "city")
 private String city;

 @Schema(example = "Example description of Client")
 @Column(name = "description")
 private String description;

 @Schema(example = "string")
 @Column(name = "company_name")
 private String companyName;

 @Schema(example = "1234567890")
 @Column(name = "phone_number")
 private String phoneNumber;

 @Schema(example = "Example Client")
 @Column(name = "title")
 private String title;

 @Schema(example = "JamesSmith@mailinator.com")
 @Column(name = "email")
 private String email;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamps")
 private Timestamp insertionTimestamps;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamps")
 private Timestamp closeTimestamps;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "string")
 @Column(name = "logo")
 private String logo;

 @Schema(example = "5")
 @Column(name = "password_expiry_after_days")
 private Integer passwordExpiryAfterDays;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private Client root;

}
