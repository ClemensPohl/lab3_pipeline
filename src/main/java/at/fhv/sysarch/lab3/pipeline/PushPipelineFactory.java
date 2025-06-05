package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.*;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model.RotationFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model.ScaleFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model.TranslationFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.ViewTransformFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced.BackfaceCullingFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced.ColorFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced.DepthSortingFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced.LightingFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage3_clip.ProjectionFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage4_ndc.PerspectiveDivisionFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.stage5_screen.ViewPortTransformFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // Default PipeLine =>  Model → View → Clip → NDC → Screen
        SourceFilter sourceFilter = new SourceFilter();

        PushFilter<Face, Face> scaleFilter = new ScaleFilter(new Mat4(1));
        RotationFilter rotationFilter = new RotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        PushFilter<Face, Face> translationFilter = new TranslationFilter(pd.getModelTranslation());

        PushFilter<Face, Face> viewTransformFilter = new ViewTransformFilter(pd.getViewTransform());

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> projectionFilter = new ProjectionFilter(pd.getProjTransform());

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> perspectiveFilter = new PerspectiveDivisionFilter();

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> viewPortTransformFilter = new ViewPortTransformFilter(pd.getViewportTransform());

        PushFilter<Pair<Face, Color>, Pair<Face, Color>> renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());

        // Advanced Filtering
        PushFilter<Face, Face> backfaceCullingFilter = new BackfaceCullingFilter();
        PushFilter<Face, Face> depthSortingFilter = new DepthSortingFilter();
        PushFilter<Face, Pair<Face, Color>> colorFilter = new ColorFilter(pd.getModelColor());
        PushFilter<Pair<Face, Color>, Pair<Face, Color>> lightingFilter = new LightingFilter(pd.getLightPos().getUnitVector());


        // b)

        // ---- START ----
        sourceFilter.setSuccessor(scaleFilter);

        // ---- MODEL ----
        scaleFilter.setSuccessor(rotationFilter);
        rotationFilter.setSuccessor(translationFilter);

        // ---- VIEW ----
        translationFilter.setSuccessor(viewTransformFilter);
            // --- Advanced
            viewTransformFilter.setSuccessor(backfaceCullingFilter);
            backfaceCullingFilter.setSuccessor(depthSortingFilter);
            depthSortingFilter.setSuccessor(colorFilter);

        if (pd.isPerformLighting()) {
            // Lighting ON:
            // Model → View → Backface culling → Depth sorting → Lighting → Projection → Perspective division → Viewport → Render
            colorFilter.setSuccessor(lightingFilter);
            lightingFilter.setSuccessor(projectionFilter);
        } else {
            // Lighting OFF:
            // Model → View → Backface culling → Depth sorting → Projection → Perspective division → Viewport → Render
            colorFilter.setSuccessor(projectionFilter);
        }

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

                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);

                rotationFilter.setRotationMatrix(newRot);

                sourceFilter.run(model);
            }
        };
    }
}