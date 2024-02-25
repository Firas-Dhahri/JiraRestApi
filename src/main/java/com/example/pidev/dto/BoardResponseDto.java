package com.example.pidev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private List<BoardDto> values;

    public List<BoardDto> getValues() {
        return values;
    }

    public void setValues(List<BoardDto> values) {
        this.values = values;
    }
}

