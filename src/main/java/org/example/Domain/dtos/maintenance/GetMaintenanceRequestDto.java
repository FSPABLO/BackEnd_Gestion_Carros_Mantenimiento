package org.example.Domain.dtos.maintenance;

public class GetMaintenanceRequestDto {
    private Long id;

    public GetMaintenanceRequestDto() {}

    public GetMaintenanceRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}