package net.javaguides.springboot.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {
    public static List<Integer> convertStringToListNumber(String input) {
        try {
            return Arrays.stream(input.split(","))
                    .map(String::trim) // xóa khoảng trắng thừa
                    .map(Integer::parseInt) // convert sang Integer
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
