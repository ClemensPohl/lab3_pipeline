package at.fhv.sysarch.lab3.utils;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class FilterUtils {

    public static Face multiplyVectorWithMatrix(Mat4 matrix, Face face){
        Vec4 v1 = matrix.multiply(face.getV1());
        Vec4 v2 = matrix.multiply(face.getV2());
        Vec4 v3 = matrix.multiply(face.getV3());

        return new Face(v1,v2,v3,face);
    }

    public static void additionVectorWithMatrix(){

    }
}
