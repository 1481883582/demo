public class Z字形变换 {

    public static void main(String[] args) {
        Z字形变换 bean = new Z字形变换();
        String convert = bean.convert("PAYPALISHIRING", 3);
        System.out.println(convert);
    }

    public String convert(String s, int numRows) {
        //如果是1行 或者字符小于等于2 没有任何Z字形意义 直接返回
        if (numRows == 1) return s;
        int length = s.length();
        if (length <= 2) return s;

        //声明字符串
        StringBuilder str = new StringBuilder();

        //算出 每次多少个字符 行数+行数-2  去头去尾没有字符(Z字形情况下)
        int bc = numRows + (numRows - 2);

        //有几行  循环几次
        for (int i = 0; i < numRows; i++) {

            //获取 字符的下标  每一行完成后  等于i下一行
            int j = i;

            //每一行第几个字符  默认是第一个  因为Z字形第一个就有
            int c = 1;

            //字节数小于总字节数继续
            while (j < length) {
                //加上每行每次Z的数据  这里的Z  更像N种I的数据
                str.append(s.charAt(j));

                //因为Z  这里是N 第一行和最后一行内数据  只处理中间
                if (i != 0 && i != numRows - 1) {

                    //算出中间位置的数据
                    int z = bc * c - i;

                    //在字符串范围后  增加
                    if (z > 0 && z < length) {
                        str.append(s.charAt(z));
                    }
                }

                //叠加每次Z的下标
                j = j + bc;

                //每行第几个字节递增
                ++c;
            }
        }

        return str.toString();
    }
}
