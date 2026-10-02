package com.task_management_system.mapper;

import com.task_management_system.dto.RegistrationDto;
import com.task_management_system.entity.User;
import org.modelmapper.ModelMapper;

public class RegistrationDtoToUser {
   static ModelMapper mapper=new ModelMapper();
   public static User convertToUser(RegistrationDto registrationDto){
       return mapper.map(registrationDto,User.class);
    }
}
