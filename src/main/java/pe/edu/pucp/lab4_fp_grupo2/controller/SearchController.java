package pe.edu.pucp.lab4_fp_grupo2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Search")
public class SearchController {


    @GetMapping(value = {"","/"})
    public String indice(){
        return "Search/indice";
    }

    @GetMapping(value = {"/Salario"})
    public String listaEmpleadosMayorSalrio (){

      //COMPLETAR
        return "Search/lista2";
    }

    @PostMapping("/busqueda")
    public String buscar (){

        //COMPLETAR
    }

    @GetMapping(value = "/Filtro2")
    public String cantidadEmpleadosPorPais (){

        //COMPLETAR
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep() {
        //COMPLETAR
        return "/Search/lista3";

    }


}
