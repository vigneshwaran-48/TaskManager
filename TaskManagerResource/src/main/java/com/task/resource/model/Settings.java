package com.task.resource.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.task.library.dto.setting.GroupBy;
import com.task.library.dto.setting.SettingsDTO;
import com.task.library.dto.setting.Sort;
import com.task.library.dto.setting.SortGroupBy;
import com.task.library.dto.setting.Theme;

@Document
public class Settings {
    @Id
    private String settingId;

    private String userId;

    private boolean groupTasks;

    private GroupBy groupBy = GroupBy.CREATED_TIME;

    private Theme theme = Theme.LIGHT;

    private Sort sort = Sort.CREATED_TIME;

    private SortGroupBy sortGroupBy = SortGroupBy.DESCENDING;

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isGroupTasks() {
        return groupTasks;
    }

    public void setGroupTasks(boolean groupTasks) {
        this.groupTasks = groupTasks;
    }

    public GroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public SortGroupBy getSortGroupBy() {
        return sortGroupBy;
    }

    public void setSortGroupBy(SortGroupBy sortGroupBy) {
        this.sortGroupBy = sortGroupBy;
    }

    public SettingsDTO toSettingsDTO() {

        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setUserId(userId);
        settingsDTO.setSettingId(settingId);
        settingsDTO.setGroupBy(groupBy);
        settingsDTO.setShouldGroupTasks(groupTasks);
        settingsDTO.setSortBy(sort);
        settingsDTO.setSortGroupBy(sortGroupBy);
        settingsDTO.setTheme(theme);

        return settingsDTO;
    }

    public static Settings toSettings(SettingsDTO settingsDTO) {

        Settings settings = new Settings();
        settings.setUserId(settingsDTO.getUserId());
        settings.setGroupBy(settingsDTO.getGroupBy());
        settings.setGroupTasks(settingsDTO.getShouldGroupTasks());
        settings.setSettingId(settingsDTO.getSettingId());
        settings.setSort(settingsDTO.getSortBy());
        settings.setTheme(settingsDTO.getTheme());
        settings.setSortGroupBy(settingsDTO.getSortGroupBy());

        return settings;
    }
}
