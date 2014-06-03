package com.ghc.edashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ghc.edashboard.web.GlobalVariables;
import com.ghc.edashboard.web.util.UploadUtil;

@Entity
@Table(name = "file")
public class File {
	private Integer id;
	private String name;
	private Long size;
	private LocalDateTime dateUp;
	private String description;
	private String downloadUrl;
	private Integer folderId;
	
	private Folder folder;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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

	@NotNull(message = "{validation.NotNull}")
	@Column(name = "size")
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@NotNull(message = "{validation.NotNull}")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "dateup")
	public LocalDateTime getDateUp() {
		return dateUp;
	}

	public void setDateUp(LocalDateTime dateUp) {
		this.dateUp = dateUp;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "download_url")
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@NotNull(message = "{validation.NotNull}")
	@Column(name = "folder_id")
	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="folder_id", insertable = false, updatable = false)
	public Folder getFolder(){
		return folder;
	}
	
	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	@javax.persistence.Transient
	public String getDateUpString() {
		String dateUpString = "";
		if (dateUp != null) {
			String dateFormatPattern = GlobalVariables.getInstance()
					.getDateFormatPattern();
			DateTimeFormatter dtfOut = DateTimeFormat
					.forPattern(dateFormatPattern);
			dateUpString = dtfOut.print(dateUp);

		}
		return dateUpString;
	}

	@javax.persistence.Transient
	public String getSizeString() {
		String sizeString = "";
		if (size != null) {
			sizeString = UploadUtil.getFileSize(size);
		}
		return sizeString;
	}
}
