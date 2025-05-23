package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import at.fhv.sysarch.lab3.pipeline.filter.stage1.RotationFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1.ScaleFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1.TranslationFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        GraphicsContext gc = pd.getGraphicsContext();



        // Get dependency Matrices
        Mat4 rotationMatrix = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0);
        Mat4 translationMatrix = pd.getModelTranslation();


        // TODO: push from the source (model)
        PushFilter sourceFilter = new SourceFilter();
        Renderer renderer = new Renderer(gc ,pd.getModelColor(), pd.getRenderingMode());

        // 1) Modulate the model Matrix
        PushFilter scaleFilter = new ScaleFilter(new Mat4(1)); // Scale * 1
        PushFilter rotationFilter = new RotationFilter(rotationMatrix);
        PushFilter translationFilter = new TranslationFilter(translationMatrix);

        // 2) Modeled Matrix
        scaleFilter.setSuccessor(rotationFilter);
        rotationFilter.setSuccessor(translationFilter);
        translationFilter.setSuccessor(renderer);

        sourceFilter.setSuccessor(scaleFilter);

        // from now on:
        // Model → View → Clip → NDC → Screen.


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

                animationRotation += (float) (fraction * Math.toRadians(50));

                ((SourceFilter) sourceFilter).process(model);



//
//                // get the modelViewProjectionMatrix by multiplying them IMPORTANT: Projection * View * Model <- in this step
//                Mat4 viewMatrix = pd.getViewTransform();
//                Mat4 projectionMatrix = pd.getProjTransform();
//                Mat4 mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);
//
//                Mat4 viewportMatrix = pd.getViewportTransform();
//
//                for (Face face : model.getFaces()) {
//                    // multiply matrix with each vertex to get the new translation
//                    Vec4 v1 = mvpMatrix.multiply(face.getV1());
//                    Vec4 v2 = mvpMatrix.multiply(face.getV2());
//                    Vec4 v3 = mvpMatrix.multiply(face.getV3());
//
//                    // Perspective divide by W of vertex
//                    v1 = v1.multiply(1f / v1.getW());
//                    v2 = v2.multiply(1f / v2.getW());
//                    v3 = v3.multiply(1f / v3.getW());
//
//                    // Transform viewport to screen coordinates
//                    v1 = viewportMatrix.multiply(v1);
//                    v2 = viewportMatrix.multiply(v2);
//                    v3 = viewportMatrix.multiply(v3);
//
//                    // Draw each line either filled or wireframe render mode
//
//                }



            }
        };
    }
}