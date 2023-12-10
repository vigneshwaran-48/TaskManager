package com.task.library.dto.setting;

public class SettingsDTO {
    
    private Long settingId;
    private String userId;
    private Theme theme;
    private Sort sortBy;
    private GroupBy groupBy;
    private SortGroupBy sortGroupBy;
    private boolean shouldGroupTasks;
    
    public Long getSettingId() {
        return settingId;
    }
    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Theme getTheme() {
        return theme;
    }
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    public Sort getSortBy() {
        return sortBy;
    }
    public void setSortBy(Sort sortBy) {
        this.sortBy = sortBy;
    }
    public GroupBy getGroupBy() {
        return groupBy;
    }
    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }
    public SortGroupBy getSortGroupBy() {
        return sortGroupBy;
    }
    public void setSortGroupBy(SortGroupBy sortGroupBy) {
        this.sortGroupBy = sortGroupBy;
    }
    public boolean getShouldGroupTasks() {
        return shouldGroupTasks;
    }
    public void setShouldGroupTasks(boolean shouldGroupTasks) {
        this.shouldGroupTasks = shouldGroupTasks;
    }
    @Override
    public String toString() {
        return "SettingsDTO [settingId=" + settingId + ", userId=" + userId + ", theme=" + theme + ", sortBy=" + sortBy
                + ", groupBy=" + groupBy + ", sortGroupBy=" + sortGroupBy + ", shouldGroupTasks=" + shouldGroupTasks
                + "]";
    }

    
}
