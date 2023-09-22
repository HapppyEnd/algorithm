import java.util.Scanner;

class Node {

  Node left, right;
  int value;

  // красный - true, черный - false
  boolean color;

  Node(int value) {
    this.value = value;
    left = null;
    right = null;

    // Новый узел, который создается, является всегда красного цвета.
    color = true;
  }
}

public class RedBlackTree {

  private static Node root = null;

  Node rotateLeft(Node node) {
    System.out.println("поворот влево");
    Node child = node.right;
    Node childLeft = child.left;

    child.left = node;
    node.right = childLeft;

    return child;
  }

  Node rotateRight(Node node) {
    System.out.println("поворот вправо");
    Node child = node.left;
    Node childRight = child.right;

    child.right = node;
    node.left = childRight;

    return child;
  }

  // Функция проверки цвета.
  boolean isRed(Node node) {
    if (node == null) {
      return false;
    }
    return (node.color == true);
  }

  // Функция изменения цвета.
  void swapColors(Node node1, Node node2) {
    boolean temp = node1.color;
    node1.color = node2.color;
    node2.color = temp;
  }

  // Вставка в дерево.
  Node insert(Node node, int value) {
    if (node == null) {
      return new Node(value);
    }

    if (value < node.value) {
      node.left = insert(node.left, value);
    } else if (value > node.value) {
      node.right = insert(node.right, value);
    } else {
      return node;
    }

    // Правый дочерний элемент красный, а левый дочерний элемент
    // черный или несуществует.
    if (isRed(node.right) && !isRed(node.left)) {
      node = rotateLeft(node);
      swapColors(node, node.left);
    }

    // Левые дочерние элементы красные.
    if (isRed(node.left) && isRed(node.left.left)) {
      node = rotateRight(node);
      swapColors(node, node.right);
    }

    // И левый, и правый дочерние элементы красные.
    if (isRed(node.left) && isRed(node.right)) {
      node.color = !node.color;
      node.left.color = false;
      node.right.color = false;
    }

    return node;
  }

  // Обход по порядку
  void inorder(Node node) {
    if (node != null) {
      inorder(node.left);
      char c = 'R';
      if (node.color == false)
        c = 'B';
      System.out.print(node.value + "" + c + " ");
      inorder(node.right);
    }
  }

  public static void main(String[] args) {

    RedBlackTree node = new RedBlackTree();
    Scanner scanner = new Scanner(System.in);

    char ch;
    do {
      System.out.println("Введите число: ");

      int number = scanner.nextInt();
      root = node.insert(root, number);

      node.inorder(root);
      System.out.println("\nПродолжить? (введите y или n)");
      ch = scanner.next().charAt(0);
    } while (ch == 'Y' || ch == 'y');
  }
}