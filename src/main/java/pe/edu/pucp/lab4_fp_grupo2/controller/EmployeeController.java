package pe.edu.pucp.lab4_fp_grupo2.controller;
import pe.edu.pucp.lab4_fp_grupo2.entity.Employees;
import pe.edu.pucp.lab4_fp_grupo2.repository.DepartmentsRepository;
import pe.edu.pucp.lab4_fp_grupo2.repository.EmployeesRepository;
import pe.edu.pucp.lab4_fp_grupo2.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"","/"})
    public String listaEmployee(@RequestParam(required=false,name="search") String search, Model model){
        List<Employees> employees;
        if( search == null || search.equals("")){
            employees = employeesRepository.findAll();
        }else{
            employees = employeesRepository.listarEmpleadosNombreApellidoDepartamentoPuestoCiudad(search.toLowerCase());
        }
        model.addAttribute("listaEmployee", employees);
        return "employee/lista";
    }

    @GetMapping("/new")
    public String nuevoEmployeeForm(@ModelAttribute("employee") Employees employee, Model model) {

        model.addAttribute("listaJefes",getListaJefes());
        model.addAttribute("listaTrabajos",jobsRepository.findAll());
        model.addAttribute("listaDepartamentos",departmentsRepository.findAll());
        return "employee/Frm";
    }

    public List<Employees> getListaJefes() {
        List<Employees> listaJefes = employeesRepository.findAll();
        Employees e = new Employees();
        e.setId(0);
        e.setFirstName("--No tiene Jefe--");
        listaJefes.add(0, e);
        return listaJefes;
    }


    @PostMapping("/save")
    public String guardarEmployee(@ModelAttribute("employee") @Valid Employees employee, BindingResult bindingResult,
                                  RedirectAttributes attr, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("listaTrabajos", jobsRepository.findAll());
            model.addAttribute("listaJefes", employeesRepository.findAll());
            model.addAttribute("listaDepartamentos", departmentsRepository.findAll());
            return "employee/Frm";
        }else {
            if (employee.getId() == 0) {
                attr.addFlashAttribute("accion","alert-success");
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employee.setHireDate(new Date());
                employeesRepository.save(employee);
                return "redirect:/employee";
            } else {
                Employees employeeDB = employeesRepository.getById(employee.getId());

                employee.setHireDate(employeeDB.getHireDate());

                employeesRepository.save(employee);
                attr.addFlashAttribute("accion","alert-warning");
                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee";
            }
        }
    }

    @GetMapping("/edit")
    public String editarEmployee(Model model,
                                 @RequestParam("id") int id) {
        Optional<Employees> optionalEmployees = employeesRepository.findById(id);
        if(optionalEmployees.isPresent()){
            Employees employee = optionalEmployees.get();
            model.addAttribute("employee",employee);
            model.addAttribute("listaJefes",getListaJefes());
            model.addAttribute("listaTrabajos",jobsRepository.findAll());
            model.addAttribute("listaDepartamentos",departmentsRepository.findAll());
            return "employee/Frm";
        }
        return "redirect:/employee";
    }

    @GetMapping("/delete")
    public String borrarEmpleado(Model model,
                                      @RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<Employees> optEmployees = employeesRepository.findById(id);

        if (optEmployees.isPresent()) {
            employeesRepository.deleteById(id);
            attr.addFlashAttribute("accion","alert-danger");
            attr.addFlashAttribute("msg","Empleado borrado exitosamente");
        }
        return "redirect:/employee";
    }

}
