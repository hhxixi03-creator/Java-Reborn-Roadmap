public class OperatorDifferenceExample {
    private static String getObject(){
        System.out.println("getObject() 被调用 返回null");
        return null;
    }
    private static Boolean checkValue() {
        System.out.println("checkValue() 被调用 返回 true");
        return true;
    }

    public static void main(String[] args) {
        String data = getObject();
        System.out.println("====使用短路逻辑与 '&&'====");
        if (data !=null && checkValue()){
            System.out.println("结果为true (不会被执行到)");
        }else {
            System.out.println("最终结果为false,checkValue()不会被执行到");
        }
        System.out.println("====非短路逻辑与 '&'====");
        if (data !=null & checkValue()){
            System.out.println("结果为true (不会被执行到)");
        } else {
            System.out.println("最终结果为false,checkValue()被调用到了！");
        }
        //  空指针异常错误示例
        try{
        if(data !=null & data.length() > 0) {
            System.out.println("空指针异常，前面就算为false .length()方法依旧被调用 null异常");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        //  正确的空指针防卫写法
        if (data != null && data.length()>0) {
            System.out.println("空指针防卫的正常应用");
            System.out.println("此代码块通过&&短路的特性，安全的检查了data对象");
        } else {
            System.out.println("空指针防卫的正确应用");
            System.out.println("由于data是null,安全的跳过了.length()的调用");
        }
        /*
        &和&&这两个运算符是Java中逻辑运算的基础，但它们的区别至关重要。核心差异在于短路评估机制。&&是短路逻辑与（Short-CircuitingLogicalAND）。
        当评估Expression_A&&Expression_B时，如果左侧的Expression_A已经评估为false，那么整个表达式的结果必然是false，&&会立即停止评估，
        不会去执行右侧的Expression_B，这避免了不必要的计算，优化了性能。相比之下，&是非短路逻辑与（Non-Short-CircuitingLogicalAND），
        它在评估时总是会执行并评估左右两侧的表达式，无论左侧的结果是true还是false。此外，&还有一层身份，即作为位运算符，对整数类型进行按位与操作，
        这是&&不具备的功能。在实际开发中，&&的短路特性在空指针防卫中发挥着不可替代的作用。例如，在执行if(obj!=null&&obj.method())这样的条件判断时，
        我们正是依赖&&在obj为null时短路，从而安全地跳过对obj.method()的调用，有效规避了NullPointerException的风险。
        因此，在进行布尔逻辑判断且不涉及位运算时，我们几乎总是推荐使用&&以确保代码的健壮性和执行效率。
        */
    }
}
