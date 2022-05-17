import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class CubeBoardView extends Group {
    CubeMesh[][][] cubes;
    float spacing = 0.1f;
    int dim = 3;

    float center;

    Rotate rotate;
    Transform transform = new Rotate();

    public FaceMesh findFaceAtCoords(int f, int x, int y) {
        for (CubeMesh[][] a : cubes) {
            for (CubeMesh[] b : a) {
                for (CubeMesh c : b) {
                    for (FaceMesh m : c.getMeshes()) {
                        if (m.getFace() == f && m.getX() == x && m.getY() == y) return m;
                    }
                }
            }
        }
        return null;
    }

    void rotateByX(int ang) {
        Point3D rotAxis;
        try {
            rotAxis = transform.inverseDeltaTransform(new Point3D(center, 1000, center));
            rotate = new Rotate(ang, center, center, center, rotAxis);
            transform = transform.createConcatenation(rotate);
            this.getTransforms().clear();
            this.getTransforms().addAll(transform);
        } catch (NonInvertibleTransformException ex) {
            throw new IllegalStateException(ex);
        }
    }

    void rotateByY(int ang) {
        rotate = new Rotate(ang, center*getScaleX(), center*getScaleY(), center*getScaleZ(), Rotate.Y_AXIS);
        transform = transform.createConcatenation(rotate);
        this.getTransforms().clear();
        this.getTransforms().addAll(transform);
    }

    float getCenter() { return center; }

    CubeBoardView(int dim) {
        this.dim = dim;
        cubes = new CubeMesh[dim][dim][dim];

        float cubeSize = new CubeMesh().getSize();

        center = (cubeSize + spacing) * (dim/2.0f) - 0.5f * spacing - cubeSize * 0.5f ;

        for (int i = 0; i < dim; ++i) {
            for (int j = 0; j < dim; ++j) {
                for (int k = 0; k < dim; ++k) {
                    CubeMesh cube = new CubeMesh();
                    float shiftDistX = (cubeSize + spacing) * i;
                    float shiftDistY = (cubeSize + spacing) * j;
                    float shiftDistZ = (cubeSize + spacing) * k;

                    cube.move(shiftDistX, shiftDistY, shiftDistZ);
                    cubes[i][j][k] = cube;
                    this.getChildren().add(cube);

                    FaceMesh temp;

                    if (k == dim-1) {
                        temp = cube.getMeshes()[3];
                        temp.setOuter(true);
                        temp.setFace(3);
                        temp.setX(i);
                        temp.setY(j);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }
                    if (k == 0) {
                        temp = cube.getMeshes()[1];
                        temp.setOuter(true);
                        temp.setFace(1);
                        temp.setX(i);
                        temp.setY(j);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }
                    if (i == dim-1) {
                        temp = cube.getMeshes()[2];
                        temp.setOuter(true);
                        temp.setFace(2);
                        temp.setX(k);
                        temp.setY(j);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }
                    if (i == 0) {
                        temp = cube.getMeshes()[4];
                        temp.setOuter(true);
                        temp.setFace(4);
                        temp.setX(k);
                        temp.setY(j);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }
                    if (j == dim-1) {
                        temp = cube.getMeshes()[0];
                        temp.setOuter(true);
                        temp.setFace(0);
                        temp.setX(i);
                        temp.setY(k);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }
                    if (j == 0) {
                        temp = cube.getMeshes()[5];
                        temp.setOuter(true);
                        temp.setFace(5);
                        temp.setX(i);
                        temp.setY(k);
                        temp.setMaterial(new PhongMaterial(Color.WHITE));
                    }

//                    if (i == 0) {
//                        for (MeshView m : cube.getMeshes()) {
//                            m.set
//                        }
//                    }
                }
            }
        }
    }
}
