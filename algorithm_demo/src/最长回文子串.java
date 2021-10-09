public class 最长回文子串 {

    public static void main(String[] args) {
        最长回文子串 bean = new 最长回文子串();
        String str =  bean.longestPalindrome("bbv");
        System.out.println(str);
    }

    //声明 截取起点与终点
    private int start, end;

    public String longestPalindrome(String s) {
        expend(s, s.length() / 2, 0);
        return s.substring(start, end);
    }

    /**
     * 递归
     * @param s  传入字符串
     * @param mid  中位数
     * @param direction 方向
     */
    private void expend(String s, int mid, int direction) {
        //声明 中位左边指针   中位右边指针
        int left = mid - 1, right = mid + 1;
        //左边指针大于等于0  中位字符与左位字符相同时  左位指针左移
        while (left >= 0 && s.charAt(mid) == s.charAt(left)) left--;
        while (right < s.length() && s.charAt(mid) == s.charAt(right)) right++;
        int leftMid = left, rightMid = right;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        if (right - left - 1 > end - start) {
            start = left + 1;
            end = right;
        }
        if (direction <= 0 && leftMid * 2 + 1 > end - start) expend(s, leftMid, -1);
        if (direction >= 0 && (s.length() - rightMid) * 2 + 1 > end - start) expend(s,  rightMid, 1);
    }
}
