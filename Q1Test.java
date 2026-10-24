public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     public static int[] parseTraversal(String traversal){
          String[] parts = traversal.split("-");
          int[] arr = new int[parts.length];
          for (int i = 0; i < parts.length; i++){
               arr[i] = Integer.parseInt(parts[i]);
          }
          return arr;
     }

     public static boolean isSorted(int[] arr){
          for (int i = 0; i < arr.length - 1; i++){
               if (arr[i] >= arr[i+1]){
                    return false;
               }
          }
          return true;
     }

     // checks whether given pre-order and inorder array belong to same valid BST structure
     public static boolean isValidBST(int[] pre, int[] in, int inStart, int inEnd, int preIndex){
          // for empty subtree
          if (inStart > inEnd){
               return true;
          } else if (preIndex >= pre.length) {
               return false;
          }              
          int rootValue = pre[preIndex];
          int rootIndex = -1;
          for (int i = inStart; i <= inEnd; i++){
               if(in[i] == rootValue){
                    rootIndex = i;
                    break;
               }
          }
          if (rootIndex == -1){
               return false;
          }
          int leftSubtreeSize = rootIndex - inStart;
          // recursively check the left and right subtrees
          return isValidBST(pre, in, inStart, rootIndex - 1, preIndex + 1) &&
               isValidBST(pre, in, rootIndex + 1, inEnd, preIndex + 1 + leftSubtreeSize);
     }

     // checks whether given post-order and inorder array traversal match
     public static boolean isValidPostOrder(int[] post, int[] in, int inStart, int inEnd, int[] postIndex){
          if (inStart > inEnd){
               return true;
          } else if (postIndex[0] < 0 || postIndex[0] >= post.length) {
          return false;
          }          
          int rootValue = post[postIndex[0]];
          postIndex[0]--;

          int rootIndex = -1;
          for (int i = inStart; i <= inEnd; i++){
               if (in[i] == rootValue){
                    rootIndex = i;
                    break;
               }
          } 
          if (rootIndex == -1){
               return false;
          }
          
          return isValidPostOrder(post, in, rootIndex + 1, inEnd, postIndex) && 
               isValidPostOrder(post, in, inStart, rootIndex - 1, postIndex);
     }

public static boolean matchesPostorder(int[] pre, int[] post, int[] in) {
    int[] generated = new int[pre.length];
    int[] preIndex = {0};
    int[] postIndex = {0};

    if (!buildPostorder(pre, in, 0, in.length - 1,
                        preIndex, generated, postIndex)) {
        return false;
    }

    if (preIndex[0] != pre.length || postIndex[0] != post.length) {
        return false;
    }

    for (int i = 0; i < post.length; i++) {
        if (generated[i] != post[i]) {
            return false;
        }
    }

    return true;
}

     public static boolean buildPostorder(int[] pre, int[] in,
          int inStart, int inEnd, int[] preIndex,
          int[] generated, int[] postIndex) {

     if (inStart > inEnd) {
          return true;
     }

     if (preIndex[0] >= pre.length) {
          return false;
     }

     int root = pre[preIndex[0]++];
     int rootIndex = -1;

     for (int i = inStart; i <= inEnd; i++) {
          if (in[i] == root) {
               rootIndex = i;
               break;
          }
     }

     if (rootIndex == -1) {
          return false;
     }

     if (!buildPostorder(pre, in, inStart, rootIndex - 1,
                         preIndex, generated, postIndex)) {
          return false;
     }

     if (!buildPostorder(pre, in, rootIndex + 1, inEnd,
                         preIndex, generated, postIndex)) {
          return false;
     }

     generated[postIndex[0]++] = root;
     return true;
     }

     public static String verify(String traversal1, String traversal2, String traversal3){

        // find the string sorted in ascending order, that is inorder traversal
        int[] arr1 = parseTraversal(traversal1);
        int[] arr2 = parseTraversal(traversal2);
        int[] arr3 = parseTraversal(traversal3);
        if (arr1.length != arr2.length || arr1.length != arr3.length || arr2.length != arr3.length){
            return "Invalid traversals";
        }                  
        int len = arr1.length;
        
        boolean isT1Sorted = isSorted(arr1);
        boolean isT2Sorted = isSorted(arr2);
        boolean isT3Sorted = isSorted(arr3);

        StringBuilder orderOfT1 = new StringBuilder();
        StringBuilder orderOfT2 = new StringBuilder();
        StringBuilder orderOfT3 = new StringBuilder();

        if (isT1Sorted){
          orderOfT1.append("Inorder");
          if (arr2[0] == arr3[len - 1] 
               && isValidBST(arr2, arr1, 0, arr1.length - 1, 0)
               && isValidPostOrder(arr3, arr1, 0, arr1.length - 1, new int[]{arr1.length - 1})
               && isValidPreorderBST(arr2)
               && isValidPostorderBST(arr3)
               && matchesPostorder(arr2, arr3, arr1)) {
               orderOfT2.append("Preorder");
               orderOfT3.append("Postorder");               
          } else if (arr3[0] == arr2[len - 1]
               && isValidBST(arr3, arr1, 0, arr1.length - 1, 0)
               && isValidPostOrder(arr2, arr1, 0, arr1.length - 1, new int[]{arr1.length - 1})
               && isValidPreorderBST(arr3)
               && isValidPostorderBST(arr2)
               && matchesPostorder(arr3, arr2, arr1)) {
               orderOfT2.append("Postorder");
               orderOfT3.append("Preorder");   
          } else {
               return "Invalid traversals";
          }
        } else if (isT2Sorted){
          orderOfT2.append("Inorder");
          if (arr1[0] == arr3[len - 1]
               && isValidBST(arr1, arr2, 0, arr2.length - 1, 0)
               && isValidPostOrder(arr3, arr2, 0, arr2.length - 1, new int[]{arr2.length - 1})
               && isValidPreorderBST(arr1)
               && isValidPostorderBST(arr3) 
               && matchesPostorder(arr1, arr3, arr2)){
               orderOfT1.append("Preorder");
               orderOfT3.append("Postorder");               
          } else if (arr3[0] == arr1[len - 1]
               && isValidBST(arr3, arr2, 0, arr2.length - 1, 0)
               && isValidPostOrder(arr1, arr2, 0, arr2.length - 1, new int[]{arr2.length - 1})
               && isValidPreorderBST(arr3)
               && isValidPostorderBST(arr1)
               && matchesPostorder(arr3, arr1, arr2)){
               orderOfT1.append("Postorder");
               orderOfT3.append("Preorder");   
          } else {
               return "Invalid traversals";
          }
        } else if (isT3Sorted){
          orderOfT3.append("Inorder");
          if (arr1[0] == arr2[len - 1]
               && isValidBST(arr1, arr3, 0, arr3.length - 1, 0)
               && isValidPostOrder(arr2, arr3, 0, arr3.length - 1, new int[]{arr3.length - 1})
               && isValidPreorderBST(arr1)
               && isValidPostorderBST(arr2)
               && matchesPostorder(arr1, arr2, arr3)) {
               orderOfT1.append("Preorder");
               orderOfT2.append("Postorder");
          } else if (arr2[0] == arr1[len - 1]
               && isValidBST(arr2, arr3, 0, arr3.length - 1, 0)
               && isValidPostOrder(arr1, arr3, 0, arr3.length - 1, new int[]{arr3.length - 1})
               && isValidPreorderBST(arr2)
               && isValidPostorderBST(arr1)
               && matchesPostorder(arr2, arr1, arr3)) {
               orderOfT1.append("Postorder");
               orderOfT2.append("Preorder");
          } else {
              return "Invalid traversals";
          }          
        } else {
          return "Invalid traversals";
        }

        return String.format("Traversal 1 - %s, Traversal 2 - %s, Traversal 3 - %s", 
                              orderOfT1.toString(), orderOfT2.toString(), orderOfT3.toString());
     }

     public static boolean isValidPreorderBST(int[] arr) {
     int[] index = {0};
     return checkPreorderBST(arr, index, Long.MIN_VALUE, Long.MAX_VALUE)
               && index[0] == arr.length;
     }

     public static boolean checkPreorderBST(int[] arr, int[] index, long lower, long upper) {
     if (index[0] >= arr.length) return true;

     int value = arr[index[0]];
     if (value <= lower || value >= upper) return true;

     index[0]++;

     return checkPreorderBST(arr, index, lower, value)
               && checkPreorderBST(arr, index, value, upper);
     }

     public static boolean isValidPostorderBST(int[] arr) {
     int[] index = {arr.length - 1};
     return checkPostorderBST(arr, index, Long.MIN_VALUE, Long.MAX_VALUE)
               && index[0] == -1;
     }

     public static boolean checkPostorderBST(int[] arr, int[] index, long lower, long upper) {
     if (index[0] < 0) return true;

     int value = arr[index[0]];
     if (value <= lower || value >= upper) return true;

     index[0]--;

     return checkPostorderBST(arr, index, value, upper)
               && checkPostorderBST(arr, index, lower, value);
     }
}