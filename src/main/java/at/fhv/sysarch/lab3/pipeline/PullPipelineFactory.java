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
import at.fhv.sysarch.lab3.pipeline.pull.stage5_screen.PullViewportTransformFilter;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Mat4;
import javafx.animation.AnimationTimer;

import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // MODEL → VIEW

        // Stage 1: Model Transform
        PullSourceFilter modelSource = new PullSourceFilter(); // You need to set model later
        PullScaleFilter scaleFilter = new PullScaleFilter(new Mat4(1));
        scaleFilter.setPredecessor(modelSource);

        PullRotationFilter rotationFilter = new PullRotationFilter(MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), 0));
        rotationFilter.setPredecessor(scaleFilter);

        PullTranslationFilter translationFilter = new PullTranslationFilter(pd.getModelTranslation());
        translationFilter.setPredecessor(rotationFilter);

        // Stage 2: View Transform and Advanced
        PullViewTransformFilter viewTransformFilter = new PullViewTransformFilter(pd.getViewTransform());
        viewTransformFilter.setPredecessor(translationFilter);

        PullBackfaceCullingFilter backfaceCullingFilter = new PullBackfaceCullingFilter();
        backfaceCullingFilter.setPredecessor(viewTransformFilter);

        PullDepthSortingFilter depthSortingFilter = new PullDepthSortingFilter();
        depthSortingFilter.setPredecessor(backfaceCullingFilter);

        PullColorFilter colorFilter = new PullColorFilter(pd.getModelColor());
        colorFilter.setPredecessor(depthSortingFilter);

        PullFilter<Pair<Face, Color>> pipelineOutput;

        if (pd.isPerformLighting()) {
            PullLightingFilter lightingFilter = new PullLightingFilter(pd.getLightPos().getUnitVector());
            lightingFilter.setPredecessor(colorFilter);
            pipelineOutput = lightingFilter;
        } else {
            pipelineOutput = colorFilter;
        }

        PullProjectionFilter projection = new PullProjectionFilter(pd.getProjTransform());
        projection.setPredecessor(pipelineOutput);

        PullPerspectiveDivisionFilter perspective = new PullPerspectiveDivisionFilter();
        perspective.setPredecessor(projection);

        PullViewportTransformFilter viewport = new PullViewportTransformFilter(pd.getViewportTransform());
        viewport.setPredecessor(perspective);

        PullFilter<Pair<Face, Color>> finalPipelineOutput = viewport;

        PullRenderer renderer = new PullRenderer(
                pd.getGraphicsContext(),
                pd.getRenderingMode(),
                finalPipelineOutput
        );



        return new AnimationRenderer(pd) {
            private float animationRotation = 0f;
            @Override
            protected void render(float fraction, Model model) {
                animationRotation += (float) (fraction * Math.toRadians(10));
                Mat4 newRotation = MatrixUtils.createRotationMatrix(pd.getModelRotAxis(), animationRotation);
                rotationFilter.setRotationMatrix(newRotation);

                depthSortingFilter.reset();

                modelSource.run(model);
                renderer.render();


            }
        };
    }
}