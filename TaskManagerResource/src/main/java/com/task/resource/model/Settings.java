package com.task.resource.model;

import com.task.library.dto.setting.GroupBy;
import com.task.library.dto.setting.SettingsDTO;
import com.task.library.dto.setting.Sort;
import com.task.library.dto.setting.SortGroupBy;
import com.task.library.dto.setting.Theme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "setting_id")
    private Long settingId;

    @Column(unique = true, nullable = false, name = "user_id")
    private String userId;

    @Column(nullable = false, name = "group_tasks")
    private boolean groupTasks;

    @Column(nullable = false, name = "group_by")
    private GroupBy groupBy = GroupBy.CREATED_TIME;

    @Column(nullable = false, name = "theme")
    private Theme theme = Theme.LIGHT;

    @Column(nullable = false, name = "sort")
    private Sort sort = Sort.CREATED_TIME;

    @Column(nullable = false, name = "sort_tasks_group_by")
    private SortGroupBy sortGroupBy = SortGroupBy.DESCENDING;

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

        return settings;
    }
}
