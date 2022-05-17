import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;

public class CubeMesh extends Group {
    FaceMesh[] faces;
    float size = 1.0f;

    // notes
    // x-axis + right
    // y-axis + down
    // z-axis + inward

    public CubeMesh() {
        faces = new FaceMesh[6];
        faces[0] = createDown();
//        faces[0].setMaterial(new PhongMaterial(Color.WHITE));

        faces[1] = createFront();
//        faces[1].setMaterial(new PhongMaterial(Color.BLUE));

        faces[2] = createRight();
//        faces[2].setMaterial(new PhongMaterial(Color.RED));

        faces[3] = createBack();
//        faces[3].setMaterial(new PhongMaterial(Color.GREEN));

        faces[4] = createLeft();
//        faces[4].setMaterial(new PhongMaterial(Color.ORANGE));

        faces[5] = createUp();
//        faces[5].setMaterial(new PhongMaterial(Color.YELLOW));


        for (MeshView m : faces) {
            m.setMaterial(new PhongMaterial(Color.BLACK));

//            m.setDrawMode(DrawMode.LINE);
//            m.setCullFace(CullFace.NONE);
        }
        this.getChildren().addAll(faces);
    }

//    public Group getGroup() {
//        return group;
//    }

    private FaceMesh createBack() {
        return new FaceMesh(createPointlessMesh());
    }

    private FaceMesh createFront() {
        float[] points = {
                size, size, -size,
                size, -size, -size,
                -size, size, -size,
                -size, -size, -size
        };

        TriangleMesh mesh = createPointlessMesh();
        mesh.getPoints().setAll(points);
        return new FaceMesh(mesh);
    }

    private FaceMesh createLeft() {
        float[] points = {
                -size, size, -size,
                -size, -size, -size,
                -size, size, size,
                -size, -size, size
        };

        TriangleMesh mesh = createPointlessMesh();
        mesh.getPoints().setAll(points);
        return new FaceMesh(mesh);
    }

    private FaceMesh createRight() {
        float[] points = {
                size, size, size,
                size, -size, size,
                size, size, -size,
                size, -size, -size
        };

        TriangleMesh mesh = createPointlessMesh();
        mesh.getPoints().setAll(points);
        //  mesh.getNormals().setAll(normals);
        return new FaceMesh(mesh);
    }

    private FaceMesh createUp() {
        float[] points = {
                -size, -size, size,
                -size, -size, -size,
                size, -size, size,
                size, -size, -size
        };

        TriangleMesh mesh = createPointlessMesh();
        mesh.getPoints().setAll(points);
        // mesh.getNormals().setAll(normals);
        return new FaceMesh(mesh);
    }

    private FaceMesh createDown() {
        float[] points = {
                -size, size,-size,
                -size, size, size,
                size, size, -size,
                size, size, size
        };

        int[] faces = new int[]{
                2, 2, 0, 0, 1, 1,
                2, 2, 1, 1, 3, 3
        };

        TriangleMesh mesh = createPointlessMesh();
        mesh.getPoints().setAll(points);
        mesh.getFaces().setAll(faces);
        return new FaceMesh(mesh);
    }
    private TriangleMesh createPointlessMesh() {
        float[] points = {
                -size, size, size,
                -size, -size, size,
                size, size, size,
                size, -size, size
        };
        float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };
        int[] faces = new int[]{
                2, 2, 0, 0, 1, 1,
                2, 2, 1, 1, 3, 3
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texCoords);
        mesh.getFaces().setAll(faces);
        return mesh;
    }

    public FaceMesh[] getMeshes() {
        return faces;
    }

    public float getSize() { return this.size * 2; }

    public void move(float x, float y, float z) {
        Translate translate = new Translate();
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }
}
