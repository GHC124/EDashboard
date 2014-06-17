package com.ghc.edashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "competency")
public class Competency {
	private Integer id;
	private String name;
	private String description;
	private String evalator;
	private Integer level;
	private Integer type;
	private Integer competencyGroupId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotEmpty(message = "{validation.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.Size}")
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull(message = "{validation.NotNull}")
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "evaluator")
	public String getEvalator() {
		return evalator;
	}

	public void setEvalator(String evalator) {
		this.evalator = evalator;
	}

	@NotNull(message = "{validation.NotNull}")
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@NotNull(message = "{validation.NotNull}")
	@Column(name = "competency_group_id")
	public Integer getCompetencyGroupId() {
		return competencyGroupId;
	}

	public void setCompetencyGroupId(Integer competencyGroupId) {
		this.competencyGroupId = competencyGroupId;
	}

}
