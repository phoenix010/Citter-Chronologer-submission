package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.impl.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.service.impl.EmployeeServiceImpl;
import com.udacity.jdnd.course3.critter.service.impl.PetServiceImpl;
import com.udacity.jdnd.course3.critter.service.impl.ScheduleServiceImpl;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    CustomerService customerService;
    EmployeeService employeeService;
    PetService petService;
    ScheduleService scheduleService;

    public ScheduleController(CustomerService customerService, EmployeeService employeeService, PetService petService, ScheduleService scheduleService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        List<Long> employeesIds = scheduleDTO.getEmployeeIds();
        List<Long> petsIds = scheduleDTO.getPetIds();
        Schedule schedule= convertScheduleDTOtoEntity(scheduleDTO);
        Schedule savedSchedule = this.scheduleService.saveSchedule(petsIds,employeesIds,schedule);
        return convertEntitytoScheduleDTO(savedSchedule);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        System.out.println("SceduleList is +" +scheduleList.toString());
        List<ScheduleDTO>scheduleDTOList =  scheduleList.stream().map(schedule->convertEntitytoScheduleDTO(schedule)).collect(Collectors.toList());
        System.out.println("scheduleDTOList is  "+scheduleDTOList.toString());
        return scheduleDTOList;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
//        throw new UnsupportedOperationException();
        List <Schedule> scheduleList = scheduleService.getSchedulesForPet(petId);
        return scheduleList.stream().map(schedule->convertEntitytoScheduleDTO(schedule)).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List <Schedule> scheduleList = scheduleService.getScheduleForEmployee(employeeId);
        return scheduleList.stream().map(x->convertEntitytoScheduleDTO(x)).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List <Schedule> scheduleList = scheduleService.getScheduleForCustomer(customerId);
        return scheduleList.stream().map(x->convertEntitytoScheduleDTO(x)).collect(Collectors.toList());
    }

    private Schedule convertScheduleDTOtoEntity(ScheduleDTO scheduleDTO){
        Set<EmployeeSkill> activities= scheduleDTO.getActivities();
        LocalDate date = scheduleDTO.getDate();
        return new Schedule(activities,date,null,null);
    }
    private ScheduleDTO convertEntitytoScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> petIds = new LinkedList<>();
        List<Long> employeeIds = new LinkedList<>();
        try{
            List<Employee> employeeList = schedule.getEmployees();
            employeeIds = employeeList.stream().map(employee -> employee.getId()).collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);

            List<Pet> petList = schedule.getPets();
            petIds= petList.stream().map(pet->pet.getId()).collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }catch(NullPointerException ex){
            // we may have null pointer exception in case we don't have employee list and pet list.
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTO.setPetIds(petIds);
        }
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}
