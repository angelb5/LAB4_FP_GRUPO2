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
    public String listaEmployee(Model model){
        model.addAttribute("listaEmployee", employeesRepository.findAll());
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/lista";
    }

    @GetMapping("/new")
    public String nuevoEmployeeForm(Model model) {
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
    public String guardarEmployee(@ModelAttribute("employees") @Valid Employees employees, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  @RequestParam(name="fechaContrato", required=false) String fechaContrato, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaJefes", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/Frm";
        }else {
            if (employees.getId() == 0) {
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employees.setHireDate(new Date());
                employeesRepository.save(employees);
                return "redirect:/employee";
            } else {

                try {
                    employees.setHireDate(new SimpleDateFormat("yyyy-MM-dd").parse(fechaContrato));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                employeesRepository.save(employees);
                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee";
            }
        }
    }

    @GetMapping("/edit")
    public String editarEmployee(@ModelAttribute("employees") @Valid Employees employees,
                                 Model model,
                                 @RequestParam("id") int id) {
        Optional<Employees> optionalEmployees = employeesRepository.findById(id);
        if(optionalEmployees.isPresent()){
            employees = optionalEmployees.get();
            model.addAttribute("employees",employees);
            model.addAttribute("listaJefes",getListaJefes());
            model.addAttribute("listaTrabajos",jobsRepository.findAll());
            model.addAttribute("listaDepartamentos",departmentsRepository.findAll());
            return "employee/editForm";
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
            attr.addFlashAttribute("msg","Empleado borrado exitosamente");
        }
        return "redirect:/employee";

    }

    @PostMapping("/search")
    public String buscar (@RequestParam("searchTxt") String searchTxt,Model model){
        List<Employees> employees = employeesRepository.listarEmpleadosNombreApellidoDepartamentoPuestoCiudad(searchTxt);
        model.addAttribute("listaEmployees",employees);
        return "employee/lista";
    }

}
