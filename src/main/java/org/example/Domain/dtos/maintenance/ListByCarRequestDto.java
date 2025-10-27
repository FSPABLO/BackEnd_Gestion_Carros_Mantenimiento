package org.example.Domain.dtos.maintenance;

public class ListByCarRequestDto {
    private Long carId;

    public ListByCarRequestDto() {}

    public ListByCarRequestDto(Long carId) {
        this.carId = carId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}