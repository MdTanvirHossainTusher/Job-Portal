package com.example.job_portal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortEntityDTO<T> {
    public static <T> List<T> sortResponseDTO(List<T> dtos, Comparator<T> comparator) {
        if(dtos == null || dtos.isEmpty()) {
            return dtos;
        }
        List<T> sortedList = new ArrayList<>(dtos);
        Collections.sort(sortedList, comparator);
        return sortedList;
    }
}
