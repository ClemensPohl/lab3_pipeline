package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.pipeline.pull.PullRenderer;
import at.fhv.sysarch.lab3.pipeline.pull.PullSourceFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage1_model.PullRotationFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage1_model.PullScaleFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage1_model.PullTranslationFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.PullViewTransformFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullBackfaceCullingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullColorFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullDepthSortingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced.PullLightingFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage3_clip.PullProjectionFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage4_ndc.PullPerspectiveDivisionFilter;
import at.fhv.sysarch.lab3.pipeline.pull.stage5_screen.PullViewPortTransformFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import javafx.animation.AnimationTimer;

import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // INITIAL ROTATION
        Mat4 initialRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0);

        // -- MODEL TRANSFORM STAGE --
        PullFilter<Face> source = new PullSourceFilter(pd.getModel());
        PullFilter<Face> scaled = new PullScaleFilter(source, MatrixUtils.createScalingMatrix(new Vec3(1,1,1)));
        PullRotationFilter rotated = new PullRotationFilter(scaled, initialRot);
        PullFilter<Face> translated = new PullTranslationFilter(rotated, pd.getModelTranslation());

        // -- VIEW STAGE --
        PullFilter<Face> viewTransformed = new PullViewTransformFilter(translated, pd.getViewTransform());
        PullFilter<Face> culled = new PullBackfaceCullingFilter(viewTransformed);
        PullFilter<Face> sorted = new PullDepthSortingFilter(culled);

        // -- COLOR STAGE --
        PullFilter<Pair<Face, Color>> colored = new PullColorFilter(sorted, pd.getModelColor());

        PullFilter<Pair<Face, Color>> lit;
        if (pd.isPerformLighting()) {
            lit = new PullLightingFilter(colored, pd.getLightPos().getUnitVector());
        } else {
            lit = colored;
        }

        // -- CLIP / PROJECTION STAGE --
        PullFilter<Pair<Face, Color>> projected = new PullProjectionFilter(lit, pd.getProjTransform());

        // -- NDC STAGE --
        PullFilter<Pair<Face, Color>> perspectiveDivided = new PullPerspectiveDivisionFilter(projected);

        // -- SCREEN STAGE --
        PullFilter<Pair<Face, Color>> screenTransformed = new PullViewPortTransformFilter(perspectiveDivided, pd.getViewportTransform());

        // -- RENDERER --
        PullRenderer renderer = new PullRenderer(screenTransformed, pd.getGraphicsContext(), pd.getRenderingMode());




        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;

            @Override
            protected void render(float fraction, Model model) {
                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRot = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);
                rotated.setRotationMatrix(newRot);

                ((PullSourceFilter) source).reset();


                PullFilter<Face> culled = new PullBackfaceCullingFilter(viewTransformed);
                PullFilter<Face> sorted = new PullDepthSortingFilter(culled);

                PullFilter<Pair<Face, Color>> colored = new PullColorFilter(sorted, pd.getModelColor());
                PullFilter<Pair<Face, Color>> lit = pd.isPerformLighting()
                        ? new PullLightingFilter(colored, pd.getLightPos().getUnitVector())
                        : colored;

                PullFilter<Pair<Face, Color>> projected = new PullProjectionFilter(lit, pd.getProjTransform());
                PullFilter<Pair<Face, Color>> perspectiveDivided = new PullPerspectiveDivisionFilter(projected);
                PullFilter<Pair<Face, Color>> screenTransformed = new PullViewPortTransformFilter(perspectiveDivided, pd.getViewportTransform());

                PullRenderer renderer = new PullRenderer(screenTransformed, pd.getGraphicsContext(), pd.getRenderingMode());

                renderer.renderAll();
            }

        };
    }
}