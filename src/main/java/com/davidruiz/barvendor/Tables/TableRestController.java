package com.davidruiz.barvendor.Tables;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mesas")
public class TableRestController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public List<TableModel> getAllTables() {
        return tableService.getAllTables();
    }
        @PutMapping("/{id}")
    public TableModel updateTable(@PathVariable Long id, @RequestBody TableModel table) {
        return tableService.updateTable(id, table);
    }

}
