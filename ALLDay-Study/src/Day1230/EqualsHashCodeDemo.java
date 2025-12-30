package Day1230;

import java.util.HashMap;
import java.util.Objects;

/*
* 验证 == vs equals 的区别，以及不重写hashCode导致的HashMap故障
* 1. == 的本质
* 基本类型（int, char 等）：比较的是值。3 == 3 是 true。
* 引用类型（String, Object 等）：比较的是内存地址。只有当两个引用指向堆内存中同一个对象实例时，才返回 true。
* 2. equals 的本质
* Object 类的默认实现：直接调用的就是 ==。也就是说，如果你不重写 equals，它和 == 没有任何区别，也是比地址。
* 重写后的目的：为了实现逻辑相等。比如两个 String 对象，内存地址不同，但字符序列是一样的，我们就认为它们相等。
*
 3. hashCode 与 equals 的“协定”
在 Java 中，这是一个硬性规定（Contract）：
若相等，必同源：如果 a.equals(b) 为 true，那么 a.hashCode() 必须等于 b.hashCode()。
若同源，未必相等：如果 hash 值一样，对象未必相等（哈希冲突）。
为什么要这样？ 想象 HashMap 是一个巨大的图书馆：
hashCode 是“书架号”。
equals 是书名对比。
找书时，先算 hashCode 快速定位到第几号书架，然后再在这个书架上用 equals 一本本比对书名。
如果你重写了 equals（书名一样）但没重写 hashCode（书架号随机），当你拿着同样的书名去找书时，算出来的书架号变了，你去错误的书架找，肯定找不到这本书
* */
class BadStudent {
    private String name;
    private int id;

    public BadStudent(String  name, int id) {
        this.name = name;
        this.id = id;
    }
    //只重写equals，逻辑是：id和name一样就认为是同一个人

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadStudent that = (BadStudent) o;
        return id == that.id && Objects.equals(name, that.name);
    }
    //故意不重写hashCode，使用Object默认的（基于内存地址）
}
class GoodStudent{
    private String name;
    private int id;
    public GoodStudent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodStudent that = (GoodStudent) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
public class EqualsHashCodeDemo {
    public static void main(String[] args) {
        System.out.println("======== equals与==的区别 ========");
        String s1 = new String("hello");
        String s2 = new String("hello");

        System.out.println("s1 == s2" + (s1 == s2));//false 内存地址不同
        System.out.println("s1.equals(s2)" + s1.equals(s2));//true 内容相同

        System.out.println("========= 不重写hashCode的灾难 ========");
        HashMap<BadStudent,Integer> badMap = new HashMap<>();
        BadStudent bad1 = new BadStudent("Tom",1);
        badMap.put(bad1,100);//存入Tom 100分
        //这是用来查询的key，逻辑上和bad1是同一个学生
        BadStudent badQuery = new BadStudent("Tom",1);
        System.out.println("BadStudent equals结果：" + bad1.equals(badQuery));//true
        //预期能取到100，实际上取不到 引物hash 不同去了错误的桶
        System.out.println("BadStudent Map的取值结果：" + badMap.get(badQuery));

        System.out.println("========= 重写hashCode的效果 ========");
        HashMap<GoodStudent,Integer> goodMap = new HashMap<>();
        GoodStudent good1 = new GoodStudent("Jerry",2);
        goodMap.put(good1,90);

        GoodStudent goodQuery = new GoodStudent("Jerry",2);
        System.out.println("GoodStudent equals结果：" + good1.equals(goodQuery));//true
        //成功取到90，因为hashCode相同且equals相同
        System.out.println("BadStudent Map的取值结果：" + goodMap.get(goodQuery));

    }
}
/*
关于 equals 和 ==，简单来说，== 比较的是内存地址（即是不是同一个物理对象），而 equals 默认也是比地址，但我们通常重写它来比较对象的内容逻辑是否相等。
至于为什么重写 equals 必须重写 hashCode，这主要是为了兼容 Java 的集合框架（如 HashMap 和 HashSet）。
这些集合是基于哈希表实现的，存取数据遵循 先比较 HashCode 定位桶，再用 equals 确定元素 的规则。
如果只重写 equals 而不重写 hashCode，会导致逻辑相等的对象生成不同的 hash 值。这样在 HashMap 中存入一个对象，用另一个逻辑相等的对象去取时，
会因为定位到错误的哈希桶而返回 null。这就打破了 Java 集合类的契约，导致程序出现严重 Bug。”
* */