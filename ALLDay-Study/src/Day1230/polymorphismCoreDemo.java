package Day1230;
/*
* 问题：父类引用指向子类对象（多态）时，调用方法看左边还是看右边？访问属性呢？
* 结论：方法看右边（看对象），属性看左边（看类型）。
* */
/**
 * 文件名: PolymorphismCoreDemo.java
 * 作用: 验证多态下 "方法看右边，属性看左边" 的核心规则
 */

class Parent {
    // 1. 父类属性
    int num = 10;

    // 2. 父类静态方法
    public static void staticMethod() {
        System.out.println("Parent - 静态方法 (Static Binding)");
    }

    // 3. 父类普通方法
    public void instanceMethod() {
        System.out.println("Parent - 普通方法 (Dynamic Binding)");
    }
}

class Child extends Parent {
    // 1. 子类属性 (名称与父类相同，发生 "隐藏" 而非重写)
    int num = 20;

    // 2. 子类静态方法 (隐藏父类静态方法)
    public static void staticMethod() {
        System.out.println("Child - 静态方法");
    }

    // 3. 子类普通方法 (重写父类方法)
    @Override
    public void instanceMethod() {
        System.out.println("Child - 普通方法");
    }
}

public class polymorphismCoreDemo {
    public static void main(String[] args) {
        // ==========================================
        // 核心场景：父类引用 指向 子类对象
        // ==========================================
        Parent p = new Child();

        System.out.println("========== 1. 访问属性 (看左边) ==========");
        // 预期输出: 10
        // 解释: 编译时确定为 Parent.num，不发生多态
        System.out.println("p.num = " + p.num);

        System.out.println("\n========== 2. 调用静态方法 (看左边) ==========");
        // 预期输出: Parent - 静态方法
        // 解释: 静态方法属于类，与引用类型绑定，不发生多态
        p.staticMethod();

        System.out.println("\n========== 3. 调用普通方法 (看右边 - 多态) ==========");
        // 预期输出: Child - 普通方法
        // 解释: 运行时检测对象的实际类型是 Child，执行子类逻辑
        p.instanceMethod();

        System.out.println("\n========== 4. 对照组：子类引用指向子类对象 ==========");
        Child c = new Child();
        System.out.println("c.num = " + c.num); // 20 (看左边是 Child)
        c.staticMethod();                     // Child静态 (看左边是 Child)
        c.instanceMethod();                   // Child普通 (看右边是 Child)
    }
}
/*
“在 Java 多态机制中，核心规则可以总结为：只有普通实例方法是看右边的（动态绑定），其他所有成员（属性、静态方法、私有方法）都是看左边的（静态绑定）。
具体原理是
普通方法：采用动态绑定（Late Binding）。JVM 在运行时会根据对象实际的内存类型（Right）去查找方法表，从而执行子类的重写逻辑，这是实现多态的基础。
成员变量：采用静态绑定（Early Binding）。变量的访问在编译期就由引用类型（Left）决定了偏移量。Java 中，子类同名字段只是隐藏了父类字段，而非重写，
它们在内存中是共存的。
静态方法：属于类层面，不依赖对象实例，所以完全由声明引用的类型决定，也是看左边。
所以，面试中如果问到 p.num 或者 p.staticMethod()，我一定会回答父类的内容；只有 p.instanceMethod() 才是子类的内容。”
* */