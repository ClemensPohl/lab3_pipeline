package at.fhv.sysarch.lab3.utils;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class FilterUtils {
    /**
     * This method accepts 2 parameters, A mat4 matrix and a Face.
     * It then multiplies the matrix with the face and returns a new Face with the old metadata but new Coordinates
     * @param matrix Mat4 class
     * @param face Face class
     * @return Face class
     */
    public static Face multiplyVectorWithMatrix(Mat4 matrix, Face face){
        Vec4 v1 = matrix.multiply(face.getV1());
        Vec4 v2 = matrix.multiply(face.getV2());
        Vec4 v3 = matrix.multiply(face.getV3());

        return new Face(v1,v2,v3,face);
    }

    public static Face divideVectorByWeight(Face face){
        Vec4 v1 = divide(face.getV1());
        Vec4 v2 = divide(face.getV2());
        Vec4 v3 = divide(face.getV3());

        return new Face(v1, v2, v3, face); // reuse face data like color or normal
    }

    private static Vec4 divide(Vec4 v) {
        float w = v.getW();
        if (w == 0f) return v;
        return new Vec4(
                v.getX() / w,
                v.getY() / w,
                v.getZ() / w,
                1f
        );
    }
}
