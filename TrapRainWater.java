package com.company;



public class TrapRainWater {

	public int trap(int[] height) {
     	if (height.length == 1)
			return 0;
		int maxLeft = 0,  count= 0, secondMaxHeigth = 0;
		int idindex = 0;
		int n = height.length;
		int[] idff = new int[n];
		for (int i = 0; i < n; ++i) {
			int left = i > 0 ? height[i - 1] : 0;
			int right = i + 1 < n ? height[i + 1] : 0;
			int current = height[i];
			if (i == 0) {
				maxLeft = current;
				continue;
			}

			if (current <= left && current <= right) {
				int m = Math.min(left, right);
				int df =  m - current;
				count += df;
				idff[idindex] = m; ++idindex;
				secondMaxHeigth = Math.max(current, secondMaxHeigth);

			} else if (current < left && current > right) {
				idff[idindex] = current; ++idindex;
				secondMaxHeigth = Math.max(current, secondMaxHeigth);
			} else if (current > left) {

				if (idindex > 0 && maxLeft > current) {
					int clevel =  current;
					int j = idindex - 1;
					while (j >=0 && idff[j] < clevel) {
						count += clevel - idff[j];
						idff[j] = clevel;
						--j;
					}
					secondMaxHeigth = Math.max(current, secondMaxHeigth);
					idff[idindex] = current; ++idindex;
				} else if (idindex > 0 && maxLeft <= current) {
					int clevel = maxLeft;
					int j = idindex - 1;
					while (j >=0 && idff[j] < clevel) {
						count += clevel - idff[j];
						idff[j] = clevel;
						--j;
					}
					idff[idindex] = current; ++idindex;
					maxLeft = current;
					secondMaxHeigth = 0;
				} else  {
					maxLeft = current;

				}
			} else if (current == left && current > right) {
				idff[idindex] = current; ++idindex;
				secondMaxHeigth = Math.max(current, secondMaxHeigth);
			}
		}
		return count;
    }

    public static void main(String[] args) {
		System.out.println(TrapRainWater.trap(new int[] {6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}));
	}
}

[0,1,0,2,1,0,1,3,2,1,2,1]
[4,2,0,3,2,5]
[5,5,1,7,1,1,5,2,7,6]
[0,7,1,4,6]
[6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3]
6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3

[0,1,0,2,1,0,1,3,2,1,2,1]
[4,2,0,3,2,5]
[5,5,1,7,1,1,5,2,7,6]
[0,7,1,4,6]
[6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3]
[0,1,2,0,3,0,1,2,0,0,4,2,1,2,5,0,1,2,0,2]
[9,6,8,8,5,6,3]
[9,2,9,3,2,2,1,4,8]