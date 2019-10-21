/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brucelibrary.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author bruce
 */
public class MathCalculation {

    private LinkedList arrangementResult;

    /**
     * 排列选择（从列表中选择n个排列）
     *
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public LinkedList arrangementSelect(String[] dataList, int n) {
        System.out.println(String.format("A(%d, %d) = %d", dataList.length, n, arrangement(dataList.length, n)));
        arrangementResult = new LinkedList<ArrayList>();
        arrangementSelect(dataList, new String[n], 0);
        return arrangementResult;
    }

    /**
     * 排列选择
     *
     * @param dataList 待选列表
     * @param resultList 前面（resultIndex-1）个的排列结果
     * @param resultIndex 选择索引，从0开始
     */
    private void arrangementSelect(String[] dataList, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果  
            System.out.println(Arrays.asList(resultList));
            return;
        }

        // 递归选择下一个  
        for (int i = 0; i < dataList.length; i++) {
            // 判断待选项是否存在于排列结果中  
            boolean exists = false;
            for (int j = 0; j < resultIndex; j++) {
                if (dataList[i].equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择  
                resultList[resultIndex] = dataList[i];
                arrangementSelect(dataList, resultList, resultIndex + 1);
            }
        }
    }

    private LinkedList combinationResult;

    /**
     * 组合选择（从列表中选择n个组合）
     *
     * @param dataList 待选列表
     * @param n 选择个数
     */
    public LinkedList combinationSelect(String[] dataList, int n) {
        combinationResult = new LinkedList<ArrayList>();
        combinationSelect(dataList, 0, new String[n], 0);
        return combinationResult;
    }

    /**
     * 组合选择
     *
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param resultIndex 选择索引，从0开始
     */
    private void combinationSelect(String[] dataList, int dataIndex, String[] resultList, int resultIndex) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果  
            combinationResult.add(new ArrayList<>(Arrays.asList(resultList.clone())));
            return;
        }

        //遞迴選擇下一個  
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1);
        }
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    public long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n
     * @param m
     * @return
     */
    public long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

    private LinkedList combinationBResult;

    /**
     * 給定一個字元陣列，以及定義一個字串長度 
     * 計算將這個字元陣列擺放在字元長度中，總共會有多少種組合
     *
     * @param chars 可以擺放的字元陣列
     * @param intNewArrayLength 新的字串長度
     */
    public LinkedList combinationB(String[] chars, int intNewArrayLength) {
        combinationBResult = new LinkedList<ArrayList>();
        String[] subchars = new String[intNewArrayLength];
        combinationB(chars, chars.length, 0, subchars, intNewArrayLength, 0);
        return combinationBResult;
    }

    /**
     *
     * @param chars 可以選擇的字元陣列
     * @param m 可以選擇的字元陣列的長度
     * @param n 目前已選擇的字元陣列數量
     * @param subchars 排列後的陣列結果
     * @param suba 排列後的陣列長度
     * @param subb 目前已排列的陣列長度
     */
    public void combinationB(String[] chars, int m, int n, String[] subchars, int suba, int subb) {
        if (suba == subb) {
//            for (int i = 0; i < subb; ++i) {
//                System.out.print(subchars[i]);
//            }
//            System.out.println("");
            combinationBResult.add(new ArrayList<>(Arrays.asList(subchars.clone())));
            return;
        } else {
            int a = 0;
            for (int i = 0; i < m; i++) {
                subchars[subb] = chars[i];
                a++;
                combinationB(chars, m, n + 1, subchars, suba, subb + 1);
            }
        }
    }
    
    /**
     * 給定一個字元陣列，以及定義一個字串長度 
     * 計算將這個字元陣列擺放在字元長度中，總共會有多少種組合
     *
     * @param chars 可以擺放的字元陣列
     * @param intNewArrayLength 新的字串長度
     */
    public LinkedList combinationC(String[] chars, int intNewArrayLength) {
        combinationBResult = new LinkedList<ArrayList>();
        String[] subchars = new String[intNewArrayLength];
        combinationB(chars, chars.length, 0, subchars, intNewArrayLength, 0);
        return combinationBResult;
    }

    /**
     *
     * @param chars 可以選擇的字元陣列
     * @param m 可以選擇的字元陣列的長度
     * @param n 目前已選擇的字元陣列數量
     * @param subchars 排列後的陣列結果
     * @param suba 排列後的陣列長度
     * @param subb 目前已排列的陣列長度
     */
    public void combinationC(String[] chars, int m, int n, String[] subchars, int suba, int subb,int[] charLimit) {
        if (suba == subb) {
            int[] countResult = new int[m];
            for (int i = 0; i < subchars.length; ++i) {
                for(int j=0;j<m;j++){
                    if(subchars[i].equals(chars[j])){
                        countResult[j]=
                    }
                }
            }
            System.out.println("");
            combinationBResult.add(new ArrayList<>(Arrays.asList(subchars.clone())));
            return;
        } else {
            int a = 0;
            for (int i = 0; i < m; i++) {
                subchars[subb] = chars[i];
                a++;
                combinationB(chars, m, n + 1, subchars, suba, subb + 1);
            }
        }
    }

    /**
     * 傳入兩種商品的單價，以及總共有多少錢 計算總共有多少種購買的組合方式
     *
     * @param goodsAPrice 商品A的單價
     * @param goodsBPrice 商品B的單價
     * @param totalMoney 總共有多少錢
     * @return
     */
    public ArrayList payCombination(int goodsAPrice, int goodsBPrice, int totalMoney) {
        //check input data
        if (goodsAPrice <= 0 || goodsBPrice <= 0 || totalMoney <= 0) {
            return null;
        }
        ArrayList resultList = new ArrayList<String>();
        int counta = totalMoney / goodsAPrice;
        int countb = 0;
        while (counta >= 0) {
            countb = (totalMoney - (goodsAPrice * counta)) / goodsBPrice;
            resultList.add(counta + "-" + countb);
            counta--;
        }
        return resultList;
    }
}
