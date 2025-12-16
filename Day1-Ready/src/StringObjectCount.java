public class StringObjectCount {
    public static void main(String[] args) {
        String s1 = "abc";//在常量池创建
        String s2 = new String("abc");//这个时候常量池已经有了
        System.out.println("S1的常量池地址"+System.identityHashCode(s1));
        System.out.println("S2的堆内存地址为"+System.identityHashCode(s2));
        //验证他们是否为同一个对象
        System.out.println(s1==s2);
        // s2 内部的 value 数组其实是复用了 s1 的 value（在较新JDK版本中底层优化略有不同，但逻辑引用一致）
        // 核心结论：s2 是堆里的一块新肉，s1 是池里的一块旧肉。
        /*
        关于 String s = new String("abc") 创建了几个对象，这取决于 字符串常量池 中是否已经存在 "abc" 这个字面量。
        严格来说，这行代码涉及两个层面的内存分配。首先，JVM 会检查字符串常量池。如果池中没有 "abc"，它会先在池中创建一个对象，这是第一个对象
        ；如果池中已经有了，这一步就跳过。其次，new 关键字会在堆内存中显式地申请一块新的空间来存储一个 String 对象，这是第二个对象。
        所以，如果常量池中之前没有 "abc"，这行代码一共创建了 2 个对象；如果常量池中早已有 "abc"，则只创建了 1 个对象（即堆中的那个）。这也正是 String
        所谓的‘不可变性’和‘常量池优化’机制的体现。”
        * */
    }
}
