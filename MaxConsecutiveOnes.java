
public class MaxConsecutiveOnes {
	public static int longestOnes(int[] nums, int k) {
		int oneCount = 0, rightIndex = 0, leftIndex = 0, maxCount = 0;
		int zeroCount = k;

		while (rightIndex < nums.length) {
			if (nums[rightIndex] == 0) {
				if (zeroCount == 0) {
					maxCount = Math.max(maxCount, oneCount);
					if (nums[leftIndex] == 0) {
						++zeroCount;
					}
					++leftIndex;
					oneCount = oneCount - 1;
//					
				} else {
					++oneCount;  ++ rightIndex;
					--zeroCount;
				}

			} else  {
				++oneCount; ++rightIndex;
			}
		}
		return Math.max(maxCount, oneCount);   
	}

    public static void main(String[] args) {
		System.out.println(longestOnes(new int[]{0, 0, 0, 1}, 4));
	}

}

//[1,1,1,0,0,0,1,1,1,1,0]
//0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1
