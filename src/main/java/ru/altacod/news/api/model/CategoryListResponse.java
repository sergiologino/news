package ru.altacod.news.api.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListResponse {

    private List<CategoryResponse> categories =new ArrayList<>();
}
