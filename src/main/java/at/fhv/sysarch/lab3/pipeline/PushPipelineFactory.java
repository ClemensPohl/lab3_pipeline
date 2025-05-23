package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.Renderer;
import at.fhv.sysarch.lab3.pipeline.filter.ScaleFilter;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        GraphicsContext gc = pd.getGraphicsContext();

        // TODO: push from the source (model)
        // SOURCE MODEL NEW SOURCE MODEL
        // SCALFEFILTER _> PUSHFILTER
        ScaleFilter scaleFilter = new ScaleFilter();
        Renderer renderer = new Renderer(gc ,pd.getModelColor());

        scaleFilter.setSuccessor(renderer);
        //Rot filetr + trans filter
        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        // TODO 2. perform backface culling in VIEW SPACE
        // TODO 3. perform depth sorting in VIEW SPACE
        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

        // TODO 7. feed into the sink (renderer)


        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;


            @Override
            protected void render(float fraction, Model model) {
                gc.setStroke(pd.getModelColor());

                animationRotation += (float) (fraction * Math.toRadians(50));

                // get the model matrix
                Vec3 modelPos = pd.getModelPos();
                Vec3 modelRot = pd.getModelRotAxis();
                Vec3 modelScale = new Vec3(1,1,1);

                // TODO remove scale conversion util
                Mat4 scaleMatrixRalph = new Mat4(10); // scale by factor 10

                // Create Matrix from local position vectors
                Mat4 scaleMatrix = MatrixUtils.createScalingMatrix(modelScale);
                Mat4 rotationMatrix = MatrixUtils.createRotationMatrix(modelRot, animationRotation);
                Mat4 translationMatrix = MatrixUtils.translationMatrix(modelPos);

                // apply the translation, rotation and scale matrix to the model matrix
                Mat4 modelMatrix = translationMatrix.multiply(rotationMatrix).multiply(scaleMatrix);

                // get the modelViewProjectionMatrix by multiplying them IMPORTANT: Projection * View * Model <- in this step
                Mat4 viewMatrix = pd.getViewTransform();
                Mat4 projectionMatrix = pd.getProjTransform();
                Mat4 mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

                // apache camel, flow api java synchron blcok aufrunfe



                Mat4 viewportMatrix = pd.getViewportTransform();

                // TODO For eliminate , class sourceModel implement pushFilter
                // successor + push methode, //push cannot be used
                // feld = successor,
                // source run -> whole model not only one polygon
                // private void run or process (Model model) _> private isch schmutz wegs interface
                // foreach face this.successor.push(f)

                for (Face face : model.getFaces()) {
                    // multiply matrix with each vertex to get the new translation
                    Vec4 v1 = mvpMatrix.multiply(face.getV1());
                    Vec4 v2 = mvpMatrix.multiply(face.getV2());
                    Vec4 v3 = mvpMatrix.multiply(face.getV3());

                    // Perspective divide by W of vertex
                    v1 = v1.multiply(1f / v1.getW());
                    v2 = v2.multiply(1f / v2.getW());
                    v3 = v3.multiply(1f / v3.getW());

                    // Transform viewport to screen coordinates
                    v1 = viewportMatrix.multiply(v1);
                    v2 = viewportMatrix.multiply(v2);
                    v3 = viewportMatrix.multiply(v3);

                    // TODO scalfilter push
                    scaleFilter.push(face);

                    // Draw each line either filled or wireframe render mode
                    if (pd.getRenderingMode() == RenderingMode.FILLED) {

                        double[] xPoints = {v1.getX(), v2.getX(), v3.getX()};
                        double[] yPoints = {v1.getY(), v2.getY(), v3.getY()};
                        gc.setFill(pd.getModelColor());
                        gc.fillPolygon(xPoints, yPoints, 3);

                    } else if( pd.getRenderingMode() == RenderingMode.WIREFRAME){
                        gc.setStroke(pd.getModelColor());

                        gc.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                        gc.strokeLine(v2.getX(), v2.getY(), v3.getX(), v3.getY());
                        gc.strokeLine(v3.getX(), v3.getY(), v1.getX(), v1.getY());

                    }else if ( pd.getRenderingMode() == RenderingMode.POINT ){
                        gc.setFill(pd.getModelColor());

                        gc.fillOval(v1.getX() - 0.5, v1.getY() - 0.5, 1, 1);
                        gc.fillOval(v2.getX() - 0.5, v2.getY() - 0.5, 1, 1);
                        gc.fillOval(v3.getX() - 0.5, v3.getY() - 0.5, 1, 1);
                    }
                }

                // SOURCEMODEL.process(model)


            }
        };
    }
}