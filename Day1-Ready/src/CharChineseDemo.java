public class CharChineseDemo {
    public static void main(String[] args) {
        //Case1 常用汉字
        char c1 = '珩';
        System.out.println("1.常用汉字成功存储:"+c1);
        System.out.println("占用位数："+Character.SIZE+"bits(2bytes)");
        //Case2 生僻字
       // char c2 = '\uD842\uDFB7';//𠮷  直接报错未结束的字符文字 (编译器认为这装不下)
        String rareWord = "𠮷";
        System.out.println("2.成功储存偏僻字："+rareWord);
        System.out.println("字符串长度 (length): " + rareWord.length());
        // 输出 2这意味着底层用了 2 个 char 数组元素来存这 1 个字！
        System.out.println("实际字符数 (codePointCount): " + rareWord.codePointCount(0, rareWord.length()));
        // 输出 1。这才是真正的逻辑字符数量。

        //可以把它拆开看底层的两个 char
        char high = rareWord.charAt(0);//高代理项
        char low = rareWord.charAt(1);//低代理项
        System.out.println(high+" "+low);//不能这样输出显示？
        System.out.println("拆解为代理对: " + Integer.toHexString(high) + " 和 " + Integer.toHexString(low));
        //Integer.toHexString() 将输入的数、准确地翻译成它对应的十六进制形式的字符串
        /*
        关于char是否能存储汉字，答案是取决于这个汉字在Unicode中的编码位置。Java的char类型占用2个字节（16位），采用UTF-16编码格式。
        绝大部分我们日常使用的汉字（包括简体和繁体），都位于Unicode的基本多文种平面(BMP)内，其编码值在U+0000到U+FFFF之间，
        因此一个char完全可以存储一个常用汉字。但是，对于超出BMP范围的字符，比如一些古汉语生僻字（如‘𠮷’）或者现代的Emoji表情，
        它们的Unicode码点超过了0xFFFF，占用了4个字节。在Java中，这些字符需要通过‘代理对’(SurrogatePair)机制，即使用两个连续的char来存储。
        如果强行将这类字符放入一个char变量中，会导致编译错误或数据丢失。所以严谨地说，char可以存储UnicodeBMP平面内的任意汉字，但无法单独存储增补平面的汉字。”
        * */
    }
}
