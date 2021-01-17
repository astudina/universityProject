package com.astudina;

class MergeSort {

    private int[] unsorted, sorted;

    MergeSort(int[] unsorted) {
        this.unsorted = unsorted;
    }

    // Разбиение входного массива и запуск рекурсивного алгоритма:
    void sort() {
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

            // рекурсия
            MergeSort leftSort = new MergeSort(left);
            MergeSort rightSort = new MergeSort(right);

            leftSort.sort();
            rightSort.sort();

            sorted = merge(leftSort.getSorted(), rightSort.getSorted());
        }
    }

    // слияние двух отсортированных частей массива
    static int[] merge(int[] left, int[] right) {
        int iterLeft = 0, iterRight = 0, iterResult = 0;
        int[] resultArr = new int[left.length + right.length];
        // пока в обеих частях есть элементы
        while (iterLeft < left.length && iterRight < right.length) {
            if (left[iterLeft] <= right[iterRight]) {
                resultArr[iterResult] = left[iterLeft];
                iterLeft++;
            } else {
                resultArr[iterResult] = right[iterRight];
                iterRight++;
            }
            iterResult++;
        }
        // когда части неравной длины и в одной уже перебрали все элементы
        if (iterLeft < left.length) {
            System.arraycopy(left, iterLeft, resultArr, iterResult, resultArr.length - iterResult);
        }
        if (iterRight < right.length) {
            System.arraycopy(right, iterRight, resultArr, iterResult, resultArr.length - iterResult);
        }
        return resultArr;
    }

    int[] getSorted() {
        return sorted;
    }
}
