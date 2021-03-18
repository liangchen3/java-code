package com.lc.java.code.demo.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadTask {


    public static void main(String[] args) throws InterruptedException {

        long startTime = System.nanoTime();

        //将任务拆解为多个线程处理
        List<Task> totalTask = new ArrayList<>();
        fillTaskList(totalTask);
        //每个线程处理的任务数量
        int everyThreadTaskCount = 50;
        //需要的线程数,向上取整 ,   102➗10 结果为11
        int threadCount = getThreadCount(totalTask.size(), everyThreadTaskCount);
        System.out.println("需要的线程数：" + threadCount);
        //结果
        List<Result> resultList = Collections.synchronizedList(new ArrayList<>());

        AtomicInteger a = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int batchNum = a.addAndGet(1);
            int startIndex = (batchNum - 1) * everyThreadTaskCount;
            int endIndex = totalTask.size() - startIndex > everyThreadTaskCount ? batchNum * everyThreadTaskCount : totalTask.size();

            //subList（fromIndex：int，toIndex：int）：List 返回从fromIndex到toindex-1 的 子列表
            List<Task> subList = totalTask.subList(startIndex, endIndex);

            // TODO: 2021/3/18 模拟一个任务：将每个子任务的Task对象的数值相加
            int taskResult = subList.stream().mapToInt(Task::getNum).sum();
            resultList.add(new Result(String.valueOf(batchNum), "成功", taskResult));

            //模拟远程接口调用，睡眠10ms
            Thread.sleep(10L);
        }


        //打印结果
        System.out.println("大小：" + resultList.size());
        int totalResultNum = resultList.stream().mapToInt(Result::getTaskResult).sum();
        System.out.println("汇总结果：" + totalResultNum);
//        resultList.forEach(result -> System.out.println(JSON.toJSON(result))); 520470934
//                                                                               2323884945
        long endTime = System.nanoTime();
        System.out.println("总耗时:" + (endTime - startTime));
    }

    private static int getThreadCount(int size, int everyThreadTaskCount) {
        // 判断式：整除法
        if ((size % everyThreadTaskCount) == 0) {
            return size / everyThreadTaskCount;
        } else {
            return (size / everyThreadTaskCount) + 1;
        }
    }

    private static void fillTaskList(List<Task> totalTask) {
        for (int i = 0; i < 10001; i++) {
            Task task = new Task();
            task.setNum(i);
            totalTask.add(task);
        }
        System.out.println("任务的大小：" + totalTask.size());
        int totalResultNum = totalTask.stream().mapToInt(Task::getNum).sum();
        System.out.println("预期汇总结果：" + totalResultNum);
    }


}
