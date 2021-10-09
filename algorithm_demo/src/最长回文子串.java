public class 最长回文子串 {

    public static void main(String[] args) {
        最长回文子串 bean = new 最长回文子串();
        String str = bean.longestPalindrome("abbbabb");
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
     *
     * @param s         传入字符串
     * @param mid       中位数
     * @param direction 方向
     */
    private void expend(String s, int mid, int direction) {
        //声明 中位左边指针   中位右边指针
        int left = mid - 1, right = mid + 1;

        //左边指针大于等于0  中位字符与左位字符相同时  左位指针左移
        while (left >= 0 && s.charAt(mid) == s.charAt(left)) left--;

        //有变指针小于等于0  中位字符与右位字符相同   右位指针右移
        while (right < s.length() && s.charAt(mid) == s.charAt(right)) right++;

        //过滤掉与中位重复字节后 最左与最右位赋值
        int leftMid = left, rightMid = right;

        //如果 左指针侧字符与右指针侧字符相同
        // 左指针大于等于0的话 递减
        // 右指针小于字符串长度的话  递增
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        //如果当前长度  大于历史长度
        //当前长度赋值
        if (right - left - 1 > end - start) {
            start = left + 1;
            end = right;
        }

        //中位指针 小于等于0  左递归
        //剩余的(左指针到0指针)*2+1 > 目前的长度
        // "bbabbbccc"  假如当前leftMid是第三个a  a的下标是2   bb(2)*2+a(1) = 5  "bbabb"整好是5  所以要继续左递归
        if (direction <= 0 && leftMid * 2 + 1 > end - start) expend(s, leftMid, -1);
        //"abbbabb" 右位指针 相同道理  假如当前leftMid是第2个a  a的是4  (总长度(7) - rightMid(4) - 自己(1)) * 2 + a(1) = 5  "bbabb"整好等于5 所有要继续右递归
        if (direction >= 0 && (s.length() - rightMid - 1) * 2 + 1 > end - start) expend(s, rightMid, 1);
    }
}
