package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.*;
import com.RentBikApp.RentBik.Model.*;
import com.RentBikApp.RentBik.Repository.CustomerRepository;
import com.RentBikApp.RentBik.Repository.GplxRepository;
import com.RentBikApp.RentBik.Repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final GplxRepository gplxRepository;
    private final RentRepository rentRepository;
    public CustomerService(CustomerRepository customerRepository, GplxRepository gplxRepository, RentRepository rentRepository) {
        this.customerRepository = customerRepository;
        this.gplxRepository = gplxRepository;
        this.rentRepository = rentRepository;
    }
    public Object saveCustomer(CustomerDto dto, Set<Integer> gplxIds){
        var customer = toCustomer(dto);

        // check cccd exist
        if (customerRepository.existsByCccd(customer.getCccd())) {
            return new ErrorResponse("CCCD must be unique");
        }

        // check phone number exist
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())){
            return new ErrorResponse("Phone number must be unique");
        }

        Set<Gplx> gplxs = new HashSet<>(gplxRepository.findAllById(gplxIds));
        customer.setGplxs(gplxs);
        return customerRepository.save(customer);
    }

    public List<CustomerResponseDto> searchCustomers(String keyword){
        List<Customer> customers = customerRepository.findByKeywordContainingIgnoreCase(keyword);
        return customers.stream()
                .map(this::toCustomerResponseDto)
                .collect(Collectors.toList());
    }

    private Customer toCustomer(CustomerDto dto){
        var customer = new Customer();
        customer.setCccd(dto.cccd());
        customer.setFullname(dto.fullname());
        customer.setBirthday(dto.birthday());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setNote(dto.note());
        return customer;
    }
    public List<CustomerResponseDto> findAllCustomer(){
        List<Customer> customers = customerRepository.findAllByOrderByIdAsc();
        return customers.stream()
                .map(this::toCustomerResponseDto)
                .collect(Collectors.toList());
    }

    public Object findCustomerByCccd(String cccd){
        // check cccd doesn't exist
        if (!customerRepository.existsByCccd(cccd)) {
            return new ErrorResponse("CCCD doesn't exist");
        }

        return toCustomerResponseDto(customerRepository.findAllByCccdContaining(cccd));
    }

    public List<CccdDto> getCccds(){
        List<Object[]> cccds = customerRepository.getCccds();

        return cccds.stream()
                .map(arr -> new CccdDto(
                    ((Number) arr[0]).intValue(),
                    (String) arr[1]
                ))
                .collect(Collectors.toList());
    }

    private CustomerResponseDto toCustomerResponseDto(Customer customer){
        Set<GplxResponseDto> gplxes = customer.getGplxs().stream()
                .map(gplx -> new GplxResponseDto(gplx.getId(), gplx.getRank()))
                .collect(Collectors.toSet());

        List<RentResponseDto> rents = customer.getRents().stream()
                .map(rent -> new RentResponseDto(
                        rent.getId(),
                        toCarResponseDto(rent.getCar()),
                        rent.getCustomer(),
                        rent.getRentalDate(),
                        rent.getExpiryDate(),
                        rent.getRentalNote(),
                        rent.getRentStatus(),
                        rent.getReturnCard()))
                .collect(Collectors.toList());

        return new CustomerResponseDto(
          customer.getId(),
          customer.getCccd(),
          customer.getFullname(),
          customer.getBirthday(),
          customer.getPhoneNumber(),
          gplxes,
          rents,
          customer.getNote()
        );
    }

    private CarResponseDto toCarResponseDto(Car car){
        return new CarResponseDto(
                car.getId(),
                car.getLicensePlate(),
                car.getType(),
                car.getBrand(),
                car.getSeries(),
                car.getInsurance(),
                car.getPurchasePrice(),
                car.getHirePrice(),
                car.getPurchaseDate(),
                car.getCarNote(),
                car.getStatus()
        );
    }

    public Object updateCustomer(CustomerDto dto, Set<Integer> gplxIds){
        var customer = customerRepository.findByCCCD(dto.cccd());

        if (customer == null) {
            return new ErrorResponse("Customer not found");
        }

        int count = customerRepository.getCustomerNotCccd(dto.phoneNumber(), dto.cccd());
        if (count > 0){
            return new ErrorResponse("Phone number must be unique");
        }

        // update
        customer.setFullname(dto.fullname());
        customer.setBirthday(dto.birthday());
        customer.setPhoneNumber(dto.phoneNumber());
        Set<Gplx> gplxs = new HashSet<>(gplxRepository.findAllById(gplxIds));
        customer.setGplxs(gplxs);
        customer.setNote(dto.note());
        // save
        return customerRepository.save(customer);
    }

    public Object deleteCustomer(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()){
            return new ErrorResponse("CCCD doesn't exist");
        }

        int count = rentRepository.getRentHasCustomerId(id);
        if (count > 0){
            return new ErrorResponse("This customer is still renting car");
        }

        customerRepository.deleteById(id);

        return new SuccessResponse("Delete successfully");
    }
}
