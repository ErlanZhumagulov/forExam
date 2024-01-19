package com.example.demo.responses;

import com.example.demo.models.AdditionalSettings;
import com.example.demo.models.Category;
import com.example.demo.models.Image;
import com.example.demo.models.enums.Availability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse {

    private Integer id;

    private String nameProduct;

    private Integer price;

    private String description;

    private String availability;

    private List<Integer> idImages;

    private List<String> additionalSettingsCriteriaList;

    private List<String> additionalSettingsValueList;

    private List<String> categoriesList;


}
