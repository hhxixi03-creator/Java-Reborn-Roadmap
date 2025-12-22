public class StringSwitchImplementation {
    public static void checkStringCase (String input) {
        if (input == null) {
            System.out.println("输入的字符串为null,跳出switch逻辑。");
            return;
        }
        System.out.println("--- 实际的 String switch  代码 ---");
        switch (input) {
            case "Hello":
                System.out.println("匹配到：Hello");
                break;
            case "World":
                System.out.println("匹配到：World");
                break;
            case "FB":
                System.out.println("匹配到：FB(HashCode可能有碰撞)");
                break;
            default:
                System.out.println("未匹配到任何 case");
        }
        System.out.println("-----------------------");

        int hash = input.hashCode();

        switch (hash) {
            case 69609650 :
                //equals做精准校验
                if ("Hello".equals(input)) {
                    System.out.println("底层逻辑匹配到：Hello");
                }
                break;
            case 83766130 :
                if ("World".equals(input)) {
                    System.out.println("底层逻辑匹配到：World");
                }
                break;
            case 2236 :
                if ("FB".equals(input)) {
                    System.out.println("底层逻辑匹配到：FB");
                }
                break;
            default:
                System.out.println("底层逻辑未匹配到任何 case");
        }
    }

    public static void main(String[] args) {
        checkStringCase("Hello");
        checkStringCase("FB");
        checkStringCase(null);
    }

}
/*
这是Java7引入的语法糖，其底层实现并非直接操作字符串，而是依赖于**hashCode()和equals()方法的组合机制**。在编译阶段，
Java编译器会将基于String的switch语句转换为一个基于int类型的switch语句，这是性能优化的核心。具体流程分为两步：
首先，编译器利用String变量的hashCode()方法来获取一个整数值，然后使用这个int值作为第一个switch语句的条件进行快速查找和分支跳转，
这通常能达到接近O(1)的查找效率。然而，由于哈希碰撞的可能性，即不同的字符串可能产生相同的hashCode，
仅仅依赖哈希值是不够的。因此，在第一步成功定位到匹配的case分支之后，编译器会自动插入一个对原始输入String调用equals()方法的检查，
以执行最终、准确的字符串内容对比。只有当equals()返回true时，该分支代码才会被执行。
最后，需要强调一个潜在的陷阱：由于转换的底层是基于方法调用的，如果作为switch条件的String变量为null，且没有在switch外部进行显式检查，
程序会尝试调用null.hashCode()，从而导致抛出NullPointerException。因此，在实际开发中，我们必须确保switch表达式不为null，
或者在switch之前进行严谨的空值判断。
**/