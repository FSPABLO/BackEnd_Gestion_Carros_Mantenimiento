package org.example.Domain.dtos.maintenance;

import org.example.Domain.models.MaintenanceType;

public class UpdateMaintenanceRequestDto {
    private int id;
    private String description;
    private MaintenanceType type;

    public UpdateMaintenanceRequestDto() {}

    public UpdateMaintenanceRequestDto(int id, String description, MaintenanceType type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaintenanceType getType() {
        return type;
    }

    public void setType(MaintenanceType type) {
        this.type = type;
    }
}