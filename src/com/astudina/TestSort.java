package com.astudina;

import java.util.Random;

public class TestSort {
    public static void main(String[] args) {

        int arrSize = 10000000;
        int[] unsorted = new int[arrSize];
        Random rand = new Random();

        // заполняем массив случайными значениями
        for (int i = 0; i < arrSize; i++) {
            unsorted[i] = rand.nextInt(10000);
        }

        StringBuilder unsortedArr = new StringBuilder("Unsorted: ");
        for (int i = 0; i < 10; i++) {
            unsortedArr.append(unsorted[i * 1000000]);
            unsortedArr.append(" ... ");
        }
        System.out.println(unsortedArr.toString());

        // MERGESORT
        // начинаем считать время
        long startTime = System.nanoTime();

        // сортировка в 1 поток
        MergeSort sorter = new MergeSort(unsorted);
        sorter.sort();

        long endTime = System.nanoTime();

        // выводим время в секундах
        double time = (double) (endTime - startTime) / 1000000000;
        System.out.println("---------------------------\r\n" + "MergeSort: " + time + " seconds");

        int[] sorted = sorter.getSorted();
        StringBuilder resArr1 = new StringBuilder("Sorted by MergeSort: ");
        for (int i = 0; i < 10; i++) {
            resArr1.append(sorted[i * 1000000]);
            resArr1.append(" ... ");
        }
        System.out.println(resArr1.toString());

        // MERGESORTMT
        // считаем время
        startTime = System.nanoTime();

        // направляем первый поток на несортированный массив (внутри он разделится на MAX_THREADS)
        MergeSortMT mergeMT = new MergeSortMT(unsorted);
        mergeMT.start();
        // Ждем завершения потока
        try {
            mergeMT.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // И опять выводим время
        endTime = System.nanoTime();

        StringBuilder resArr2 = new StringBuilder("---------------------------\r\n" + "Sorted by MergeSortMT: ");
        for (int i = 0; i < 10; i++) {
            resArr2.append(sorted[i * 1000000]);
            resArr2.append(" ... ");
        }
        System.out.println(resArr2.toString());

        double timeMT = (double) (endTime - startTime) / 1000000000;
        System.out.println("MergeSortMT: " + timeMT + " seconds");

        System.out.println("---------------------------\r\n" + "MergeSort - MergeSortMT: "
                + (time - timeMT) + " seconds");
    }
}
