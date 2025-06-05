package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.RotationFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.ScaleFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage1_model.TranslationFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage2_view.ViewTransformFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage2_view.advanced.BackfaceCullingFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage2_view.advanced.DepthSortingFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage2_view.advanced.LightingFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage3_clip.ProjectionFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage4_ndc.PerspectiveDivisionFilter;
import at.fhv.sysarch.lab3.pipeline.filter.stage5_screen.ViewPortTransformFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;

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
        PushFilter backfaceCullingFilter = new BackfaceCullingFilter();
        PushFilter depthSortingFilter = new DepthSortingFilter();
        PushFilter lightingFilter = new LightingFilter();


        // b)

        // ---- START ----
        sourceFilter.setSuccessor(scaleFilter);

        // ---- MODEL ----
        scaleFilter.setSuccessor(rotationFilter);
        rotationFilter.setSuccessor(translationFilter);

        // ---- VIEW ----
        translationFilter.setSuccessor(viewTransformFilter);
        viewTransformFilter.setSuccessor(backfaceCullingFilter);
        backfaceCullingFilter.setSuccessor(depthSortingFilter);

        if (pd.isPerformLighting()) {
            // Lighting ON:
            // Model → View → Backface culling → Depth sorting → Lighting → Projection → Perspective division → Viewport → Render
            depthSortingFilter.setSuccessor(lightingFilter);
        } else {
            // Lighting OFF:
            // Model → View → Backface culling → Depth sorting → Projection → Perspective division → Viewport → Render
            depthSortingFilter.setSuccessor(viewPortTransformFilter);
        }



        // ---- CLIP ----
        depthSortingFilter.setSuccessor(projectionFilter);

        // ---- NDC ----
        projectionFilter.setSuccessor(perspectiveFilter);

        // ---- SCREEN ----
        perspectiveFilter.setSuccessor(viewPortTransformFilter);

        // ---- RENDER ----
        viewPortTransformFilter.setSuccessor(renderer);


        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {

                animationRotation += (float) (fraction * Math.toRadians(50));



                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.modelRotAxis

                // TODO compute updated model-view transformation

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline

                ((SourceFilter) sourceFilter).process(model);
            }
        };
    }
}