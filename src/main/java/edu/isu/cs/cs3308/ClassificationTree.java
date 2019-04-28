package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.BinaryTree;
import edu.isu.cs.cs3308.structures.Node;
import edu.isu.cs.cs3308.structures.impl.BinarySearchTree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree;
import edu.isu.cs.cs3308.structures.impl.LinkedBinaryTree.BinaryTreeNode;
import edu.isu.cs.cs3308.traversals.BreadthFirstTraversal;
import edu.isu.cs.cs3308.traversals.InOrderTraversal;
import edu.isu.cs.cs3308.traversals.PreOrderTraversal;
import edu.isu.cs.cs3308.traversals.TreeTraversal;
import edu.isu.cs.cs3308.traversals.commands.EnumeratedSaveCommand;
import edu.isu.cs.cs3308.traversals.commands.EnumerationCommand;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * Binary Tree based classification tree
 *
 * @author Isaac Griffith
 * @author Dylan Lasher
 */
public class ClassificationTree
{

    private LinkedBinaryTree<Datum> tree;

    public ClassificationTree()
    {
        tree = new LinkedBinaryTree<>();
        load();
    }

    public void identify() {
        askQuestions(tree.root(), "");
    }

    private void askQuestions(Node<Datum> currNode, String input)
    {
        Scanner asker = new Scanner(System.in);

        if (input.equals("Y")) {
            if (tree.isExternal(currNode) || tree.left(currNode) == null)
            {
                System.out.println("Thank you!\n");
            }
            else if (tree.left(currNode) != null)
            {
                askQuestions(tree.left(currNode), "");
            }
            else {
                askQuestions(tree.right(currNode), "");
            }
        }
        else if (input.equals("N")) {
            if (tree.right(currNode) != null) {
                askQuestions(tree.right(currNode), "");
            }
            else {
                System.out.println("I am not familiar with any " + notAnimalString(currNode) +
                        " animals do not have the property of " + currNode.getElement());
                System.out.println("Please, tell me what this animal is. > ");
                String inputAnimal = asker.next();

                System.out.println("What property does a " + inputAnimal + " have that " +
                        currNode.getElement() + " lacks? > ");
                input = asker.next();
                System.out.println();

                currNode = tree.addRight(currNode, new Datum(input));
                tree.addRight(currNode, new Datum("a " + inputAnimal));
            }
        }
        else {
            System.out.println("Is this creature " + currNode.getElement() + "? (Y or N) > ");
            input = asker.next().toUpperCase();
            askQuestions(currNode,input);
        }
    }

    private String notAnimalString(Node<Datum> currNode)
    {
        String retString = "";
        int depth = tree.depth(currNode);

        for (int i = 0; i < depth; i++)
        {
            currNode = currNode.getParent();

            if (i != 0)
            {
                retString = ", " + retString;
            }
            retString = currNode.getElement() + retString;
        }
        return retString;
    }

    /**
     * Saves to file.
     */
    public void save()
    {
        TreeTraversal<Datum> trav = new InOrderTraversal<>(tree);
        trav.setCommand(new EnumerationCommand());
        trav.traverse();
        trav = new BreadthFirstTraversal<>(tree);

        Scanner asker = new Scanner(System.in);
        System.out.println("Tree filename for saving: ");
        String filename = asker.next();

        try {
            Path p = Paths.get(filename);
            Files.deleteIfExists(p);
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(new File(filename), false)))
        {
            trav.setCommand(new EnumeratedSaveCommand(writer));
            trav.traverse();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    public void load()
    {
        Scanner asker = new Scanner(System.in);
        System.out.println("Filename of Tree to Open: ");
        String input = asker.next();
        System.out.println();

        if (Files.exists(Paths.get(input)))
        {
            List<String> treeLines = new LinkedList<>();

            try {
                treeLines = Files.readAllLines(Paths.get(input));
            }
            catch (IOException ex)
            {
                System.out.println(ex.toString());
            }

            parsedTree(treeLines);
        }
        else {
            hardcodedParse();
        }
    }

    private void parsedTree(List<String> treeLines)
    {
        LinkedList<String> indexList = new LinkedList<>();
        LinkedList<Node<Datum>> nodeList = new LinkedList<>();

        for (String line : treeLines)
        {
            String[] parsed = line.split(":");
            Datum temp = new Datum(parsed[3]);

            while (!indexList.isEmpty() && !parsed[0].equals(indexList.peek()))
            {
                indexList.poll();
                nodeList.poll();
            }

            if (indexList.isEmpty() || !indexList.peek().equals(parsed[1]))
            {
                indexList.offer(parsed[1]);
            }

            if (tree.isEmpty())
            {
                nodeList.offer(tree.setRoot(temp));
            }
            else {
                if (parsed[2].equals("l"))
                {
                    nodeList.offer(tree.addLeft(nodeList.peek(), temp));
                } else {
                    nodeList.offer(tree.addRight(nodeList.peek(), temp));
                }
            }
        }
    }

    /**
     * From project description
     */
    private void hardcodedParse()
    {
        String rawText =
                "-1:7:r:furry\n" +
                        "7:1:l:squeaky\n" +
                        "7:13:r:aquatic\n" +
                        "1:0:l:a mouse\n" +
                        "1:5:r:bipedal\n" +
                        "13:11:l:legged\n" +
                        "13:14:r:a snake\n" +
                        "5:3:l:reclusive\n" +
                        "5:6:r:a dog\n" +
                        "11:9:l:shelled\n" +
                        "11:12:r:a fish\n" +
                        "3:2:l:a bigfoot\n" +
                        "3:4:r:a human\n" +
                        "9:8:l:a crab\n" +
                        "9:10:r:a salamander\n";
        String[] treeList = rawText.split("\n");
        List<String> parsedList = new LinkedList<>();

        for (String node : treeList) {
            if (node.split(":")[3].subSequence(0,2) != "a ")
            {
                parsedList.add(node);
            }
        }

        parsedTree(parsedList);
    }
}