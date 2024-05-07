package com.davidruiz.barvendor.Tables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mesas")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping("/listar")
    public String showTableList(Model model) {
        List<TableModel> tables = tableService.getAllTables();
        model.addAttribute("tables", tables);

        return "listarMesas"; // Nombre de la vista de lista de mesas
    }

    @GetMapping("/crear")
    public String showCreateTableForm(Model model) {
        model.addAttribute("table", new TableModel());
        return "crearMesa"; // Nombre de la vista de creación de mesa
    }

    @PostMapping("/crear")
    public String createTable(@ModelAttribute("table") TableModel table) {
        tableService.createTable(table);
        return "redirect:/mesas/listar";
    }

    @GetMapping("/editar/{id}")
    public String showEditTableForm(@PathVariable("id") Long id, Model model) {
        TableModel table = tableService.getTableById(id);
        model.addAttribute("table", table);
        return "editarMesa"; // Nombre de la vista de edición de mesa
    }

    @PostMapping("/editar")
    public String editTable(@ModelAttribute("table") TableModel table) {
        tableService.updateTable(table);
        return "redirect:/mesas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteTable(@PathVariable("id") Long id) {
        tableService.deleteTable(id);
        return "redirect:/mesas/listar";
    }

}
