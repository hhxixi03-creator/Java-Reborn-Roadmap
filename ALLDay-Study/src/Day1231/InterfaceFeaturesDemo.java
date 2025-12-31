package Day1231;
/**
 * 作用: 验证 JDK 8 接口的 default 方法（扩展性/冲突）与 static 方法
 结论：“default 是为了‘不改代码也能升级功能’（后悔药），static 是为了‘扔掉工具类，让接口自力更生’（收纳盒）。”
 1. 为什么要有 default 方法？（核心痛点：API 演进）
 在 Java 8 之前，接口有着严格的“侵入性”。
 场景：Java 8 想在集合库（List, Set）中增加 stream() 方法来支持函数式编程。
 灾难：List 接口是 JDK 1.2 就有的。全世界有亿万行代码实现了 List 接口（比如 Hibernate 的 PersistentList，或者你项目里的 MyCustomList）
 。如果 Java 8 直接在 List 接口里加个 abstract void stream()，那么全世界升级到 Java 8 的瞬间，所有自定义集合类都会编译失败。
 救世主：default 方法允许在接口中提供方法的默认实现。旧的类不需要改动，自动继承这个默认实现；新的类如果有特殊需求，也可以重写它。

 2. 为什么要有 static 方法？（核心痛点：代码组织）
 场景：通常接口只定义规范，工具方法（如排序、判空）不得不放到另一个类里（如 Collections）。这违背了“高内聚”原则。
 改进：允许接口定义静态方法，就是把工具方法放回它逻辑上所属的地方。
 */


//1，定义一个支付接口
interface Payment {
    // 传统抽象方法
    void pay(double amount);

    //【新特性1】 default方法；提供默认的退款逻辑
    //作用：即使旧的实现类不写这个新方法，也能拥有退款功能
    default void refund(double amount) {
        System.out.println("[Payment接口默认逻辑] 退款：" + amount + "元(原路返回)");
    }

    //【新特性2】 static方法，接口自带的工具方法
    //作用：检验金额是否合法
    static boolean isAmountValid(double amount) {
        return amount > 0;
    }
}
//2. 另一个接口，用于演示多继承冲突
interface RiskControl {
    //故意定义一个和Payment 同名的default方法
    default void refund(double amount) {
        System.out.println("[RiskCOntrol接口默认逻辑] 风险退款：" + amount + "元(需人工审核)");
    }
}
//3.实现类A:老老实实只实现核心功能，使用默认退款
class CashPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("现金支付：" + amount);
    }
    //这里没有重写refund，直接继承Payment 的 default实现
}
//4.实现类B:我有自己的想法，重写默认方法
class CreditCardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("信用卡支付：" + amount);
    }

    @Override
    public void refund(double amount) {
        System.out.println(">>信用卡退款：" + amount + "元(退回到信用卡额度)");
    }
}
//实现类C：多继承导致冲突，必须手动解决
class OnlinePayment implements Payment,RiskControl {

    @Override
    public void pay(double amount) {

    }
//编译报错 Payment和RiskControl 都有 refund 编译器不知道选谁 必须重写 并且显式指定调用哪个
    @Override
    public void refund(double amount) {
        System.out.println(">>在线支付处理退款冲突");
        RiskControl.super.refund(amount);
        //也可以加上Payment的
    }
}
public class InterfaceFeaturesDemo {
    public static void main(String[] args) {
        System.out.println("========1.验证defult方法的向后兼容性========");
        Payment cash = new CashPayment();
        cash.pay(100);
        cash.refund(100);//调用了接口的默认实现
        System.out.println("========2.验证defult方法的重写========");
        Payment credit = new CreditCardPayment();
        credit.pay(1000);
        credit.refund(300);//调用的子类的重写逻辑
        System.out.println("========3.验证static方法========");
        double money = -12;
        //注意：必须用接口名调用，不能用对象名调用(如 cash.isAmountValid是非法的)
        if (Payment.isAmountValid(money)) {
            System.out.println("金额合法");
        } else {
            System.out.println("金额非法：" + money);
        }
        System.out.println("========4.验证多借口冲突解决========");
        Payment online = new OnlinePayment();
        online.refund(300);
    }
}
/*
“JDK 8 引入 default 和 static 方法，核心目的是为了在保持向后兼容的前提下，扩展 API 的能力。
关于 default 方法：它的出现主要是为了配合 Stream API 的落地。它允许我们在不破坏现有庞大实现类体系的情况下，向接口中添加新方法。
它遵循‘类优先’原则来解决调用冲突。这改变了接口‘一成不变’的刻板印象，让接口具备了进化的能力。

关于 static 方法：它增强了接口的内聚性，允许我们将与该接口强相关的工具方法（如校验、工厂方法）直接定义在接口内部，从而减少了类似 Collections
这种外部工具类的使用。

总的来说，这两个特性让 Java 的接口更像是一个‘功能丰富的契约’，而不仅仅是‘空洞的规范’。”
* */