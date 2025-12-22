import java.util.Arrays;

public class ArrayInitExample {
    public static void main(String[] args) {
        //初始化二维数组，只指定第一位长度
        //这被称为‘交错数组’或‘不规则数组’的基础
        int[][] arr = new int[3][];
        System.out.println("检查arr[0]的值");
        System.out.println("arr[0]的值是："+arr[0]);
        System.out.println("尝试访问arr[0][0]");
        try {
            int val = arr[0][0];
        } catch (Exception e) {
            System.out.println("发生异常 试图从null中取值（NullPointerException）");
        }
        //如何正确的填入内层数组
        //手动创建一个数组，挂载到arr[0]上
        arr[0] = new int[]{10,20,30};
        arr[1] = new int[5];//也可以挂载一个不同长度的数组，这就是为什么二维数组叫数组的数组
        arr[2] = new int[]{40,50,60,70};
//        arr[3] = new int[]{2,3,4,5,6};//ArrayIndexOutOfBoundsException


        System.out.println("初始化arr[0]的值："+ Arrays.toString(arr[0]));
        System.out.println("初始化arr[0][0]的值："+ arr[0][0]);
        System.out.println("初始化arr[2][3]的值："+ arr[2][3]);
//        System.out.println("初始化arr[3][2]的值："+ arr[3][2]);
        //报错 第一维长度只有 3，合法下标只能是 0、1、2。arr[3] 本身就越界了
        System.out.println(arr[1].length);
    }
}
/*
关于int[][]arr=newint[3][]初始化后arr[0]的值，答案是null。这是因为在Java中，二维数组本质上是**“数组的数组”。
当我们使用newint[3][]这种语法时，JVM仅仅在堆内存中申请了第一维（外层）数组的空间，长度为3。这个外层数组的元素类型是int[]，
属于引用数据类型**。根据Java的内存分配机制，引用类型的默认初始化值是null。此时，第二维（内层）的具体数组对象并没有被创建或分配内存。
因此，arr指向的数组对象中存储了三个null引用。如果在这个状态下直接访问arr[0][0]，程序会抛出NullPointerException。
这种初始化方式通常用于创建交错数组（JaggedArray），即允许每一行的列数不同，后续需要手动为arr[0]、arr[1]等单独赋值一个新的int[]对象。
* */