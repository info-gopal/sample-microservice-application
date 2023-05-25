package com.infovision.addressapplication.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infovision.addressapplication.dto.AddressDto;
import com.infovision.addressapplication.entities.Address;
import com.infovision.addressapplication.repositories.AddressRepository;
import com.infovision.addressapplication.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ResponseEntity<AddressDto> getAddressByempId(Long empid) {
		Address address = addressRepository.findAddressByEmpId(empid);
		AddressDto addressDto = modelMapper.map(address, AddressDto.class);
		return new ResponseEntity<AddressDto>(addressDto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AddressDto> saveAddress(AddressDto addressdto) {
		Address address = modelMapper.map(addressdto, Address.class);
		address = addressRepository.save(address);
		AddressDto addressDto = modelMapper.map(address, AddressDto.class);

		return new ResponseEntity<AddressDto>(addressDto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<Address>> getAllEmployee() {
		List<Address> getEmployeeList = addressRepository.findAll();
		return new ResponseEntity<List<Address>>(getEmployeeList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<AddressDto> updateAddress(AddressDto addressDto) {
		
		Address address = addressRepository.findById(addressDto.getAddId()).get();
		address.setAddDist(addressDto.getAddDist());
		address.setAddId(addressDto.getAddId());
		address.setAddPincode(addressDto.getAddPincode());
		address.setAddState(addressDto.getAddState());
		address.setAddStreet(addressDto.getAddStreet());
		address.setAddTown(addressDto.getAddTown());
		address.setAddVillage(addressDto.getAddVillage());
		
		 address = addressRepository.save(address);
		 addressDto = modelMapper.map(address, AddressDto.class);
		// TODO Auto-generated method stub
		return new ResponseEntity<AddressDto>(addressDto,HttpStatus.ACCEPTED);
	}

}
