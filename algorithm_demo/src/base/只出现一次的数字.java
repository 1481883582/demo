package base;

/**
 * 只出现一次的数字
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 *
 * 输入: [2,2,1]
 * 输出: 1
 * 示例 2:
 *
 * 输入: [4,1,2,1,2]
 * 输出: 4
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x21ib6/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * 这题说的是只有一个数出现了一次，其他数字都出现了2次，让我们求这个只出现一次的数字。这题使用位运算是最容易解决的，关于位运算有下面几个规律
 *
 * 1^1=0;
 *
 * 1^0=1;
 *
 * 0^1=1;
 *
 * 0^0=0;
 *
 *
 *
 * 也就说0和1异或的时候相同的异或结果为0，不同的异或结果为1，根据上面的规律我们得到
 *
 * a^a=0；自己和自己异或等于0
 *
 * a^0=a；任何数字和0异或还等于他自己
 *
 * a^b^c=a^c^b；异或运算具有交换律
 *
 * 作者：数据结构和算法
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x21ib6/?discussion=LIRNfM
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class 只出现一次的数字 {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{4,1,2,1,2}));
    }
    public static int singleNumber(int[] nums) {
        int a = 0;
        for(int i = 0; i < nums.length; i++){
            a ^= nums[i];
        }
        return a;
    }
    //
}
