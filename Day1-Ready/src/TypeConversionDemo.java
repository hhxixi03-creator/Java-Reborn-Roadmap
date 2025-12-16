public class TypeConversionDemo {
    public static void main(String[] args) {
        byte b = 127;

        // b = b+1;报错 因为byte short char 在参与算术运算时会自动转为int 所以b+1结果是int byte类型b不成立
        b = (byte) (b+1);
        System.out.println(b);//正确写法需要强转
        byte c = 127;
        c += 1;//成立 因为复合逻辑表达式在逻辑上等价于 E1 = (Type)(E1+1);
        System.out.println(c);
        /*
         对于 b = b + 1，Java 编译器会报错。这是因为 Java 中的 byte、short、char 在参与算术运算时，会先自动提升为 int 类型，
         所以 b + 1 的结果实际上是 int 类型。将高精度的 int 赋值给低精度的 byte 时，编译器无法确定是否安全，必须要求
         程序员显式地进行强制类型转换，如 b = (byte)(b + 1)。
         而对于 b += 1，它能编译通过。这是因为 Java 语言规范规定，复合赋值运算符（如 +=、-=）内部包含了隐式的强制类型转换。
         b += 1 等价于 b = (byte)(b + 1)。
        不过需要特别注意，虽然 += 方便，但它隐藏了类型转换的细节，在处理边界值时（比如 127 + 1）会导致数据溢出（变成 -128），
        这在实际业务逻辑中可能会埋下 Bug，需要格外小心。”
        */

    }
}
