package com.infovision.employeeapplication.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infovision.employeeapplication.dto.AddressDto;
import com.infovision.employeeapplication.dto.EmployeeDto;
import com.infovision.employeeapplication.entities.Employee;
import com.infovision.employeeapplication.repository.EmployeeRepository;
import com.infovision.employeeapplication.service.EmployeeService;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ResponseEntity<EmployeeDto> saveEmployee(EmployeeDto employee) {

		logger.info("Enter into save employee data in saveEmployee method");
		Employee employeeentity = null;
		EmployeeDto employeeDto = null;

		try {
			employeeentity = modelMapper.map(employee, Employee.class);
			Employee empResponse = employeeRepository.save(employeeentity);
			employeeDto = modelMapper.map(empResponse, EmployeeDto.class);

			ResponseEntity<AddressDto> postForEntity = restTemplate.postForEntity(
					"http://localhost:8585/addressApplication/address/saveAddress", employee.getAddressDto(),
					AddressDto.class);

			employeeDto.setAddressDto(postForEntity.getBody());

		} catch (Exception e) {
			logger.error("error in save employee---> ", e);
		}

		logger.info("exit from save employee data in saveEmployee method");
		return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<EmployeeDto> getEmployeDetailsById(Long empid) {

		Employee employee = null;
		EmployeeDto employeeDto = null;
		try {
			employee = employeeRepository.findById(empid).get();
			employeeDto = modelMapper.map(employee, EmployeeDto.class);
			ResponseEntity<AddressDto> forEntity = restTemplate
					.getForEntity("http://localhost:8585/addressApplication/address/" + empid, AddressDto.class);
			//
			AddressDto addressDto = forEntity.getBody();
			employeeDto.setAddressDto(addressDto);
		} catch (Exception e) {
			logger.error("error in getbyId employee---> ", e);
		}

		return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EmployeeDto> updateEmployee(EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		Employee employee = modelMapper.map(employeeDto, Employee.class);

		Employee employeeResponse = employeeRepository.findById(employee.getEmpId()).get();

		employeeResponse.setEmpAge(employee.getEmpAge());
		employeeResponse.setEmpEmail(employee.getEmpEmail());
		employeeResponse.setEmpName(employee.getEmpName());
		Employee updetedemp = employeeRepository.save(employeeResponse);

		EmployeeDto updatedDto = modelMapper.map(updetedemp, EmployeeDto.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<AddressDto> entity = new HttpEntity<>(employeeDto.getAddressDto(), headers);

		ResponseEntity<AddressDto> response = restTemplate.exchange("http://localhost:8585/addressApplication/address/",
				HttpMethod.PUT, entity, AddressDto.class);
		updatedDto.setAddressDto(response.getBody());

		return new ResponseEntity<EmployeeDto>(updatedDto, HttpStatus.ACCEPTED);
	}

}
