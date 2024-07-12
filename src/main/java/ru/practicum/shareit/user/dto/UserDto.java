package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    @Email
    private String email;

    @JsonIgnore
    public Optional<String> getNameIfExists() {
        return Optional.ofNullable(name);
    }

    @JsonIgnore
    public Optional<String> getEmailIfExists() {
        return Optional.ofNullable(email);
    }
}
