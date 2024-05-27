package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.CccdDto;
import com.RentBikApp.RentBik.DTO.CustomerDto;
import com.RentBikApp.RentBik.DTO.CustomerResponseDto;
import com.RentBikApp.RentBik.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customers")
    public List<CustomerResponseDto> findAllCustomer(){
        return customerService.findAllCustomer();
    }

    @GetMapping("customers/{customer_cccd}")
    public Object findCustomerByCccd(
            @PathVariable("customer_cccd") String cccd
    ){
        return customerService.findCustomerByCccd(cccd);
    }

    @GetMapping("customers/search")
    public List<CustomerResponseDto> searchCustomers(
            @RequestParam String keyword
    ){
        return customerService.searchCustomers(keyword);
    }

    @GetMapping("/customers/cccds")
    public List<CccdDto> getCccds(){
        return customerService.getCccds();
    }

    @PostMapping("/customers/add")
    public Object addUser(
            @RequestBody CustomerDto dto
    ){
        return customerService.saveCustomer(dto, dto.gplxIds());
    }

    @PutMapping("/customers/update")
    public Object updateUser(
            @RequestBody CustomerDto dto
    ){
        return customerService.updateCustomer(dto, dto.gplxIds());
    }

    @DeleteMapping("/customers/{customer_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object deleteCustomer(
            @PathVariable("customer_id") Integer id
    ){
        return customerService.deleteCustomer(id);
    }
}
