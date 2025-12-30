package Day1230;

public class LifecycleDemo {
    public static void main(String[] args) {
        System.out.println("========第一次new Child()========");
        new child();
        System.out.println("========第二次new Child()========");
        new child();
    }
}
class parent {
    //1.父类静态变量 （为了打印日志，用函数赋值）
    static int parentStaticVar = printLog("1. 父类-静态变量");
    //2.父类静态代码块
    static {
        System.out.println("2. 父类-静态代码块");
    }
    //3.父类实例变量
    int parentInstanceVar = printLog("5. 父类-实例变量");
    //4.父类普通代码块
    {
        System.out.println("6. 父类-普通代码块");
    }
    //5.父类构造器
    public parent() {
        System.out.println("7. 父类-构造器");
    }
    //辅助打印方法
    protected static int printLog(String str) {
        System.out.println(str);
        return 0;
    }
}
class child extends parent{
    //1.子类静态变量
    static int childStaticVar = printLog("3. 子类-静态变量");
    //2.子类静态代码块
    static {
        System.out.println("4. 子类-静态代码块");
    }
    //3.子类实例变量
    int childInstanceVar = printLog("8. 子类-实例变量");
    //4.子类普通代码块
    {
        System.out.println("9. 子类-普通代码块");
    }
    //5.子类构造器
    public child() {
        System.out.println("10. 子类-构造器");
    }
}
/*
关于 Java 的执行顺序，核心原则是类级别优先于对象级别，父类优先于子类。
具体流程是这样的：
首先是类加载阶段：JVM 第一次加载子类时，会先加载父类。此时会由父到子依次执行静态变量初始化和静态代码块。这一步在整个 JVM 生命周期中只会执行一次。
其次是对象创建阶段：当 new 对象时，执行顺序依然是先父后子。
首先执行父类的普通代码块和实例成员赋值（按顺序），然后执行父类构造器。
父类构造完成后，再执行子类的普通代码块和实例成员赋值，最后执行子类构造器。
简单来说就是：父类静态 -> 子类静态 -> 父类（代码块+构造） -> 子类（代码块+构造）。其中静态部分只执行一次。”

为什么执行一次？
静态代码块之所以只执行一次，不是因为“static 特殊”，
而是因为它被编译进了 <clinit>，而 JVM 明确规定：
一个类在一个类加载器下，整个生命周期中只允许完成一次类初始化。
*/