package Day1231;
/*
深度演示抽象类与接口在 "IS-A" (血缘) 和 "CAN-DO" (能力) 上的本质区别

1. 抽象类：为了“复用”与“模板”
设计初衷：代码复用（Code Reuse）。
本质：它是对一类事物的抽象。它把子类共有的**状态（字段）和行为（方法）**提取出来，避免重复代码。
核心：它包含状态（成员变量）。这是接口做不到的（接口只有常量）。

2. 接口：为了“解耦”与“契约”
设计初衷：定义规范（Contract）。
本质：它是一种行为契约。它告诉外部“我有这个能力”，但不限制内部怎么实现。
核心：它打通了不同层级、不同血缘的对象。比如 Bird（鸟）和 Airplane（飞机）完全没关系，但它们都可以实现 Flyable（会飞）接口。
*/

//1.定义接口(能力/契约) 接口代表 "CAN-DO" 能连接USB
interface USB{
    // 接口这里只能放常量
    String STANDARD_VERSION = "3.0";
    //抽象方法：仅仅定义 不关心谁来实现
    void connect();
    // java8 默认方法(拓展能力，不破坏现有实现)
    default void charge() {
        System.out.println("USB 通用充电中...");
    }
}
//2.定义抽象类 (血缘/模板) 代表"IS-A"：这是一种电子设备
abstract class ElectronicDevice {
    //【核心区别】 抽象类可以有“状态” 即成员变量
    protected String brand;
    //【核心区别】 抽象类可以有构造器 用于初始化父类状态
    public ElectronicDevice(String brand) {
        this.brand = brand;
    }
    //它可以有具体的方法实现（代码复用）
    public void powerOn() {
        System.out.println(brand + "设备开机了，系统启动中...");
    }
    public abstract void shutDown();
}
//3.具体实现类

//类1：鼠标
class Mouse extends ElectronicDevice implements USB {

    public Mouse(String brand) {
        super(brand);
    }

    // 实现抽象类的抽象方法
    @Override
    public void shutDown() {
        System.out.println(brand + "鼠标断电，指示灯熄灭。");
    }
    // 实现接口的抽象方法
    @Override
    public void connect() {
        System.out.println(brand + "鼠标已插入USB接口，驱动加载中...");
    }
}
// 类2：键盘
class Keyboard extends ElectronicDevice implements USB{

    public Keyboard(String brand) {
        super(brand);
    }

    @Override
    public void shutDown() {
        System.out.println(brand + "键盘休眠。");
    }

    @Override
    public void connect() {
        System.out.println(brand + "键盘已连接，背光灯亮起。");
    }
}

//奇怪的组合 电子狗
abstract class Anmial {
    abstract void bark();
}
//RoboDog IS-A Anmial(虽然有点奇怪，假设它是生物)，但也能插USB(CAN-DO)
class RoboDog extends Anmial implements USB {

    @Override
    void bark() {
        System.out.println("机械狗汪汪叫");
    }

    @Override
    public void connect() {
        System.out.println("电子狗连接USB 进行数据同步...");
    }
}
public class InterfaceVSAbstractDemo {
    public static void main(String[] args) {
        System.out.println("========1.抽象类体系(IS-A)========");
//        ElectronicDevice d = new ElectronicDevice();报错 抽象类不能实例化
        Mouse m = new Mouse("罗技");
        m.powerOn();//复用父类方法
        m.connect();//重写接口方法
        m.shutDown();//子类具体逻辑
        System.out.println("========2.接口多态(CAN-DO)========");
        //这里的list 不管你是不是电子设备，只管你是不是USB设备
        USB[] usbDevices = {new Mouse("雷蛇"),new Keyboard("樱桃"),new RoboDog()};
        for (USB device : usbDevices) {
            //面向接口编程，我不知道你是谁，但我知道你能connect
            System.out.println(">>发现新设备：");
            device.connect();
            device.charge();
        }
    }
}
/*
从设计层面看：
抽象类 (IS-A) 是对类本质的抽象，它是自底向上的重构（把子类共有的代码抽出来）。它用于定义严密的继承体系，子类和父类是强耦合的。
接口 (CAN-DO) 是对行为的抽象，它是自顶向下的设计（先定义标准，再让别人实现）。它用于解耦，允许不相关的类拥有相同的能力。

从语法细节看（这是决定性因素）：
最关键的区别在于状态。抽象类可以有普通成员变量（保存状态），而接口只能有静态常量（无状态）。
这也导致了抽象类有构造器，而接口没有。

最佳实践：
在现代 Java 开发中，我们遵循‘多用组合/接口，少用继承’的原则。除非两个类之间有非常明确的父子层级且需要复用字段状态，否则优先使用接口，以保持系统的灵活性和扩展性。”
* */