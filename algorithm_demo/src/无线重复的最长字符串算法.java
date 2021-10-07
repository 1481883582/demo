public class 无线重复的最长字符串算法 {
    public static void main(String[] args) {
        int i = lengthOfLongestSubstring("abcabcbb");
        System.out.println(i);
    }

    public static int lengthOfLongestSubstring(String s) {
        //利用ASCII 128字节
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int start = 0; // 窗口开始位置
        int res = 0;

        for(int i = 0; i < n; i++) {
            //获取 char的每一个字符 数值
            int index = s.charAt(i);

            //获取当前窗口起始位置
            start = Math.max(start, last[index] + 1);

            //与历史最长对比   当前位置  减去之前这个字节的位置  + 1（当前自己）
            res = Math.max(res, i - start + 1);

            //把当前 位置赋值于字节map中
            last[index] = i;
        }

        return res;

    }
}
