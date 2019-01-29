import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;

public class S3TreeNode extends DefaultMutableTreeNode {

    private boolean isLoaded;


    public S3TreeNode(S3Object userObject) {
        super(userObject);
        this.isLoaded = false;
    }

    public S3TreeNode(S3Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.isLoaded = false;
    }

    public S3TreeNode(S3Object userObject, boolean allowsChildren, boolean isLoaded) {
        super(userObject, allowsChildren);
        this.isLoaded = isLoaded;
    }

    public void addS3Children(Collection<S3Object> s3Objects) {
        for (S3Object s3Object : s3Objects) {
            this.add(new S3TreeNode(s3Object));
        }
        this.isLoaded = true;
    }

    @Override
    public boolean isLeaf() {
        return !((S3Object) this.userObject).isDir();
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    public String toString() {
        return ((S3Object) this.userObject).getName();
    }

}
