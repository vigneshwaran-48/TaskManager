package com.task.library.dto.task;

public class TagDTO {

	private Long tagId;
	private String tagName;
	private String tagColor;
	private Long taskId;

	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagColor() {
		return tagColor;
	}
	public void setTagColor(String tagColor) {
		this.tagColor = tagColor;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String toString() {
		return "TagDTO [tagId=" + tagId + ", tagName=" + tagName + ", tagColor=" + tagColor + ", taskId=" + taskId
				+ "]";
	}
}
