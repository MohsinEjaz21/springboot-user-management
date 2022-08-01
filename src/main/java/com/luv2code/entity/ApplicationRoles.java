

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
@Table(name="application_roles")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class ApplicationRoles implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "application_role_id")
 @Schema(example = "1")
 private Integer applicationRolesId;

 @Schema(example = "string")
 @Column(name = "name")
 private String name;

 @Schema(example = "string")
 @Column(name = "type")
 private String type;

 @Schema(example = "1")
 @Column(name = "menu_flag")
 private Integer menuFlag;

 @Schema(example = "Example ApplicationRoles")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @Column(name = "menu_parent")
 private Integer menuParent;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamps")
 private Timestamp insertionTimestamps;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamps")
 private Timestamp closeTimestamps;

 @Schema(example = "string")
 @Column(name = "category")
 private String category;

 @Schema(example = "string")
 @Column(name = "url")
 private String url;

 @Schema(example = "1")
 @Column(name = "page_flag")
 private Integer pageFlag;

 @Schema(example = "1")
 @Column(name = "event_flag")
 private Integer eventFlag;

 @Schema(example = "string")
 @Column(name = "sub_category")
 private String subCategory;

 @Schema(example = "string")
 @Column(name = "quick_link")
 private String quickLink;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @Schema(example = "string")
 @Column(name = "icon")
 private String icon;

 @Schema(example = "string")
 @Column(name = "category_icon")
 private String categoryIcon;

 @Schema(example = "1")
 @Column(name = "app_role_id")
 private Integer appRoleId;

 @Schema(example = "Example description of ApplicationRoles")
 @Column(name = "description")
 private String description;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private ApplicationRoles root;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

 @ManyToOne
 @JoinColumn(name = "application_id")
 private Applications application;

}
