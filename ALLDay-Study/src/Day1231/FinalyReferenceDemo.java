package Day1231;
/*
final 修饰引用类型（如 final User u = new User()）时，是地址不可变还是内容不可变？
final 锁住的是‘狗链子’（地址），锁不住‘狗’（内容）。
地址不可变：你手里有一条狗链子（引用 u），一旦拴住了一只狗（new User），你就再也不能把这条链子拴到别的狗身上了（不能 u = new User()）。
内容可变：但是，这条链子拴住的那只狗本身，它是胖了、瘦了、还是改名叫“旺财”了（u.setName("...")），final 完全管不着。

1. 变量分两半：栈（Stack）与 堆（Heap）
当你写 final User u = new User(); 时，内存里发生了两件事：
在栈内存（Stack）：创建了一个变量盒子，名字叫 u。这个盒子里装的不是对象本身，而是一个数字（比如 0x1234），这个数字是对象在堆里的地址。
在堆内存（Heap）：真正创建了 User 对象的数据（比如 name, age）。

2. final 的管辖范围
final 修饰的是 栈里的变量 u。
它给 u 贴了个封条：“这个盒子里的数字（地址）一旦写进去，就不准改！”
所以，你不能把 0x1234 改成 0x5678（即指向另一个对象）。

3. 鞭长莫及
至于 0x1234 这个地址所指向的堆内存区域（对象内部的数据），final 根本管不到。除非该对象内部的字段（如 name）也被定义为 final，否则你可以随意修改。
**/

import java.util.Arrays;

class Dog{
    String name;
    int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class FinalyReferenceDemo {
    public static void main(String[] args) {
        System.out.println("========1.初始化final的引用========");
        //p指向堆内存中的一个对象
        final Dog  dog1 = new Dog("旺财",2);
        System.out.println("初始状态：" + dog1);
        System.out.println("引用地址（hash）:" + System.identityHashCode(dog1));
        System.out.println("========2.验证内容是可变的========");
        dog1.name = "梨子";
        dog1.age = 3;
        System.out.println("修改后的内容：" + dog1);
        System.out.println("修改后的引用地址（Hash）:" + System.identityHashCode(dog1));
        System.out.println("结论：地址没变 但内部数据变了");
        System.out.println("========3.验证地址是不可变的========");
        //试图让dog1指向一个新对象
        //dog1 = new Dog("JIM",12);
        System.out.println("如果让final修饰的对象 去指向一个新对象 编译器报错：无法将值赋给 final 变量 'dog1'");
        System.out.println("========4.延申：final数组========");
        final int[] nums = {1,2,3};
        System.out.println("数组初始：" + Arrays.toString(nums));
        nums[0] = 99;//允许！修改的是堆里的数组
        System.out.println("数组修改：" + Arrays.toString(nums));
//        nums = new int[]{1,2,3};禁止 这里修改的是引用地址
        int[] num = new int[]{1,2,3,4,4};
        int a[] = new int[3];
        a[0] = 1;a[1] = 2;a[2] = 3;
        System.out.println(Arrays.toString(a));
        int [] b = {5,6,7};
        System.out.println(Arrays.toString(b));
    }
}
/*
当 final 修饰引用类型时，它的规则非常明确：地址不可变，内容可变。
从内存模型上看，final 锁定的是栈内存（Stack） 中的引用变量，保证它始终指向同一个堆内存（Heap） 地址。一旦赋值，就不能再指向其他对象。
但是，该地址对应的堆内存中的对象数据（即对象的成员变量），完全不受 final 的限制。除非这些成员变量本身也被 final 修饰，或者该类是像 String 那样
的不可变类，否则我们依然可以通过方法或直接访问来修改对象的内部状态。
面试中常考的 final List 或 final 数组，其实都能正常添加元素或修改元素值，就是这个道理。
**/