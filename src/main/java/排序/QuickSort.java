/**
 * @Project: testDeomInSpring
 * @ClassName: QuickSort
 * @Date: 2024年 05月 08日 11:00
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 排序;

/**
 * @Description:
 * @Date: 2024年 05月 08日 11:00
 * @Author: MR.Yu
 **/
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {10, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left;
        int j = right;
        int temp = arr[left];
        while (i < j) {
            while (i < j && arr[j] >= temp) {
                j--;
            }
            arr[i] = arr[j];
            while (i < j && arr[i] <= temp) {
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i] = temp;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }
}
