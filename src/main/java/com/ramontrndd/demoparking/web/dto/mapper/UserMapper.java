package com.ramontrndd.demoparking.web.dto.mapper;


import com.ramontrndd.demoparking.entity.User;
import com.ramontrndd.demoparking.web.dto.UserCreateDto;
import com.ramontrndd.demoparking.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

public class UserMapper {

   public static User toUser(UserCreateDto createDto) {
    return new ModelMapper().map(createDto, User.class);
   }


    public static UserResponseDto toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        ModelMapper mapperMain = new ModelMapper();
        TypeMap<User, UserResponseDto> propertyMapper =
                mapperMain.createTypeMap(User.class, UserResponseDto.class);
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> role, UserResponseDto::setRole)
        );
        return mapperMain.map(user, UserResponseDto.class);
    }
}
