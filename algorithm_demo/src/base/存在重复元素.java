package base;

/**
 * 存在重复元素
 * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3,1]
 * 输出：true
 * 示例 2：
 *
 * 输入：nums = [1,2,3,4]
 * 输出：false
 * 示例 3：
 *
 * 输入：nums = [1,1,1,3,3,4,3,2,4,2]
 * 输出：true
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x248f5/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class 存在重复元素 {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{-1, -3, 2, 4, -5}));
    }

    /**
     * 模拟hash表  0特殊处理
     *
     * @param nums
     * @return
     */
    public static boolean containsDuplicate(int[] nums) {
        //长度不够  直接返回
        int l = nums.length;
        if (l < 2) {
            return false;
        }

        //声明hash表
        int[] hashArr = new int[l];

        // 0特殊处理  0的计数器  原因的int类型默认是0
        int count0 = 0;

        for (int i = 0; i < l; i++) {

            // 0特殊处理
            if (nums[i] == 0) {
                count0++;
                continue;
            }
            if (count0 > 1) return true;

            //数字过大  余数获取要放入的位置
            int hash = nums[i] % l;
            //如果是负数  加上总长度 保证在[0-长度]范围之内
            if (hash < 0) {
                hash += l;
            }

            while (true) {
                //如果该位置没存过  就存入  跳出循环
                if (hashArr[hash] == 0) {
                    hashArr[hash] = nums[i];
                    break;
                    // 如果存过了就比较  是否重复了
                } else if (hashArr[hash] == nums[i]) {
                    return true;
                } else {
                    //位置被占了  又不重复  就往一下个位置放
                    hash++;
                    //等于总长度  还没找到位置  就减去总长度从0开始
                    if (hash >= l) {
                        hash -= l;
                    }
                }
            }
        }
        return false;
    }
}
