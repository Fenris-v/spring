package org.example.lesson5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable {
    private Long id;
    private String name;
    private String author;
    private CategoryDto category;
}
