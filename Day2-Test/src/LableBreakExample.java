public class LableBreakExample {
    public static void main(String[] args) {
        int [][] matrix = {
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}
        };
        int target = 7;
        boolean isFound = false;

        System.out.println("开始搜索");

        searchLoop:
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("正在检查：matrix[%d][%d] = %d/n",i,j,matrix[i][j]);
                if (matrix[i][j] == target ) {
                    isFound = true;
                    System.out.println("》》找到目标值" + target + "！准备跳出所有循环《《");
                    // 使用break lable 跳出名为searchLoop的外层循环
                    break searchLoop;
                }
            }
            System.out.println("这一层不会被打印，因为外层循环已经被终止了");
        }
        System.out.println("---搜索结束---");
        if (isFound) {
            System.out.println("成功定位目标");
        } else {
            System.out.println("未找到目标");
        }
    }
}
/*
在Java开发中，处理多层嵌套循环时，如果我们希望在满足特定条件后立即终止所有循环层级，而不仅仅是当前层，
最直接且优雅的方式是使用带标签的break语句（LabeledBreak）。其核心原理是利用Java的goto风格（
虽然Java只有保留字goto但不支持实际使用，Label机制是其受控的替代品）的跳转机制。
首先，我们需要在目标外层循环的起始处定义一个标签，格式为LabelName:。
然后，在内层循环的判断逻辑中，使用breakLabelName;。这会指示JVM直接中断该标签所修饰的循环结构，
并将控制权转移到该循环结构闭合大括号之后的代码行。相比于传统的做法——即在内层break后设置一个boolean标志位（flag），
并在外层循环中再次检查该标志位来进行二次break——使用breaklabel能显著减少样板代码，消除不必要的状态变量，使代码逻辑更加清晰紧凑。
这也是在进行二维数组搜索或复杂算法剪枝时常用的最佳实践之一。
* */