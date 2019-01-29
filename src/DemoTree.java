import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;


public class DemoTree {

    private JTree tree;
    DefaultMutableTreeNode root;

    private int addCounter = 0;


    public DemoTree() {
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

        DemoTree demo = new DemoTree();

        demo.testDemo();


    }


    public void testDemo() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        S3TreeNode root = new S3TreeNode(new S3Object("S3", true), true, true);
        createNodes(root);
        tree = new JTree(root);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                /* if nothing is selected */
                if (node == null) return;

                /* retrieve the node that was selected */
                Object nodeInfo = node.getUserObject();
                System.out.println("INFO: " + nodeInfo);
                System.out.println("Could add new content here...");
            }
        });

        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                System.out.println("TreeWillExpand");
                Object parent = event.getPath().getLastPathComponent();
                S3TreeNode parentNode = (S3TreeNode) parent;
                S3Object s3Object = (S3Object) parentNode.getUserObject();
                System.out.println(parent);
                if (s3Object.getName().equalsIgnoreCase("bucket3")) {
                    parentNode.addS3Children(getDummyS3Objects());
                    updateModel(parentNode);
                }

            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                System.out.println("TreeWillCollapse");

            }
        });



        JScrollPane treeView = new JScrollPane(tree);
        frame.add(treeView, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);


    }


    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new S3TreeNode(new S3Object("bucket1", true), true);
        top.add(category);

        book = new S3TreeNode(new S3Object("file1a"));
        category.add(book);

        book = new S3TreeNode(new S3Object("file1b"));
        category.add(book);

        book = new S3TreeNode(new S3Object("file1c"));
        category.add(book);

        // another category

        category = new S3TreeNode(new S3Object("bucket2", true));
        top.add(category);

        //VM
        book = new S3TreeNode(new S3Object("file2a"));
        category.add(book);

        //Language Spec
        book = new S3TreeNode(new S3Object("file2a"));
        category.add(book);

        // another category
        category = new S3TreeNode(new S3Object("bucket3", true));
        top.add(category);

    }

    private Collection<S3Object> getDummyS3Objects() {
        System.out.println("Dynamically expanding data...");
        ArrayList<S3Object> list = new ArrayList<S3Object>();

        System.out.println("Adding node filea3" + addCounter++);
        list.add(new S3Object("file3a" + addCounter));

        System.out.println("Adding node filea3" + addCounter++);
        list.add(new S3Object("file3a" + addCounter));

        System.out.println("Adding node filea3" + addCounter++);
        list.add(new S3Object("file3a" + addCounter, true));

        return list;

    }


    private void updateModel(DefaultMutableTreeNode parent) {
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        if (parent != null) {
            model.reload(parent);
        } else {
            model.reload(root);
        }
    }

}
