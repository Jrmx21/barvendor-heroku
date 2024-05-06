package com.davidruiz.barvendor.Tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public List<TableModel> getAllTables() {
        return tableRepository.findAll();
    }

    public TableModel createTable(TableModel table) {
        return tableRepository.save(table);
    }

    public TableModel updateTable(Long id, TableModel updatedTable) {
        Optional<TableModel> existingTableOptional = tableRepository.findById(id);
        if (existingTableOptional.isPresent()) {
            TableModel existingTable = existingTableOptional.get();
            // Actualizar los campos necesarios del objeto existingTable con los valores de updatedTable
            // Por ejemplo:
            existingTable.setEspacio_comensales(updatedTable.getEspacio_comensales());
            existingTable.setUbicacion(updatedTable.getUbicacion());
            existingTable.setOcupada(updatedTable.isOcupada());
            // Si necesitas actualizar la lista de cuentas asociadas, hazlo aquí
            return tableRepository.save(existingTable);
        }
        return null; // Manejar el caso en que la mesa no exista
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    public TableModel getTableById(Long id) {
        Optional<TableModel> tableOptional = tableRepository.findById(id);
        return tableOptional.orElse(null);
    }

    public void updateTable(TableModel table) {
        tableRepository.save(table);
    }
}
