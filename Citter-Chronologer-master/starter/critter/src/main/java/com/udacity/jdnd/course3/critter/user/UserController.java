package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    CustomerService customerService;
    PetService petService;
    EmployeeService employeeService;
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    public UserController(CustomerService customerService, PetService petService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        LOGGER.info("Saving Customer");
        Customer owner = convertCustomerDTOtoEntity(customerDTO);
        Customer savedOwner = this.customerService.saveCustomer(owner);
        return convertEntityToCustomerDTO(savedOwner);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        LOGGER.info("Displaying the list of customers and their pets");
        List<Customer> ownerList = customerService.getAllCustomers();
        System.out.println("OwnerList is as "+ownerList.toString());
        List<CustomerDTO> ownerListDTO =ownerList.stream().map(owner ->convertEntityToCustomerDTO(owner)).collect(Collectors.toList());
        System.out.println("ownerListDTO is "+ownerListDTO.toString());
        return ownerListDTO;
}

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        LOGGER.info("Displaying owners w.r.t. PetId");
        Pet pet = petService.getPetById(petId);
        Customer owner = pet.getOwner();
//        return new CustomerDTO(owner.getId(), owner.getName(),owner.getPhoneNumber(), owner.getNotes());
        return convertEntityToCustomerDTO(owner);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        LOGGER.info("Saving Employee");
        Employee employee=convertEmployeeDTOtoEntity(employeeDTO);
        Employee saveEmployee = this.employeeService.saveEmployee(employee);//ERROR
        return convertEntityToEmployeeDTO(saveEmployee);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = this.employeeService.getEmployeeById(employeeId);
        return convertEntityToEmployeeDTO(employee);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO findEmployeeByID( @PathVariable long employeeId) {
        LOGGER.info("Displaying employee by employeeID");
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEntityToEmployeeDTO(employee);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/employee/{employeeId}")
    public String setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
//        throw new UnsupportedOperationException();
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
//        employeeService.setAvailability(employee);
        Employee employee1 = employeeService.updateEmployee(employee);
        return employee1.getDaysAvailable().toString();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<Employee> employees = employeeService.getAvailableEmployees(
                employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());

        return employees.stream().map(this::convertEntityToEmployeeDTO).collect(Collectors.toList());
    }
    private Employee convertEmployeeDTOtoEntity(EmployeeDTO employeeDTO){
        return new Employee(employeeDTO.getName(),employeeDTO.getSkills(),employeeDTO.getDaysAvailable());
    }
    private EmployeeDTO convertEntityToEmployeeDTO(Employee employee){
        return new EmployeeDTO(employee.getId(), employee.getName(),employee.getSkills(),employee.getDaysAvailable());
    }
    private List<CustomerDTO> convertEntityListToCustomerDTOList(List <Customer> customer){
        return  customer.stream().map(x -> convertEntityToCustomerDTO(x)).collect(Collectors.toList());
    }

    private Customer convertCustomerDTOtoEntity(CustomerDTO customerDTO){
        return new Customer (customerDTO.getName(),customerDTO.getPhoneNumber(),customerDTO.getNotes());
    }
    private CustomerDTO convertEntityToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petsId= new LinkedList<>() ;
        List<String> petsName = new LinkedList<>();
        try{
            customerDTO.setPetIds(customer.getPets().stream().map(pet->pet.getId()).collect(Collectors.toList()));
            customerDTO.setPetNames(customer.getPets().stream().map(pet->pet.getName()).collect(Collectors.toList()));
        }catch(NullPointerException ex){
            //getPets() can generate an exception in a scenario where we add the first pet for any owner
            //this exception is null pointer exception.
            customerDTO.setPetIds(petsId);
            customerDTO.setPetNames(petsName);

        }
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
    }


}
