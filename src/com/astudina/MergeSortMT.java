package com.astudina;

public class MergeSortMT extends Thread {
    private int[] unsorted, sorted;

    // ограничиваем максимальное количество запускаемых потоков
    private static final int MAX_THREADS = 100;

    MergeSortMT(int[] unsorted) {
        this.unsorted = unsorted;
    }

    public void run() {
        int middle;
        int[] left, right;

        //  если массив из одного элемента, то нет смысла сортировать
        if (unsorted.length <= 1) {
            sorted = unsorted;
        } else {
            // разделим массив на две части
            middle = unsorted.length / 2;
            left = new int[middle];
            right = new int[unsorted.length - middle];
            System.arraycopy(unsorted, 0, left, 0, middle);
            System.arraycopy(unsorted, middle, right, 0, unsorted.length - middle);

            // Пока не превысили максимальное количество потоков, запускаем рекурсивно новые потоки на 2-х частях
            if (activeCount() < MAX_THREADS) {
                MergeSortMT leftSort = new MergeSortMT(left);
                MergeSortMT rightSort = new MergeSortMT(right);
                leftSort.start();
                rightSort.start();

                // как только потоки дождутся друг друга, добавляем еще
                try {
                    leftSort.join();
                    rightSort.join();
                    sorted = MergeSort.merge(leftSort.getSorted(), rightSort.getSorted());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                // когда новых потоков запускать нельзя - запускаем алгоритм для одного потока
                MergeSort leftSort = new MergeSort(left);
                MergeSort rightSort = new MergeSort(right);

                // воспользуемся встроенной в java сортировкой для сортировки частей массива
                leftSort.sort();
                rightSort.sort();

                sorted = MergeSort.merge(leftSort.getSorted(), rightSort.getSorted());
            }

        }
    }

    int[] getSorted() {
        return sorted;
    }
}
