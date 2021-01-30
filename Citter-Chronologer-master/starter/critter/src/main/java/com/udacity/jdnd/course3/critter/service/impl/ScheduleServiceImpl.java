package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.BadRequestException;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleRepository scheduleRepository;
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    PetService petService;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository,EmployeeService employeeService, PetService petService) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @Override
    public Schedule saveSchedule(List<Long> petsIds,List<Long> employeesIds,Schedule schedule) {

        boolean flag= true;
        List<Employee> employeeList = new LinkedList<>();
        List<Pet> petList = new LinkedList<>();
        for(Long id: employeesIds){
            flag = employeeService.isEmployeeInDB(id);
            System.out.println("flag is "+flag);
            if(!flag){
                throw new BadRequestException("Schedule service cannot save this request as employee with  "+id + " is not in DB");
            }
            Employee employee = employeeService.getEmployeeById(id);
            employeeList.add(employee);
            Set<EmployeeSkill> skills = employee.getSkills();
            Set<EmployeeSkill> activities = schedule.getActivities();

            activities.stream().filter(activity ->{
                skills.stream().anyMatch(skill -> {
                    if (!activity.equals(skill)) {
                        throw new BadRequestException("schedule service cannot save this employee's skill set is different than activities");
                    }
                    return true;
                });
                return true;
            });

            Set<DayOfWeek> daysAvailable = employee.getDaysAvailable();
            DayOfWeek daysSchedule = schedule.getDate().getDayOfWeek();

            daysAvailable.stream().filter(dayOfWeek -> {
                if(!dayOfWeek.equals(daysSchedule))
                    throw new BadRequestException("Schedule service cannot save this this employee with id  "+id +"  is not available");

                return true;
            });
        }

        for(Long id: petsIds){
            flag = petService.isPetInDB(id);
            System.out.println("valur of flag in adding pet "+flag);
            if(!flag){
                throw new BadRequestException("Schedule service cannot save this request as Pet  "+id+"  is not in DB");
            }
            Pet pet = petService.getPetById(id);
            petList.add(pet);
//        for()
        }

        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getSchedulesForPet(long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    @Override
    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    @Override
    public List<Schedule> getScheduleForCustomer(long customerId) {

        String errorMessage = "Customer not found in Id : " + customerId;

        Optional<Customer> optionalCustomer= customerRepository.findById(customerId);
        Customer customer = (Customer) optionalCustomer.orElseThrow(() -> new ObjectNotFoundException(errorMessage));
        List<Pet> pets = customer.getPets();

        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findAllByPetsId(pet.getId()));
        }
        return schedules;
    }
}
