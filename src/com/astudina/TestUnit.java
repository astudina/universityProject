package com.astudina;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TestUnit {
    // тестируем сортировку
    @org.junit.jupiter.api.Test
    void sort() {
        int arrSize = 50;
        int[] unsorted = new int[arrSize];
        Random rand = new Random();

        // заполняем массив случайными значениями
        for (int i = 0; i < arrSize; i++) {
            unsorted[i] = rand.nextInt(1000);
        }

        // наша сортировка
        MergeSort sorter = new MergeSort(unsorted);
        sorter.sort();
        int[] sorted = sorter.getSorted();

        // джавовская сортировка
        Arrays.sort(unsorted);

        assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
    }

    // тестируем метод, сливающий два отсортированных массива
    @org.junit.jupiter.api.Test
    void merge() {
        int[] left = {1, 7, 15, 24};
        int[] right = {6, 12, 78, 100, 103};

        int[] res = new int[left.length + right.length];
        System.arraycopy(left, 0, res, 0,  left.length);
        System.arraycopy(right, 0, res, left.length, right.length);

        Arrays.sort(res);

        assertEquals(Arrays.toString(res), Arrays.toString(MergeSort.merge(left, right)));
    }
}