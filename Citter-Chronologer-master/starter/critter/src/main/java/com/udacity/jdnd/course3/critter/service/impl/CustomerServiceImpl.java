package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if(isNullOrEmpty(customer.getName().trim())){
            throw new ObjectNotFoundException("Please type your name");
        }
       return  customerRepository.save(customer);
    }
    private static boolean isNullOrEmpty(String str){
        if(str !=null && !str.isEmpty())
            return false;
        else
            return true;
    }
    private Boolean isCustomerinDB(String name){
        try{
            Customer customer= customerRepository.findByName(name);
            System.out.println(customer.getName()+"employee is");
        }catch (Exception ex){
            System.out.println("Exception is "+ex);
            System.out.println("ex is null. so customer doesn't exist");
            return false;
        }
        return true;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List <Customer>customerList =  customerRepository.findAll();
        if(customerList.size() == 0){
            throw new ObjectNotFoundException("No customers in the DB");
        }
        return customerList;
    }

    @Override
    public Customer findById(long customerId) {
        Optional<Customer> owner = customerRepository.findById(customerId);
        if(owner.isPresent()){
            return owner.get();
        }else{
            throw new ObjectNotFoundException("customer not found");
        }
    }
}
