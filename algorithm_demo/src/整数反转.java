public class 整数反转 {
    public static void main(String[] args) {
        整数反转 bean = new 整数反转();
        int convert = bean.reverse(-123);
        System.out.println(convert);
    }

    public int reverse(int x) {
        //声明 Long类型  防止整数翻转溢出  无法接受
        long l = 0;

        //传入参数不为0时处理
        while(x != 0 ){

            // (l)0 * 10 + (余数)-3
            // (l)-3 * 10 + (余数)-2
            // (l)-32 * 10 + (余数)-1
            l = l * 10 + x % 10;

            // -123
            // -12
            // -1
            x = x / 10;
        }

        //l可能int溢出
        //强转int以后会变成int最大值
        //不于原来int相等
        //如果l == int l  说明转成int以后没有溢出  正常返回
        //不等于  说明溢出 返回 0
        return (int)l == l? (int)l:0;
    }
}
