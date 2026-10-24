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
          if (arr2[0] == arr3[len - 1] && isValidBST(arr2, arr1, 0, arr1.length - 1, 0) && 
               isValidPostOrder(arr3, arr1, 0, arr1.length - 1, new int[]{arr1.length - 1})){
               orderOfT2.append("Preorder");
               orderOfT3.append("Postorder");               
          } else if (arr3[0] == arr2[len - 1] && isValidBST(arr3, arr1, 0, arr1.length - 1, 0) && 
               isValidPostOrder(arr2, arr1, 0, arr1.length - 1, new int[]{arr1.length - 1})){
               orderOfT2.append("Postorder");
               orderOfT3.append("Preorder");   
          } else {
               return "Invalid traversals";
          }
        } else if (isT2Sorted){
          orderOfT2.append("Inorder");
          if (arr1[0] == arr3[len - 1] && isValidBST(arr1, arr2, 0, arr2.length - 1, 0) && 
               isValidPostOrder(arr3, arr2, 0, arr2.length - 1, new int[]{arr2.length - 1})){
               orderOfT1.append("Preorder");
               orderOfT3.append("Postorder");               
          } else if (arr3[0] == arr1[len - 1] && isValidBST(arr3, arr2, 0, arr2.length - 1, 0) && 
               isValidPostOrder(arr1, arr2, 0, arr2.length - 1, new int[]{arr2.length - 1})){
               orderOfT1.append("Postorder");
               orderOfT3.append("Preorder");   
          } else {
               return "Invalid traversals";
          }
        } else if (isT3Sorted){
          orderOfT3.append("Inorder");
          if (arr1[0] == arr2[len - 1] && isValidBST(arr1, arr3, 0, arr3.length - 1, 0) &&
               isValidPostOrder(arr2, arr3, 0, arr3.length - 1, new int[]{arr3.length - 1})) {
               orderOfT1.append("Preorder");
               orderOfT2.append("Postorder");
          } else if (arr2[0] == arr1[len - 1] && isValidBST(arr2, arr3, 0, arr3.length - 1, 0) &&
               isValidPostOrder(arr1, arr3, 0, arr3.length - 1, new int[]{arr3.length - 1})) {
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
}