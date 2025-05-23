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

        // Default PipeLine =>  Model → View → Clip → NDC → Screen
        PushFilter sourceFilter = new SourceFilter();
        PushFilter scaleFilter = new ScaleFilter(new Mat4(1));
        PushFilter rotationFilter = new RotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        PushFilter translationFilter = new TranslationFilter(pd.getModelTranslation());
        PushFilter viewTransformFilter = new ViewTransformFilter(pd.getViewTransform());
        PushFilter projectionFilter = new ProjectionFilter(pd.getProjTransform());
        PushFilter perspectiveFilter = new PerspectiveDivisionFilter();
        PushFilter viewPortTransformFilter = new ViewPortTransformFilter(pd.getViewportTransform());
        PushFilter renderer = new Renderer(pd.getGraphicsContext() ,pd.getModelColor(), pd.getRenderingMode());

        // Advanced Filtering


        // b)

        // ---- START ----
        sourceFilter.setSuccessor(scaleFilter);

        // ---- MODEL ----
        scaleFilter.setSuccessor(rotationFilter);
        rotationFilter.setSuccessor(translationFilter);

        // ---- VIEW ----
        // TODO 2. perform backface culling in VIEW SPACE
        // TODO 3. perform lighting in VIEW SPACE
        // TODO 4. perform depth sorting in VIEW SPACE
        if (pd.isPerformLighting()) {
            // Lighting ON:
            // Model → View → Backface culling → Depth sorting → Lighting → Projection → Perspective division → Viewport → Render
        } else {
            // Lighting OFF:
            // Model → View → Backface culling → Depth sorting → Projection → Perspective division → Viewport → Render
        }

        translationFilter.setSuccessor(viewTransformFilter);

        // ---- CLIP ----
        viewTransformFilter.setSuccessor(projectionFilter);

        // ---- NDC ----
        projectionFilter.setSuccessor(perspectiveFilter);

        // ---- SCREEN ----
        perspectiveFilter.setSuccessor(viewPortTransformFilter);

        // ---- RENDER ----
        viewPortTransformFilter.setSuccessor(renderer);


        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {

                animationRotation += (float) (fraction * Math.toRadians(50));

                ((SourceFilter) sourceFilter).process(model);
            }
        };
    }
}