package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.RotationFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.ScaleFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.TranslationFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage2_view.ViewTransformFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage3_clip.ProjectionFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage4_ndc.PerspectiveDivisionFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage5_screen.ViewPortTransformFilter;
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

        PushFilter sourceFilter = new SourceFilter();
        Renderer renderer = new Renderer(gc ,pd.getModelColor(), pd.getRenderingMode());

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // Model → View → Clip → NDC → Screen.

        // a) Gather filters
            // stage 1 filter Model
        PushFilter scaleFilter = new ScaleFilter(new Mat4(1)); // Scale * 1
        PushFilter rotationFilter = new RotationFilter(rotationMatrix);
        PushFilter translationFilter = new TranslationFilter(translationMatrix);
            // stage 2 filter View
        PushFilter viewTransformFilter = new ViewTransformFilter(pd.getViewTransform());
            // stage 3 filter Clip
        PushFilter projectionFilter = new ProjectionFilter(pd.getProjTransform());
            // stage 4 Perspective
        PushFilter perspectiveFilter = new PerspectiveDivisionFilter();
            // stage 5 Viewport to Screen
        PushFilter viewPortTransformFilter = new ViewPortTransformFilter(pd.getViewportTransform());

        // b)
            //stage 1 Model
        sourceFilter.setSuccessor(scaleFilter);
        scaleFilter.setSuccessor(rotationFilter);
        rotationFilter.setSuccessor(translationFilter);
            // stage 2 view
        translationFilter.setSuccessor(viewTransformFilter);
            // stage 3 filter Clip
        viewTransformFilter.setSuccessor(projectionFilter);
            //stage 4 perspective
        projectionFilter.setSuccessor(perspectiveFilter);
            //stage 5 viewport to screen
        perspectiveFilter.setSuccessor(viewPortTransformFilter);
            // at last render
        viewPortTransformFilter.setSuccessor(renderer);





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

//                    // Transform viewport to screen coordinates
//                    v1 = viewportMatrix.multiply(v1);
//                    v2 = viewportMatrix.multiply(v2);
//                    v3 = viewportMatrix.multiply(v3);
//
//                    // Draw each line either filled or wireframe render mode



            }
        };
    }
}