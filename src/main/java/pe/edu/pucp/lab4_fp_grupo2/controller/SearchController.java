package pe.edu.pucp.lab4_fp_grupo2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.lab4_fp_grupo2.entity.Employees;
import pe.edu.pucp.lab4_fp_grupo2.repository.DepartmentsRepository;
import pe.edu.pucp.lab4_fp_grupo2.repository.EmployeesRepository;

import java.util.List;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"","/"})
    public String indice(){
        return "Search/indice";
    }

    @GetMapping(value = {"/Salario"})
    public String listaEmpleadosMayorSalario (Model model){

      //COMPLETAR
        model.addAttribute("listEmpleadosSalario",employeesRepository.listarEmpleadosMayorSalario());
        return "Search/lista2";
    }

    /**@PostMapping("/busqueda")
    public String buscar (){

        //COMPLETAR
    }**/

    @GetMapping(value = "/Filtro2")
    public String salarioMaxDepartamento (Model model){

        //COMPLETAR
        model.addAttribute("listSalarioMaxDepart",departmentsRepository.listaSueldoDepartamento());
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep(@RequestParam("id") String id, Model model) {
        //COMPLETAR
        model.addAttribute("listaEmpDepart",departmentsRepository.listaEmpleadoDepartmento(id));
        return "/Search/lista3";

    }


}
