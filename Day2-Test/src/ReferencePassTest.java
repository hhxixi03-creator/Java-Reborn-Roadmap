public class ReferencePassTest {
    static class Person{
        String name;
        Person (String name) {this.name = name;}

        @Override
        public String toString() {
            return name;
        }

        public static void main(String[] args) {
            Person p1 = new Person("zhangsan");
            Person p2 = new Person("Lisi");
            System.out.println("----交换前----");
            System.out.println("p1:"+p1);
            System.out.println("p2:"+p2);
            swap(p1,p2);
            System.out.println("---交换后（主函数视角）---");
            System.out.println("p1:"+p1);
            System.out.println("p2:"+p2);
            System.out.println("结论：p1和p2没有变 说明没有引用传递");
        }
        private static void swap(Person x, Person y){
            Person temp = x;
            x = y;
            y = temp;
            System.out.println("方法内部完成交换 x = " + x + " y = " + y);
        }
        private static void  changeName(Person x) {
            x.name = "王五";
        }
    }
}
