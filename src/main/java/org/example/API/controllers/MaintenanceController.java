package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.DataAccess.services.MaintenanceService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.maintenance.*;
import org.example.Domain.models.Car;
import org.example.Domain.models.Maintenance;

import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final Gson gson = new Gson();

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    public ResponseDto route(RequestDto request) {
        try {
            switch (request.getRequest()) {
                case "add":
                    return handleAddMaintenance(request);
                case "update":
                    return handleUpdateMaintenance(request);
                case "delete":
                    return handleDeleteMaintenance(request);
                case "get":
                    return handleGetMaintenance(request);
                case "list_by_car":
                    return handleListByCar(request);
                default:
                    return new ResponseDto(false, "Unknown request: " + request.getRequest(), null);
            }
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage(), null);
        }
    }

    // --- ADD ---
    private ResponseDto handleAddMaintenance(RequestDto request) {
        AddMaintenanceRequestDto dto = gson.fromJson(request.getData(), AddMaintenanceRequestDto.class);

        // Usar un Car conectado a la sesión
        Car car = maintenanceService.getCarById(dto.getCarId());
        if (car == null) {
            return new ResponseDto(false, "Car not found", null);
        }

        System.out.println("Recibido AddMaintenanceRequestDto:");
        System.out.println("Descripción: " + dto.getDescription());
        System.out.println("Tipo: " + dto.getType());
        System.out.println("CarId: " + dto.getCarId());

        Maintenance maintenance = maintenanceService.createMaintenance(dto.getDescription(), dto.getType(), car);
        return new ResponseDto(true, "Maintenance added successfully", gson.toJson(toResponseDto(maintenance)));
    }

    // --- UPDATE ---
    private ResponseDto handleUpdateMaintenance(RequestDto request) {
        UpdateMaintenanceRequestDto dto = gson.fromJson(request.getData(), UpdateMaintenanceRequestDto.class);
        Maintenance maintenance = maintenanceService.updateMaintenance(dto.getId(), dto.getDescription(), dto.getType(), null); // el car no se usa

        if (maintenance == null) {
            return new ResponseDto(false, "Maintenance not found", null);
        }

        return new ResponseDto(true, "Maintenance updated successfully", gson.toJson(toResponseDto(maintenance)));
    }

    // --- DELETE ---
    private ResponseDto handleDeleteMaintenance(RequestDto request) {
        DeleteMaintenanceRequestDto dto = gson.fromJson(request.getData(), DeleteMaintenanceRequestDto.class);
        boolean deleted = maintenanceService.deleteMaintenance(dto.getId());

        if (!deleted) {
            return new ResponseDto(false, "Maintenance not found or could not be deleted", null);
        }

        return new ResponseDto(true, "Maintenance deleted successfully", null);
    }

    // --- GET ONE ---
    private ResponseDto handleGetMaintenance(RequestDto request) {
        GetMaintenanceRequestDto dto = gson.fromJson(request.getData(), GetMaintenanceRequestDto.class);
        Maintenance maintenance = maintenanceService.getMaintenanceById(dto.getId());

        if (maintenance == null) {
            return new ResponseDto(false, "Maintenance not found", null);
        }

        return new ResponseDto(true, "Maintenance retrieved successfully", gson.toJson(toResponseDto(maintenance)));
    }

    // --- LIST BY CAR ---
    private ResponseDto handleListByCar(RequestDto request) {
        ListByCarRequestDto dto = gson.fromJson(request.getData(), ListByCarRequestDto.class);
        List<Maintenance> list = maintenanceService.getAllMaintenanceByCarId(dto.getCarId());

        List<MaintenanceResponseDto> dtoList = list.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

        return new ResponseDto(true, "List by car retrieved successfully", gson.toJson(new ListMaintenancesResponseDto(dtoList)));
    }

    // --- Helper ---
    private MaintenanceResponseDto toResponseDto(Maintenance m) {
        return new MaintenanceResponseDto(
                m.getId(),
                m.getDescription(),
                m.getType(),
                m.getCarMaintenance().getId()
        );
    }
}