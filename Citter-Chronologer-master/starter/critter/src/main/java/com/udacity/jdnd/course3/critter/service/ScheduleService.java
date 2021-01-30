package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule saveSchedule(List<Long> petsIds,List<Long> employeesIds,Schedule schedule);

    List<Schedule> getAllSchedules();

    List<Schedule> getSchedulesForPet(long petId);

    List<Schedule> getScheduleForEmployee(long employeeId);

    List<Schedule> getScheduleForCustomer(long customerId);
}
