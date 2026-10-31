import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Node;

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
static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          String[] inputs = {traversal1, traversal2, traversal3};
          int[][] numbers = new int[3][];

          try {
               for (int i = 0; i < 3; i++) {
                    String[] parts = inputs[i].split("-");
                    numbers[i] = new int[parts.length];
                    for (int j = 0; j < parts.length; j++) {
                         numbers[i][j] = Integer.parseInt(parts[j]);
                    }
               }
          } catch (NumberFormatException e) {
               return "Invalid traversals";
          }

          if (numbers[0].length == 0 || numbers[0].length != numbers[1].length
                    || numbers[1].length != numbers[2].length) {
               return "Invalid traversals";
          }

          
          int[] sorted = numbers[0].clone();
          Arrays.sort(sorted);
          for (int i = 1; i < sorted.length; i++) {
               if (sorted[i] == sorted[i - 1]) {
                    return "Invalid traversals";
               }
          }
          for (int i = 1; i < 3; i++) {
               int[] copy = numbers[i].clone();
               Arrays.sort(copy);
               if (!Arrays.equals(sorted, copy)) {
                    return "Invalid traversals";
               }
          }

          
          for (int pre = 0; pre < 3; pre++) {
               Node root = null;
               for (int value : numbers[pre]) {
                    root = insert(root, value);
               }

               ArrayList<Integer> inorder = new ArrayList<>();
               ArrayList<Integer> preorder = new ArrayList<>();
               ArrayList<Integer> postorder = new ArrayList<>();
               getInorder(root, inorder);
               getPreorder(root, preorder);
               getPostorder(root, postorder);

               if (!matches(numbers[pre], preorder)) {
                    continue;
               }
               for (int in = 0; in < 3; in++) {
                    if (in == pre || !matches(numbers[in], inorder)) {
                         continue;
                    }
                    int post = 3 - pre - in;
                    if (matches(numbers[post], postorder)) {
                         String[] order = new String[3];
                         order[pre] = "Preorder";
                         order[in] = "Inorder";
                         order[post] = "Postorder";
                         return "Traversal 1 - " + order[0] + ", Traversal 2 - "
                                   + order[1] + ", Traversal 3 - " + order[2];
                    }
               }
          }
          return "Invalid traversals";
          
     }

     static Node insert(Node root, int value) {
          if (root == null) {
               return new Node(value);
          }
          if (value < root.value) {
               root.left = insert(root.left, value);
          } else {
               root.right = insert(root.right, value);
          }
          return root;
     }

     static void getInorder(Node root, ArrayList<Integer> values) {
          if (root == null) return;
          getInorder(root.left, values);
          values.add(root.value);
          getInorder(root.right, values);
     }

     static void getPreorder(Node root, ArrayList<Integer> values) {
          if (root == null) return;
          values.add(root.value);
          getPreorder(root.left, values);
          getPreorder(root.right, values);
     }

     static void getPostorder(Node root, ArrayList<Integer> values) {
          if (root == null) return;
          getPostorder(root.left, values);
          getPostorder(root.right, values);
          values.add(root.value);
     }

     static boolean matches(int[] numbers, ArrayList<Integer> values) {
          if (numbers.length != values.size()) return false;
          for (int i = 0; i < numbers.length; i++) {
               if (numbers[i] != values.get(i)) return false;
          }
          return true;
     }
}